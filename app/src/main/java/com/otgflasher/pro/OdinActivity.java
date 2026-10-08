package com.otgflasher.pro;import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;public class OdinActivity extends Activity{TextView txtDetail,log;ProgressBar bar;@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_odin);txtDetail=findViewById(R.id.txtDetail);log=findViewById(R.id.logView);bar=findViewById(R.id.progressBar);findViewById(R.id.btnBack).setOnClickListener(v->finish());findViewById(R.id.btn_FLASH_AP_only).setOnClickListener(v->run("heimdall flash --AP /sdcard/Download/AP.tar.md5"));
findViewById(R.id.btn_FLASH_BL_only).setOnClickListener(v->run("heimdall flash --BL /sdcard/Download/BL.tar.md5"));
findViewById(R.id.btn_FLASH_CP_only).setOnClickListener(v->run("heimdall flash --CP /sdcard/Download/CP.tar.md5"));
findViewById(R.id.btn_FLASH_CSC_only).setOnClickListener(v->run("heimdall flash --CSC /sdcard/Download/CSC.tar.md5"));
findViewById(R.id.btn_FLASH_HOME_CSC).setOnClickListener(v->run("heimdall flash --CSC /sdcard/Download/HOME_CSC.tar.md5"));
findViewById(R.id.btn_FLASH_FULL_AP_BL_CP_CSC).setOnClickListener(v->run("heimdall flash --AP /sdcard/Download/AP.tar.md5 --BL /sdcard/Download/BL.tar.md5 --CP /sdcard/Download/CP.tar.md5 --CSC /sdcard/Download/CSC.tar.md5"));
findViewById(R.id.btn_READ_PIT).setOnClickListener(v->run("heimdall print-pit"));
findViewById(R.id.btn_WRITE_PIT).setOnClickListener(v->run("heimdall flash --pit /sdcard/Download/pit.pit"));
findViewById(R.id.btn_DUMP_PIT).setOnClickListener(v->run("heimdall download-pit --output /sdcard/Download/pit.pit"));
findViewById(R.id.btn_BYPASS_KG).setOnClickListener(v->run("adb shell pm disable-user --user 0 com.samsung.android.kgclient"));
findViewById(R.id.btn_BYPASS_MDM).setOnClickListener(v->run("adb shell pm disable-user --user 0 com.samsung.android.mdm"));
findViewById(R.id.btn_BYPASS_FRP).setOnClickListener(v->run("adb shell content insert --uri content://settings/secure --bind name:s:user_setup_complete --bind value:s:1"));
findViewById(R.id.btn_ERASE_Nand_All).setOnClickListener(v->run("heimdall flash --no-reboot --resume -- Pit + --Nand Erase All"));
findViewById(R.id.btn_REBOOT_Download).setOnClickListener(v->run("adb reboot download"));
findViewById(R.id.btn_REBOOT_System).setOnClickListener(v->run("adb reboot"));
}void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=2;if(pr>95)pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}}