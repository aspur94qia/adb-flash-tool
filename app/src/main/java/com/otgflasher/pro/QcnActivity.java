package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;
public class QcnActivity extends Activity{
TextView txtDetail,log;ProgressBar bar;
@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_qcn);txtDetail=findViewById(R.id.txtDetail);log=findViewById(R.id.logView);bar=findViewById(R.id.progressBar);findViewById(R.id.btnBack).setOnClickListener(v->finish());
findViewById(R.id.btn_BACKUP_QCN_dd_modemst1).setOnClickListener(v->run("dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/modemst1.img bs=4096"));
findViewById(R.id.btn_BACKUP_QCN_dd_modemst2).setOnClickListener(v->run("dd if=/dev/block/bootdevice/by-name/modemst2 of=/sdcard/Download/modemst2.img bs=4096"));
findViewById(R.id.btn_BACKUP_QCN_dd_fsg).setOnClickListener(v->run("dd if=/dev/block/bootdevice/by-name/fsg of=/sdcard/Download/fsg.img bs=4096"));
findViewById(R.id.btn_BACKUP_QCN_dd_fsc).setOnClickListener(v->run("dd if=/dev/block/bootdevice/by-name/fsc of=/sdcard/Download/fsc.img bs=4096"));
findViewById(R.id.btn_BACKUP_FULL_QCN_4_files).setOnClickListener(v->run("dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/qcn_full/modemst1.img; dd if=/dev/block/bootdevice/by-name/modemst2 of=/sdcard/Download/qcn_full/modemst2.img; dd if=/dev/block/bootdevice/by-name/fsg of=/sdcard/Download/qcn_full/fsg.img; dd if=/dev/block/bootdevice/by-name/fsc of=/sdcard/Download/qcn_full/fsc.img"));
findViewById(R.id.btn_RESTORE_QCN_modemst1).setOnClickListener(v->run("dd if=/sdcard/Download/modemst1.img of=/dev/block/bootdevice/by-name/modemst1 bs=4096"));
findViewById(R.id.btn_RESTORE_QCN_modemst2).setOnClickListener(v->run("dd if=/sdcard/Download/modemst2.img of=/dev/block/bootdevice/by-name/modemst2 bs=4096"));
findViewById(R.id.btn_RESTORE_QCN_fsg).setOnClickListener(v->run("dd if=/sdcard/Download/fsg.img of=/dev/block/bootdevice/by-name/fsg bs=4096"));
findViewById(R.id.btn_BACKUP_EFS).setOnClickListener(v->run("dd if=/dev/block/bootdevice/by-name/efs1 of=/sdcard/Download/efs1.img; dd if=/dev/block/bootdevice/by-name/efs2 of=/sdcard/Download/efs2.img"));
findViewById(R.id.btn_RESTORE_EFS).setOnClickListener(v->run("dd if=/sdcard/Download/efs1.img of=/dev/block/bootdevice/by-name/efs1"));
findViewById(R.id.btn_DIAG_enable).setOnClickListener(v->run("adb shell setprop sys.usb.config diag,adb; adb shell setprop persist.usb.config diag,adb"));

}
void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=3;if(pr>95) pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}
}
