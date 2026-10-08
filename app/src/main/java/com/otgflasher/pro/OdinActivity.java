
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class OdinActivity extends Activity {
    TextView txtFile, txtDetail, log; ProgressBar bar; String file="/sdcard/Download/";
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_odin);
        txtFile=findViewById(R.id.txtFile); txtDetail=findViewById(R.id.txtDetail); log=findViewById(R.id.logView); bar=findViewById(R.id.progressBar);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        findViewById(R.id.btnBrowse).setOnClickListener(v->pick());
        try{ findViewById(R.id.btn0).setOnClickListener(v->run("heimdall flash --AP /sdcard/Download/AP.tar.md5 --BL /sdcard/Download/BL.tar.md5 --CP /sdcard/Download/CP.tar.md5 --CSC /sdcard/Download/CSC.tar.md5")); }catch(Exception e){}
        try{ findViewById(R.id.btn1).setOnClickListener(v->run("adb shell pm disable-user com.samsung.android.kgclient")); }catch(Exception e){}
        try{ findViewById(R.id.btn2).setOnClickListener(v->run("adb shell am broadcast -a com.samsung.android.fmm.action.FRP_BYPASS")); }catch(Exception e){}
        try{ findViewById(R.id.btn3).setOnClickListener(v->run("heimdall print-pit")); }catch(Exception e){}
        try{ findViewById(R.id.btn4).setOnClickListener(v->run("")); }catch(Exception e){}
        try{ findViewById(R.id.btn5).setOnClickListener(v->run("")); }catch(Exception e){}
    }
    void pick(){ File cur=new File("/sdcard/Download"); File[] arr=cur.listFiles(); if(arr==null) return; String[] n=new String[arr.length]; for(int i=0;i<arr.length;i++) n[i]=arr[i].getName(); new android.app.AlertDialog.Builder(this).setTitle("Select").setItems(n,(d,w)->{ file=arr[w].getAbsolutePath(); txtFile.setText(file); }).show(); }
    void run(String c){ bar.setProgress(10); log.append("\n> $ "+c+"\n"); new Thread(()->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String l; int pr=10; while((l=r.readLine())!=null){ pr+=5; int pp=pr>90?90:pr; String ll=l; runOnUiThread(()->{ bar.setProgress(pp); txtDetail.setText(pp+"% • "+ll); log.append(ll+"\n"); }); } p.waitFor(); runOnUiThread(()->bar.setProgress(100)); }catch(Exception e){ runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n")); } }).start(); }
}
