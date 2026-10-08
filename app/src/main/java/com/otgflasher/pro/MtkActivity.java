package com.otgflasher.pro;import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;public class MtkActivity extends Activity{TextView txtDetail,log;ProgressBar bar;@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_mtk);txtDetail=findViewById(R.id.txtDetail);log=findViewById(R.id.logView);bar=findViewById(R.id.progressBar);findViewById(R.id.btnBack).setOnClickListener(v->finish());findViewById(R.id.btn_BYPASS_AUTH_SLA_DAA).setOnClickListener(v->run("python3 -m mtk payload"));
findViewById(R.id.btn_DOWNLOAD_ONLY).setOnClickListener(v->run("python3 -m mtk w --scatter /sdcard/Download/MT6765_scatter.txt"));
findViewById(R.id.btn_FIRMWARE_UPGRADE).setOnClickListener(v->run("python3 -m mtk w --scatter /sdcard/Download/MT6765_scatter.txt --firmware-upgrade"));
findViewById(R.id.btn_FORMAT_ALL___DOWNLOAD).setOnClickListener(v->run("python3 -m mtk w --scatter /sdcard/Download/MT6765_scatter.txt --format-all"));
findViewById(R.id.btn_READBACK_ADD).setOnClickListener(v->run("python3 -m mtk r --help"));
findViewById(R.id.btn_READBACK_boot_0x0_0x2000000).setOnClickListener(v->run("python3 -m mtk r boot /sdcard/Download/boot.img"));
findViewById(R.id.btn_READBACK_preloader).setOnClickListener(v->run("python3 -m mtk r preloader /sdcard/Download/preloader.bin"));
findViewById(R.id.btn_READBACK_nvram).setOnClickListener(v->run("python3 -m mtk r nvram /sdcard/Download/nvram.img"));
findViewById(R.id.btn_FORMAT_Auto_Format).setOnClickListener(v->run("python3 -m mtk e frp,userdata,cache"));
findViewById(R.id.btn_FORMAT_Manual_Format_frp_0x0_0x100000).setOnClickListener(v->run("python3 -m mtk e frp"));
findViewById(R.id.btn_FORMAT_whole_flash_except_bootloader).setOnClickListener(v->run("python3 -m mtk e frp,userdata,cache,nvram"));
findViewById(R.id.btn_MEMORY_TEST_RAM).setOnClickListener(v->run("python3 -m mtk da test"));
findViewById(R.id.btn_MEMORY_TEST_EMMC).setOnClickListener(v->run("python3 -m mtk da test --emmc"));
findViewById(R.id.btn_ERASE_FRP).setOnClickListener(v->run("python3 -m mtk e frp"));
findViewById(R.id.btn_ERASE_FRP_userdata).setOnClickListener(v->run("python3 -m mtk e frp,userdata"));
findViewById(R.id.btn_STOP).setOnClickListener(v->run("pkill -9 python3"));
}void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=2;if(pr>95)pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}}