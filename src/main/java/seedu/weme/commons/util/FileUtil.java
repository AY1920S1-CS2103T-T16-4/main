package seedu.weme.commons.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import seedu.weme.model.DirectoryPath;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.UniqueMemeList;
import seedu.weme.model.util.MemeUtil;


/**
 * Writes and reads files
 */
public class FileUtil {

    public static final String MESSAGE_READ_FILE_FAILURE = "Error encountered while reading the file %s";
    public static final String MESSAGE_COPY_FAILURE_SOURCE_DOES_NOT_EXIST = "Copy failed: source file does not exist";
    public static final String MESSAGE_EXPORT_FAILURE_INVALID_FILENAME = "Export failed: Invalid File Directory Given";
    public static final String MESSAGE_EXPORT_FAILURE_UNKNOWN_ERROR = "Export failed: Internal Error Encountered: ";


    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via {@link Paths#get(String)},
     * otherwise returns false.
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ipe) {
            return false;
        }
        return true;
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path file) throws IOException {
        if (Files.exists(file)) {
            return;
        }

        createParentDirsOfFile(file);

        Files.createFile(file);
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path file) throws IOException {
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

    /**
     * Returns a randomly generated UUID String
     */
    public static String generateUuidString() {
        return UUID.randomUUID().toString();
    }

    /**
     * Copies the file from a directory to another directory.
     *
     * @param from the source
     * @param to   the destination
     * @throws IOException if the source file does not exist.
     */
    public static void copy(Path from, Path to) throws IOException {
        if (isFileExists(from)) {
            createParentDirsOfFile(to);
            Files.copy(from, to);
        } else {
            throw new IOException(MESSAGE_COPY_FAILURE_SOURCE_DOES_NOT_EXIST);
        }
    }

    /**
     * Copies the file from a given InputStream to a file path.
     *
     * @param from the source
     * @param to   the destination
     * @throws IOException if the copy failed
     */
    public static void copy(InputStream from, Path to) throws IOException {
        createParentDirsOfFile(to);
        Files.copy(from, to);
    }

    /**
     * Exports a list of memes to a given directory.
     *
     * @param memeList list of memes to be emported.
     * @param directoryPath directory path for the memes to be exported to.
     * @throws IOException error encountered while exporting.
     */
    public static void export(UniqueMemeList memeList, DirectoryPath directoryPath) throws IOException {
        try {
            for (Meme meme : memeList) {
                String fileName = meme.getFilePath().getFilePath().getFileName().toString();
                String fileExportPath = directoryPath.getFilePath() + "/" + fileName;
                if (isValidPath(fileExportPath)) {
                    FileUtil.copy(meme.getFilePath().getFilePath(), Paths.get(fileExportPath));
                } else {
                    throw new IOException(MESSAGE_EXPORT_FAILURE_INVALID_FILENAME);
                }
            }
        } catch (IOException e) {
            throw new IOException(MESSAGE_EXPORT_FAILURE_UNKNOWN_ERROR + e.toString());
        }
    }

    /**
     * Loads a meme from a given directory into the application.
     *
     * @param importList Internal List for memes to be loaded in.
     * @param directoryPath Path containing memes to load.
     */
    public static void load(UniqueMemeList importList, DirectoryPath directoryPath) {
        final File folder = new File(directoryPath.toString());
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                load(importList, new DirectoryPath(fileEntry.getPath())); // recursive call
            } else {
                if (isFileExists(fileEntry.toPath()) && isValidImageExtension(getExtension(fileEntry.toPath()).get())) {
                    Meme meme = MemeUtil.generateImportMeme(fileEntry.getPath());
                    importList.add(meme);
                }
            }
        }
    }

    private static boolean isValidImageExtension(String extension) {
        return extension.equals("png") || extension.equals("jpg");
    }

    /**
     * Gets the extension of {@code Path}.
     *
     * @param path the {@code Path} to extract the extension from
     * @return the extension if present, or {@code Optional#empty()} if there is none
     */
    public static Optional<String> getExtension(Path path) {
        String pathString = path.toString();
        if (pathString.contains(".")) {
            return Optional.of(pathString.substring(pathString.lastIndexOf(".") + 1));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets the filename of a path represented by a string.
     * @param pathString the String to extract the file name from
     * @return the filename
     */
    public static String getFileName(String pathString) {
        return Paths.get(pathString).getFileName().toString();
    }
}
