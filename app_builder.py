import os
for p in ["app/src/main/java/com/otgflasher/pro","app/src/main/res/layout","app/src/main/res/values"]:
    os.makedirs(p,exist_ok=True)
open("settings.gradle","w").write("pluginManagement{\n repositories{ google(); mavenCentral(); gradlePluginPortal() }\n}\nplugins{ id 'com.android.application' version '8.2.2' apply false }\ninclude ':app'\n")
open("build.gradle","w").write("allprojects{ repositories{ google(); mavenCentral() } }\n")
open("gradle.properties","w").write("android.useAndroidX=true\n")
open("app/build.gradle","w").write("plugins{ id 'com.android.application' }\nandroid{ compileSdk 34; namespace 'com.otgflasher.pro'; defaultConfig{ applicationId 'com.otgflasher.pro'; minSdk 26; targetSdk 34; versionCode 3; versionName '3.0' } }\ndependencies{ implementation 'androidx.appcompat:appcompat:1.6.1' }\n")
open("app/src/main/res/values/strings.xml","w").write('<resources><string name="app_name">OTG Flasher Pro</string></resources>')
open("app/src/main/res/values/themes.xml","w").write('<resources><style name="Theme.OTGFlasher" parent="android:Theme.Material.Light.NoActionBar"/></resources>')
open("app/src/main/AndroidManifest.xml","w").write('<manifest xmlns:android="http://schemas.android.com/apk/res/android"><uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/><uses-permission android:name="android.permission.USB_PERMISSION"/><application android:theme="@style/Theme.OTGFlasher" android:label="OTG Flasher Pro"><activity android:name=".MainActivity" android:exported="true"><intent-filter><action android:name="android.intent.action.MAIN"/><category android:name="android.intent.category.LAUNCHER"/></intent-filter></activity></application></manifest>')

open("app/src/main/res/layout/activity_main.xml","w").write('''<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent" android:layout_height="match_parent" android:background="#F5F5F5">
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="match_parent" android:padding="12dp">

<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="OTG FLASHER PRO v3 - FULL ADB/FASTBOOT" android:textSize="14sp" android:textStyle="bold" android:gravity="center" android:padding="8dp" android:background="#212121" android:textColor="#00FF00"/>

<TextView android:id="@+id/statusTxt" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="STATUS: Belum colok OTG" android:padding="8dp" android:background="#333" android:textColor="#0F0" android:layout_marginTop="8dp" android:fontFamily="monospace" android:textSize="12sp"/>

<TextView android:id="@+id/fileTxt" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="File: Belum ada" android:padding="8dp" android:background="#222" android:textColor="#FF0" android:layout_marginTop="6dp"/>

<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="8dp">
<Button android:id="@+id/btnPick" android:layout_width="0dp" android:layout_weight="1" android:layout_height="50dp" android:text="PILIH FILE IMG" android:textSize="11sp"/>
<Button android:id="@+id/btnDevices" android:layout_width="0dp" android:layout_weight="1" android:layout_height="50dp" android:text="CEK DEVICE" android:textSize="11sp" android:layout_marginStart="4dp"/>
</LinearLayout>

<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="--- REBOOT MENU ---" android:textSize="12sp" android:textStyle="bold" android:gravity="center" android:layout_marginTop="12dp"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content">
<Button android:id="@+id/btnRebootBL" android:layout_width="0dp" android:layout_weight="1" android:layout_height="45dp" android:text="REBOOT BOOTLOADER" android:textSize="9sp"/>
<Button android:id="@+id/btnRebootRec" android:layout_width="0dp" android:layout_weight="1" android:layout_height="45dp" android:text="REBOOT RECOVERY" android:textSize="9sp" android:layout_marginStart="4dp"/>
<Button android:id="@+id/btnRebootSys" android:layout_width="0dp" android:layout_weight="1" android:layout_height="45dp" android:text="REBOOT SYSTEM" android:textSize="9sp" android:layout_marginStart="4dp"/>
</LinearLayout>

<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="--- FLASH PARTITION ---" android:textSize="12sp" android:textStyle="bold" android:gravity="center" android:layout_marginTop="12dp"/>
<Spinner android:id="@+id/spinPart" android:layout_width="match_parent" android:layout_height="50dp" android:background="#FFF"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="4dp">
<Button android:id="@+id/btnFlash" android:layout_width="0dp" android:layout_weight="1" android:layout_height="60dp" android:text="FLASH PARTITION" android:backgroundTint="#D32F2F" android:textSize="12sp"/>
<Button android:id="@+id/btnFlashAll" android:layout_width="0dp" android:layout_weight="1" android:layout_height="60dp" android:text="FLASH BATCH" android:backgroundTint="#FF9800" android:textSize="12sp" android:layout_marginStart="4dp"/>
</LinearLayout>

<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="--- WIPE / ERASE / UNLOCK ---" android:textSize="12sp" android:textStyle="bold" android:gravity="center" android:layout_marginTop="12dp"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content">
<Button android:id="@+id/btnEraseData" android:layout_width="0dp" android:layout_weight="1" android:layout_height="45dp" android:text="ERASE DATA" android:textSize="9sp"/>
<Button android:id="@+id/btnEraseCache" android:layout_width="0dp" android:layout_weight="1" android:layout_height="45dp" android:text="ERASE CACHE" android:textSize="9sp" android:layout_marginStart="4dp"/>
<Button android:id="@+id/btnFormat" android:layout_width="0dp" android:layout_weight="1" android:layout_height="45dp" android:text="FORMAT DATA" android:textSize="9sp" android:layout_marginStart="4dp"/>
</LinearLayout>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="4dp">
<Button android:id="@+id/btnUnlock" android:layout_width="0dp" android:layout_weight="1" android:layout_height="45dp" android:text="UNLOCK BL" android:textSize="9sp"/>
<Button android:id="@+id/btnLock" android:layout_width="0dp" android:layout_weight="1" android:layout_height="45dp" android:text="LOCK BL" android:textSize="9sp" android:layout_marginStart="4dp"/>
<Button android:id="@+id/btnDisableVbmeta" android:layout_width="0dp" android:layout_weight="1" android:layout_height="45dp" android:text="DISABLE VBMETA" android:textSize="8sp" android:layout_marginStart="4dp"/>
</LinearLayout>

<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="--- MANUAL COMMAND ---" android:textSize="12sp" android:textStyle="bold" android:gravity="center" android:layout_marginTop="12dp"/>
<EditText android:id="@+id/editCmd" android:layout_width="match_parent" android:layout_height="50dp" android:hint="contoh: fastboot flash boot boot.img" android:background="#FFF" android:padding="8dp"/>
<Button android:id="@+id/btnRunCmd" android:layout_width="match_parent" android:layout_height="50dp" android:text="JALANKAN COMMAND" android:backgroundTint="#212121" android:layout_marginTop="4dp"/>

<TextView android:id="@+id/logView" android:layout_width="match_parent" android:layout_height="500dp" android:text="LOG READY:\n> Colok OTG ke HP target\n> Klik CEK DEVICE\n> Pilih file img\n> Pilih partition &amp; FLASH\n\nFITUR:\n- Flash boot/recovery/system/vendor/vbmeta/super\n- Erase/Format\n- Unlock Bootloader\n- Reboot menu\n- Manual fastboot/adb command\n" android:background="#111" android:textColor="#0F0" android:padding="8dp" android:fontFamily="monospace" android:textSize="11sp" android:layout_marginTop="8dp"/>

</LinearLayout>
</ScrollView>
''')

open("app/src/main/java/com/otgflasher/pro/MainActivity.java","w").write(r'''
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
''')
print("builder v3 FULL")
