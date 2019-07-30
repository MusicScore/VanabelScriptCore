package net.vanabel.vanabelscriptcore.files;

import net.vanabel.vanabelscriptcore.utils.Validator;

import java.io.File;
import java.io.IOException;

public class FileLoader {

    public FileLoader(String filepath) throws IOException {
        File toFile = new File(Validator.isNotNull(filepath, "Filepath cannot be null!"));

        if (!toFile.isFile()) {
            throw new IOException("Filepath \"" + toFile.getPath() + "\" does not point to a valid file!");
        }
        if (!toFile.canRead()) {
            throw new IOException("File \"" + toFile.getPath() + "\" is not readable! Is it already open?");
        }
    }
}
