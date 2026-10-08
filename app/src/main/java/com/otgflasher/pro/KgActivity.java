package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class KgActivity extends Activity{
TextView txtDetail,log;ProgressBar bar;
@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_kg);txtDetail=findViewById(R.id.txtDetail);log=findViewById(R.id.logView);bar=findViewById(R.id.progressBar);findViewById(R.id.btnBack).setOnClickListener(v->finish());
findViewById(R.id.btn_DISABLE_KGCLIENT_pm_disable).setOnClickListener(v->run("adb shell pm disable-user --user 0 com.samsung.android.kgclient"));
findViewById(R.id.btn_DISABLE_MDM_pm_disable).setOnClickListener(v->run("adb shell pm disable-user --user 0 com.samsung.android.mdm; adb shell pm disable-user --user 0 com.samsung.android.mdm.service"));
findViewById(R.id.btn_DISABLE_KNOX_pm_disable_knox).setOnClickListener(v->run("adb shell pm disable-user --user 0 com.samsung.knox.kpecore; adb shell pm disable-user --user 0 com.samsung.knox.securefolder"));
findViewById(R.id.btn_REMOVE_KGCLIENT_uninstall).setOnClickListener(v->run("adb shell pm uninstall -k --user 0 com.samsung.android.kgclient"));
findViewById(R.id.btn_REMOVE_MDM).setOnClickListener(v->run("adb shell pm uninstall -k --user 0 com.samsung.android.mdm"));
findViewById(R.id.btn_BYPASS_KG_via_adb_shell_settings).setOnClickListener(v->run("adb shell settings put secure kg_state 0; adb shell settings put secure kg_locked 0"));
findViewById(R.id.btn_BYPASS_KG_via_build.prop).setOnClickListener(v->run("adb shell setprop ro.config.knox 0; adb shell setprop ro.config.kg 0"));
findViewById(R.id.btn_DISABLE_KG_via_samfw).setOnClickListener(v->run("adb shell am start -n com.samfw.frp/com.samfw.frp.MainActivity"));
findViewById(R.id.btn_RESET_KG).setOnClickListener(v->run("adb shell pm clear com.samsung.android.kgclient; adb reboot"));

}
void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=3;if(pr>95) pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}
}
