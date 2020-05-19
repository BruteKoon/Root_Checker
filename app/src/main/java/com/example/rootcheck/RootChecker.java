package com.example.rootcheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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


}
