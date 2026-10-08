package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class MtkActivity extends Activity{
TextView txtDetail,log;ProgressBar bar;
@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_mtk);txtDetail=findViewById(R.id.txtDetail);log=findViewById(R.id.logView);bar=findViewById(R.id.progressBar);findViewById(R.id.btnBack).setOnClickListener(v->finish());
findViewById(R.id.btn_BYPASS_AUTH_SLA_DAA).setOnClickListener(v->run("python3 -m mtk payload"));
findViewById(R.id.btn_FLASH_Download_Only).setOnClickListener(v->run("python3 -m mtk w --scatter /sdcard/Download/MT6765_scatter.txt"));
findViewById(R.id.btn_FLASH_Firmware_Upgrade).setOnClickListener(v->run("python3 -m mtk w --scatter /sdcard/Download/MT6765_scatter.txt --firmware-upgrade"));
findViewById(R.id.btn_FLASH_Format_All___Download).setOnClickListener(v->run("python3 -m mtk w --scatter /sdcard/Download/MT6765_scatter.txt --format-all"));
findViewById(R.id.btn_READBACK_boot_0x0_0x2000000).setOnClickListener(v->run("python3 -m mtk r boot /sdcard/Download/boot.img"));
findViewById(R.id.btn_READBACK_preloader).setOnClickListener(v->run("python3 -m mtk r preloader /sdcard/Download/preloader.bin"));
findViewById(R.id.btn_FORMAT_frp___userdata).setOnClickListener(v->run("python3 -m mtk e frp,userdata"));
findViewById(R.id.btn_FORMAT_all).setOnClickListener(v->run("python3 -m mtk e frp,userdata,cache"));
findViewById(R.id.btn_ERASE_FRP).setOnClickListener(v->run("python3 -m mtk e frp"));
findViewById(R.id.btn_DUMP_preloader).setOnClickListener(v->run("python3 -m mtk r preloader /sdcard/Download/dump_preloader.bin"));
findViewById(R.id.btn_REBOOT).setOnClickListener(v->run("python3 -m mtk reset"));

}
void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=3;if(pr>95) pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}
}
