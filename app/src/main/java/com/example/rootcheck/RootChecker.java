package com.example.rootcheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.example.rootcheck.Const.BINARY_SU;

public class RootChecker {
    boolean Root_Flag = false;

    String test_flag(){
        return "RootChecker Flag";
    }

    boolean Get_Root_Flag(){
        return this.Root_Flag;
    }


    /**
     * A variation on the checking for SU, this attempts a 'which su'
     * @return true if su found
     */
    public boolean Check_Su_Exists(){
        Process process = null;
        try{
            process = Runtime.getRuntime().exec(new String[]{"which", BINARY_SU});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return in.readLine() != null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    /**
     *  check for this existence of "su" binary
     * @return true if "su" found
     */
    public boolean Check_For_Su_Binary(){
        String[] pathsArray = Const.getPaths();

        String filename = "su";

        boolean result = false;

        for (String path : pathsArray) {
            String completePath = path + filename;
            File f = new File(path, filename);
            boolean fileExists = f.exists();
            if (fileExists) {
                //binary detected!
                result = true;
            }
        }
        return result;
    }

    /**
     *  check for this existence of "busybox" binary
     * @return true if "su" found
     */
    public boolean Check_For_BusyBox(){
        String[] pathsArray = Const.getPaths();

        String filename = "busybox";

        boolean result = false;

        for (String path : pathsArray) {
            String completePath = path + filename;
            File f = new File(path, filename);
            boolean fileExists = f.exists();
            if (fileExists) {
                //binary detected!
                result = true;
            }
        }
        return result;
    }


    /**
     *  Exec "getprop" command
     * @return propety list
     */
    private String[] propsReader() {
        try {
            InputStream inputstream = Runtime.getRuntime().exec("getprop").getInputStream();
            if (inputstream == null) return null;
            String propVal = new Scanner(inputstream).useDelimiter("\\A").next();
            return propVal.split("\n");
        } catch (IOException | NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Checks for several system properties for
     * @return - true if dangerous props are found
     */
    public boolean checkForDangerousProps() {

        final Map<String, String> dangerousProps = new HashMap<>();
        dangerousProps.put("ro.debuggable", "1");
        dangerousProps.put("ro.secure", "0");

        boolean result = false;

        String[] lines = propsReader();

        if (lines == null){
            // Could not read, assume false;
            return false;
        }

        for (String line : lines) {
            System.out.println("sukhoon " + line);
            for (String key : dangerousProps.keySet()) {
                if (line.contains(key)) {
                    String badValue = dangerousProps.get(key);
                    badValue = "[" + badValue + "]";
                    if (line.contains(badValue)) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }
}
