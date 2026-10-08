package com.otgflasher.pro;import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;public class AdbActivity extends Activity{TextView txtDetail,log;ProgressBar bar;@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_adb);txtDetail=findViewById(R.id.txtDetail);log=findViewById(R.id.logView);bar=findViewById(R.id.progressBar);findViewById(R.id.btnBack).setOnClickListener(v->finish());findViewById(R.id.btn_DEVICES_adb_devices).setOnClickListener(v->run("adb devices"));
findViewById(R.id.btn_SHELL_adb_shell).setOnClickListener(v->run("adb shell"));
findViewById(R.id.btn_LOGCAT_adb_logcat).setOnClickListener(v->run("adb logcat"));
findViewById(R.id.btn_INSTALL_APK).setOnClickListener(v->run("adb install /sdcard/Download/app.apk"));
findViewById(R.id.btn_PUSH_file_to_sdcard).setOnClickListener(v->run("adb push /sdcard/Download/file.txt /sdcard/Download/"));
findViewById(R.id.btn_PULL_file_from_sdcard).setOnClickListener(v->run("adb pull /sdcard/Download/file.txt /sdcard/Download/pull/"));
findViewById(R.id.btn_SCREENCAP).setOnClickListener(v->run("adb shell screencap /sdcard/Download/screen.png"));
findViewById(R.id.btn_SCREENRECORD).setOnClickListener(v->run("adb shell screenrecord /sdcard/Download/record.mp4"));
findViewById(R.id.btn_REBOOT).setOnClickListener(v->run("adb reboot"));
findViewById(R.id.btn_REBOOT_bootloader).setOnClickListener(v->run("adb reboot bootloader"));
findViewById(R.id.btn_REBOOT_recovery).setOnClickListener(v->run("adb reboot recovery"));
findViewById(R.id.btn_REBOOT_edl).setOnClickListener(v->run("adb reboot edl"));
findViewById(R.id.btn_ROOT_adb_root).setOnClickListener(v->run("adb root"));
findViewById(R.id.btn_REMOUNT).setOnClickListener(v->run("adb remount"));
findViewById(R.id.btn_DISABLE-VERITY).setOnClickListener(v->run("adb disable-verity"));
findViewById(R.id.btn_SHELL_pm_list_packages).setOnClickListener(v->run("adb shell pm list packages"));
}void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=2;if(pr>95)pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}}