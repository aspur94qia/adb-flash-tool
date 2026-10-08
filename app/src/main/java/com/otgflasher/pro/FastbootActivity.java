package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class FastbootActivity extends Activity{
TextView txtDetail,log;ProgressBar bar;
@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_fastboot);txtDetail=findViewById(R.id.txtDetail);log=findViewById(R.id.logView);bar=findViewById(R.id.progressBar);findViewById(R.id.btnBack).setOnClickListener(v->finish());
findViewById(R.id.btn_FLASH_boot_a).setOnClickListener(v->run("fastboot flash boot_a /sdcard/Download/boot.img"));
findViewById(R.id.btn_FLASH_boot_b).setOnClickListener(v->run("fastboot flash boot_b /sdcard/Download/boot.img"));
findViewById(R.id.btn_FLASH_boot).setOnClickListener(v->run("fastboot flash boot /sdcard/Download/boot.img"));
findViewById(R.id.btn_FLASH_recovery).setOnClickListener(v->run("fastboot flash recovery /sdcard/Download/recovery.img"));
findViewById(R.id.btn_FLASH_vbmeta).setOnClickListener(v->run("fastboot flash vbmeta /sdcard/Download/vbmeta.img"));
findViewById(R.id.btn_FLASH_vbmeta_a).setOnClickListener(v->run("fastboot flash vbmeta_a /sdcard/Download/vbmeta.img"));
findViewById(R.id.btn_FLASH_super).setOnClickListener(v->run("fastboot flash super /sdcard/Download/super.img"));
findViewById(R.id.btn_FLASH_system).setOnClickListener(v->run("fastboot flash system /sdcard/Download/system.img"));
findViewById(R.id.btn_ERASE_frp).setOnClickListener(v->run("fastboot erase frp"));
findViewById(R.id.btn_ERASE_userdata).setOnClickListener(v->run("fastboot erase userdata"));
findViewById(R.id.btn_SET_ACTIVE_A).setOnClickListener(v->run("fastboot --set-active=a"));
findViewById(R.id.btn_SET_ACTIVE_B).setOnClickListener(v->run("fastboot --set-active=b"));
findViewById(R.id.btn_REBOOT_bootloader).setOnClickListener(v->run("fastboot reboot bootloader"));
findViewById(R.id.btn_REBOOT_system).setOnClickListener(v->run("fastboot reboot"));
findViewById(R.id.btn_UNLOCK_BL).setOnClickListener(v->run("fastboot flashing unlock"));
findViewById(R.id.btn_LOCK_BL).setOnClickListener(v->run("fastboot flashing lock"));
findViewById(R.id.btn_OEM_device-info).setOnClickListener(v->run("fastboot oem device-info"));

}
void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=3;if(pr>95) pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}
}
