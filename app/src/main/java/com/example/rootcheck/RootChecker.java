package com.example.rootcheck;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.example.rootcheck.Const.BINARY_SU;

public class RootChecker {

    private final Context mContext;
    private boolean loggingEnabled = true;

    boolean Root_Flag = false;

    public RootChecker(Context mContext) {
        this.mContext = mContext;
    }

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
     * Using the PackageManager, check for a list of well known root apps. @link {Const.knownRootAppsPackages}
     * @param additionalRootManagementApps - array of additional packagenames to search for
     * @return true if one of the apps it's installed
     */

    public boolean Check_RootManagementApps(String[] additionalRootManagementApps) {

        // Create a list of package names to iterate over from constants any others provided
        ArrayList<String> packages = new ArrayList<>(Arrays.asList(Const.knownRootAppsPackages));
        if (additionalRootManagementApps!=null && additionalRootManagementApps.length>0){
            packages.addAll(Arrays.asList(additionalRootManagementApps));
        }

        return isAnyPackageFromListInstalled(packages);
    }

    /**
     * Check if any package in the list is installed ( this package is root app )
     * @param packages - list of packages to search for
     * @return true if any of the packages are installed
     */
    private boolean isAnyPackageFromListInstalled(List<String> packages){
        boolean result = false;

        PackageManager pm = mContext.getPackageManager();

        for (String packageName : packages) {
            try {
                // Root app detected
                pm.getPackageInfo(packageName, 0);
                result = true;
            } catch (PackageManager.NameNotFoundException e) {
                // Exception thrown, package is not installed into the system
            }
        }

        return result;
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
