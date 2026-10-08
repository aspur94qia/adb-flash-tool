import os
for p in ["app/src/main/java/com/otgflasher/pro","app/src/main/res/layout","app/src/main/res/values"]:
 os.makedirs(p,exist_ok=True)
open("settings.gradle","w").write("pluginManagement{\n repositories{\n  google()\n  mavenCentral()\n  gradlePluginPortal()\n }\n}\ninclude ':app'\n")
open("build.gradle","w").write("allprojects{\n repositories{\n  google()\n  mavenCentral()\n }\n}\n")
open("gradle.properties","w").write("android.useAndroidX=true\n")
open("app/build.gradle","w").write("plugins{id 'com.android.application'}\nandroid{compileSdk 34; namespace 'com.otgflasher.pro'; defaultConfig{applicationId 'com.otgflasher.pro'; minSdk 26; targetSdk 34; versionCode 1; versionName '1.0'}}\ndependencies{implementation 'androidx.appcompat:appcompat:1.6.1'}\n")
open("app/src/main/res/values/strings.xml","w").write('<resources><string name="app_name">OTG Flasher</string></resources>')
open("app/src/main/AndroidManifest.xml","w").write('<manifest xmlns:android="http://schemas.android.com/apk/res/android"><uses-permission android:name="android.permission.USB_PERMISSION"/><application android:label="OTG Flasher"><activity android:name=".MainActivity" android:exported="true"><intent-filter><action android:name="android.intent.action.MAIN"/><category android:name="android.intent.category.LAUNCHER"/></intent-filter></activity></application></manifest>')
open("app/src/main/res/layout/activity_main.xml","w").write('<?xml version="1.0" encoding="utf-8"?><LinearLayout xmlns:android="http://schemas.android.com/apk/res/android" android:orientation="vertical" android:layout_width="match_parent" android:layout_height="match_parent" android:gravity="center"><TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="OTG FLASHER READY" android:textSize="24sp"/><Button android:id="@+id/btnFlash" android:layout_width="match_parent" android:layout_height="60dp" android:text="FLASH" android:layout_margin="16dp"/></LinearLayout>')
open("app/src/main/java/com/otgflasher/pro/MainActivity.java","w").write("package com.otgflasher.pro;\nimport android.os.Bundle;\nimport androidx.appcompat.app.AppCompatActivity;\npublic class MainActivity extends AppCompatActivity{\n protected void onCreate(Bundle b){\n  super.onCreate(b);\n  setContentView(R.layout.activity_main);\n }\n}\n")
print("builder fixed")
