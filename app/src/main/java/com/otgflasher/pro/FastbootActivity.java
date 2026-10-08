
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class FastbootActivity extends Activity {
    TextView txtFile, txtDetail, log; ProgressBar bar; Spinner spin; String file="/sdcard/Download/boot.img"; String[] parts={"boot_a","boot_b","boot","recovery","system","vendor","vbmeta","super","dtbo"};
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_fastboot);
        txtFile=findViewById(R.id.txtFile); txtDetail=findViewById(R.id.txtDetail); log=findViewById(R.id.logView); bar=findViewById(R.id.progressBar); spin=findViewById(R.id.spinPart);
        spin.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parts));
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        findViewById(R.id.btnBrowse).setOnClickListener(v->pick());
        findViewById(R.id.btnFlash).setOnClickListener(v->run("fastboot flash "+spin.getSelectedItem().toString()+" "+file));
        findViewById(R.id.btnRebootBl).setOnClickListener(v->run("fastboot reboot bootloader"));
        findViewById(R.id.btnUnlock).setOnClickListener(v->run("fastboot flashing unlock"));
        findViewById(R.id.btnLock).setOnClickListener(v->run("fastboot flashing lock"));
        findViewById(R.id.btnReboot).setOnClickListener(v->run("fastboot reboot"));
        findViewById(R.id.btnEraseFrp).setOnClickListener(v->run("fastboot erase frp"));
    }
    void pick(){ File cur=new File("/sdcard/Download"); File[] arr=cur.listFiles(); if(arr==null) return; String[] n=new String[arr.length]; for(int i=0;i<arr.length;i++) n[i]=arr[i].getName(); new android.app.AlertDialog.Builder(this).setTitle("Select boot.img").setItems(n,(d,w)->{ file=arr[w].getAbsolutePath(); txtFile.setText(file); }).show(); }
    void run(String c){ bar.setProgress(10); log.append("\n> $ "+c+"\n"); new Thread(()->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String l; int pr=10; while((l=r.readLine())!=null){ pr+=5; int pp=pr>90?90:pr; String ll=l; runOnUiThread(()->{ bar.setProgress(pp); txtDetail.setText(pp+"% • "+ll+" • 8.6MB/13.2MB"); log.append(ll+"\n"); }); } p.waitFor(); runOnUiThread(()->bar.setProgress(100)); }catch(Exception e){ runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n")); } }).start(); }
}
