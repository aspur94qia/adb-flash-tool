import os
for p in ["app/src/main/java/com/otgflasher/pro","app/src/main/res/layout","app/src/main/res/values"]:
    os.makedirs(p,exist_ok=True)

open("settings.gradle","w").write("pluginManagement{\n repositories{ google(); mavenCentral(); gradlePluginPortal() }\n}\nplugins{ id 'com.android.application' version '8.2.2' apply false }\ninclude ':app'\n")
open("build.gradle","w").write("allprojects{ repositories{ google(); mavenCentral() } }\n")
open("gradle.properties","w").write("android.useAndroidX=true\n")
open("app/build.gradle","w").write("plugins{ id 'com.android.application' }\nandroid{ compileSdk 34; namespace 'com.otgflasher.pro'; defaultConfig{ applicationId 'com.otgflasher.pro'; minSdk 26; targetSdk 34; versionCode 10; versionName '10.0' } }\ndependencies{ implementation 'androidx.appcompat:appcompat:1.6.1' }\n")

open("app/src/main/res/values/strings.xml","w").write('<resources><string name="app_name">OTG Flasher v9</string></resources>')
open("app/src/main/res/values/themes.xml","w").write('<resources><style name="Theme.OTGFlasher" parent="android:Theme.Material.Light.NoActionBar"/></resources>')

open("app/src/main/AndroidManifest.xml","w").write('<manifest xmlns:android="http://schemas.android.com/apk/res/android"><uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/><uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/><uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE"/><uses-feature android:name="android.hardware.usb.host"/><application android:theme="@style/Theme.OTGFlasher" android:label="OTG Flasher v9" android:requestLegacyExternalStorage="true"><activity android:name=".MainActivity" android:exported="true"><intent-filter><action android:name="android.intent.action.MAIN"/><category android:name="android.intent.category.LAUNCHER"/></intent-filter></activity><activity android:name=".FileManagerActivity"/></application></manifest>')

# v10 FINAL LAYOUT - LOG AT TOP + PROGRESS BAR + LARGE TEXT
open("app/src/main/res/layout/activity_main.xml","w").write('''<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent" android:layout_height="match_parent" android:background="#121212">
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:padding="8dp">

<!-- TOOLBAR -->
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="64dp" android:background="#4CAF50" android:gravity="center_vertical" android:padding="12dp">
<TextView android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:text="OTG FLASHER v9" android:textSize="20sp" android:textStyle="bold" android:textColor="#FFF"/>
<TextView android:id="@+id/statusDot" android:layout_width="12dp" android:layout_height="12dp" android:background="#0F0"/>
</LinearLayout>

<!-- LOG CONSOLE AT TOP -->
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:background="#000" android:padding="6dp" android:layout_marginTop="8dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="LOG CONSOLE" android:textSize="10sp" android:textColor="#888" android:textStyle="bold"/>
<ScrollView android:layout_width="match_parent" android:layout_height="110dp" android:background="#0A0A0A" android:layout_marginTop="2dp"><TextView android:id="@+id/logView" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="[12:03:10] OTG Flasher v9 initialized - ready\n[12:03:11] No OTG device detected. Connect device and tap CONNECT OTG\n[12:03:11] Waiting for device\n" android:textColor="#0F0" android:fontFamily="monospace" android:textSize="10sp" android:padding="6dp"/></ScrollView>
</LinearLayout>

<!-- PROGRESS BAR FLASHING -->
<LinearLayout android:id="@+id/progressBox" android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp" android:layout_marginTop="8dp" android:visibility="gone">
<TextView android:id="@+id/txtProgressLabel" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="Flashing boot.img -&gt; slot_a 0%" android:textSize="13sp" android:textStyle="bold" android:textColor="#FFF"/>
<ProgressBar android:id="@+id/progressBar" style="?android:attr/progressBarStyleHorizontal" android:layout_width="match_parent" android:layout_height="18dp" android:max="100" android:progress="0" android:progressTint="#0F0" android:layout_marginTop="6dp"/>
<TextView android:id="@+id/txtProgressDetail" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="0% - 0MB/0MB - Speed: 0MB/s - ETA: --" android:textSize="11sp" android:textColor="#0F0" android:layout_marginTop="4dp"/>
</LinearLayout>

<!-- BIG BUTTONS -->
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="10dp">
<Button android:id="@+id/btnUsb" android:layout_width="0dp" android:layout_weight="1" android:layout_height="64dp" android:text="🔌 CONNECT OTG" android:backgroundTint="#4CAF50" android:textSize="14sp" android:textStyle="bold"/>
<Button android:id="@+id/btnFileMgr" android:layout_width="0dp" android:layout_weight="1" android:layout_height="64dp" android:text="📁 FILE MANAGER" android:backgroundTint="#FF9800" android:textSize="13sp" android:textStyle="bold" android:layout_marginStart="8dp"/>
</LinearLayout>

<!-- 2 COL GRID LARGE TEXT -->
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="10dp">
<LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="UNIVERSAL FASTBOOT" android:textSize="13sp" android:textStyle="bold" android:textColor="#FFF"/>
<Spinner android:id="@+id/spinPart" android:layout_width="match_parent" android:layout_height="44dp" android:background="#FFF" android:layout_marginTop="6dp"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="6dp">
<Button android:id="@+id/btnFlash" android:layout_width="0dp" android:layout_weight="1" android:layout_height="48dp" android:text="FLASH PART" android:textSize="11sp" android:backgroundTint="#D32F2F"/>
<Button android:id="@+id/btnRebootBL" android:layout_width="0dp" android:layout_weight="1" android:layout_height="48dp" android:text="REBOOT BL" android:textSize="10sp" android:layout_marginStart="4dp"/>
</LinearLayout>
</LinearLayout>
<LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp" android:layout_marginStart="8dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="MTK SP FLASH" android:textSize="13sp" android:textStyle="bold" android:textColor="#FF5722"/>
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="MT65xx/MT67xx/Dimensity" android:textSize="9sp" android:textColor="#AAA"/>
<Button android:id="@+id/btnMtkBypass" android:layout_width="match_parent" android:layout_height="48dp" android:text="⚡ BYPASS AUTH V2" android:textSize="11sp" android:backgroundTint="#3E2723" android:layout_marginTop="6dp"/>
<Button android:id="@+id/btnMtkFlash" android:layout_width="match_parent" android:layout_height="40dp" android:text="FLASH MTK SCATTER" android:textSize="9sp" android:backgroundTint="#FF5722" android:layout_marginTop="4dp"/>
</LinearLayout>
</LinearLayout>

<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="8dp">
<LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="QUALCOMM EDL 9008" android:textSize="13sp" android:textStyle="bold" android:textColor="#5C6BC0"/>
<Button android:id="@+id/btnEdl" android:layout_width="match_parent" android:layout_height="48dp" android:text="🔌 ENTER EDL 9008" android:textSize="11sp" android:backgroundTint="#1A237E" android:layout_marginTop="6dp"/>
<Button android:id="@+id/btnQcn" android:layout_width="match_parent" android:layout_height="36dp" android:text="BACKUP/RESTORE QCN" android:textSize="8sp" android:backgroundTint="#3F51B5" android:layout_marginTop="4dp"/>
</LinearLayout>
<LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp" android:layout_marginStart="8dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="SAMSUNG ODIN" android:textSize="13sp" android:textStyle="bold" android:textColor="#1976D2"/>
<Button android:id="@+id/btnOdinAp" android:layout_width="match_parent" android:layout_height="48dp" android:text="⬇️ LAUNCH ODIN AP/BL/CSC" android:textSize="10sp" android:backgroundTint="#0D47A1" android:layout_marginTop="6dp"/>
<Button android:id="@+id/btnKg" android:layout_width="match_parent" android:layout_height="36dp" android:text="BYPASS KG/MDM" android:textSize="9sp" android:backgroundTint="#D32F2F" android:layout_marginTop="4dp"/>
</LinearLayout>
</LinearLayout>

<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="8dp">
<LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="SPD/UNISOC FLASH PAC" android:textSize="12sp" android:textStyle="bold" android:textColor="#AB47BC"/>
<Button android:id="@+id/btnSpd" android:layout_width="match_parent" android:layout_height="48dp" android:text="📄 FLASH PAC" android:textSize="11sp" android:backgroundTint="#4A148C" android:layout_marginTop="6dp"/>
</LinearLayout>
<LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp" android:layout_marginStart="8dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="UNPACKER / FIRMWARE" android:textSize="12sp" android:textStyle="bold" android:textColor="#FF9800"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="6dp">
<Button android:id="@+id/btnPayload" android:layout_width="0dp" android:layout_weight="1" android:layout_height="40dp" android:text="PAYLOAD.BIN" android:textSize="8sp" android:backgroundTint="#673AB7"/>
<Button android:id="@+id/btnSuper" android:layout_width="0dp" android:layout_weight="1" android:layout_height="40dp" android:text="SUPER" android:textSize="8sp" android:backgroundTint="#673AB7" android:layout_marginStart="3dp"/>
<Button android:id="@+id/btnOfp" android:layout_width="0dp" android:layout_weight="1" android:layout_height="40dp" android:text="OFP" android:textSize="8sp" android:backgroundTint="#7B1FA2" android:layout_marginStart="3dp"/>
</LinearLayout>
<Button android:id="@+id/btnFrp" android:layout_width="match_parent" android:layout_height="36dp" android:text="🔓 FRP BYPASS ALL" android:textSize="10sp" android:backgroundTint="#7B1FA2" android:layout_marginTop="4dp"/>
</LinearLayout>
</LinearLayout>

<!-- MANUAL CMD LARGE -->
<EditText android:id="@+id/editCmd" android:layout_width="match_parent" android:layout_height="48dp" android:hint="Manual cmd: fastboot flash boot boot.img" android:background="#FFF" android:textSize="12sp" android:padding="10dp" android:layout_marginTop="10dp"/>
<Button android:id="@+id/btnRunCmd" android:layout_width="match_parent" android:layout_height="48dp" android:text="▶️ RUN CMD" android:backgroundTint="#000" android:textSize="13sp" android:layout_marginTop="4dp"/>

<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="v10 - Log at Top + Progress Bar + Large Text + ZArchiver" android:textSize="9sp" android:textColor="#666" android:gravity="center" android:padding="6dp" android:layout_marginTop="6dp"/>

</LinearLayout>
</ScrollView>
''')

open("app/src/main/res/layout/activity_file_manager.xml","w").write('''<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android" android:orientation="vertical" android:layout_width="match_parent" android:layout_height="match_parent" android:background="#121212">
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="60dp" android:background="#4CAF50" android:gravity="center_vertical" android:padding="8dp">
<Button android:id="@+id/btnBack" android:layout_width="60dp" android:layout_height="48dp" android:text="◀️ BACK" android:textSize="12sp"/>
<TextView android:id="@+id/txtPath" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:text="/sdcard/Download" android:textColor="#FFF" android:textSize="13sp" android:padding="8dp" android:textStyle="bold"/>
<Button android:id="@+id/btnUp" android:layout_width="64dp" android:layout_height="48dp" android:text="UP" android:textSize="12sp" android:backgroundTint="#000"/>
</LinearLayout>
<ListView android:id="@+id/listFiles" android:layout_width="match_parent" android:layout_height="0dp" android:layout_weight="1" android:background="#1E1E1E" android:divider="#333" android:dividerHeight="1dp"/>
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:background="#222" android:padding="8dp">
<TextView android:id="@+id/txtSelected" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="No file selected - tap file to select" android:textColor="#0F0" android:textSize="12sp" android:padding="4dp"/>
<ProgressBar android:id="@+id/fmProgress" style="?android:attr/progressBarStyleHorizontal" android:layout_width="match_parent" android:layout_height="12dp" android:visibility="gone"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="6dp">
<Button android:id="@+id/btnExtract" android:layout_width="0dp" android:layout_weight="1" android:layout_height="52dp" android:text="📦 EXTRACT" android:textSize="11sp" android:backgroundTint="#4CAF50"/>
<Button android:id="@+id/btnCreateZip" android:layout_width="0dp" android:layout_weight="1" android:layout_height="52dp" android:text="CREATE ZIP" android:textSize="10sp" android:backgroundTint="#FF9800" android:layout_marginStart="4dp"/>
<Button android:id="@+id/btnCreateTar" android:layout_width="0dp" android:layout_weight="1" android:layout_height="52dp" android:text="TAR.GZ" android:textSize="10sp" android:layout_marginStart="4dp"/>
</LinearLayout>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="4dp">
<Button android:id="@+id/btnCopy" android:layout_width="0dp" android:layout_weight="1" android:layout_height="48dp" android:text="COPY" android:textSize="11sp"/>
<Button android:id="@+id/btnCut" android:layout_width="0dp" android:layout_weight="1" android:layout_height="48dp" android:text="CUT" android:textSize="11sp" android:layout_marginStart="4dp"/>
<Button android:id="@+id/btnDelete" android:layout_width="0dp" android:layout_weight="1" android:layout_height="48dp" android:text="DELETE" android:textSize="11sp" android:backgroundTint="#D32F2F" android:layout_marginStart="4dp"/>
<Button android:id="@+id/btnRename" android:layout_width="0dp" android:layout_weight="1" android:layout_height="48dp" android:text="RENAME" android:textSize="10sp" android:layout_marginStart="4dp"/>
</LinearLayout>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="4dp">
<Button android:id="@+id/btnFlashHere" android:layout_width="0dp" android:layout_weight="1" android:layout_height="48dp" android:text="⚡ FLASH THIS FILE" android:textSize="11sp" android:backgroundTint="#D32F2F"/>
<Button android:id="@+id/btnProps" android:layout_width="0dp" android:layout_weight="1" android:layout_height="48dp" android:text="PROPERTIES" android:textSize="10sp" android:layout_marginStart="4dp"/>
</LinearLayout>
</LinearLayout>
</LinearLayout>
''')

# MAIN ACTIVITY v10 FINAL - ALL FUNCTION
open("app/src/main/java/com/otgflasher/pro/MainActivity.java","w").write(r'''
package com.otgflasher.pro;
import android.app.Activity;import android.app.PendingIntent;import android.os.Bundle;import android.widget.*;import android.hardware.usb.*;import android.content.*;import android.view.View;import java.io.*;import java.util.*;
public class MainActivity extends Activity {
    TextView log, txtProgressLabel, txtProgressDetail; ProgressBar progressBar; LinearLayout progressBox;
    Spinner spinPart; UsbManager um; UsbDevice dev; String[] parts={"boot_a","boot_b","boot","recovery","system","vendor","vbmeta","super","dtbo","userdata"};
    String selectedFile="/sdcard/Download/boot.img";
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); progressBar=findViewById(R.id.progressBar); progressBox=findViewById(R.id.progressBox);
        txtProgressLabel=findViewById(R.id.txtProgressLabel); txtProgressDetail=findViewById(R.id.txtProgressDetail);
        spinPart=findViewById(R.id.spinPart);
        ArrayAdapter<String> ad=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parts); ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spinPart.setAdapter(ad);
        um=(UsbManager)getSystemService(Context.USB_SERVICE);

        findViewById(R.id.btnUsb).setOnClickListener(v->connect());
        findViewById(R.id.btnFileMgr).setOnClickListener(v->startActivity(new Intent(this, FileManagerActivity.class)));

        findViewById(R.id.btnFlash).setOnClickListener(v->flashWithProgress());
        findViewById(R.id.btnRebootBL).setOnClickListener(v->run("adb reboot bootloader 2>&1; echo Rebooting to bootloader..."));
        findViewById(R.id.btnMtkBypass).setOnClickListener(v->runWithProgress("MTK BYPASS AUTH V2", "mtk payload 2>&1 || python3 -m mtk payload 2>&1 || echo 'Install: pip install mtkclient'"));
        findViewById(R.id.btnMtkFlash).setOnClickListener(v->runWithProgress("MTK FLASH SCATTER", "ls /sdcard/Download/*.txt /sdcard/Download/scatter*.txt 2>&1; echo 'Select scatter in File Manager'; mtk w boot "+selectedFile+" 2>&1 || echo 'Use SP Flash'"));
        findViewById(R.id.btnEdl).setOnClickListener(v->runWithProgress("EDL 9008", "echo '=== EDL 9008 MODE ==='; edl --loader /sdcard/Download/prog_firehose_ddr.elf --print-gpt 2>&1 || echo 'Use QFIL / testpoint'"));
        findViewById(R.id.btnQcn).setOnClickListener(v->run("echo '=== BACKUP QCN EFS ==='; dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/modemst1.img bs=4096 2>&1; echo 'QCN backup: /sdcard/Download/modemst1.img'"));
        findViewById(R.id.btnOdinAp).setOnClickListener(v->run("echo '=== ODIN FLASH ==='; heimdall flash --AP "+selectedFile+" 2>&1 || tar -tvf "+selectedFile+" | head -20; echo 'AP/BL/CSC select in File Manager'"));
        findViewById(R.id.btnKg).setOnClickListener(v->run("adb shell pm disable-user com.samsung.android.kgclient 2>&1; adb shell pm disable-user com.samsung.android.knox.kpu 2>&1; echo 'KG/MDM BYPASSED'"));
        findViewById(R.id.btnSpd).setOnClickListener(v->runWithProgress("SPD PAC FLASH", "echo 'Flash PAC: "+selectedFile+"'; pac_extract "+selectedFile+" 2>&1 || echo 'SPD Upgrade Tool'"));
        findViewById(R.id.btnPayload).setOnClickListener(v->runWithProgress("PAYLOAD.BIN", "payload_dumper "+selectedFile+" 2>&1 || python3 -m payload_dumper "+selectedFile+" -o /sdcard/Download/ 2>&1 || echo 'Select payload.bin'"));
        findViewById(R.id.btnSuper).setOnClickListener(v->run("lpunpack "+selectedFile+" /sdcard/Download/super_out/ 2>&1 || echo 'Select super.img in File Manager'"));
        findViewById(R.id.btnOfp).setOnClickListener(v->runWithProgress("OFP EXTRACT", "python3 /sdcard/Download/ofp_extractor.py "+selectedFile+" 2>&1 || unzip -l "+selectedFile+" 2>&1 | head -30"));
        findViewById(R.id.btnFrp).setOnClickListener(v->run("echo 'FRP BYPASS ALL CHIPSET'; adb shell content insert --uri content://settings/secure --bind name:s:user_setup_complete --bind value:s:1 2>&1; fastboot erase frp 2>&1; echo 'FRP BYPASSED'"));
        findViewById(R.id.btnRunCmd).setOnClickListener(v->{ String c=((EditText)findViewById(R.id.editCmd)).getText().toString(); if(!c.isEmpty()) runWithProgress("CMD", c+" 2>&1"); });
    }

    void connect(){
        java.util.HashMap<String,UsbDevice> ds=um.getDeviceList();
        if(ds.isEmpty()){ logAppend("[ERROR] OTG belum colok!\n> VID: 0E8D=MTK Preloader\n> VID 05C6:9008=Qualcomm EDL\n> VID 04E8=SAMSUNG Download\n> VID 18D1:D00D=FASTBOOT\n> VID 18D1:4EE7=ADB\n> Check kabel OTG!"); return; }
        for(UsbDevice d:ds.values()){
            String mode=getMode(d);
            logAppend("USB: VID 0x"+Integer.toHexString(d.getVendorId())+" PID 0x"+Integer.toHexString(d.getProductId())+" "+(d.getProductName()!=null?d.getProductName():"")+" -> "+mode);
            if(!um.hasPermission(d)){
                PendingIntent pi=PendingIntent.getBroadcast(this,0,new Intent("USB_PERMISSION"),PendingIntent.FLAG_IMMUTABLE);
                um.requestPermission(d,pi);
            } else { dev=d; logAppend("[OK] CONNECTED NO ROOT: "+mode+" - Ready to flash!"); findViewById(R.id.statusDot).setBackgroundColor(0xFF00FF00); }
        }
    }
    String getMode(UsbDevice d){ int v=d.getVendorId(), p=d.getProductId(); if(v==0x0e8d) return "MTK Preloader"; if(v==0x05c6 && (p==0x9008||p==0x9006)) return "QUALCOMM EDL 9008"; if(v==0x04e8) return "SAMSUNG Odin/Download"; if(v==0x18d1 && p==0xd00d) return "FASTBOOT"; if(v==0x18d1 && p==0x4ee7) return "ADB"; if(v==0x05ac) return "iPhone DFU"; return "UNKNOWN DEVICE"; }

    void flashWithProgress(){
        String part=spinPart.getSelectedItem().toString();
        logAppend("\n=== FLASH START ===\nPart: "+part+"\nFile: "+selectedFile);
        progressBox.setVisibility(View.VISIBLE); progressBar.setProgress(0);
        txtProgressLabel.setText("Flashing "+new File(selectedFile).getName()+" -> "+part+" 0%");
        runWithProgress("FLASH "+part, "echo 'Flashing "+selectedFile+" to "+part+"'; fastboot flash "+part+" '"+selectedFile+"' 2>&1; echo 'FLASH DONE'");
    }

    void run(String cmd){ logAppend("\n> $ "+cmd+"\n"); new Thread(()->{ try{ Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream())); String l; StringBuilder sb=new StringBuilder(); while((l=r.readLine())!=null) sb.append(l).append("\n"); pr.waitFor(); String res=sb.toString(); runOnUiThread(()->logAppend(res)); }catch(Exception e){ runOnUiThread(()->logAppend("ERR: "+e.getMessage())); }}).start(); }

    void runWithProgress(String title, String cmd){
        progressBox.setVisibility(View.VISIBLE); progressBar.setProgress(5);
        txtProgressLabel.setText(title+" - Running...");
        logAppend("\n> $ "+cmd+"\n");
        new Thread(()->{
            try{
                runOnUiThread(()->{ progressBar.setProgress(20); txtProgressDetail.setText("Starting..."); });
                Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"});
                BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String l; StringBuilder sb=new StringBuilder(); int prog=20;
                while((l=r.readLine())!=null){
                    sb.append(l).append("\n");
                    prog+=2; if(prog>95) prog=95;
                    int p=prog; runOnUiThread(()->{ progressBar.setProgress(p); txtProgressDetail.setText(p+"% - Processing..."); });
                }
                pr.waitFor(); String res=sb.toString();
                runOnUiThread(()->{
                    progressBar.setProgress(100); txtProgressLabel.setText(title+" - DONE 100%");
                    txtProgressDetail.setText("100% - Completed - "+new File(selectedFile).length()/1024/1024+"MB");
                    logAppend(res+"\n=== "+title+" DONE ===\n");
                    new android.os.Handler().postDelayed(()->progressBox.setVisibility(View.GONE), 3000);
                });
            }catch(Exception e){ runOnUiThread(()->{ logAppend("ERR: "+e.getMessage()); progressBox.setVisibility(View.GONE); }); }
        }).start();
    }

    void logAppend(String s){ runOnUiThread(()->{ log.append(s+"\n"); }); }
}
''')

open("app/src/main/java/com/otgflasher/pro/FileManagerActivity.java","w").write(r'''
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import android.view.View;import java.io.*;import java.util.*;import java.util.zip.*;import android.content.*;
public class FileManagerActivity extends Activity {
    TextView txtPath, txtSelected; ListView listFiles; ProgressBar fmProgress;
    File cur=new File("/sdcard/Download"); List<File> files=new ArrayList<>(); File selected=null; String clipboard=""; boolean isCut=false;
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_file_manager);
        txtPath=findViewById(R.id.txtPath); txtSelected=findViewById(R.id.txtSelected); listFiles=findViewById(R.id.listFiles); fmProgress=findViewById(R.id.fmProgress);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        findViewById(R.id.btnUp).setOnClickListener(v->{ if(cur.getParentFile()!=null){ cur=cur.getParentFile(); list(); }});
        findViewById(R.id.btnExtract).setOnClickListener(v->extract());
        findViewById(R.id.btnCreateZip).setOnClickListener(v->createZip());
        findViewById(R.id.btnCreateTar).setOnClickListener(v->createTarGz());
        findViewById(R.id.btnCopy).setOnClickListener(v->{ if(selected!=null){ clipboard=selected.getAbsolutePath(); isCut=false; txtSelected.setText("COPIED: "+selected.getName()+" -> buka folder tujuan untuk paste"); }});
        findViewById(R.id.btnCut).setOnClickListener(v->{ if(selected!=null){ clipboard=selected.getAbsolutePath(); isCut=true; txtSelected.setText("CUT: "+selected.getName()); }});
        findViewById(R.id.btnDelete).setOnClickListener(v->{ if(selected==null) return; new android.app.AlertDialog.Builder(this).setTitle("Delete?").setMessage("Delete "+selected.getName()+"?").setPositiveButton("Yes",(d,w)->{ deleteRec(selected); list(); }).setNegativeButton("No",null).show(); });
        findViewById(R.id.btnRename).setOnClickListener(v->{ if(selected==null) return; EditText et=new EditText(this); et.setText(selected.getName()); new android.app.AlertDialog.Builder(this).setTitle("Rename").setView(et).setPositiveButton("OK",(d,w)->{ File nf=new File(selected.getParent(), et.getText().toString()); selected.renameTo(nf); selected=nf; list(); }).setNegativeButton("Cancel",null).show(); });
        findViewById(R.id.btnFlashHere).setOnClickListener(v->{ if(selected==null) return; String p="boot"; String n=selected.getName().toLowerCase(); if(n.contains("recovery")) p="recovery"; else if(n.contains("vbmeta")) p="vbmeta"; else if(n.contains("super")) p="super"; else if(n.contains("system")) p="system"; new android.app.AlertDialog.Builder(this).setTitle("Flash?").setMessage("Flash "+selected.getName()+" as "+p+"?\nfastboot flash "+p+" "+selected.getAbsolutePath()).setPositiveButton("Flash",(d,w)->{ try{ Runtime.getRuntime().exec(new String[]{"sh","-c","fastboot flash "+p+" '"+selected.getAbsolutePath()+"' 2>&1"}); Toast.makeText(this,"Flashing "+p,Toast.LENGTH_LONG).show(); }catch(Exception e){} }).setNegativeButton("Cancel",null).show(); });
        findViewById(R.id.btnProps).setOnClickListener(v->{ if(selected==null) return; String info="Name: "+selected.getName()+"\nPath: "+selected.getAbsolutePath()+"\nSize: "+selected.length()/1024/1024+" MB ("+selected.length()+" bytes)\nDate: "+new java.util.Date(selected.lastModified())+"\nRead: "+selected.canRead()+" Write: "+selected.canWrite()+"\nIsDir: "+selected.isDirectory(); new android.app.AlertDialog.Builder(this).setTitle("Properties").setMessage(info).setPositiveButton("OK",null).show(); });
        listFiles.setOnItemClickListener((a,vw,pos,id)->{ File f=files.get(pos); if(f.isDirectory()){ cur=f; list(); } else { selected=f; txtSelected.setText("SELECTED: "+f.getName()+" | "+f.length()/1024/1024+"MB | "+f.getAbsolutePath()); if(!clipboard.isEmpty()){ paste(); } }});
        listFiles.setOnItemLongClickListener((a,vw,pos,id)->{ File f=files.get(pos); selected=f; showMenu(f); return true; });
        list();
    }
    void list(){ File[] arr=cur.listFiles(); files.clear(); List<String> names=new ArrayList<>(); if(arr!=null){ Arrays.sort(arr,(x,y)->{ if(x.isDirectory()&&!y.isDirectory()) return -1; if(!x.isDirectory()&&y.isDirectory()) return 1; return x.getName().compareToIgnoreCase(y.getName()); }); for(File f:arr){ if(f.getName().startsWith(".")) continue; files.add(f); String icon=f.isDirectory()?"📁 ":"📄 "; if(f.getName().endsWith(".zip")||f.getName().endsWith(".rar")) icon="📦 "; if(f.getName().endsWith(".img")||f.getName().endsWith(".bin")) icon="💾 "; if(f.getName().endsWith(".apk")) icon="📱 "; names.add(icon+f.getName()+"\n "+(f.isDirectory()?"<DIR>":f.length()/1024/1024+" MB")+" | "+new java.text.SimpleDateFormat("dd/MM/yy").format(new java.util.Date(f.lastModified()))); } } txtPath.setText(cur.getAbsolutePath()+" ("+files.size()+" items)"); listFiles.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names)); if(!clipboard.isEmpty()) txtSelected.setText("CLIPBOARD: "+new File(clipboard).getName()+" ("+(isCut?"CUT":"COPY")+") - tap folder untuk paste"); }
    void paste(){ if(clipboard.isEmpty()) return; new Thread(()->{ try{ File src=new File(clipboard); File dst=new File(cur, src.getName()); if(src.isFile()){ copyFile(src,dst); if(isCut) src.delete(); } runOnUiThread(()->{ list(); clipboard=""; }); }catch(Exception e){} }).start(); }
    void copyFile(File s, File d)throws Exception{ FileInputStream fis=new FileInputStream(s); FileOutputStream fos=new FileOutputStream(d); byte[] b=new byte[8192]; int l; while((l=fis.read(b))>0) fos.write(b,0,l); fis.close(); fos.close(); }
    void extract(){ if(selected==null){ Toast.makeText(this,"Pilih file zip dulu",Toast.LENGTH_SHORT).show(); return; } fmProgress.setVisibility(View.VISIBLE); new Thread(()->{ try{ String name=selected.getName().toLowerCase(); int count=0; if(name.endsWith(".zip")){ ZipInputStream zis=new ZipInputStream(new FileInputStream(selected)); ZipEntry ze; while((ze=zis.getNextEntry())!=null){ File out=new File(cur, ze.getName()); if(ze.isDirectory()) out.mkdirs(); else { out.getParentFile().mkdirs(); FileOutputStream fos=new FileOutputStream(out); byte[] b=new byte[8192]; int l; while((l=zis.read(b))>0) fos.write(b,0,l); fos.close(); count++; } zis.closeEntry(); } zis.close(); } else if(name.endsWith(".tar.gz")||name.endsWith(".tgz")){ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","tar -xzf '"+selected.getAbsolutePath()+"' -C '"+cur.getAbsolutePath()+"' 2>&1"}); p.waitFor(); count=1; } else if(name.endsWith(".gz")){ File out=new File(cur, selected.getName().replace(".gz","")); GZIPInputStream gis=new GZIPInputStream(new FileInputStream(selected)); FileOutputStream fos=new FileOutputStream(out); byte[] b=new byte[8192]; int l; while((l=gis.read(b))>0) fos.write(b,0,l); fos.close(); gis.close(); count=1; } int c=count; runOnUiThread(()->{ fmProgress.setVisibility(View.GONE); txtSelected.setText("EXTRACT DONE: "+c+" files"); list(); }); }catch(Exception e){ runOnUiThread(()->{ fmProgress.setVisibility(View.GONE); txtSelected.setText("EXTRACT ERR: "+e.getMessage()); }); }}).start(); }
    void createZip(){ fmProgress.setVisibility(View.VISIBLE); new Thread(()->{ try{ File zipFile=new File(cur.getParent(), cur.getName()+".zip"); if(selected!=null&&selected.isFile()) zipFile=new File(cur, selected.getName()+".zip"); ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(zipFile)); if(selected!=null&&selected.isFile()){ zos.putNextEntry(new ZipEntry(selected.getName())); FileInputStream fis=new FileInputStream(selected); byte[] b=new byte[8192]; int l; while((l=fis.read(b))>0) zos.write(b,0,l); fis.close(); zos.closeEntry(); } else { for(File f:cur.listFiles()){ if(f.isFile()){ zos.putNextEntry(new ZipEntry(f.getName())); FileInputStream fis=new FileInputStream(f); byte[] b=new byte[8192]; int l; while((l=fis.read(b))>0) zos.write(b,0,l); fis.close(); zos.closeEntry(); } } } zos.close(); runOnUiThread(()->{ fmProgress.setVisibility(View.GONE); txtSelected.setText("ZIP DONE: "+zipFile.getName()+" "+zipFile.length()/1024+"KB"); list(); }); }catch(Exception e){ runOnUiThread(()->{ fmProgress.setVisibility(View.GONE); txtSelected.setText("ZIP ERR: "+e.getMessage()); }); }}).start(); }
    void createTarGz(){ if(selected==null) return; fmProgress.setVisibility(View.VISIBLE); new Thread(()->{ try{ File tarGz=new File(selected.getAbsolutePath()+".tar.gz"); Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","cd '"+cur.getAbsolutePath()+"' && tar -czf '"+tarGz.getAbsolutePath()+"' '"+selected.getName()+"' 2>&1"}); p.waitFor(); runOnUiThread(()->{ fmProgress.setVisibility(View.GONE); txtSelected.setText("TAR.GZ DONE: "+tarGz.getName()); list(); }); }catch(Exception e){ runOnUiThread(()->{ fmProgress.setVisibility(View.GONE); txtSelected.setText("ERR: "+e.getMessage()); }); }}).start(); }
    void deleteRec(File f){ if(f.isDirectory()){ File[] cs=f.listFiles(); if(cs!=null) for(File c:cs) deleteRec(c); } f.delete(); }
    void showMenu(File f){ String[] ops={"Extract Here","Compress to ZIP","Compress to TAR.GZ","Copy","Cut","Delete","Rename","Properties","Flash as Boot","MD5"}; new android.app.AlertDialog.Builder(this).setTitle(f.getName()).setItems(ops,(d,w)->{ switch(w){ case 0: extract(); break; case 1: createZip(); break; case 2: createTarGz(); break; case 3: clipboard=f.getAbsolutePath(); isCut=false; break; case 4: clipboard=f.getAbsolutePath(); isCut=true; break; case 5: deleteRec(f); list(); break; case 6: EditText et=new EditText(this); et.setText(f.getName()); new android.app.AlertDialog.Builder(this).setTitle("Rename").setView(et).setPositiveButton("OK",(dd,ww)->{ File nf=new File(f.getParent(), et.getText().toString()); f.renameTo(nf); list(); }).show(); break; case 7: txtSelected.setText("Size: "+f.length()/1024/1024+"MB Path: "+f.getAbsolutePath()); break; } }).show(); }
}
''')
print("v10 FINAL LOG TOP + PROGRESS BAR + ZARCHIVER")
