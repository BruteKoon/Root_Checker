package com.example.rootcheck;

import java.util.ArrayList;
import java.util.Arrays;

public class Const {
    public static final String BINARY_SU = "su";

    public static final String[] suPaths ={
            "/data/local/",
            "/data/local/bin/",
            "/data/local/xbin/",
            "/sbin/",
            "/su/bin/",
            "/system/bin/",
            "/system/bin/.ext/",
            "/system/bin/failsafe/",
            "/system/sd/xbin/",
            "/system/usr/we-need-root/",
            "/system/xbin/",
            "/cache/",
            "/data/",
            "/dev/"
    };

    static String[] getPaths(){
        ArrayList<String> paths = new ArrayList<>(Arrays.asList(suPaths));

        String sysPaths = System.getenv("PATH");

        // If we can't get the path variable just return the static paths
        if (sysPaths == null || "".equals(sysPaths)){
            return paths.toArray(new String[0]);
        }

        for (String path : sysPaths.split(":")){

            if (!path.endsWith("/")){
                path = path + '/';
            }

            if (!paths.contains(path)){
                paths.add(path);
            }
        }

        return paths.toArray(new String[0]);
    }
}
