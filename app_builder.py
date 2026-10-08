import os
for p in ["app/src/main/java/com/otgflasher/pro","app/src/main/res/layout","app/src/main/res/values"]:
 os.makedirs(p,exist_ok=True)
open("settings.gradle","w").write("include ':app'")
open("build.gradle","w").write("buildscript{\n repositories{google();mavenCentral()}\n dependencies{classpath 'com.android.tools.build:gradle:8.2.2'}\n}\nallprojects{\n repositories{google();mavenCentral()}\n}\n")
open("gradle.properties","w").write("android.useAndroidX=true")
open("app/build.gradle","w").write("apply plugin: 'com.android.application'\nrepositories{google();mavenCentral()}\nandroid{compileSdk 34;namespace 'com.otgflasher.pro';defaultConfig{applicationId 'com.otgflasher.pro';minSdk 26;targetSdk 34;versionCode 1;versionName '1.0'}}\n")
open("app/src/main/res/values/strings.xml","w").write('<resources><string name="app_name">OTG Flasher</string></resources>')
open("app/src/main/AndroidManifest.xml","w").write('<manifest xmlns:android="http://schemas.android.com/apk/res/android"><application android:label="OTG Flasher"><activity android:name=".MainActivity" android:exported="true"><intent-filter><action android:name="android.intent.action.MAIN"/><category android:name="android.intent.category.LAUNCHER"/></intent-filter></activity></application></manifest>')
open("app/src/main/res/layout/activity_main.xml","w").write('<?xml version="1.0"?><LinearLayout xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent" android:layout_height="match_parent" android:orientation="vertical"><TextView android:layout_width="match_parent" android:layout_height="wrap_content" android:text="OTG FLASHER v2 READY" android:textSize="20sp" android:gravity="center" android:padding="20dp"/><Button android:id="@+id/btn" android:layout_width="match_parent" android:layout_height="60dp" android:text="FLASH BOOT"/></LinearLayout>')
open("app/src/main/java/com/otgflasher/pro/MainActivity.java","w").write('package com.otgflasher.pro;import android.os.Bundle;import androidx.appcompat.app.AppCompatActivity;public class MainActivity extends AppCompatActivity{protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_main);}}')
