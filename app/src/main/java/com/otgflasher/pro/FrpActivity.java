package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class FrpActivity extends Activity{
TextView txtDetail,log;ProgressBar bar;
@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_frp);txtDetail=findViewById(R.id.txtDetail);log=findViewById(R.id.logView);bar=findViewById(R.id.progressBar);findViewById(R.id.btnBack).setOnClickListener(v->finish());
findViewById(R.id.btn_ADB_FRP_settings_secure_user_setup_complete_1).setOnClickListener(v->run("adb shell content insert --uri content://settings/secure --bind name:s:user_setup_complete --bind value:s:1"));
findViewById(R.id.btn_ADB_FRP_remove_accounts.db).setOnClickListener(v->run("adb shell rm /data/system/users/0/accounts.db; adb shell rm /data/system/users/0/accounts_ce.db"));
findViewById(R.id.btn_ADB_FRP_pm_clear_gsf_login).setOnClickListener(v->run("adb shell pm clear com.google.android.gsf.login; adb shell pm clear com.google.android.gms"));
findViewById(R.id.btn_FASTBOOT_ERASE_frp).setOnClickListener(v->run("fastboot erase frp"));
findViewById(R.id.btn_FASTBOOT_ERASE_config).setOnClickListener(v->run("fastboot erase config"));
findViewById(R.id.btn_FASTBOOT_ERASE_persist).setOnClickListener(v->run("fastboot erase persist"));
findViewById(R.id.btn_MTK_ERASE_frp___nvram).setOnClickListener(v->run("python3 -m mtk e frp,nvram,nvdata"));
findViewById(R.id.btn_EDL_ERASE_frp).setOnClickListener(v->run("edl --loader=/sdcard/Download/prog_firehose_ddr.elf --memory=ufs --erase --memoryname=frp"));
findViewById(R.id.btn_SAMSUNG_FRP_via_adb___cp).setOnClickListener(v->run("adb shell am start -n com.google.android.gsf.login/com.google.android.gsf.login.LoginActivity"));
findViewById(R.id.btn_UNIVERSAL_FRP_bypass).setOnClickListener(v->run("adb shell am start -a android.intent.action.VIEW -d https://www.google.com"));
findViewById(R.id.btn_RESET_FRP).setOnClickListener(v->run("fastboot erase frp; fastboot erase config"));

}
void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=3;if(pr>95) pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}
}
