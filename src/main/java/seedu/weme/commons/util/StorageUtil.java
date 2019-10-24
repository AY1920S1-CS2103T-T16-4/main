package seedu.weme.commons.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.weme.model.DirectoryPath;

/**
 * Utility class for Import and Export.
 */
public class StorageUtil {

    /**
     * Loads a meme from a given directory into the application.
     *
     * @param directoryPath Path containing memes to load.
     * @return List of loadable paths.
     */
    public static List<Path> loadImages(DirectoryPath directoryPath) throws IOException {
        return FileUtil.loadImagePath(directoryPath);
    }

    /**
     * Exports a list of memes to a given directory.
     *
     * @param pathList list of memes to be emported.
     * @param exportPath directory path for the memes to be exported to.
     * @throws IOException error encountered while exporting.
     */
    public static void export(List<Path> pathList, Path exportPath) throws IOException {
        FileUtil.copyFiles(pathList, exportPath);
    }

}
