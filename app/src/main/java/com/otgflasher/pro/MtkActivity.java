
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class MtkActivity extends Activity {
    TextView txtScatter, txtFw, txtAuth, txtDetail, log; ProgressBar bar; String scatter="/sdcard/Download/MT6765_scatter.txt", fw="/sdcard/Download/", auth="/sdcard/Download/auth_sv5.auth";
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_mtk);
        txtScatter=findViewById(R.id.txtScatter); txtFw=findViewById(R.id.txtFw); txtAuth=findViewById(R.id.txtAuth); txtDetail=findViewById(R.id.txtDetail); log=findViewById(R.id.logView); bar=findViewById(R.id.progressBar);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        findViewById(R.id.btnBrowseScatter).setOnClickListener(v->pick(0)); findViewById(R.id.btnBrowseFw).setOnClickListener(v->pick(1)); findViewById(R.id.btnBrowseAuth).setOnClickListener(v->pick(2));
        findViewById(R.id.btnFlash).setOnClickListener(v->run("mtk w --scatter "+scatter+" --firmware "+fw+" --bypass"));
        findViewById(R.id.btnReadback).setOnClickListener(v->run("mtk r boot /sdcard/Download/boot.img"));
        findViewById(R.id.btnFormat).setOnClickListener(v->run("mtk e frp,userdata"));
        findViewById(R.id.btnBypass).setOnClickListener(v->run("mtk payload || python3 -m mtk payload"));
    }
    void pick(int t){ File cur=new File("/sdcard/Download"); File[] arr=cur.listFiles(); if(arr==null) return; String[] n=new String[arr.length]; for(int i=0;i<arr.length;i++) n[i]=arr[i].getName(); new android.app.AlertDialog.Builder(this).setTitle("Select").setItems(n,(d,w)->{ String p=arr[w].getAbsolutePath(); if(t==0){ scatter=p; txtScatter.setText(p); } else if(t==1){ fw=arr[w].isDirectory()?p:arr[w].getParent(); txtFw.setText(fw); } else { auth=p; txtAuth.setText(p); } }).show(); }
    void run(String c){ bar.setProgress(10); log.append("\n> $ "+c+"\n"); new Thread(()->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String l; int pr=10; while((l=r.readLine())!=null){ pr+=4; int pp=pr>90?90:pr; String ll=l; runOnUiThread(()->{ bar.setProgress(pp); txtDetail.setText(pp+"% • "+ll); log.append(ll+"\n"); }); } p.waitFor(); runOnUiThread(()->bar.setProgress(100)); }catch(Exception e){ runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n")); } }).start(); }
}
