package com.pijukebox.Player;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class that implements easily callable logging features to a local file. The
 * logs in this class are written as plaintext files and stored in the same
 * directory as this JVM assumes to run in. A different path may be specified,
 * in which case it is important to specify an <b>existing</b> directory or a
 * file with <b>at most one inexistent parent</b> folder.
 *
 * This method and its class do not ensure that any directory exists, except for
 * the directory of this class, maybe with a subdirectory Logs. Classes that use
 * a {@link com.pijukebox.controller.PiJukeboxPlayer.Player Player} are encouraged to set a mechanism
 * in place that creates the missing directories.
 *
 * If the specified directory or its direct parent exists, the existence of the
 * file is guaranteed, so long as the JVM instance executing this method has
 * read and write permissions on the directory. Starting this with elevated
 * privileges is therefore <b>strongly</b> recommended.
 */
public class ErrorLogger {

    private static Path LOG_FILE;
    private LocalDateTime ldt;
    private final List<String> errorLines;

    /**
     * Instantiate an ErrorLogger object that logs to a file called "queue.out"
     * that is located in a subdirectory Logs. This subdirectory can be found in
     * the same place as the executed jar or class file. The log will be signed
     * with the date and time from the system.
     *
     * @throws IOException
     */
    public ErrorLogger() throws IOException {
        this(LocalDateTime.now(), initPath());
    }

    /**
     * Private constructor that allows specifying a custom
     * {@link java.time.LocalDateTime LocalDateTime} and a different
     * {@link java.nio.file.Path Path} to initialize with. Appends the default
     * filename to the Path if only a directory was given.
     *
     * @param ldtInit LocalDateTime to initialize with.
     * @param override Path to override the default with
     * @throws IOException
     */
    private ErrorLogger(LocalDateTime ldtInit, Path override) throws IOException {
        //Check if the file is present, if not we create it.
        if (!override.toFile().isFile()) {
            LOG_FILE = FileSystems.getDefault().getPath(override.toString(), "Logs", "error.out");
        } else {
            LOG_FILE = override;
        }
        if (!ErrorLogger.createLogFile(LOG_FILE)) {
            throw new IOException("LOG_FILE Could not be created at " + LOG_FILE.toString());
        }
        this.errorLines = new ArrayList<>();
        String firstLines = "**********New session started at: " + ldtInit.toString() + "**********";
        errorLines.add(firstLines);
        writeLogLines();
    }

    /**
     * Create a Path object pointing to the folder where the initial class or
     * package file containing this ErrorLogger resides. If any pathParts are
     * given, the directories will be created as specified using
     * {@link java.nio.file.Files#createDirectories(java.nio.file.Path, java.nio.file.attribute.FileAttribute...) Files.createDirectories(path/pathParts)},
     * as long as a valid path can be created from it.
     *
     * @param pathParts A variable amount of Strings for creating
     * subdirectories.
     * @return an absolute Path based on the location of this class or package.
     */
    static Path initPath(String... pathParts) {
        Path initPath;
        String pathString;
        try {
            //Get the location where we're being run from and strip any protocol/source
            //identifiers like file:///, file:/, jar:/ and war:/ and split off the
            //package structure of jar and war files; these are Java ¿directories?
            pathString = FileSystems.getDefault().getPath(URLDecoder.decode(ErrorLogger.class.getResource("").toString(), "UTF-8").replaceAll("[a-zA-Z]{2,}:[/]*", "").split("\\!")[0]).getParent().toString();
            StringBuilder createDirs = new StringBuilder();
            createDirs.append(pathString);
            for (String s : pathParts) {
                //Make sure the path is separated correctly
                createDirs.append(File.separator);
                //Assume users or other programmers will ruin everything!
                //Add all directories, strip any leading or trailing (back)slashes
                createDirs.append(s.replaceAll("\\|/", ""));
            }
            Files.createDirectories(FileSystems.getDefault().getPath(createDirs.toString()));
        } catch (Exception ex) { //Seems desperate, but a few unchecked exceptions might happen here
            pathString = System.getProperty("user.dir");
        }
        initPath = FileSystems.getDefault().getPath(pathString, pathParts);
        return initPath;
    }

    public boolean writeLog(Exception ex, boolean fatal) {
        ldt = LocalDateTime.now();
        if (fatal) {
            errorLines.add("~~~~~FATAL ERROR occurred at: " + ldt.toString() + "~~~~~");
        } else {
            errorLines.add("-----non-fatal error occurred at: " + ldt.toString() + "-----");
        }
        errorLines.add("\tError: " + ex.toString());
        errorLines.add("\tMessage: " + ex.getMessage());
        ex.printStackTrace(System.err);
        return writeLogLines();
    }

    private boolean writeLogLines() {
        try {
            Files.write(LOG_FILE, errorLines, StandardOpenOption.APPEND);
            if (errorLines.isEmpty()) {
                return false;
            } else if (errorLines.get(0).contains("~~~~~")) {
                for (Iterator<String> it = errorLines.iterator(); it.hasNext();) {
                    String s = it.next();
                    System.err.println(s);
                }
                //Mortal Kombat voice: FATALITY!
                System.exit(1);
            }
            errorLines.clear();
            return true;
        } catch (IOException e) {
            //We can't write to a log file... All hell is raining down, huh?
            System.err.println("FATAL ERROR!");
            System.err.println("\t" + e.toString());
            System.err.println("\t" + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(2);
            return false;
        }
    }

    private static boolean createLogFile(Path p) {
        try {
            if (!Files.exists(p)) {
                //If Logs/error.out doesn't exist, check Logs/
                if (!Files.exists(p.getParent())) {
                    //Might have been removed during or after runtime
                    Files.createDirectories(p.getParent());
                }
                Files.createFile(p.toAbsolutePath());
            }
        } catch (IOException io) {
            io.printStackTrace(System.err);
            return false;
        }
        return true;
    }
}
