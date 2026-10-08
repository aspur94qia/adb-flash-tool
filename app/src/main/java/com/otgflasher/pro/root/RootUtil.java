package com.otgflasher.pro.root;
import java.io.*;
public class RootUtil {
    public static boolean isRooted(){
        try{
            Process p=Runtime.getRuntime().exec("su -c id");
            BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line=r.readLine();
            p.waitFor();
            return line!=null && line.contains("uid=0");
        }catch(Exception e){ return false; }
    }
    public static String execRoot(String cmd){
        StringBuilder out=new StringBuilder();
        try{
            Process p=Runtime.getRuntime().exec(new String[]{"su","-c",cmd});
            BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader er=new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String l;
            while((l=r.readLine())!=null) out.append(l).append("\n");
            while((l=er.readLine())!=null) out.append(l).append("\n");
            p.waitFor();
        }catch(Exception e){ out.append("ERR: "+e.getMessage()); }
        return out.toString();
    }
    public static boolean execRootBool(String cmd){
        try{
            Process p=Runtime.getRuntime().exec(new String[]{"su","-c",cmd});
            int exit=p.waitFor();
            return exit==0;
        }catch(Exception e){ return false; }
    }
}
