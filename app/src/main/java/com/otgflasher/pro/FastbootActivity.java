package com.otgflasher.pro;import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import java.io.*;
import android.os.Bundle;import android.widget.*;import java.io.*;public class FastbootActivity extends Activity{
private String pickedFilePath="";
private String pickedFileName="";
TextView txtDetail,log;ProgressBar bar;@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_fastboot);
        try {
            android.widget.Button btnPick = new android.widget.Button(this);
            btnPick.setText("PILIH FILE FIRMWARE");
            btnPick.setBackgroundColor(0xFFFF6D00);
            btnPick.setTextColor(0xFFFFFFFF);
            btnPick.setOnClickListener(v->pickFile());
            android.view.ViewGroup root = (android.view.ViewGroup) findViewById(android.R.id.content);
            if(root!=null && root.getChildAt(0) instanceof android.view.ViewGroup){
                ((android.view.ViewGroup)root.getChildAt(0)).addView(btnPick, 0);
            } else if(root!=null) { root.addView(btnPick); }
        } catch(Exception e){}
txtDetail=findViewById(R.id.txt_detail);log=findViewById(R.id.log_view);bar=findViewById(R.id.progress_bar);findViewById(R.id.btn_back).setOnClickListener(v->finish());findViewById(R.id.btn_flash_boot_a_0).setOnClickListener(v->run("fastboot flash boot_a " + (pickedFilePath.isEmpty()? "/sdcard/Download/boot.img" : pickedFilePath) + ""));
findViewById(R.id.btn_flash_boot_b_1).setOnClickListener(v->run("fastboot flash boot_b " + (pickedFilePath.isEmpty()? "/sdcard/Download/boot.img" : pickedFilePath) + ""));
findViewById(R.id.btn_flash_boot_2).setOnClickListener(v->run("fastboot flash boot " + (pickedFilePath.isEmpty()? "/sdcard/Download/boot.img" : pickedFilePath) + ""));
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
}
private void pickFile() {
    try {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("*/*");
        i.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(i, "PILIH FILE FIRMWARE"), 9999);
    } catch(Exception e){ log.append("\nError picker: "+e+"\n"); }
}
@Override
protected void onActivityResult(int req, int res, Intent data) {
    super.onActivityResult(req,res,data);
    if(req==9999 && res==RESULT_OK && data!=null && data.getData()!=null){
        try {
            Uri uri = data.getData();
            String name = "picked_file";
            try {
                android.database.Cursor c=getContentResolver().query(uri,null,null,null,null);
                if(c!=null){ int idx=c.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME); if(c.moveToFirst()) name=c.getString(idx); c.close(); }
            } catch(Exception e){}
            java.io.File out = new java.io.File("/sdcard/Download/"+name);
            java.io.InputStream is = getContentResolver().openInputStream(uri);
            java.io.OutputStream os = new java.io.FileOutputStream(out);
            byte[] buf=new byte[8192]; int len; while((len=is.read(buf))>0) os.write(buf,0,len);
            is.close(); os.close();
            pickedFilePath = out.getAbsolutePath();
            pickedFileName = name;
            if(txtDetail!=null) txtDetail.setText("File: "+name+"\n"+pickedFilePath);
            if(log!=null) log.append("\nFILE TERPILIH: "+pickedFilePath+"\n");
            android.widget.Toast.makeText(this, "OK "+name, 0).show();
        } catch(Exception e){ if(log!=null) log.append("\nGagal: "+e+"\n"); }
    }
}

void run(String c){bar.setProgress(5);log.append("\n> "+c+"\n");new Thread(()->{try{Process p=Runtime.getRuntime().exec(new String[]{"sh","-c",c+" 2>&1"});BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));String l;int pr=5;while((l=r.readLine())!=null){pr+=2;if(pr>95)pr=95;String ll=l;int pp=pr;runOnUiThread(()->{bar.setProgress(pp);txtDetail.setText(pp+"% "+ll);log.append(ll+"\n");});}p.waitFor();runOnUiThread(()->bar.setProgress(100));}catch(Exception e){runOnUiThread(()->log.append("ERR "+e.getMessage()+"\n"));}}).start();}}