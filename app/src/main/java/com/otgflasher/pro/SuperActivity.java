
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class SuperActivity extends Activity {
    TextView txtScatter, txtFw, txtLabel, txtDetail, log; ProgressBar bar;
    String file="/sdcard/Download/super.img";
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_super);
        txtScatter=findViewById(R.id.txtScatter); txtFw=findViewById(R.id.txtFw); txtLabel=findViewById(R.id.txtProgressLabel); txtDetail=findViewById(R.id.txtProgressDetail); log=findViewById(R.id.logView); bar=findViewById(R.id.progressBar);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        txtScatter.setText(file);
        findViewById(R.id.btnBrowseScatter).setOnClickListener(v->pick());
        findViewById(R.id.btnBrowseFw).setOnClickListener(v->pick());
        findViewById(R.id.btnFlash).setOnClickListener(v->runProg("UNPACK SUPER","lpunpack  /sdcard/Download/ /sdcard/Download/out/super.img"));
        findViewById(R.id.btnBypass).setOnClickListener(v->runProg("BYPASS","echo bypass SuperActivity"));
        findViewById(R.id.btnReadback).setOnClickListener(v->runProg("READ","echo read"));
        findViewById(R.id.btnFormat).setOnClickListener(v->runProg("FORMAT","echo format"));
    }
    void pick(){ File cur=new File("/sdcard/Download"); File[] arr=cur.listFiles(); if(arr==null) return; String[] n=new String[arr.length]; for(int i=0;i<arr.length;i++) n[i]=arr[i].getName(); new android.app.AlertDialog.Builder(this).setTitle("Select").setItems(n,(d,w)->{ file=arr[w].getAbsolutePath(); txtScatter.setText(file); }).show(); }
    void runProg(String t,String c){ bar.setProgress(10); txtLabel.setText(t); log.append("\n> $ "+c+"\n"); new Thread(()->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String l; int pr=10; while((l=r.readLine())!=null){ pr+=5; if(pr>90) pr=90; int pp=pr; String ll=l; runOnUiThread(()->{ bar.setProgress(pp); txtDetail.setText(pp+"%"); log.append(ll+"\n"); }); } p.waitFor(); runOnUiThread(()->{ bar.setProgress(100); txtLabel.setText(t+" DONE"); }); }catch(Exception e){ runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n")); } }).start(); }
}
