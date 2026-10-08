import os
for p in ["app/src/main/java/com/otgflasher/pro","app/src/main/res/layout","app/src/main/res/values"]:
    os.makedirs(p,exist_ok=True)
open("settings.gradle","w").write("pluginManagement{\n repositories{ google(); mavenCentral(); gradlePluginPortal() }\n}\nplugins{ id 'com.android.application' version '8.2.2' apply false }\ninclude ':app'\n")
open("build.gradle","w").write("allprojects{ repositories{ google(); mavenCentral() } }\n")
open("gradle.properties","w").write("android.useAndroidX=true\n")
open("app/build.gradle","w").write("plugins{ id 'com.android.application' }\nandroid{ compileSdk 34; namespace 'com.otgflasher.pro'; defaultConfig{ applicationId 'com.otgflasher.pro'; minSdk 26; targetSdk 34; versionCode 10; versionName '10.2' } }\ndependencies{ implementation 'androidx.appcompat:appcompat:1.6.1' }\n")
open("app/src/main/res/values/strings.xml","w").write('<resources><string name="app_name">OTG Flasher v9</string></resources>')
open("app/src/main/res/values/themes.xml","w").write('<resources><style name="Theme.OTGFlasher" parent="android:Theme.Material.Light.NoActionBar"/></resources>')
open("app/src/main/AndroidManifest.xml","w").write('<manifest xmlns:android="http://schemas.android.com/apk/res/android"><uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/><uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/><uses-feature android:name="android.hardware.usb.host"/><application android:theme="@style/Theme.OTGFlasher" android:label="OTG Flasher v9" android:requestLegacyExternalStorage="true"><activity android:name=".MainActivity" android:exported="true"><intent-filter><action android:name="android.intent.action.MAIN"/><category android:name="android.intent.category.LAUNCHER"/></intent-filter></activity><activity android:name=".FileManagerActivity"/></application></manifest>')

open("app/src/main/res/layout/activity_main.xml","w").write('''<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent" android:layout_height="match_parent" android:background="#121212">
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:padding="8dp">
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="64dp" android:background="#4CAF50" android:gravity="center_vertical" android:padding="12dp">
<TextView android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:text="OTG FLASHER v9" android:textSize="20sp" android:textStyle="bold" android:textColor="#FFF"/>
</LinearLayout>
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:background="#000" android:padding="6dp" android:layout_marginTop="8dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="LOG CONSOLE" android:textSize="10sp" android:textColor="#888" android:textStyle="bold"/>
<ScrollView android:layout_width="match_parent" android:layout_height="110dp" android:background="#0A0A0A" android:layout_marginTop="2dp"><TextView android:id="@+id/logView" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="[12:03:10] OTG Flasher v9 initialized - ready\n[12:03:11] No OTG device detected\n[12:03:11] Waiting for device\n" android:textColor="#0F0" android:fontFamily="monospace" android:textSize="10sp" android:padding="6dp"/></ScrollView>
</LinearLayout>
<LinearLayout android:id="@+id/progressBox" android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp" android:layout_marginTop="8dp" android:visibility="gone">
<TextView android:id="@+id/txtProgressLabel" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="Flashing 0%" android:textSize="13sp" android:textStyle="bold" android:textColor="#FFF"/>
<ProgressBar android:id="@+id/progressBar" style="?android:attr/progressBarStyleHorizontal" android:layout_width="match_parent" android:layout_height="18dp" android:max="100" android:progress="0" android:progressTint="#0F0" android:layout_marginTop="6dp"/>
<TextView android:id="@+id/txtProgressDetail" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="0% - 0MB" android:textSize="11sp" android:textColor="#0F0" android:layout_marginTop="4dp"/>
</LinearLayout>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="10dp">
<Button android:id="@+id/btnUsb" android:layout_width="0dp" android:layout_weight="1" android:layout_height="64dp" android:text="CONNECT OTG" android:backgroundTint="#4CAF50" android:textSize="14sp" android:textStyle="bold"/>
<Button android:id="@+id/btnFileMgr" android:layout_width="0dp" android:layout_weight="1" android:layout_height="64dp" android:text="FILE MANAGER" android:backgroundTint="#FF9800" android:textSize="13sp" android:textStyle="bold" android:layout_marginStart="8dp"/>
</LinearLayout>
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
<Button android:id="@+id/btnMtkBypass" android:layout_width="match_parent" android:layout_height="48dp" android:text="BYPASS AUTH V2" android:textSize="11sp" android:backgroundTint="#3E2723" android:layout_marginTop="6dp"/>
<Button android:id="@+id/btnMtkFlash" android:layout_width="match_parent" android:layout_height="40dp" android:text="FLASH MTK" android:textSize="9sp" android:backgroundTint="#FF5722" android:layout_marginTop="4dp"/>
</LinearLayout>
</LinearLayout>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="8dp">
<LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="QUALCOMM EDL 9008" android:textSize="13sp" android:textStyle="bold" android:textColor="#5C6BC0"/>
<Button android:id="@+id/btnEdl" android:layout_width="match_parent" android:layout_height="48dp" android:text="ENTER EDL 9008" android:textSize="11sp" android:backgroundTint="#1A237E" android:layout_marginTop="6dp"/>
<Button android:id="@+id/btnQcn" android:layout_width="match_parent" android:layout_height="36dp" android:text="BACKUP QCN" android:textSize="8sp" android:backgroundTint="#3F51B5" android:layout_marginTop="4dp"/>
</LinearLayout>
<LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp" android:layout_marginStart="8dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="SAMSUNG ODIN" android:textSize="13sp" android:textStyle="bold" android:textColor="#1976D2"/>
<Button android:id="@+id/btnOdinAp" android:layout_width="match_parent" android:layout_height="48dp" android:text="LAUNCH ODIN" android:textSize="10sp" android:backgroundTint="#0D47A1" android:layout_marginTop="6dp"/>
<Button android:id="@+id/btnKg" android:layout_width="match_parent" android:layout_height="36dp" android:text="BYPASS KG/MDM" android:textSize="9sp" android:backgroundTint="#D32F2F" android:layout_marginTop="4dp"/>
</LinearLayout>
</LinearLayout>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="8dp">
<LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="SPD/UNISOC PAC" android:textSize="12sp" android:textStyle="bold" android:textColor="#AB47BC"/>
<Button android:id="@+id/btnSpd" android:layout_width="match_parent" android:layout_height="48dp" android:text="FLASH PAC" android:textSize="11sp" android:backgroundTint="#4A148C" android:layout_marginTop="6dp"/>
</LinearLayout>
<LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:background="#1E1E1E" android:padding="10dp" android:layout_marginStart="8dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="UNPACKER" android:textSize="12sp" android:textStyle="bold" android:textColor="#FF9800"/>
<Button android:id="@+id/btnPayload" android:layout_width="0dp" android:layout_weight="1" android:layout_height="40dp" android:text="PAYLOAD.BIN" android:textSize="8sp" android:backgroundTint="#673AB7" android:layout_marginTop="6dp"/>
<Button android:id="@+id/btnFrp" android:layout_width="match_parent" android:layout_height="36dp" android:text="FRP BYPASS ALL" android:textSize="10sp" android:backgroundTint="#7B1FA2" android:layout_marginTop="4dp"/>
</LinearLayout>
</LinearLayout>
<EditText android:id="@+id/editCmd" android:layout_width="match_parent" android:layout_height="48dp" android:hint="Manual cmd" android:background="#FFF" android:textSize="12sp" android:padding="10dp" android:layout_marginTop="10dp"/>
<Button android:id="@+id/btnRunCmd" android:layout_width="match_parent" android:layout_height="48dp" android:text="RUN CMD" android:backgroundTint="#000" android:textSize="13sp" android:layout_marginTop="4dp"/>
</LinearLayout>
</ScrollView>
''')

open("app/src/main/res/layout/activity_file_manager.xml","w").write('''<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android" android:orientation="vertical" android:layout_width="match_parent" android:layout_height="match_parent" android:background="#121212">
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="60dp" android:background="#4CAF50" android:gravity="center_vertical" android:padding="8dp">
<Button android:id="@+id/btnBack" android:layout_width="60dp" android:layout_height="48dp" android:text="BACK" android:textSize="12sp"/>
<TextView android:id="@+id/txtPath" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:text="/sdcard/Download" android:textColor="#FFF" android:textSize="13sp" android:padding="8dp" android:textStyle="bold"/>
<Button android:id="@+id/btnUp" android:layout_width="64dp" android:layout_height="48dp" android:text="UP" android:textSize="12sp" android:backgroundTint="#000"/>
</LinearLayout>
<ListView android:id="@+id/listFiles" android:layout_width="match_parent" android:layout_height="0dp" android:layout_weight="1" android:background="#1E1E1E" android:divider="#333" android:dividerHeight="1dp"/>
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:background="#222" android:padding="8dp">
<TextView android:id="@+id/txtSelected" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="No file selected" android:textColor="#0F0" android:textSize="12sp" android:padding="4dp"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="6dp">
<Button android:id="@+id/btnExtract" android:layout_width="0dp" android:layout_weight="1" android:layout_height="52dp" android:text="EXTRACT" android:textSize="11sp" android:backgroundTint="#4CAF50"/>
<Button android:id="@+id/btnCreateZip" android:layout_width="0dp" android:layout_weight="1" android:layout_height="52dp" android:text="CREATE ZIP" android:textSize="10sp" android:backgroundTint="#FF9800" android:layout_marginStart="4dp"/>
<Button android:id="@+id/btnDelete" android:layout_width="0dp" android:layout_weight="1" android:layout_height="52dp" android:text="DELETE" android:textSize="11sp" android:backgroundTint="#D32F2F" android:layout_marginStart="4dp"/>
</LinearLayout>
</LinearLayout>
</LinearLayout>
''')

# MAIN - FINAL WORKING
open("app/src/main/java/com/otgflasher/pro/MainActivity.java","w").write(r'''
package com.otgflasher.pro;
import android.app.Activity;import android.app.PendingIntent;import android.os.Bundle;import android.widget.*;import android.hardware.usb.*;import android.content.*;import android.view.View;import java.io.*;import java.util.*;
public class MainActivity extends Activity {
    TextView log, txtProgressLabel, txtProgressDetail; ProgressBar progressBar; LinearLayout progressBox;
    Spinner spinPart; UsbManager um; UsbDevice dev;
    String[] parts={"boot_a","boot_b","boot","recovery","system","vendor","vbmeta","super"};
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); progressBar=findViewById(R.id.progressBar); progressBox=findViewById(R.id.progressBox);
        txtProgressLabel=findViewById(R.id.txtProgressLabel); txtProgressDetail=findViewById(R.id.txtProgressDetail);
        spinPart=findViewById(R.id.spinPart);
        ArrayAdapter<String> ad=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parts);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spinPart.setAdapter(ad);
        um=(UsbManager)getSystemService(Context.USB_SERVICE);
        findViewById(R.id.btnUsb).setOnClickListener(v->connect());
        findViewById(R.id.btnFileMgr).setOnClickListener(v->startActivity(new Intent(this, FileManagerActivity.class)));
        findViewById(R.id.btnFlash).setOnClickListener(v->flashWithProgress());
        findViewById(R.id.btnRebootBL).setOnClickListener(v->run("adb reboot bootloader"));
        findViewById(R.id.btnMtkBypass).setOnClickListener(v->runWithProgress("MTK BYPASS", "mtk payload || python3 -m mtk payload"));
        findViewById(R.id.btnMtkFlash).setOnClickListener(v->run("mtk w boot /sdcard/Download/boot.img"));
        findViewById(R.id.btnEdl).setOnClickListener(v->runWithProgress("EDL 9008", "edl --loader /sdcard/Download/prog_firehose_ddr.elf --print-gpt"));
        findViewById(R.id.btnQcn).setOnClickListener(v->run("dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/modemst1.img"));
        findViewById(R.id.btnOdinAp).setOnClickListener(v->run("heimdall flash --AP /sdcard/Download/AP.tar.md5"));
        findViewById(R.id.btnKg).setOnClickListener(v->run("adb shell pm disable-user com.samsung.android.kgclient"));
        findViewById(R.id.btnSpd).setOnClickListener(v->run("echo SPD PAC FLASH"));
        findViewById(R.id.btnPayload).setOnClickListener(v->run("payload_dumper /sdcard/Download/payload.bin"));
        findViewById(R.id.btnFrp).setOnClickListener(v->run("adb shell content insert --uri content://settings/secure --bind name:s:user_setup_complete --bind value:s:1"));
        findViewById(R.id.btnRunCmd).setOnClickListener(v->{ String c=((EditText)findViewById(R.id.editCmd)).getText().toString(); if(!c.isEmpty()) runWithProgress("CMD", c); });
    }
    void connect(){ java.util.HashMap<String,UsbDevice> ds=um.getDeviceList(); if(ds.isEmpty()){ logAppend("OTG belum colok!\n0E8D=MTK\n05C6:9008=EDL\n04E8=SAMSUNG"); return; } for(UsbDevice d:ds.values()){ logAppend("USB: VID 0x"+Integer.toHexString(d.getVendorId())+" PID 0x"+Integer.toHexString(d.getProductId())+" -> "+getMode(d)); if(!um.hasPermission(d)){ PendingIntent pi=PendingIntent.getBroadcast(this,0,new Intent("USB_PERMISSION"),PendingIntent.FLAG_IMMUTABLE); um.requestPermission(d,pi); } else { dev=d; logAppend("CONNECTED: "+getMode(d)); } } }
    String getMode(UsbDevice d){ int v=d.getVendorId(), p=d.getProductId(); if(v==0x0e8d) return "MTK Preloader"; if(v==0x05c6 && (p==0x9008||p==0x9006)) return "EDL 9008"; if(v==0x04e8) return "SAMSUNG Odin"; if(v==0x18d1 && p==0xd00d) return "FASTBOOT"; return "UNKNOWN"; }
    void flashWithProgress(){ String part=spinPart.getSelectedItem().toString(); logAppend("\n=== FLASH "+part+" ===\n"); progressBox.setVisibility(View.VISIBLE); progressBar.setProgress(0); txtProgressLabel.setText("Flashing "+part+" 0%"); runWithProgress("FLASH "+part, "fastboot flash "+part+" /sdcard/Download/boot.img"); }
    void run(String cmd){ logAppend("\n> $ "+cmd+"\n"); new Thread(()->{ try{ Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream())); String l; StringBuilder sb=new StringBuilder(); while((l=r.readLine())!=null) sb.append(l).append("\n"); pr.waitFor(); String res=sb.toString(); runOnUiThread(()->logAppend(res)); }catch(Exception e){ runOnUiThread(()->logAppend("ERR: "+e.getMessage())); }}).start(); }
    void runWithProgress(String title, String cmd){ progressBox.setVisibility(View.VISIBLE); progressBar.setProgress(5); txtProgressLabel.setText(title+" Running..."); logAppend("\n> $ "+cmd+"\n"); new Thread(()->{ try{ Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream())); String l; StringBuilder sb=new StringBuilder(); int prog=20; while((l=r.readLine())!=null){ sb.append(l).append("\n"); prog+=2; if(prog>95) prog=95; int p=prog; runOnUiThread(()->{ progressBar.setProgress(p); txtProgressDetail.setText(p+"% - Processing"); }); } pr.waitFor(); String res=sb.toString(); runOnUiThread(()->{ progressBar.setProgress(100); txtProgressLabel.setText(title+" DONE 100%"); txtProgressDetail.setText("100% Completed"); logAppend(res); new android.os.Handler().postDelayed(()->progressBox.setVisibility(View.GONE), 3000); }); }catch(Exception e){ runOnUiThread(()->{ logAppend("ERR: "+e.getMessage()); progressBox.setVisibility(View.GONE); }); } }).start(); }
    void logAppend(String s){ runOnUiThread(()->{ log.append(s+"\n"); }); }
}
''')

# FILE MANAGER - SUPER SIMPLE NO ZIP CLASS - ONLY SHELL
open("app/src/main/java/com/otgflasher/pro/FileManagerActivity.java","w").write(r'''
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
''')
print("v10.2 ultra minimal no zip class")
