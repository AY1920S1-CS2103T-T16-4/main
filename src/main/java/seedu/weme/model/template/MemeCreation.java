package seedu.weme.model.template;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

/**
 * Represents a meme creation session.
 */
public class MemeCreation {

    private final BufferedImage image;

    public MemeCreation(Template template) throws IOException {
        Path templateImagePath = template.getImagePath().getFilePath().toAbsolutePath();
        image = ImageIO.read(templateImagePath.toFile());
    }

    public BufferedImage getCurrentImage() {
        return image;
    }

    public void addText(MemeText text) {
        // TODO
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof MemeCreation)) {
            return false;
        }

        MemeCreation otherSession = (MemeCreation) other;
        return image.equals(otherSession.image);
    }
}
