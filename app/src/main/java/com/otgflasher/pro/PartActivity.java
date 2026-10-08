package com.otgflasher.pro;import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;public class PartActivity extends Activity{TextView txtDetail,log;ProgressBar bar;@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_part);txtDetail=findViewById(R.id.txtDetail);log=findViewById(R.id.logView);bar=findViewById(R.id.progressBar);findViewById(R.id.btnBack).setOnClickListener(v->finish());findViewById(R.id.btn_PRINT_sgdisk_sda_sdb).setOnClickListener(v->run("sgdisk --print /dev/block/sda; sgdisk --print /dev/block/sdb"));
findViewById(R.id.btn_PRINT_gdisk_-l_sda).setOnClickListener(v->run("gdisk -l /dev/block/sda"));
findViewById(R.id.btn_PRINT_parted_sda_print).setOnClickListener(v->run("parted /dev/block/sda print"));
findViewById(R.id.btn_LIST_by-name).setOnClickListener(v->run("ls -l /dev/block/bootdevice/by-name/"));
findViewById(R.id.btn_BACKUP_GPT_34_sectors).setOnClickListener(v->run("dd if=/dev/block/sda of=/sdcard/Download/gpt_backup.img bs=512 count=34"));
findViewById(R.id.btn_RESTORE_GPT).setOnClickListener(v->run("dd if=/sdcard/Download/gpt_backup.img of=/dev/block/sda bs=512 count=34"));
findViewById(R.id.btn_BACKUP_all_partitions_list).setOnClickListener(v->run("ls -l /dev/block/bootdevice/by-name/ > /sdcard/Download/part_list.txt; cat /sdcard/Download/part_list.txt"));
findViewById(R.id.btn_ERASE_frp).setOnClickListener(v->run("fastboot erase frp; sgdisk --delete 1 /dev/block/sda"));
findViewById(R.id.btn_ERASE_userdata).setOnClickListener(v->run("fastboot erase userdata"));
findViewById(R.id.btn_RESIZE_super_lpresize_0).setOnClickListener(v->run("lpresize /dev/block/super 0; lpresize /sdcard/Download/super.img 0"));
findViewById(R.id.btn_CREATE_partition).setOnClickListener(v->run("sgdisk --new=1:0:0 --typecode=1:8300 /dev/block/sda"));
findViewById(R.id.btn_DELETE_partition).setOnClickListener(v->run("sgdisk --delete 1 /dev/block/sda"));
findViewById(R.id.btn_BACKUP_super).setOnClickListener(v->run("dd if=/dev/block/super of=/sdcard/Download/super_backup.img bs=4096"));
findViewById(R.id.btn_FLASH_super).setOnClickListener(v->run("fastboot flash super /sdcard/Download/super_backup.img"));
}void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=2;if(pr>95)pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}}