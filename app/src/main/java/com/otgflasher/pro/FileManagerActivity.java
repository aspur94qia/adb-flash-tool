
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;import java.util.*;
public class FileManagerActivity extends Activity {
    TextView txtPath, txtSelected; ListView listFiles;
    File cur=new File("/sdcard/Download");
    java.util.List<File> files=new java.util.ArrayList<>();
    File selected=null;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_file_manager);
        txtPath=findViewById(R.id.txtPath);
        txtSelected=findViewById(R.id.txtSelected);
        listFiles=findViewById(R.id.listFiles);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        findViewById(R.id.btnUp).setOnClickListener(v->{ if(cur.getParentFile()!=null){ cur=cur.getParentFile(); list(); }});
        findViewById(R.id.btnExtract).setOnClickListener(v->doExtract());
        findViewById(R.id.btnCreateZip).setOnClickListener(v->doZip());
        findViewById(R.id.btnDelete).setOnClickListener(v->{ if(selected!=null){ selected.delete(); list(); }});
        listFiles.setOnItemClickListener((a,vw,pos,id)->{
            File f=files.get(pos);
            if(f.isDirectory()){ cur=f; list(); }
            else { selected=f; txtSelected.setText("SELECTED: "+f.getName()+" "+f.length()/1024/1024+"MB"); }
        });
        list();
    }
    void list(){
        File[] arr=cur.listFiles();
        files.clear();
        java.util.List<String> names=new java.util.ArrayList<>();
        if(arr!=null){
            java.util.Arrays.sort(arr);
            for(File f:arr){
                if(f.getName().startsWith(".")) continue;
                files.add(f);
                String t=f.isDirectory()?"DIR ":"FILE ";
                names.add(t+f.getName()+" "+(f.isDirectory()?"":f.length()/1024/1024+"MB"));
            }
        }
        txtPath.setText(cur.getAbsolutePath()+" ("+files.size()+")");
        listFiles.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names));
    }
    void doExtract(){
        if(selected==null){ Toast.makeText(this,"Pilih file zip",Toast.LENGTH_SHORT).show(); return; }
        String cmd="cd '"+cur.getAbsolutePath()+"' && unzip -o '"+selected.getAbsolutePath()+"' 2>&1 || tar -xzf '"+selected.getAbsolutePath()+"' 2>&1 || echo Extract via tar";
        runCmd(cmd);
    }
    void doZip(){
        if(selected==null){ Toast.makeText(this,"Pilih file",Toast.LENGTH_SHORT).show(); return; }
        String cmd="cd '"+cur.getAbsolutePath()+"' && zip -r '"+selected.getName()+".zip' '"+selected.getName()+"' 2>&1";
        runCmd(cmd);
    }
    void runCmd(String cmd){
        txtSelected.setText("Running: "+cmd);
        new Thread(()->{
            try{
                Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd});
                BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream()));
                StringBuilder sb=new StringBuilder(); String l;
                while((l=r.readLine())!=null) sb.append(l).append("\n");
                pr.waitFor();
                String res=sb.toString();
                runOnUiThread(()->{ txtSelected.setText(res); list(); });
            }catch(Exception e){ runOnUiThread(()->txtSelected.setText("ERR: "+e.getMessage())); }
        }).start();
    }
}
