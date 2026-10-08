
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class MtkActivity extends Activity {
    TextView txtScatter, txtFw, txtLabel, txtDetail, log; ProgressBar bar;
    String scatter="/sdcard/Download/MT6765_scatter.txt", fw="/sdcard/Download/";
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_mtk);
        txtScatter=findViewById(R.id.txtScatter); txtFw=findViewById(R.id.txtFw); txtLabel=findViewById(R.id.txtProgressLabel); txtDetail=findViewById(R.id.txtProgressDetail); log=findViewById(R.id.logView); bar=findViewById(R.id.progressBar);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        txtScatter.setText(scatter); txtFw.setText(fw);
        findViewById(R.id.btnBrowseScatter).setOnClickListener(v->pick(true));
        findViewById(R.id.btnBrowseFw).setOnClickListener(v->pick(false));
        findViewById(R.id.btnFlash).setOnClickListener(v->runProg("FLASH MTK","mtk w --scatter "+scatter+" --firmware "+fw+" --bypass"));
        findViewById(R.id.btnBypass).setOnClickListener(v->runProg("BYPASS AUTH","mtk payload || python3 -m mtk payload"));
        findViewById(R.id.btnReadback).setOnClickListener(v->runProg("READBACK","mtk r boot /sdcard/Download/boot.img"));
        findViewById(R.id.btnFormat).setOnClickListener(v->runProg("FORMAT","mtk e frp,userdata"));
    }
    void pick(boolean isScatter){ File cur=new File("/sdcard/Download"); File[] arr=cur.listFiles(); if(arr==null) return; String[] n=new String[arr.length]; for(int i=0;i<arr.length;i++) n[i]=arr[i].getName(); new android.app.AlertDialog.Builder(this).setTitle("Select").setItems(n,(d,w)->{ if(isScatter){ scatter=arr[w].getAbsolutePath(); txtScatter.setText(scatter); } else { fw=arr[w].isDirectory()?arr[w].getAbsolutePath():arr[w].getParent(); txtFw.setText(fw); } }).show(); }
    void runProg(String t,String c){ bar.setProgress(10); txtLabel.setText(t); log.append("\n> $ "+c+"\n"); new Thread(()->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String l; int pr=10; while((l=r.readLine())!=null){ pr+=4; if(pr>90) pr=90; int pp=pr; String ll=l; runOnUiThread(()->{ bar.setProgress(pp); txtDetail.setText(pp+"%"); log.append(ll+"\n"); }); } p.waitFor(); runOnUiThread(()->{ bar.setProgress(100); txtLabel.setText(t+" DONE"); }); }catch(Exception e){ runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n")); } }).start(); }
}
