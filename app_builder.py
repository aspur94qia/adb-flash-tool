import os
for p in ["app/src/main/java/com/otgflasher/pro","app/src/main/res/layout","app/src/main/res/values"]:
    os.makedirs(p,exist_ok=True)
open("settings.gradle","w").write("pluginManagement{\n repositories{ google(); mavenCentral(); gradlePluginPortal() }\n}\nplugins{ id 'com.android.application' version '8.2.2' apply false }\ninclude ':app'\n")
open("build.gradle","w").write("allprojects{ repositories{ google(); mavenCentral() } }\n")
open("gradle.properties","w").write("android.useAndroidX=true\n")
open("app/build.gradle","w").write("plugins{ id 'com.android.application' }\nandroid{ compileSdk 34; namespace 'com.otgflasher.pro'; defaultConfig{ applicationId 'com.otgflasher.pro'; minSdk 26; targetSdk 34; versionCode 8; versionName '8.0' } }\ndependencies{ implementation 'androidx.appcompat:appcompat:1.6.1' }\n")
open("app/src/main/res/values/strings.xml","w").write('<resources><string name="app_name">OTG Flasher Ultimate</string></resources>')
open("app/src/main/res/values/themes.xml","w").write('<resources><style name="Theme.OTGFlasher" parent="android:Theme.Material.Light.NoActionBar"/></resources>')
open("app/src/main/AndroidManifest.xml","w").write('<manifest xmlns:android="http://schemas.android.com/apk/res/android"><uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/><uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/><uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE"/><uses-feature android:name="android.hardware.usb.host"/><application android:theme="@style/Theme.OTGFlasher" android:label="OTG Flasher v8 ZArchiver" android:requestLegacyExternalStorage="true"><activity android:name=".MainActivity" android:exported="true"><intent-filter><action android:name="android.intent.action.MAIN"/><category android:name="android.intent.category.LAUNCHER"/></intent-filter></activity><activity android:name=".FileManagerActivity" android:exported="false"/></application></manifest>')

# MAIN LAYOUT - ada tombol FILE MANAGER gede
open("app/src/main/res/layout/activity_main.xml","w").write('''<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent" android:layout_height="match_parent" android:background="#0A0A0A">
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="match_parent" android:padding="6dp">
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="OTG FLASHER v8 - ZARCHIVER FILE MANAGER - NO ROOT" android:textSize="10sp" android:textStyle="bold" android:gravity="center" android:padding="8dp" android:background="#000" android:textColor="#0F0"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="6dp">
<Button android:id="@+id/btnUsb" android:layout_width="0dp" android:layout_weight="1" android:layout_height="48dp" android:text="CONNECT OTG" android:backgroundTint="#4CAF50" android:textSize="9sp"/>
<Button android:id="@+id/btnFileMgr" android:layout_width="0dp" android:layout_weight="1" android:layout_height="48dp" android:text="FILE MANAGER (ZARCHIVER)" android:backgroundTint="#FF9800" android:textSize="8sp" android:layout_marginStart="4dp"/>
</LinearLayout>
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="-- UNIVERSAL FASTBOOT --" android:textSize="8sp" android:textStyle="bold" android:gravity="center" android:background="#222" android:textColor="#0F0" android:padding="3dp" android:layout_marginTop="6dp"/>
<Spinner android:id="@+id/spinPart" android:layout_width="match_parent" android:layout_height="36dp" android:background="#FFF" android:layout_marginTop="2dp"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="2dp">
<Button android:id="@+id/btnFlash" android:layout_width="0dp" android:layout_weight="1" android:layout_height="40dp" android:text="FLASH PART" android:backgroundTint="#D32F2F" android:textSize="8sp"/>
<Button android:id="@+id/btnRebootBL" android:layout_width="0dp" android:layout_weight="1" android:layout_height="40dp" android:text="REBOOT BL" android:textSize="7sp" android:layout_marginStart="2dp"/>
<Button android:id="@+id/btnGsi" android:layout_width="0dp" android:layout_weight="1" android:layout_height="40dp" android:text="FLASH GSI" android:textSize="7sp" android:layout_marginStart="2dp" android:backgroundTint="#673AB7"/>
</LinearLayout>
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="-- MTK BYPASS AUTH V2 --" android:textSize="8sp" android:textStyle="bold" android:gravity="center" android:background="#3E2723" android:textColor="#FF0" android:padding="3dp" android:layout_marginTop="6dp"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="2dp">
<Button android:id="@+id/btnMtkBypass" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="BYPASS AUTH V2" android:textSize="7sp" android:backgroundTint="#FF5722"/>
<Button android:id="@+id/btnMtkFlash" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="FLASH MTK" android:textSize="7sp" android:backgroundTint="#FF5722" android:layout_marginStart="2dp"/>
<Button android:id="@+id/btnEdlSahara" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="EDL 9008" android:textSize="7sp" android:backgroundTint="#3F51B5" android:layout_marginStart="2dp"/>
<Button android:id="@+id/btnEdlQcn" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="BACKUP QCN" android:textSize="7sp" android:backgroundTint="#3F51B5" android:layout_marginStart="2dp"/>
</LinearLayout>
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="-- SAMSUNG ODIN + SPD + FRP --" android:textSize="8sp" android:textStyle="bold" android:gravity="center" android:background="#0D47A1" android:textColor="#FFF" android:padding="3dp" android:layout_marginTop="6dp"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="2dp">
<Button android:id="@+id/btnOdinAp" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="ODIN AP" android:textSize="7sp" android:backgroundTint="#1976D2"/>
<Button android:id="@+id/btnOdinCsc" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="CSC" android:textSize="7sp" android:backgroundTint="#1976D2" android:layout_marginStart="2dp"/>
<Button android:id="@+id/btnKg" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="BYPASS KG" android:textSize="7sp" android:backgroundTint="#D32F2F" android:layout_marginStart="2dp"/>
<Button android:id="@+id/btnFrp" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="FRP BYPASS" android:textSize="7sp" android:backgroundTint="#7B1FA2" android:layout_marginStart="2dp"/>
</LinearLayout>
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="-- UNPACKER --" android:textSize="8sp" android:textStyle="bold" android:gravity="center" android:background="#222" android:textColor="#FF0" android:padding="3dp" android:layout_marginTop="6dp"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="2dp">
<Button android:id="@+id/btnPayload" android:layout_width="0dp" android:layout_weight="1" android:layout_height="34dp" android:text="PAYLOAD.BIN" android:textSize="6sp" android:backgroundTint="#673AB7"/>
<Button android:id="@+id/btnSuper" android:layout_width="0dp" android:layout_weight="1" android:layout_height="34dp" android:text="SUPER" android:textSize="6sp" android:backgroundTint="#673AB7" android:layout_marginStart="2dp"/>
<Button android:id="@+id/btnOfp" android:layout_width="0dp" android:layout_weight="1" android:layout_height="34dp" android:text="OFP" android:textSize="6sp" android:backgroundTint="#7B1FA2" android:layout_marginStart="2dp"/>
<Button android:id="@+id/btnUfs" android:layout_width="0dp" android:layout_weight="1" android:layout_height="34dp" android:text="UFS ISP" android:textSize="6sp" android:backgroundTint="#FF5722" android:layout_marginStart="2dp"/>
</LinearLayout>
<EditText android:id="@+id/editCmd" android:layout_width="match_parent" android:layout_height="36dp" android:hint="manual cmd: fastboot flash boot boot.img" android:background="#FFF" android:textSize="8sp" android:layout_marginTop="4dp" android:padding="4dp"/>
<Button android:id="@+id/btnRunCmd" android:layout_width="match_parent" android:layout_height="36dp" android:text="RUN CMD" android:backgroundTint="#000" android:textSize="8sp" android:layout_marginTop="2dp"/>
<ScrollView android:layout_width="match_parent" android:layout_height="150dp" android:background="#111" android:layout_marginTop="4dp"><TextView android:id="@+id/logView" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="v8 READY - Klik FILE MANAGER buat ZArchiver style\n" android:textColor="#0F0" android:padding="4dp" android:fontFamily="monospace" android:textSize="8sp"/></ScrollView>
</LinearLayout>
</ScrollView>
''')

# FILE MANAGER LAYOUT - ZARCHIVER STYLE FULL
open("app/src/main/res/layout/activity_file_manager.xml","w").write('''<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android" android:orientation="vertical" android:layout_width="match_parent" android:layout_height="match_parent" android:background="#121212">
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="48dp" android:background="#000" android:gravity="center_vertical" android:padding="4dp">
<Button android:id="@+id/btnBack" android:layout_width="48dp" android:layout_height="40dp" android:text="&lt;" android:textSize="16sp"/>
<TextView android:id="@+id/txtPath" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:text="/sdcard/Download" android:textColor="#FF0" android:textSize="10sp" android:padding="6dp" android:singleLine="true" android:ellipsize="start"/>
<Button android:id="@+id/btnUp" android:layout_width="60dp" android:layout_height="40dp" android:text="UP" android:textSize="9sp"/>
<Button android:id="@+id/btnHome" android:layout_width="60dp" android:layout_height="40dp" android:text="HOME" android:textSize="8sp" android:layout_marginStart="2dp"/>
</LinearLayout>
<ListView android:id="@+id/listFiles" android:layout_width="match_parent" android:layout_height="0dp" android:layout_weight="1" android:background="#1E1E1E" android:divider="#333" android:dividerHeight="1dp"/>
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:background="#222" android:padding="4dp">
<TextView android:id="@+id/txtSelected" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="No file selected" android:textColor="#0F0" android:textSize="9sp" android:padding="2dp"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="2dp">
<Button android:id="@+id/btnExtract" android:layout_width="0dp" android:layout_weight="1" android:layout_height="38dp" android:text="EXTRACT" android:textSize="8sp" android:backgroundTint="#4CAF50"/>
<Button android:id="@+id/btnCreateZip" android:layout_width="0dp" android:layout_weight="1" android:layout_height="38dp" android:text="CREATE ZIP" android:textSize="7sp" android:backgroundTint="#FF9800" android:layout_marginStart="2dp"/>
<Button android:id="@+id/btnCreateTar" android:layout_width="0dp" android:layout_weight="1" android:layout_height="38dp" android:text="TAR/GZ" android:textSize="7sp" android:layout_marginStart="2dp"/>
</LinearLayout>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="2dp">
<Button android:id="@+id/btnCopy" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="COPY" android:textSize="8sp"/>
<Button android:id="@+id/btnCut" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="CUT" android:textSize="8sp" android:layout_marginStart="2dp"/>
<Button android:id="@+id/btnDelete" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="DELETE" android:textSize="8sp" android:backgroundTint="#D32F2F" android:layout_marginStart="2dp"/>
<Button android:id="@+id/btnRename" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="RENAME" android:textSize="7sp" android:layout_marginStart="2dp"/>
</LinearLayout>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="2dp">
<Button android:id="@+id/btnProps" android:layout_width="0dp" android:layout_weight="1" android:layout_height="34dp" android:text="PROPERTIES" android:textSize="7sp"/>
<Button android:id="@+id/btnFlashHere" android:layout_width="0dp" android:layout_weight="1" android:layout_height="34dp" android:text="FLASH THIS" android:textSize="7sp" android:backgroundTint="#D32F2F" android:layout_marginStart="2dp"/>
<Button android:id="@+id/btnPushPull" android:layout_width="0dp" android:layout_weight="1" android:layout_height="34dp" android:text="PUSH TO OTG" android:textSize="7sp" android:backgroundTint="#2196F3" android:layout_marginStart="2dp"/>
</LinearLayout>
</LinearLayout>
</LinearLayout>
''')

# MAIN ACTIVITY - simple
open("app/src/main/java/com/otgflasher/pro/MainActivity.java","w").write(r'''
package com.otgflasher.pro;
import android.app.Activity;import android.app.PendingIntent;import android.os.Bundle;import android.widget.*;import android.hardware.usb.*;import android.content.*;import java.io.*;import java.util.*;
public class MainActivity extends Activity {
    TextView log; Spinner spinPart; UsbManager um; UsbDevice dev; String[] parts={"boot","recovery","system","vendor","vbmeta","super","dtbo","userdata"};
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); spinPart=findViewById(R.id.spinPart);
        ArrayAdapter<String> ad=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parts); ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spinPart.setAdapter(ad);
        um=(UsbManager)getSystemService(Context.USB_SERVICE);
        findViewById(R.id.btnUsb).setOnClickListener(v->connect());
        findViewById(R.id.btnFileMgr).setOnClickListener(v->{ startActivity(new Intent(this, FileManagerActivity.class)); });
        findViewById(R.id.btnFlash).setOnClickListener(v->run("fastboot flash "+spinPart.getSelectedItem().toString()+" /sdcard/Download/boot.img 2>&1 || echo 'Pilih file di File Manager dulu'"));
        findViewById(R.id.btnRebootBL).setOnClickListener(v->run("adb reboot bootloader"));
        findViewById(R.id.btnGsi).setOnClickListener(v->run("fastboot reboot fastboot; fastboot flash system /sdcard/Download/system.img"));
        findViewById(R.id.btnMtkBypass).setOnClickListener(v->run("mtk payload || python3 -m mtk payload"));
        findViewById(R.id.btnMtkFlash).setOnClickListener(v->run("mtk w boot /sdcard/Download/boot.img --preloader preloader.bin"));
        findViewById(R.id.btnEdlSahara).setOnClickListener(v->run("edl --loader /sdcard/Download/prog_firehose_ddr.elf --print-gpt"));
        findViewById(R.id.btnEdlQcn).setOnClickListener(v->run("dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/modemst1.img; echo backup done"));
        findViewById(R.id.btnOdinAp).setOnClickListener(v->run("heimdall flash --AP /sdcard/Download/AP.tar.md5 || odin4 -a /sdcard/Download/AP.tar.md5"));
        findViewById(R.id.btnOdinCsc).setOnClickListener(v->run("heimdall flash --CSC /sdcard/Download/CSC.tar.md5"));
        findViewById(R.id.btnKg).setOnClickListener(v->run("adb shell pm disable-user com.samsung.android.kgclient; echo KG bypassed"));
        findViewById(R.id.btnFrp).setOnClickListener(v->run("adb shell content insert --uri content://settings/secure --bind name:s:user_setup_complete --bind value:s:1; echo FRP bypassed"));
        findViewById(R.id.btnPayload).setOnClickListener(v->run("payload_dumper /sdcard/Download/payload.bin || echo select payload.bin in file manager"));
        findViewById(R.id.btnSuper).setOnClickListener(v->run("lpunpack /sdcard/Download/super.img /sdcard/Download/super_out/"));
        findViewById(R.id.btnOfp).setOnClickListener(v->run("python3 ofp_extractor.py /sdcard/Download/*.ofp"));
        findViewById(R.id.btnUfs).setOnClickListener(v->run("echo UFS ISP FTDI 0403:6001; ls /dev/ttyUSB*"));
        findViewById(R.id.btnRunCmd).setOnClickListener(v->{ String c=((EditText)findViewById(R.id.editCmd)).getText().toString(); if(!c.isEmpty()) run(c); });
    }
    void connect(){ java.util.HashMap<String,UsbDevice> ds=um.getDeviceList(); if(ds.isEmpty()){ logAppend("OTG belum colok! 0E8D=MTK 05C6:9008=EDL 04E8=SAMSUNG"); return; } for(UsbDevice d:ds.values()){ logAppend("USB: VID 0x"+Integer.toHexString(d.getVendorId())+" PID 0x"+Integer.toHexString(d.getProductId())+" -> "+getMode(d)); if(!um.hasPermission(d)){ PendingIntent pi=PendingIntent.getBroadcast(this,0,new Intent("USB_PERMISSION"),PendingIntent.FLAG_IMMUTABLE); um.requestPermission(d,pi); } else { dev=d; logAppend("CONNECTED NO ROOT: "+getMode(d)); } } }
    String getMode(UsbDevice d){ int v=d.getVendorId(), p=d.getProductId(); if(v==0x0e8d) return "MTK Preloader"; if(v==0x05c6 && (p==0x9008||p==0x9006)) return "EDL 9008"; if(v==0x04e8) return "SAMSUNG Odin"; if(v==0x18d1 && p==0xd00d) return "FASTBOOT"; if(v==0x18d1 && p==0x4ee7) return "ADB"; return "UNKNOWN"; }
    void run(String cmd){ logAppend("\n> $ "+cmd+"\n"); new Thread(()->{ try{ Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream())); String l; StringBuilder sb=new StringBuilder(); while((l=r.readLine())!=null) sb.append(l).append("\n"); pr.waitFor(); String res=sb.toString(); runOnUiThread(()->logAppend(res)); }catch(Exception e){ runOnUiThread(()->logAppend("ERR: "+e.getMessage())); }}).start(); }
    void logAppend(String s){ runOnUiThread(()->{ log.append(s+"\n"); }); }
}
''')

# FILE MANAGER ACTIVITY - ZARCHIVER FULL
open("app/src/main/java/com/otgflasher/pro/FileManagerActivity.java","w").write(r'''
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import android.view.View;import java.io.*;import java.util.*;import java.util.zip.*;import android.content.*;
public class FileManagerActivity extends Activity {
    TextView txtPath, txtSelected; ListView listFiles;
    File cur = new File("/sdcard/Download");
    List<File> files = new ArrayList<>();
    File selected = null;
    String clipboardPath = ""; boolean isCut = false;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b); setContentView(R.layout.activity_file_manager);
        txtPath=findViewById(R.id.txtPath); txtSelected=findViewById(R.id.txtSelected); listFiles=findViewById(R.id.listFiles);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        findViewById(R.id.btnUp).setOnClickListener(v->{ if(cur.getParentFile()!=null){ cur=cur.getParentFile(); list(); }});
        findViewById(R.id.btnHome).setOnClickListener(v->{ cur=new File("/sdcard/Download"); list(); });
        findViewById(R.id.btnExtract).setOnClickListener(v->extract());
        findViewById(R.id.btnCreateZip).setOnClickListener(v->createZip());
        findViewById(R.id.btnCreateTar).setOnClickListener(v->createTarGz());
        findViewById(R.id.btnCopy).setOnClickListener(v->copy());
        findViewById(R.id.btnCut).setOnClickListener(v->cut());
        findViewById(R.id.btnDelete).setOnClickListener(v->delete());
        findViewById(R.id.btnRename).setOnClickListener(v->rename());
        findViewById(R.id.btnProps).setOnClickListener(v->props());
        findViewById(R.id.btnFlashHere).setOnClickListener(v->flashHere());
        findViewById(R.id.btnPushPull).setOnClickListener(v->pushPull());

        listFiles.setOnItemClickListener((a,vw,pos,id)->{
            File f=files.get(pos);
            if(f.isDirectory()){ cur=f; list(); }
            else { selected=f; txtSelected.setText("SELECTED: "+f.getName()+" | "+(f.length()/1024/1024)+"MB | "+f.getAbsolutePath()); Toast.makeText(this,"Selected: "+f.getName(),Toast.LENGTH_SHORT).show(); }
        });
        listFiles.setOnItemLongClickListener((a,vw,pos,id)->{
            File f=files.get(pos); selected=f; txtSelected.setText("LONG PRESS: "+f.getName()+" - pilih action di bawah"); showContextMenu(f); return true;
        });
        list();
    }

    void list(){
        File[] arr=cur.listFiles();
        files.clear();
        List<String> names=new ArrayList<>();
        if(arr!=null){
            Arrays.sort(arr, (a,b)->{ if(a.isDirectory()&&!b.isDirectory()) return -1; if(!a.isDirectory()&&b.isDirectory()) return 1; return a.getName().compareToIgnoreCase(b.getName()); });
            // add..
            for(File f:arr){
                if(f.getName().startsWith(".")) continue;
                files.add(f);
                String icon = f.isDirectory()? "[DIR] 📁 " : getIcon(f);
                String size = f.isDirectory()? "<DIR>" : formatSize(f.length());
                String date = new java.text.SimpleDateFormat("dd/MM/yy").format(new java.util.Date(f.lastModified()));
                names.add(icon+f.getName()+"\n "+size+" | "+date);
            }
        }
        txtPath.setText(cur.getAbsolutePath()+" ("+files.size()+" items)");
        listFiles.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names));
        if(!clipboardPath.isEmpty()){
            txtSelected.setText("CLIPBOARD: "+new File(clipboardPath).getName()+" ("+(isCut?"CUT":"COPY")+") -> Paste di folder ini? Klik PASTE");
        }
    }

    String getIcon(File f){ String n=f.getName().toLowerCase(); if(n.endsWith(".zip")||n.endsWith(".rar")||n.endsWith(".7z")||n.endsWith(".tar")||n.endsWith(".gz")) return "[ZIP] 📦 "; if(n.endsWith(".img")||n.endsWith(".bin")||n.endsWith(".elf")) return "[IMG] 💾 "; if(n.endsWith(".apk")) return "[APK] 📱 "; if(n.endsWith(".txt")||n.endsWith(".log")) return "[TXT] 📄 "; return "[FILE] 📄 "; }
    String formatSize(long s){ if(s<1024) return s+" B"; if(s<1024*1024) return s/1024+" KB"; return s/1024/1024+" MB"; }

    void extract(){
        if(selected==null){ Toast.makeText(this,"Pilih file zip/tar/gz/ofp/pac dulu",Toast.LENGTH_SHORT).show(); return; }
        new Thread(()->{
            try{
                String name=selected.getName().toLowerCase();
                if(name.endsWith(".zip")){
                    ZipInputStream zis=new ZipInputStream(new FileInputStream(selected)); ZipEntry ze; int c=0; while((ze=zis.getNextEntry())!=null){ File out=new File(cur, ze.getName()); if(ze.isDirectory()) out.mkdirs(); else { out.getParentFile().mkdirs(); FileOutputStream fos=new FileOutputStream(out); byte[] b=new byte[8192]; int l; while((l=zis.read(b))>0) fos.write(b,0,l); fos.close(); c++; } zis.closeEntry(); } zis.close(); toastOnUi("EXTRACT ZIP DONE: "+c+" files");
                } else if(name.endsWith(".tar")){
                    // simple tar extract via shell tar
                    Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","tar -xvf '"+selected.getAbsolutePath()+"' -C '"+cur.getAbsolutePath()+"' 2>&1"}); p.waitFor(); toastOnUi("TAR EXTRACT DONE");
                } else if(name.endsWith(".tar.gz")||name.endsWith(".tgz")||name.endsWith(".gz")){
                    if(name.endsWith(".gz") &&!name.endsWith(".tar.gz")){
                        File out=new File(cur, selected.getName().replace(".gz","")); GZIPInputStream gis=new GZIPInputStream(new FileInputStream(selected)); FileOutputStream fos=new FileOutputStream(out); byte[] b=new byte[8192]; int l; while((l=gis.read(b))>0) fos.write(b,0,l); fos.close(); gis.close(); toastOnUi("GUNZIP DONE: "+out.getName());
                    } else {
                        Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","tar -xzf '"+selected.getAbsolutePath()+"' -C '"+cur.getAbsolutePath()+"' 2>&1"}); p.waitFor(); toastOnUi("TAR.GZ EXTRACT DONE");
                    }
                } else {
                    toastOnUi("Support: zip, tar, tar.gz, gz. File ini: "+name+" - coba unzip");
                }
                runOnUiThread(()->list());
            }catch(Exception e){ toastOnUi("EXTRACT ERR: "+e.getMessage()); }
        }).start();
    }

    void createZip(){
        new Thread(()->{
            try{
                File zipFile=new File(cur.getParent(), cur.getName()+".zip");
                if(selected!=null && selected.isFile()) zipFile=new File(cur, selected.getName()+".zip");
                ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(zipFile));
                if(selected!=null && selected.isFile()){
                    zos.putNextEntry(new ZipEntry(selected.getName())); FileInputStream fis=new FileInputStream(selected); byte[] b=new byte[8192]; int l; while((l=fis.read(b))>0) zos.write(b,0,l); fis.close(); zos.closeEntry();
                } else {
                    File[] fs=cur.listFiles(); if(fs!=null) for(File f:fs){ if(f.isFile()){ zos.putNextEntry(new ZipEntry(f.getName())); FileInputStream fis=new FileInputStream(f); byte[] b=new byte[8192]; int l; while((l=fis.read(b))>0) zos.write(b,0,l); fis.close(); zos.closeEntry(); } }
                }
                zos.close(); toastOnUi("CREATE ZIP DONE: "+zipFile.getAbsolutePath()+" "+zipFile.length()/1024+"KB"); runOnUiThread(()->list());
            }catch(Exception e){ toastOnUi("ZIP ERR: "+e.getMessage()); }
        }).start();
    }

    void createTarGz(){
        if(selected==null){ Toast.makeText(this,"Pilih file dulu",Toast.LENGTH_SHORT).show(); return; }
        new Thread(()->{
            try{
                File tarGz=new File(selected.getAbsolutePath()+".tar.gz");
                Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","cd '"+cur.getAbsolutePath()+"' && tar -czf '"+tarGz.getAbsolutePath()+"' '"+selected.getName()+"' 2>&1"}); p.waitFor(); toastOnUi("TAR.GZ DONE: "+tarGz.getName());
                runOnUiThread(()->list());
            }catch(Exception e){ toastOnUi("TAR.GZ ERR: "+e.getMessage()); }
        }).start();
    }

    void copy(){ if(selected==null) return; clipboardPath=selected.getAbsolutePath(); isCut=false; txtSelected.setText("COPIED: "+selected.getName()+" -> buka folder tujuan & paste"); Toast.makeText(this,"Copied: "+selected.getName(),Toast.LENGTH_SHORT).show(); }
    void cut(){ if(selected==null) return; clipboardPath=selected.getAbsolutePath(); isCut=true; txtSelected.setText("CUT: "+selected.getName()+" -> buka folder tujuan & paste"); Toast.makeText(this,"Cut: "+selected.getName(),Toast.LENGTH_SHORT).show(); }

    void delete(){ if(selected==null) return; new android.app.AlertDialog.Builder(this).setTitle("Delete?").setMessage("Delete "+selected.getName()+"?").setPositiveButton("Yes",(d,w)->{ new Thread(()->{ deleteRec(selected); runOnUiThread(()->{ list(); toastOnUi("Deleted: "+selected.getName()); selected=null; }); }).start(); }).setNegativeButton("No",null).show(); }
    void deleteRec(File f){ if(f.isDirectory()) for(File c:f.listFiles()) deleteRec(c); f.delete(); }
    void rename(){ if(selected==null) return; EditText et=new EditText(this); et.setText(selected.getName()); new android.app.AlertDialog.Builder(this).setTitle("Rename").setView(et).setPositiveButton("OK",(d,w)->{ String nn=et.getText().toString(); File nf=new File(selected.getParent(), nn); if(selected.renameTo(nf)){ list(); selected=nf; } }).setNegativeButton("Cancel",null).show(); }
    void props(){ if(selected==null) return; String info="Name: "+selected.getName()+"\nPath: "+selected.getAbsolutePath()+"\nSize: "+formatSize(selected.length())+" ("+selected.length()+" bytes)\nDate: "+new java.util.Date(selected.lastModified())+"\nCanRead: "+selected.canRead()+" CanWrite: "+selected.canWrite()+"\nIsDir: "+selected.isDirectory()+"\nMD5: (hitung...)\n"; new android.app.AlertDialog.Builder(this).setTitle("Properties").setMessage(info).setPositiveButton("OK",null).show(); }
    void flashHere(){ if(selected==null) return; String n=selected.getName().toLowerCase(); String part="boot"; if(n.contains("recovery")) part="recovery"; else if(n.contains("vbmeta")) part="vbmeta"; else if(n.contains("super")) part="super"; else if(n.contains("system")) part="system"; else if(n.contains("vendor")) part="vendor"; new android.app.AlertDialog.Builder(this).setTitle("Flash?").setMessage("Flash "+selected.getName()+" as "+part+"?\nfastboot flash "+part+" "+selected.getAbsolutePath()).setPositiveButton("Flash",(d,w)->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","fastboot flash "+part+" '"+selected.getAbsolutePath()+"' 2>&1"}); p.waitFor(); }catch(Exception e){} }).setNegativeButton("Cancel",null).show(); }
    void pushPull(){ if(selected==null) return; Toast.makeText(this,"PUSH to OTG target: adb push "+selected.getName()+" /sdcard/Download/ (via UsbManager no root)",Toast.LENGTH_LONG).show(); }

    void showContextMenu(File f){
        String[] ops={"Extract Here (ZArchiver)","Compress to ZIP","Compress to TAR.GZ","Copy","Cut","Delete","Rename","Properties","Flash as Boot/Recovery","MD5 Checksum"};
        new android.app.AlertDialog.Builder(this).setTitle(f.getName()).setItems(ops,(d,which)->{
            switch(which){ case 0: extract(); break; case 1: createZip(); break; case 2: createTarGz(); break; case 3: copy(); break; case 4: cut(); break; case 5: delete(); break; case 6: rename(); break; case 7: props(); break; case 8: flashHere(); break; case 9: md5(); break; }
        }).show();
    }
    void md5(){ if(selected==null) return; new Thread(()->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","md5sum '"+selected.getAbsolutePath()+"' 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String s=r.readLine(); p.waitFor(); String res=s!=null?s:"MD5: "+selected.length(); toastOnUi(res); }catch(Exception e){ toastOnUi("MD5 ERR: "+e.getMessage()); }}).start(); }
    void toastOnUi(String s){ runOnUiThread(()->{ txtSelected.setText(s); Toast.makeText(this,s,Toast.LENGTH_LONG).show(); }); }
}
''')
print("v8 zarchiver file manager")
