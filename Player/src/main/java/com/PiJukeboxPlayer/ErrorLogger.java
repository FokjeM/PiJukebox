package com.PiJukeboxPlayer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 */
public class ErrorLogger {
    
    private final static Path logFile = FileSystems.getDefault().getPath("D:\\Logs\\error.out");;
    private LocalDateTime ldt;
    private final List<String> errorLines;
    
    public ErrorLogger(LocalDateTime ldtInit) throws IOException {
        if(!Files.exists(logFile)) {
            Files.write(logFile, new byte[0], StandardOpenOption.CREATE);
        }
        this.errorLines = new ArrayList<>();
        String firstLines = "**********New session started at: " + ldtInit.toString() + "**********";
        errorLines.add(firstLines);
        writeLogLines();
    }
    
    public boolean writeLog(Exception ex, boolean fatal) {
        ldt = LocalDateTime.now();
        if(fatal) {
            errorLines.add("~~~~~FATAL ERROR occurred at: " + ldt.toString() + "~~~~~");
        } else {
            errorLines.add("-----non-fatal error occurred at: " + ldt.toString() + "-----");
        }
        errorLines.add("\tError: " + ex.toString());
        errorLines.add("\tMessage: " + ex.getMessage());
        return writeLogLines();
    }
    
    private boolean writeLogLines(){
        try {
            if(!Files.exists(logFile)) {
                //Might have been removed during runtime
                Files.write(logFile, new byte[0], StandardOpenOption.CREATE);
            }
            Files.write(logFile, errorLines, StandardOpenOption.APPEND);
            if(errorLines.isEmpty()) {
                return false;
            } else if(errorLines.get(0).contains("~~~~~")){
                System.err.println("FATAL ERROR!");
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
            System.err.println("FATAL ERROR!");
            System.err.println("\t" + e.toString());
            System.err.println("\t" + e.getMessage());
            System.exit(2);
            return false;
        }
    }
    
    public boolean debugLine(String msg){
        errorLines.add("-----" + msg);
        return writeLogLines();
    }
}
