package com.otgflasher.pro.root;
import java.io.*;
public class RootUtil {
    public static boolean isRoot(){
        try{ Process p=Runtime.getRuntime().exec("su -c id"); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String s=r.readLine(); p.waitFor(); return s!=null && s.contains("uid=0"); }catch(Exception e){return false;}
    }
    public static String su(String c){
        StringBuilder o=new StringBuilder(); try{ Process p=Runtime.getRuntime().exec(new String[]{"su","-c",c}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String l; while((l=r.readLine())!=null) o.append(l).append("\n"); p.waitFor(); }catch(Exception e){ o.append(e.getMessage()); } return o.toString();
    }
}
