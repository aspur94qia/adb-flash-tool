A="".join(chr(c) for c in [97,110,100,114,111,105,100])

import os
os.makedirs("app/src/main/java/com/otgflasher/pro", exist_ok=True)
os.makedirs("app/src/main/res/layout", exist_ok=True)
os.makedirs("app/src/main/res/values", exist_ok=True)
os.makedirs("app/src/main/res/drawable", exist_ok=True)
for d in ["mipmap-hdpi","mipmap-mdpi","mipmap-xhdpi","mipmap-xxhdpi","mipmap-xxxhdpi"]:
    os.makedirs(f"app/src/main/res/{d}", exist_ok=True)
os.makedirs("app/src/main/jniLibs/arm64-v8a", exist_ok=True)

open("app/build.gradle","w").write("plugins { id 'com.{A}.application' }\n{A} { compileSdk 34; namespace 'com.otgflasher.pro'\n defaultConfig { applicationId 'com.otgflasher.pro'; minSdk 26; targetSdk 34; versionCode 20; versionName '2.0 TERMINAL' }\n buildTypes { release { minifyEnabled false } } }\ndependencies { implementation 'androidx.appcompat:appcompat:1.6.1'; implementation 'com.google.{A}.material:material:1.11.0' }".replace("{A}", A))

open("app/src/main/res/values/colors.xml","w").write('<resources><color name="bg">#020A1A</color><color name="cyan">#00D4FF</color><color name="orange">#FFAA00</color></resources>')
open("app/src/main/res/values/themes.xml","w").write('<resources><style name="Theme.OTG" parent="Theme.Material3.DayNight.NoActionBar"><item name="{A}:statusBarColor">#020A1A</item><item name="{A}:windowBackground">#020A1A</item></style></resources>'.replace("{A}", A))
open("app/src/main/res/drawable/bg_card.xml","w").write('<shape xmlns:{A}="http://schemas.{A}.com/apk/res/{A}"><corners {A}:radius="16dp"/><solid {A}:color="#0F1F3A"/></shape>'.replace("{A}", A))
open("app/src/main/res/drawable/bg_btn_cyan.xml","w").write('<shape xmlns:{A}="http://schemas.{A}.com/apk/res/{A}"><corners {A}:radius="12dp"/><solid {A}:color="#00D4FF"/></shape>'.replace("{A}", A))
open("app/src/main/res/drawable/bg_btn_orange.xml","w").write('<shape xmlns:{A}="http://schemas.{A}.com/apk/res/{A}"><corners {A}:radius="12dp"/><solid {A}:color="#FFAA00"/></shape>'.replace("{A}", A))
open("app/src/main/res/drawable/bg_btn_red.xml","w").write('<shape xmlns:{A}="http://schemas.{A}.com/apk/res/{A}"><corners {A}:radius="12dp"/><solid {A}:color="#FF3B30"/></shape>'.replace("{A}", A))

open("app/src/main/{A}Manifest.xml","w").write('<manifest xmlns:{A}="http://schemas.{A}.com/apk/res/{A}"><uses-permission {A}:name="{A}.permission.INTERNET"/><uses-feature {A}:name="{A}.hardware.usb.host" {A}:required="false"/><application {A}:icon="@mipmap/ic_launcher" {A}:label="OTG Flasher Terminal" {A}:theme="@style/Theme.OTG" {A}:allowBackup="true"><activity {A}:name=".MainActivity" {A}:exported="true" {A}:windowSoftInputMode="adjustResize"><intent-filter><action {A}:name="{A}.intent.action.MAIN"/><category {A}:name="{A}.intent.category.LAUNCHER"/></intent-filter></activity></application></manifest>'.replace("{A}", A))

layout = """<LinearLayout xmlns:{A}="http://schemas.{A}.com/apk/res/{A}" {A}:layout_width="match_parent" {A}:layout_height="match_parent" {A}:background="#020A1A" {A}:orientation="vertical">
 <LinearLayout {A}:layout_width="match_parent" {A}:layout_height="wrap_content" {A}:orientation="vertical" {A}:padding="14dp" {A}:background="#0F1F3A">
  <TextView {A}:layout_width="match_parent" {A}:layout_height="wrap_content" {A}:text="OTG FLASHER TERMINAL v2.0" {A}:textColor="#00D4FF" {A}:textSize="16sp" {A}:textStyle="bold" {A}:gravity="center"/>
  <TextView {A}:id="@+id/tvUsb" {A}:layout_width="match_parent" {A}:layout_height="wrap_content" {A}:text="USB: No OTG" {A}:textColor="#FFAA00" {A}:textSize="12sp" {A}:layout_marginTop="4dp"/>
  <TextView {A}:id="@+id/tvPath" {A}:layout_width="match_parent" {A}:layout_height="wrap_content" {A}:text="No file selected" {A}:textColor="#888" {A}:textSize="11sp" {A}:ellipsize="middle" {A}:singleLine="true" {A}:layout_marginTop="2dp"/>
 </LinearLayout>
 <ScrollView {A}:layout_width="match_parent" {A}:layout_height="0dp" {A}:layout_weight="1">
  <LinearLayout {A}:layout_width="match_parent" {A}:layout_height="wrap_content" {A}:orientation="vertical" {A}:padding="10dp">
   <LinearLayout {A}:layout_width="match_parent" {A}:layout_height="wrap_content" {A}:orientation="horizontal">
    <Button {A}:id="@+id/btnPick" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="46dp" {A}:text="PICK IMG" {A}:textSize="12sp" {A}:layout_margin="3dp"/>
    <Button {A}:id="@+id/btnAdb" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="46dp" {A}:text="ADB DEVICES" {A}:background="@drawable/bg_btn_cyan" {A}:textSize="11sp" {A}:layout_margin="3dp"/>
    <Button {A}:id="@+id/btnFastboot" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="46dp" {A}:text="FASTBOOT" {A}:background="@drawable/bg_btn_orange" {A}:textSize="11sp" {A}:layout_margin="3dp"/>
   </LinearLayout>
   <TextView {A}:layout_width="match_parent" {A}:layout_height="wrap_content" {A}:text="FLASH TO PARTITION" {A}:textColor="#00D4FF" {A}:textSize="11sp" {A}:layout_marginTop="10dp" {A}:layout_marginBottom="4dp"/>
   <LinearLayout {A}:layout_width="match_parent" {A}:layout_height="wrap_content" {A}:orientation="horizontal">
    <Button {A}:id="@+id/btnBoot" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="50dp" {A}:text="BOOT" {A}:background="@drawable/bg_btn_cyan" {A}:layout_margin="3dp"/>
    <Button {A}:id="@+id/btnRecovery" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="50dp" {A}:text="RECOVERY" {A}:background="@drawable/bg_btn_cyan" {A}:layout_margin="3dp"/>
    <Button {A}:id="@+id/btnSystem" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="50dp" {A}:text="SYSTEM" {A}:background="@drawable/bg_btn_cyan" {A}:layout_margin="3dp"/>
   </LinearLayout>
   <LinearLayout {A}:layout_width="match_parent" {A}:layout_height="wrap_content" {A}:orientation="horizontal">
    <Button {A}:id="@+id/btnVendor" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="50dp" {A}:text="VENDOR" {A}:background="@drawable/bg_btn_cyan" {A}:layout_margin="3dp"/>
    <Button {A}:id="@+id/btnUserdata" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="50dp" {A}:text="USERDATA" {A}:background="@drawable/bg_btn_orange" {A}:layout_margin="3dp"/>
    <Button {A}:id="@+id/btnVbmeta" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="50dp" {A}:text="VBMETA" {A}:background="@drawable/bg_btn_orange" {A}:textSize="11sp" {A}:layout_margin="3dp"/>
   </LinearLayout>
   <LinearLayout {A}:layout_width="match_parent" {A}:layout_height="wrap_content" {A}:orientation="horizontal">
    <Button {A}:id="@+id/btnUnlock" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="44dp" {A}:text="UNLOCK BL" {A}:background="@drawable/bg_btn_orange" {A}:textSize="11sp" {A}:layout_margin="3dp"/>
    <Button {A}:id="@+id/btnWipe" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="44dp" {A}:text="WIPE DATA" {A}:background="@drawable/bg_btn_red" {A}:textSize="11sp" {A}:layout_margin="3dp"/>
    <Button {A}:id="@+id/btnReboot" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="44dp" {A}:text="REBOOT" {A}:background="@drawable/bg_btn_red" {A}:textSize="11sp" {A}:layout_margin="3dp"/>
   </LinearLayout>
   <LinearLayout {A}:layout_width="match_parent" {A}:layout_height="wrap_content" {A}:orientation="horizontal" {A}:layout_marginTop="8dp">
    <EditText {A}:id="@+id/etCmd" {A}:layout_width="0dp" {A}:layout_weight="1" {A}:layout_height="46dp" {A}:hint="custom fastboot cmd" {A}:textColor="#FFF" {A}:textColorHint="#555" {A}:background="@drawable/bg_card" {A}:padding="10dp" {A}:textSize="11sp"/>
    <Button {A}:id="@+id/btnRun" {A}:layout_width="70dp" {A}:layout_height="46dp" {A}:text="RUN" {A}:background="@drawable/bg_btn_cyan" {A}:layout_marginLeft="5dp"/>
   </LinearLayout>
   <TextView {A}:id="@+id/tvLog" {A}:layout_width="match_parent" {A}:layout_height="320dp" {A}:background="#000000" {A}:text="> Ready" {A}:textColor="#00FF88" {A}:textSize="11sp" {A}:fontFamily="monospace" {A}:padding="8dp" {A}:layout_marginTop="8dp"/>
  </LinearLayout>
 </ScrollView>
</LinearLayout>
""".replace("{A}", A)
open("app/src/main/res/layout/activity_main.xml","w").write('<?xml version="1.0" encoding="utf-8"?>'+layout)

java_code = """
package com.otgflasher.pro;
import {A}.app.PendingIntent; import {A}.content.*; import {A}.hardware.usb.*;
import {A}.net.Uri; import {A}.os.Bundle; import {A}.widget.*;
import androidx.appcompat.app.AppCompatActivity; import java.io.*; import java.util.HashMap;
public class MainActivity extends AppCompatActivity {
 TextView tvLog, tvUsb, tvPath; EditText etCmd; String selectedPath=""; String adbPath="";
 UsbManager usbManager; static final String ACTION_USB="com.otgflasher.pro.USB";
 @Override protected void onCreate(Bundle b){
  super.onCreate(b); setContentView(R.layout.activity_main);
  tvLog=findViewById(R.id.tvLog); tvUsb=findViewById(R.id.tvUsb); tvPath=findViewById(R.id.tvPath); etCmd=findViewById(R.id.etCmd);
  usbManager=(UsbManager)getSystemService(Context.USB_SERVICE);
  adbPath=getApplicationInfo().nativeLibraryDir+"/libadb.so";
  log("> libadb: "+adbPath); checkUsb();
  findViewById(R.id.btnPick).setOnClickListener(v->pickFile());
  findViewById(R.id.btnAdb).setOnClickListener(v->exec("adb devices"));
  findViewById(R.id.btnFastboot).setOnClickListener(v->exec("fastboot devices"));
  findViewById(R.id.btnBoot).setOnClickListener(v->flash("boot"));
  findViewById(R.id.btnRecovery).setOnClickListener(v->flash("recovery"));
  findViewById(R.id.btnSystem).setOnClickListener(v->flash("system"));
  findViewById(R.id.btnVendor).setOnClickListener(v->flash("vendor"));
  findViewById(R.id.btnUserdata).setOnClickListener(v->flash("userdata"));
  findViewById(R.id.btnVbmeta).setOnClickListener(v->{ String img = selectedPath.isEmpty() ? "vbmeta.img" : selectedPath; exec("fastboot --disable-verity --disable-verification flash vbmeta "+img); });
  findViewById(R.id.btnUnlock).setOnClickListener(v->exec("fastboot flashing unlock"));
  findViewById(R.id.btnWipe).setOnClickListener(v->exec("fastboot -w"));
  findViewById(R.id.btnReboot).setOnClickListener(v->exec("fastboot reboot"));
  findViewById(R.id.btnRun).setOnClickListener(v->{ String c = etCmd.getText().toString().trim(); if(!c.isEmpty()) exec(c); });
  registerReceiver(usbReceiver,new IntentFilter(ACTION_USB));
 }
 void checkUsb(){ HashMap<String,UsbDevice> list=usbManager.getDeviceList(); if(list.isEmpty()) tvUsb.setText("USB: No OTG device"); else { for(UsbDevice d:list.values()){ tvUsb.setText("USB: "+d.getDeviceName()+" VID:"+d.getVendorId()); requestPerm(d); } } }
 void requestPerm(UsbDevice dev){ PendingIntent pi=PendingIntent.getBroadcast(this,0,new Intent(ACTION_USB),PendingIntent.FLAG_MUTABLE); usbManager.requestPermission(dev,pi); }
 BroadcastReceiver usbReceiver=new BroadcastReceiver(){ public void onReceive(Context c,Intent i){ if(ACTION_USB.equals(i.getAction())){ boolean ok=i.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED,false); tvUsb.setText(ok?"USB Permission OK - Ready to flash":"USB Permission Denied"); } } };
 void pickFile(){ Intent intent=new Intent(Intent.ACTION_GET_CONTENT); intent.setType("*/*"); startActivityForResult(intent,99); }
 @Override protected void onActivityResult(int req,int res,Intent data){ super.onActivityResult(req,res,data); if(req==99 && res==RESULT_OK && data!=null){ Uri uri=data.getData(); try{ InputStream is=getContentResolver().openInputStream(uri); File out=new File(getCacheDir(),"flash.img"); FileOutputStream fos=new FileOutputStream(out); byte[] buf=new byte[8192]; int len; while((len=is.read(buf))>0) fos.write(buf,0,len); fos.close(); is.close(); selectedPath=out.getAbsolutePath(); tvPath.setText("FILE: "+selectedPath); log("> Loaded: "+selectedPath+" ("+out.length()/1024+" KB)"); } catch(Exception e){ log("ERR: "+e.getMessage()); } } }
 void flash(String part){ if(selectedPath.isEmpty()){ log("! Pick IMG first!"); return; } exec("fastboot flash "+part+" "+selectedPath); }
 void exec(String cmd){ log("$ "+cmd); new Thread(()->{ try{ File f=new File(adbPath); Process p; String full=cmd.startsWith("adb")?adbPath+" "+cmd.substring(3):cmd.startsWith("fastboot")?adbPath+" "+cmd:cmd; if(f.exists() && f.length()>1000) p=Runtime.getRuntime().exec(full); else { try{ p=Runtime.getRuntime().exec(new String[]{"su","-c",cmd}); } catch(Exception e){ p=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd}); } } BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream())); BufferedReader be=new BufferedReader(new InputStreamReader(p.getErrorStream())); String line; while((line=br.readLine())!=null){ String l=line; runOnUiThread(()->log(l)); } while((line=be.readLine())!=null){ String l=line; runOnUiThread(()->log(l)); } p.waitFor(); runOnUiThread(()->log("> done")); } catch(Exception e){ runOnUiThread(()->log("ERR: "+e.getMessage())); } }).start(); }
 void log(String s){ runOnUiThread(()->{ tvLog.append("\n"+s); }); }
}
""".replace("{A}", A)
open("app/src/main/java/com/otgflasher/pro/MainActivity.java","w").write(java_code)
print("builder ok")
