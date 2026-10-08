
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import android.content.Intent;import android.net.Uri;import java.io.*;import java.util.*;
public class MainActivity extends Activity {
    TextView log, fileTxt, statusTxt; EditText editCmd; Spinner spinPart;
    String filePath="/sdcard/Download/boot.img";
    String[] partitions={"boot","recovery","system","vendor","vbmeta","vbmeta_system","super","dtbo","userdata","cache"};
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); fileTxt=findViewById(R.id.fileTxt); statusTxt=findViewById(R.id.statusTxt);
        editCmd=findViewById(R.id.editCmd); spinPart=findViewById(R.id.spinPart);
        ArrayAdapter<String> ad=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, partitions); ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spinPart.setAdapter(ad);

        findViewById(R.id.btnPick).setOnClickListener(v->{ Intent i=new Intent(Intent.ACTION_GET_CONTENT); i.setType("*/*"); startActivityForResult(i,99); });
        findViewById(R.id.btnDevices).setOnClickListener(v-> runShell("echo '=== ADB DEVICES ===' && adb devices 2>&1 || echo 'adb not found, coba root'; echo '\n=== FASTBOOT DEVICES ===' && fastboot devices 2>&1 || echo 'fastboot not found'; echo '\n=== USB LIST ===' && ls /dev/bus/usb/ 2>&1; echo '\n=== PROP ===' && getprop | grep -i usb"));
        findViewById(R.id.btnRebootBL).setOnClickListener(v-> runShell("adb reboot bootloader || su -c 'reboot bootloader'"));
        findViewById(R.id.btnRebootRec).setOnClickListener(v-> runShell("adb reboot recovery || su -c 'reboot recovery'"));
        findViewById(R.id.btnRebootSys).setOnClickListener(v-> runShell("adb reboot || fastboot reboot || su -c 'reboot'"));
        findViewById(R.id.btnEraseData).setOnClickListener(v-> runShell("fastboot erase userdata || fastboot -w"));
        findViewById(R.id.btnEraseCache).setOnClickListener(v-> runShell("fastboot erase cache"));
        findViewById(R.id.btnFormat).setOnClickListener(v-> runShell("fastboot format userdata && fastboot format cache"));
        findViewById(R.id.btnUnlock).setOnClickListener(v-> runShell("fastboot oem unlock || fastboot flashing unlock"));
        findViewById(R.id.btnLock).setOnClickListener(v-> runShell("fastboot oem lock || fastboot flashing lock"));
        findViewById(R.id.btnDisableVbmeta).setOnClickListener(v-> runShell("fastboot --disable-verity --disable-verification flash vbmeta vbmeta.img; echo 'Flash vbmeta kosong buat disable verity'"));
        findViewById(R.id.btnFlash).setOnClickListener(v->{
            if(filePath.isEmpty()){ toast("Pilih file dulu bos"); return; }
            String part=spinPart.getSelectedItem().toString();
            runShell("echo 'Flashing "+part+" <- "+filePath+"' && fastboot flash "+part+" "+filePath+" || echo 'Coba pake su dd' && su -c 'dd if="+filePath+" of=/dev/block/bootdevice/by-name/"+part+" && echo SUCCESS FLASH "+part+"'");
        });
        findViewById(R.id.btnFlashAll).setOnClickListener(v-> runShell("echo 'BATCH FLASH MODE - taro semua img di /sdcard/Download/' && ls /sdcard/Download/*.img && echo '\n--- FLASH ALL ---' && for f in /sdcard/Download/*.img; do echo flashing $f; fastboot flash $(basename $f .img) $f; done"));
        findViewById(R.id.btnRunCmd).setOnClickListener(v->{ String cmd=editCmd.getText().toString(); if(cmd.isEmpty()){ toast("Isi command dulu"); return; } runShell(cmd); });
    }
    void runShell(String cmd){
        log.append("\n> $ "+cmd+"\n");
        new Thread(()->{
            try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String line; StringBuilder out=new StringBuilder(); while((line=r.readLine())!=null){ out.append(line).append("\n"); } p.waitFor(); String res=out.toString(); runOnUiThread(()->{ log.append(res+"\n--- DONE ---\n"); statusTxt.setText("LAST: "+cmd.substring(0,Math.min(30,cmd.length()))); }); }catch(Exception e){ runOnUiThread(()-> log.append("ERROR: "+e.getMessage()+"\n")); }
        }).start();
    }
    @Override protected void onActivityResult(int c,int r,Intent d){ super.onActivityResult(c,r,d); if(c==99 && r==RESULT_OK && d!=null){ Uri uri=d.getData(); filePath=uri.getPath(); if(uri.getPath().contains("/document/")){ filePath="/sdcard/Download/"+uri.getLastPathSegment(); } fileTxt.setText("File: "+uri.getLastPathSegment()+"\n"+filePath); log.append("\nFile dipilih: "+filePath+"\n"); editCmd.setText("fastboot flash "+spinPart.getSelectedItem().toString()+" "+filePath); } }
    void toast(String s){ Toast.makeText(this,s,Toast.LENGTH_SHORT).show(); }
}
