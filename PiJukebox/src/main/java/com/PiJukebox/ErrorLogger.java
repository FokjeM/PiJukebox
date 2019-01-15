package com.PiJukebox;

import java.io.IOException;
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
    
    private static Path logFile;
    private LocalDateTime ldt;
    private final List<String> errorLines;
    
    public ErrorLogger(LocalDateTime ldtInit) {
        this.errorLines = new ArrayList<>();
        String firstLines = "**********New session started at: " + ldtInit.toString() + "**********";
        errorLines.add(0, firstLines);
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
                Files.write(logFile, new byte[0], StandardOpenOption.CREATE);
            }
            Files.write(logFile, errorLines, StandardOpenOption.WRITE);
            if(errorLines.get(0).contains("~~~~~")){
                System.err.print("FATAL ERROR!");
                for (Iterator<String> it = errorLines.iterator(); it.hasNext();) {
                    String s = it.next();
                    System.err.print(s);
                }
                //Mortal Kombat voice: FATALITY!
                System.exit(1);
            }
            errorLines.clear();
            return true;
        } catch (IOException e) {
            System.err.print("FATAL ERROR!");
            System.err.print("\t" + e.toString());
            System.err.print("\t" + e.getMessage());
            System.exit(2);
            return false;
        }
    }
    
    public boolean writeFFPlayLog(String msg){
        errorLines.add("+--------------+");
        errorLines.add("FFPlay only outputs on stderr;\r\n\tOutput:");
        errorLines.add(msg);
        return writeLogLines();
    }
}
