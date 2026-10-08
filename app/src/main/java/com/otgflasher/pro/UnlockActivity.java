package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class UnlockActivity extends Activity{
TextView txtDetail,log;ProgressBar bar;
@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_unlock);txtDetail=findViewById(R.id.txtDetail);log=findViewById(R.id.logView);bar=findViewById(R.id.progressBar);findViewById(R.id.btnBack).setOnClickListener(v->finish());
findViewById(R.id.btn_CHECK_BL_status_fastboot_oem_device-info).setOnClickListener(v->run("fastboot oem device-info; fastboot getvar unlocked; fastboot flashing get_unlock_ability"));
findViewById(R.id.btn_UNLOCK_flashing_unlock).setOnClickListener(v->run("fastboot flashing unlock"));
findViewById(R.id.btn_UNLOCK_flashing_unlock_critical).setOnClickListener(v->run("fastboot flashing unlock_critical"));
findViewById(R.id.btn_UNLOCK_oem_unlock).setOnClickListener(v->run("fastboot oem unlock"));
findViewById(R.id.btn_UNLOCK_oem_unlock-go).setOnClickListener(v->run("fastboot oem unlock-go"));
findViewById(R.id.btn_LOCK_flashing_lock).setOnClickListener(v->run("fastboot flashing lock"));
findViewById(R.id.btn_LOCK_flashing_lock_critical).setOnClickListener(v->run("fastboot flashing lock_critical"));
findViewById(R.id.btn_MTK_UNLOCK).setOnClickListener(v->run("python3 -m mtk da seccfg unlock; python3 -m mtk xflash seccfg unlock"));
findViewById(R.id.btn_SAMSUNG_UNLOCK_via_oem_unlock).setOnClickListener(v->run("adb shell svc oem unlock; fastboot oem unlock"));
findViewById(R.id.btn_RELOCK).setOnClickListener(v->run("fastboot flashing lock; fastboot oem lock"));

}
void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=3;if(pr>95) pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}
}
