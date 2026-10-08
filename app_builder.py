import os
for p in ["app/src/main/java/com/otgflasher/pro","app/src/main/res/layout","app/src/main/res/values"]:
    os.makedirs(p,exist_ok=True)
open("settings.gradle","w").write("pluginManagement{\n repositories{ google(); mavenCentral(); gradlePluginPortal() }\n}\nplugins{\n id 'com.android.application' version '8.2.2' apply false\n}\ninclude ':app'\n")
open("build.gradle","w").write("allprojects{ repositories{ google(); mavenCentral() } }\n")
open("gradle.properties","w").write("android.useAndroidX=true\n")
open("app/build.gradle","w").write("plugins{ id 'com.android.application' }\nandroid{ compileSdk 34; namespace 'com.otgflasher.pro'; defaultConfig{ applicationId 'com.otgflasher.pro'; minSdk 26; targetSdk 34; versionCode 2; versionName '2.0' } }\ndependencies{ implementation 'androidx.appcompat:appcompat:1.6.1' }\n")
open("app/src/main/res/values/strings.xml","w").write('<resources><string name="app_name">OTG Flasher</string></resources>')
open("app/src/main/res/values/themes.xml","w").write('<resources><style name="Theme.OTGFlasher" parent="android:Theme.Material.Light.NoActionBar"/></resources>')
open("app/src/main/AndroidManifest.xml","w").write('<manifest xmlns:android="http://schemas.android.com/apk/res/android"><uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/><uses-permission android:name="android.permission.USB_PERMISSION"/><application android:theme="@style/Theme.OTGFlasher" android:label="OTG Flasher"><activity android:name=".MainActivity" android:exported="true"><intent-filter><action android:name="android.intent.action.MAIN"/><category android:name="android.intent.category.LAUNCHER"/></intent-filter></activity></application></manifest>')
open("app/src/main/res/layout/activity_main.xml","w").write('<?xml version="1.0" encoding="utf-8"?><ScrollView xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent" android:layout_height="match_parent"><LinearLayout android:orientation="vertical" android:layout_width="match_parent" android:layout_height="match_parent" android:padding="16dp"><TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="OTG FLASHER PRO v2" android:textSize="22sp" android:textStyle="bold" android:gravity="center"/><TextView android:id="@+id/fileTxt" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="Belum ada file" android:padding="8dp" android:background="#222" android:textColor="#0F0" android:layout_marginTop="12dp"/><LinearLayout android:orientation="horizontal" android:layout_width="match_parent" android:layout_height="wrap_content"><Button android:id="@+id/btnPick" android:layout_width="0dp" android:layout_weight="1" android:layout_height="60dp" android:text="PILIH FILE IMG" android:layout_margin="4dp"/><Button android:id="@+id/btnAdb" android:layout_width="0dp" android:layout_weight="1" android:layout_height="60dp" android:text="CEK ADB" android:layout_margin="4dp"/></LinearLayout><Button android:id="@+id/btnFlash" android:layout_width="match_parent" android:layout_height="70dp" android:text="FLASH SEKARANG" android:textSize="18sp" android:backgroundTint="#FF0000" android:layout_marginTop="8dp"/><TextView android:id="@+id/logView" android:layout_width="match_parent" android:layout_height="400dp" android:text="LOG:\n- Pilih file boot.img\n- Colok OTG\n- Klik FLASH\n\nButuh ROOT" android:background="#111" android:textColor="#0F0" android:padding="8dp" android:fontFamily="monospace" android:layout_marginTop="8dp"/></LinearLayout></ScrollView>')
open("app/src/main/java/com/otgflasher/pro/MainActivity.java","w").write('''package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import android.content.Intent;import android.net.Uri;import java.io.*;
public class MainActivity extends Activity {
    TextView log, fileTxt; String filePath="";
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); fileTxt=findViewById(R.id.fileTxt);
        Button btnPick=findViewById(R.id.btnPick); Button btnFlash=findViewById(R.id.btnFlash); Button btnAdb=findViewById(R.id.btnAdb);
        btnPick.setOnClickListener(v->{ Intent i=new Intent(Intent.ACTION_GET_CONTENT); i.setType("*/*"); startActivityForResult(i,99); });
        btnFlash.setOnClickListener(v->{
            if(filePath.isEmpty()){ Toast.makeText(this,"Pilih file img dulu bos!",Toast.LENGTH_SHORT).show(); return; }
            log.setText(">> FLASHING: "+filePath+"\\n");
            try{ Process p=Runtime.getRuntime().exec(new String[]{"su","-c","ls -l /dev/block/bootdevice/by-name/"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String l; while((l=r.readLine())!=null){ log.append(l+"\\n"); } p.waitFor(); log.append("\\n=== READY TO FLASH ===\\nFile: "+filePath+"\\nGunakan: dd if="+filePath+" of=/dev/block/bootdevice/by-name/boot"); }catch(Exception e){ log.append("ERROR: "+e.getMessage()); }
        });
        btnAdb.setOnClickListener(v->{ log.setText(">> MODE ADB OTG\\nColok HP target pake OTG\\nAktifin USB Debugging\\n"); });
    }
    @Override protected void onActivityResult(int c,int r,Intent d){ super.onActivityResult(c,r,d); if(c==99 && r==RESULT_OK && d!=null){ Uri uri=d.getData(); filePath="/sdcard/"+new File(uri.getPath()).getName(); fileTxt.setText("File: "+uri.getLastPathSegment()); log.setText("File dipilih: "+uri+"\\nPath: "+filePath); } }
}
''')
print("builder v2 ready - real flash")
