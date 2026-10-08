package com.otgflasher.pro;import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;public class FastbootActivity extends Activity{TextView txtDetail,log;ProgressBar bar;@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_fastboot);txtDetail=findViewById(R.id.txt_detail);log=findViewById(R.id.log_view);bar=findViewById(R.id.progress_bar);findViewById(R.id.btn_back).setOnClickListener(v->finish());findViewById(R.id.btn_flash_boot_a_0).setOnClickListener(v->run("fastboot flash boot_a /sdcard/Download/boot.img"));
findViewById(R.id.btn_flash_boot_b_1).setOnClickListener(v->run("fastboot flash boot_b /sdcard/Download/boot.img"));
findViewById(R.id.btn_flash_boot_2).setOnClickListener(v->run("fastboot flash boot /sdcard/Download/boot.img"));
findViewById(R.id.btn_flash_recovery_3).setOnClickListener(v->run("fastboot flash recovery /sdcard/Download/recovery.img"));
findViewById(R.id.btn_flash_vbmeta_disable_verity_4).setOnClickListener(v->run("fastboot flash vbmeta /sdcard/Download/vbmeta.img --disable-verity --disable-verification"));
findViewById(R.id.btn_flash_super_5).setOnClickListener(v->run("fastboot flash super /sdcard/Download/super.img"));
findViewById(R.id.btn_flash_system_6).setOnClickListener(v->run("fastboot flash system /sdcard/Download/system.img"));
findViewById(R.id.btn_erase_frp_7).setOnClickListener(v->run("fastboot erase frp"));
findViewById(R.id.btn_erase_userdata_8).setOnClickListener(v->run("fastboot erase userdata"));
findViewById(R.id.btn_getvar_all_9).setOnClickListener(v->run("fastboot getvar all"));
findViewById(R.id.btn_oem_device_info_10).setOnClickListener(v->run("fastboot oem device-info"));
findViewById(R.id.btn_flashing_unlock_11).setOnClickListener(v->run("fastboot flashing unlock"));
findViewById(R.id.btn_flashing_lock_12).setOnClickListener(v->run("fastboot flashing lock"));
findViewById(R.id.btn_reboot_bootloader_13).setOnClickListener(v->run("fastboot reboot bootloader"));
findViewById(R.id.btn_reboot_edl_14).setOnClickListener(v->run("fastboot reboot edl"));
}void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=2;if(pr>95)pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}}