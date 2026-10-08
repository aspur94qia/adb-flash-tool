
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class EdlActivity extends Activity {
    TextView txtFile, txtDetail, log; ProgressBar bar; String file="/sdcard/Download/";
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_edl);
        txtFile=findViewById(R.id.txtFile); txtDetail=findViewById(R.id.txtDetail); log=findViewById(R.id.logView); bar=findViewById(R.id.progressBar);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        findViewById(R.id.btnBrowse).setOnClickListener(v->pick());
        try{ findViewById(R.id.btn0).setOnClickListener(v->run("edl --loader prog_firehose_ddr.elf --printgpt")); }catch(Exception e){}
        try{ findViewById(R.id.btn1).setOnClickListener(v->run("edl --loader prog_firehose_ddr.elf --rawprogram rawprogram.xml --patch patch.xml")); }catch(Exception e){}
        try{ findViewById(R.id.btn2).setOnClickListener(v->run("edl reset")); }catch(Exception e){}
        try{ findViewById(R.id.btn3).setOnClickListener(v->run("dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/modemst1.img")); }catch(Exception e){}
        try{ findViewById(R.id.btn4).setOnClickListener(v->run("edl --erase frp")); }catch(Exception e){}
        try{ findViewById(R.id.btn5).setOnClickListener(v->run("edl --sahara")); }catch(Exception e){}
        try{ findViewById(R.id.btn6).setOnClickListener(v->run("")); }catch(Exception e){}
        try{ findViewById(R.id.btn7).setOnClickListener(v->run("")); }catch(Exception e){}
    }
    void pick(){ File cur=new File("/sdcard/Download"); File[] arr=cur.listFiles(); if(arr==null) return; String[] n=new String[arr.length]; for(int i=0;i<arr.length;i++) n[i]=arr[i].getName(); new android.app.AlertDialog.Builder(this).setTitle("Select").setItems(n,(d,w)->{ file=arr[w].getAbsolutePath(); txtFile.setText(file); }).show(); }
    void run(String c){ bar.setProgress(10); log.append("\n> "+c+"\n"); new Thread(()->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String l; int pr=10; while((l=r.readLine())!=null){ pr+=5; int pp=pr>90?90:pr; String ll=l; runOnUiThread(()->{ bar.setProgress(pp); txtDetail.setText(pp+" percent "+ll); log.append(ll+"\n"); }); } p.waitFor(); runOnUiThread(()->bar.setProgress(100)); }catch(Exception e){ runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n")); } }).start(); }
}
