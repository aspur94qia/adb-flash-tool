import os
for p in ["app/src/main/java/com/otgflasher/pro","app/src/main/res/layout","app/src/main/res/values"]:
    os.makedirs(p,exist_ok=True)
open("settings.gradle","w").write("pluginManagement{ repositories{ google(); mavenCentral(); gradlePluginPortal() } }\nplugins{ id 'com.android.application' version '8.2.2' apply false }\ninclude ':app'\n")
open("build.gradle","w").write("allprojects{ repositories{ google(); mavenCentral() } }\n")
open("gradle.properties","w").write("android.useAndroidX=true\n")
open("app/build.gradle","w").write("plugins{ id 'com.android.application' }\nandroid{ compileSdk 34; namespace 'com.otgflasher.pro'; defaultConfig{ applicationId 'com.otgflasher.pro'; minSdk 26; targetSdk 34; versionCode 13; versionName '13.0 MANY TOOLS' } }\ndependencies{ implementation 'androidx.appcompat:appcompat:1.6.1' }\n")
open("app/src/main/res/values/strings.xml","w").write('<resources><string name="app_name">OTG Flasher v9</string></resources>')
open("app/src/main/res/values/themes.xml","w").write('<resources><style name="Theme.OTGFlasher" parent="android:Theme.Material.Light.NoActionBar"/></resources>')
open("app/src/main/AndroidManifest.xml","w").write('<manifest xmlns:android="http://schemas.android.com/apk/res/android"><uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/><uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/><uses-feature android:name="android.hardware.usb.host"/><application android:theme="@style/Theme.OTGFlasher" android:label="OTG Flasher v9" android:requestLegacyExternalStorage="true"><activity android:name=".MainActivity" android:exported="true"><intent-filter><action android:name="android.intent.action.MAIN"/><category android:name="android.intent.category.LAUNCHER"/></intent-filter></activity><activity android:name=".MtkActivity"/><activity android:name=".FastbootActivity"/><activity android:name=".EdlActivity"/><activity android:name=".OdinActivity"/><activity android:name=".SpdActivity"/><activity android:name=".PayloadActivity"/><activity android:name=".SuperActivity"/><activity android:name=".OfpActivity"/><activity android:name=".FrpActivity"/><activity android:name=".MiActivity"/><activity android:name=".QcnActivity"/><activity android:name=".KgActivity"/><activity android:name=".UnlockActivity"/><activity android:name=".PartActivity"/><activity android:name=".FileManagerActivity"/></application></manifest>')

# MAIN - 14 ICONS GRID - SEMUA DIPISAH
open("app/src/main/res/layout/activity_main.xml","w").write('''<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent" android:layout_height="match_parent" android:background="#0F1115">
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:padding="10dp">
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="52dp" android:background="#2ECC71" android:gravity="center_vertical" android:padding="10dp"><TextView android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:text="OTG FLASHER v9" android:textSize="17sp" android:textStyle="bold" android:textColor="#FFF"/></LinearLayout>
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:background="#1A1D24" android:padding="10dp" android:layout_marginTop="8dp"><TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="LOG CONSOLE" android:textSize="9sp" android:textStyle="bold" android:textColor="#7F8C8D"/><TextView android:id="@+id/logView" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="Ready. No device connected." android:textColor="#BDC3C7" android:textSize="10sp" android:paddingTop="4dp"/><LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="8dp"><Button android:id="@+id/btnUsb" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="CONNECT OTG" android:textColor="#2ECC71" android:backgroundTint="#1A1D24" android:textSize="9sp"/><Button android:id="@+id/btnFileMgr" android:layout_width="0dp" android:layout_weight="1" android:layout_height="36dp" android:text="FILE MANAGER" android:textColor="#FFF" android:backgroundTint="#1A1D24" android:textSize="9sp" android:layout_marginStart="6dp"/></LinearLayout></LinearLayout>
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="TOOLS - SEMUA DIPISAH" android:textSize="10sp" android:textStyle="bold" android:textColor="#2ECC71" android:layout_marginTop="12dp"/>
<!-- ROW 1 -->
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="6dp">
<LinearLayout android:id="@+id/cardFastboot" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="⚡" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="FASTBOOT" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
<LinearLayout android:id="@+id/cardMtk" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp" android:layout_marginStart="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="🔲" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="MTK SP" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
<LinearLayout android:id="@+id/cardEdl" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp" android:layout_marginStart="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="🔷" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="EDL 9008" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
</LinearLayout>
<!-- ROW 2 -->
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="6dp">
<LinearLayout android:id="@+id/cardOdin" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="📱" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="ODIN" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
<LinearLayout android:id="@+id/cardSpd" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp" android:layout_marginStart="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="🧩" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="SPD PAC" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
<LinearLayout android:id="@+id/cardPayload" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp" android:layout_marginStart="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="📦" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="PAYLOAD" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
</LinearLayout>
<!-- ROW 3 -->
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="6dp">
<LinearLayout android:id="@+id/cardSuper" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="💾" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="SUPER" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
<LinearLayout android:id="@+id/cardOfp" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp" android:layout_marginStart="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="🗜️" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="OFP" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
<LinearLayout android:id="@+id/cardFrp" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp" android:layout_marginStart="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="🔓" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="FRP" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
</LinearLayout>
<!-- ROW 4 -->
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="6dp">
<LinearLayout android:id="@+id/cardMi" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="🔑" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="MI ACC" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
<LinearLayout android:id="@+id/cardQcn" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp" android:layout_marginStart="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="📡" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="QCN" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
<LinearLayout android:id="@+id/cardKg" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp" android:layout_marginStart="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="🛡️" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="KG/MDM" android:textSize="8sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
</LinearLayout>
<!-- ROW 5 -->
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="6dp">
<LinearLayout android:id="@+id/cardUnlock" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="🔓" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="UNLOCK BL" android:textSize="7sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
<LinearLayout android:id="@+id/cardPart" android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#1A1D24" android:gravity="center" android:padding="6dp" android:layout_marginStart="6dp"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="💿" android:textSize="22sp"/><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="PARTITION" android:textSize="7sp" android:textColor="#FFF" android:textStyle="bold" android:gravity="center" android:layout_marginTop="4dp"/></LinearLayout>
<LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1" android:layout_height="90dp" android:background="#0F1115" android:gravity="center" android:padding="6dp" android:layout_marginStart="6dp"></LinearLayout>
</LinearLayout>
</LinearLayout>
</ScrollView>
''')

# DETAIL TEMPLATE - FILE PICKER + PROGRESS INSIDE
detail='''<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent" android:layout_height="match_parent" android:background="#0F1115">
<LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:padding="12dp">
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="52dp" android:background="#1A1D24" android:gravity="center_vertical" android:padding="10dp"><Button android:id="@+id/btnBack" android:layout_width="40dp" android:layout_height="40dp" android:text="←" android:textColor="#FFF" android:backgroundTint="#1A1D24" android:textSize="16sp"/><TextView android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:text="%TITLE%" android:textSize="14sp" android:textStyle="bold" android:textColor="#FFF" android:gravity="center"/></LinearLayout>
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="SELECT FILE" android:textSize="9sp" android:textStyle="bold" android:textColor="#7F8C8D" android:layout_marginTop="14dp"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="46dp" android:background="#1A1D24" android:gravity="center_vertical" android:padding="8dp" android:layout_marginTop="6dp"><TextView android:id="@+id/txtScatter" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:text="/sdcard/Download/%HINT%" android:textSize="10sp" android:textColor="#FFF"/><Button android:id="@+id/btnBrowseScatter" android:layout_width="76dp" android:layout_height="34dp" android:text="BROWSE" android:textColor="#FFF" android:backgroundTint="#2ECC71" android:textSize="9sp"/></LinearLayout>
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="FIRMWARE FOLDER / OPTIONS" android:textSize="9sp" android:textStyle="bold" android:textColor="#7F8C8D" android:layout_marginTop="10dp"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="46dp" android:background="#1A1D24" android:gravity="center_vertical" android:padding="8dp" android:layout_marginTop="6dp"><TextView android:id="@+id/txtFw" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:text="Select folder..." android:textSize="10sp" android:textColor="#7F8C8D"/><Button android:id="@+id/btnBrowseFw" android:layout_width="76dp" android:layout_height="34dp" android:text="BROWSE" android:textColor="#FFF" android:backgroundTint="#2C3E50" android:textSize="9sp"/></LinearLayout>
<CheckBox android:id="@+id/cbDa" android:layout_width="match_parent" android:layout_height="36dp" android:text="DA/SLA Bypass" android:textColor="#FFF" android:textSize="11sp" android:checked="true" android:layout_marginTop="6dp"/>
<CheckBox android:id="@+id/cbFormat" android:layout_width="match_parent" android:layout_height="36dp" android:text="Format All + Download" android:textColor="#FFF" android:textSize="11sp"/>
<TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="PROGRESS BAR - DI DALAM" android:textSize="9sp" android:textStyle="bold" android:textColor="#2ECC71" android:layout_marginTop="14dp"/>
<LinearLayout android:id="@+id/progressBox" android:orientation="vertical" android:layout_width="match_parent" android:layout_height="wrap_content" android:background="#1A1D24" android:padding="10dp" android:layout_marginTop="6dp"><TextView android:id="@+id/txtProgressLabel" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="Ready - %TITLE%" android:textSize="11sp" android:textStyle="bold" android:textColor="#FFF"/><ProgressBar android:id="@+id/progressBar" style="?android:attr/progressBarStyleHorizontal" android:layout_width="match_parent" android:layout_height="12dp" android:max="100" android:progress="0" android:progressTint="#2ECC71" android:layout_marginTop="6dp"/><TextView android:id="@+id/txtProgressDetail" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="0MB / 0MB" android:textSize="9sp" android:textColor="#7F8C8D" android:layout_marginTop="4dp"/></LinearLayout>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="14dp"><Button android:id="@+id/btnFlash" android:layout_width="0dp" android:layout_weight="1" android:layout_height="52dp" android:text="FLASH" android:textColor="#FFF" android:backgroundTint="#2ECC71" android:textSize="11sp" android:textStyle="bold"/><Button android:id="@+id/btnReadback" android:layout_width="0dp" android:layout_weight="1" android:layout_height="52dp" android:text="READBACK" android:textColor="#FFF" android:backgroundTint="#34495E" android:textSize="9sp" android:layout_marginStart="6dp"/></LinearLayout>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginTop="6dp"><Button android:id="@+id/btnFormat" android:layout_width="0dp" android:layout_weight="1" android:layout_height="52dp" android:text="FORMAT" android:textColor="#FFF" android:backgroundTint="#E67E22" android:textSize="10sp"/><Button android:id="@+id/btnBypass" android:layout_width="0dp" android:layout_weight="1" android:layout_height="52dp" android:text="BYPASS" android:textColor="#FFF" android:backgroundTint="#8E44AD" android:textSize="9sp" android:layout_marginStart="6dp"/></LinearLayout>
<TextView android:id="@+id/logView" android:layout_width="match_parent" android:layout_height="wrap_content" android:background="#000" android:textColor="#0F0" android:textSize="9sp" android:fontFamily="monospace" android:padding="8dp" android:layout_marginTop="10dp" android:text="[LOG] Ready\n"/>
</LinearLayout>
</ScrollView>
'''

# CREATE ALL DETAIL LAYOUTS
tools=[("mtk","MTK SP FLASH","MT6765_scatter.txt"),("fastboot","UNIVERSAL FASTBOOT","boot.img"),("edl","QUALCOMM EDL 9008","prog_firehose.elf"),("odin","SAMSUNG ODIN","AP.tar.md5"),("spd","SPD PAC FLASH","firmware.pac"),("payload","PAYLOAD.BIN","payload.bin"),("super","SUPER UNPACK","super.img"),("ofp","OFP EXTRACT","firmware.ofp"),("frp","FRP BYPASS",""),("mi","MI ACCOUNT",""),("qcn","QCN BACKUP",""),("kg","KG MDM BYPASS",""),("unlock","UNLOCK BL",""),("part","PARTITION MGR","")]
for lay,title,hint in tools:
    open(f"app/src/main/res/layout/activity_{lay}.xml","w").write(detail.replace("%TITLE%",title).replace("%HINT%",hint))

open("app/src/main/res/layout/activity_file_manager.xml","w").write('''<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android" android:orientation="vertical" android:layout_width="match_parent" android:layout_height="match_parent" android:background="#0F1115">
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="56dp" android:background="#2ECC71" android:gravity="center_vertical" android:padding="8dp"><Button android:id="@+id/btnBack" android:layout_width="60dp" android:layout_height="40dp" android:text="BACK" android:textColor="#FFF" android:textSize="10sp"/><TextView android:id="@+id/txtPath" android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" android:text="/sdcard/Download" android:textColor="#FFF" android:textSize="11sp" android:padding="8dp"/><Button android:id="@+id/btnUp" android:layout_width="50dp" android:layout_height="40dp" android:text="UP" android:textColor="#FFF" android:textSize="10sp"/></LinearLayout>
<ListView android:id="@+id/listFiles" android:layout_width="match_parent" android:layout_height="0dp" android:layout_weight="1" android:background="#1A1D24"/>
<TextView android:id="@+id/txtSelected" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="No file selected" android:textColor="#0F0" android:textSize="10sp" android:padding="8dp" android:background="#222"/>
<LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content" android:padding="6dp"><Button android:id="@+id/btnExtract" android:layout_width="0dp" android:layout_weight="1" android:layout_height="44dp" android:text="EXTRACT" android:textColor="#FFF" android:textSize="9sp" android:backgroundTint="#2ECC71"/><Button android:id="@+id/btnCreateZip" android:layout_width="0dp" android:layout_weight="1" android:layout_height="44dp" android:text="ZIP" android:textColor="#FFF" android:textSize="9sp" android:backgroundTint="#E67E22" android:layout_marginStart="4dp"/><Button android:id="@+id/btnDelete" android:layout_width="0dp" android:layout_weight="1" android:layout_height="44dp" android:text="DELETE" android:textColor="#FFF" android:textSize="9sp" android:backgroundTint="#E74C3C" android:layout_marginStart="4dp"/></LinearLayout>
</LinearLayout>
''')

open("app/src/main/java/com/otgflasher/pro/MainActivity.java","w").write(r'''
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.content.Intent;import android.widget.*;import android.hardware.usb.*;
public class MainActivity extends Activity {
    TextView log; UsbManager um;
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); um=(UsbManager)getSystemService(USB_SERVICE);
        findViewById(R.id.btnUsb).setOnClickListener(v->connect());
        findViewById(R.id.btnFileMgr).setOnClickListener(v->startActivity(new Intent(this, FileManagerActivity.class)));
        findViewById(R.id.cardFastboot).setOnClickListener(v->startActivity(new Intent(this, FastbootActivity.class)));
        findViewById(R.id.cardMtk).setOnClickListener(v->startActivity(new Intent(this, MtkActivity.class)));
        findViewById(R.id.cardEdl).setOnClickListener(v->startActivity(new Intent(this, EdlActivity.class)));
        findViewById(R.id.cardOdin).setOnClickListener(v->startActivity(new Intent(this, OdinActivity.class)));
        findViewById(R.id.cardSpd).setOnClickListener(v->startActivity(new Intent(this, SpdActivity.class)));
        findViewById(R.id.cardPayload).setOnClickListener(v->startActivity(new Intent(this, PayloadActivity.class)));
        findViewById(R.id.cardSuper).setOnClickListener(v->startActivity(new Intent(this, SuperActivity.class)));
        findViewById(R.id.cardOfp).setOnClickListener(v->startActivity(new Intent(this, OfpActivity.class)));
        findViewById(R.id.cardFrp).setOnClickListener(v->startActivity(new Intent(this, FrpActivity.class)));
        findViewById(R.id.cardMi).setOnClickListener(v->startActivity(new Intent(this, MiActivity.class)));
        findViewById(R.id.cardQcn).setOnClickListener(v->startActivity(new Intent(this, QcnActivity.class)));
        findViewById(R.id.cardKg).setOnClickListener(v->startActivity(new Intent(this, KgActivity.class)));
        findViewById(R.id.cardUnlock).setOnClickListener(v->startActivity(new Intent(this, UnlockActivity.class)));
        findViewById(R.id.cardPart).setOnClickListener(v->startActivity(new Intent(this, PartActivity.class)));
    }
    void connect(){ java.util.HashMap<String,UsbDevice> ds=um.getDeviceList(); if(ds.isEmpty()){ log.setText("Ready.\nNo device connected.\nVID 0E8D=MTK\n05C6:9008=EDL\n04E8=SAMSUNG\n18D1=FASTBOOT"); return; } for(android.hardware.usb.UsbDevice d:ds.values()){ log.append("\nUSB: VID 0x"+Integer.toHexString(d.getVendorId())); } }
}
''')

# MTK FULL
open("app/src/main/java/com/otgflasher/pro/MtkActivity.java","w").write(r'''
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class MtkActivity extends Activity {
    TextView txtScatter, txtFw, txtLabel, txtDetail, log; ProgressBar bar;
    String scatter="/sdcard/Download/MT6765_scatter.txt", fw="/sdcard/Download/";
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_mtk);
        txtScatter=findViewById(R.id.txtScatter); txtFw=findViewById(R.id.txtFw); txtLabel=findViewById(R.id.txtProgressLabel); txtDetail=findViewById(R.id.txtProgressDetail); log=findViewById(R.id.logView); bar=findViewById(R.id.progressBar);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        txtScatter.setText(scatter); txtFw.setText(fw);
        findViewById(R.id.btnBrowseScatter).setOnClickListener(v->pick(true));
        findViewById(R.id.btnBrowseFw).setOnClickListener(v->pick(false));
        findViewById(R.id.btnFlash).setOnClickListener(v->runProg("FLASH MTK","mtk w --scatter "+scatter+" --firmware "+fw+" --bypass"));
        findViewById(R.id.btnBypass).setOnClickListener(v->runProg("BYPASS AUTH","mtk payload || python3 -m mtk payload"));
        findViewById(R.id.btnReadback).setOnClickListener(v->runProg("READBACK","mtk r boot /sdcard/Download/boot.img"));
        findViewById(R.id.btnFormat).setOnClickListener(v->runProg("FORMAT","mtk e frp,userdata"));
    }
    void pick(boolean isScatter){ File cur=new File("/sdcard/Download"); File[] arr=cur.listFiles(); if(arr==null) return; String[] n=new String[arr.length]; for(int i=0;i<arr.length;i++) n[i]=arr[i].getName(); new android.app.AlertDialog.Builder(this).setTitle("Select").setItems(n,(d,w)->{ if(isScatter){ scatter=arr[w].getAbsolutePath(); txtScatter.setText(scatter); } else { fw=arr[w].isDirectory()?arr[w].getAbsolutePath():arr[w].getParent(); txtFw.setText(fw); } }).show(); }
    void runProg(String t,String c){ bar.setProgress(10); txtLabel.setText(t); log.append("\n> $ "+c+"\n"); new Thread(()->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String l; int pr=10; while((l=r.readLine())!=null){ pr+=4; if(pr>90) pr=90; int pp=pr; String ll=l; runOnUiThread(()->{ bar.setProgress(pp); txtDetail.setText(pp+"%"); log.append(ll+"\n"); }); } p.waitFor(); runOnUiThread(()->{ bar.setProgress(100); txtLabel.setText(t+" DONE"); }); }catch(Exception e){ runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n")); } }).start(); }
}
''')

# GENERIC TEMPLATE FOR OTHERS
tmpl=r'''
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class %s extends Activity {
    TextView txtScatter, txtFw, txtLabel, txtDetail, log; ProgressBar bar;
    String file="/sdcard/Download/%s";
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_%s);
        txtScatter=findViewById(R.id.txtScatter); txtFw=findViewById(R.id.txtFw); txtLabel=findViewById(R.id.txtProgressLabel); txtDetail=findViewById(R.id.txtProgressDetail); log=findViewById(R.id.logView); bar=findViewById(R.id.progressBar);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        txtScatter.setText(file);
        findViewById(R.id.btnBrowseScatter).setOnClickListener(v->pick());
        findViewById(R.id.btnBrowseFw).setOnClickListener(v->pick());
        findViewById(R.id.btnFlash).setOnClickListener(v->runProg("%s","%s"));
        findViewById(R.id.btnBypass).setOnClickListener(v->runProg("BYPASS","echo bypass %s"));
        findViewById(R.id.btnReadback).setOnClickListener(v->runProg("READ","echo read"));
        findViewById(R.id.btnFormat).setOnClickListener(v->runProg("FORMAT","echo format"));
    }
    void pick(){ File cur=new File("/sdcard/Download"); File[] arr=cur.listFiles(); if(arr==null) return; String[] n=new String[arr.length]; for(int i=0;i<arr.length;i++) n[i]=arr[i].getName(); new android.app.AlertDialog.Builder(this).setTitle("Select").setItems(n,(d,w)->{ file=arr[w].getAbsolutePath(); txtScatter.setText(file); }).show(); }
    void runProg(String t,String c){ bar.setProgress(10); txtLabel.setText(t); log.append("\n> $ "+c+"\n"); new Thread(()->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String l; int pr=10; while((l=r.readLine())!=null){ pr+=5; if(pr>90) pr=90; int pp=pr; String ll=l; runOnUiThread(()->{ bar.setProgress(pp); txtDetail.setText(pp+"%%"); log.append(ll+"\n"); }); } p.waitFor(); runOnUiThread(()->{ bar.setProgress(100); txtLabel.setText(t+" DONE"); }); }catch(Exception e){ runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n")); } }).start(); }
}
'''
cfgs=[("FastbootActivity","fastboot","boot.img","FLASH FASTBOOT","fastboot flash boot %s"),("EdlActivity","edl","prog_firehose.elf","FLASH EDL","edl --loader %s"),("OdinActivity","odin","AP.tar.md5","FLASH ODIN","heimdall flash --AP %s"),("SpdActivity","spd","firmware.pac","FLASH PAC","echo spd flash %s"),("PayloadActivity","payload","payload.bin","EXTRACT PAYLOAD","payload_dumper %s"),("SuperActivity","super","super.img","UNPACK SUPER","lpunpack %s /sdcard/Download/out/"),("OfpActivity","ofp","firmware.ofp","EXTRACT OFP","python3 ofp_extractor.py %s"),("FrpActivity","frp","","FRP BYPASS","adb shell content insert --uri content://settings/secure --bind name:s:user_setup_complete --bind value:s:1"),("MiActivity","mi","","MI BYPASS","adb shell pm disable-user com.xiaomi.finddevice"),("QcnActivity","qcn","","BACKUP QCN","dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/modemst1.img"),("KgActivity","kg","","KG BYPASS","adb shell pm disable-user com.samsung.android.kgclient"),("UnlockActivity","unlock","","UNLOCK BL","fastboot flashing unlock"),("PartActivity","part","","PARTITION","sgdisk --print /dev/block/sda")]
for cls,lay,fname,title,cmd in cfgs:
    final_cmd=cmd % " /sdcard/Download/"+fname if "%s" in cmd else cmd
    # escape % for java
    java_cmd=final_cmd.replace("%","%%") if "%%" not in final_cmd else final_cmd
    # Actually write raw
    open(f"app/src/main/java/com/otgflasher/pro/{cls}.java","w").write(tmpl % (cls, fname, lay, title, final_cmd, cls))

open("app/src/main/java/com/otgflasher/pro/FileManagerActivity.java","w").write(r'''
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;import java.util.*;
public class FileManagerActivity extends Activity {
    TextView txtPath, txtSelected; ListView listFiles; File cur=new File("/sdcard/Download"); java.util.List<File> files=new java.util.ArrayList<>(); File selected=null;
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_file_manager);
        txtPath=findViewById(R.id.txtPath); txtSelected=findViewById(R.id.txtSelected); listFiles=findViewById(R.id.listFiles);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        findViewById(R.id.btnUp).setOnClickListener(v->{ if(cur.getParentFile()!=null){ cur=cur.getParentFile(); list(); }});
        findViewById(R.id.btnExtract).setOnClickListener(v->doCmd("cd '"+cur.getAbsolutePath()+"' && unzip -o '"+(selected!=null?selected.getAbsolutePath():"")+"' 2>&1"));
        findViewById(R.id.btnCreateZip).setOnClickListener(v->doCmd("cd '"+cur.getAbsolutePath()+"' && zip -r '"+(selected!=null?selected.getName():"archive")+".zip' '"+(selected!=null?selected.getName():".")+"' 2>&1"));
        findViewById(R.id.btnDelete).setOnClickListener(v->{ if(selected!=null){ selected.delete(); list(); }});
        listFiles.setOnItemClickListener((a,vw,pos,id)->{ File f=files.get(pos); if(f.isDirectory()){ cur=f; list(); } else { selected=f; txtSelected.setText("SELECTED: "+f.getName()); }});
        list();
    }
    void list(){ File[] arr=cur.listFiles(); files.clear(); java.util.List<String> names=new java.util.ArrayList<>(); if(arr!=null){ java.util.Arrays.sort(arr); for(File f:arr){ if(f.getName().startsWith(".")) continue; files.add(f); String t=f.isDirectory()?"[DIR] ":"[FILE] "; names.add(t+f.getName()); } } txtPath.setText(cur.getAbsolutePath()+" ("+files.size()+")"); listFiles.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names)); }
    void doCmd(String cmd){ txtSelected.setText("Run: "+cmd); new Thread(()->{ try{ Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd}); BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream())); StringBuilder sb=new StringBuilder(); String l; while((l=r.readLine())!=null) sb.append(l).append("\n"); pr.waitFor(); String res=sb.toString(); runOnUiThread(()->{ txtSelected.setText(res); list(); }); }catch(Exception e){ runOnUiThread(()->txtSelected.setText("ERR: "+e.getMessage())); } }).start(); }
}
''')
print("v13 MANY TOOLS")
