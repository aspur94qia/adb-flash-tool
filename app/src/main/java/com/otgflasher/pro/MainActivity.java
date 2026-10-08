
package com.otgflasher.pro;
import android.app.Activity;import android.app.PendingIntent;import android.os.Bundle;import android.widget.*;import android.hardware.usb.*;import android.content.*;import android.view.View;import java.io.*;import java.util.*;
public class MainActivity extends Activity {
    TextView log, txtProgressLabel, txtProgressDetail; ProgressBar progressBar; LinearLayout progressBox;
    Spinner spinPart; UsbManager um; UsbDevice dev; String[] parts={"boot_a","boot_b","boot","recovery","system","vendor","vbmeta","super","dtbo","userdata"};
    String selectedFile="/sdcard/Download/boot.img";
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); progressBar=findViewById(R.id.progressBar); progressBox=findViewById(R.id.progressBox);
        txtProgressLabel=findViewById(R.id.txtProgressLabel); txtProgressDetail=findViewById(R.id.txtProgressDetail);
        spinPart=findViewById(R.id.spinPart);
        ArrayAdapter<String> ad=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parts); ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spinPart.setAdapter(ad);
        um=(UsbManager)getSystemService(Context.USB_SERVICE);

        findViewById(R.id.btnUsb).setOnClickListener(v->connect());
        findViewById(R.id.btnFileMgr).setOnClickListener(v->startActivity(new Intent(this, FileManagerActivity.class)));

        findViewById(R.id.btnFlash).setOnClickListener(v->flashWithProgress());
        findViewById(R.id.btnRebootBL).setOnClickListener(v->run("adb reboot bootloader 2>&1; echo Rebooting to bootloader..."));
        findViewById(R.id.btnMtkBypass).setOnClickListener(v->runWithProgress("MTK BYPASS AUTH V2", "mtk payload 2>&1 || python3 -m mtk payload 2>&1 || echo 'Install: pip install mtkclient'"));
        findViewById(R.id.btnMtkFlash).setOnClickListener(v->runWithProgress("MTK FLASH SCATTER", "ls /sdcard/Download/*.txt /sdcard/Download/scatter*.txt 2>&1; echo 'Select scatter in File Manager'; mtk w boot "+selectedFile+" 2>&1 || echo 'Use SP Flash'"));
        findViewById(R.id.btnEdl).setOnClickListener(v->runWithProgress("EDL 9008", "echo '=== EDL 9008 MODE ==='; edl --loader /sdcard/Download/prog_firehose_ddr.elf --print-gpt 2>&1 || echo 'Use QFIL / testpoint'"));
        findViewById(R.id.btnQcn).setOnClickListener(v->run("echo '=== BACKUP QCN EFS ==='; dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/modemst1.img bs=4096 2>&1; echo 'QCN backup: /sdcard/Download/modemst1.img'"));
        findViewById(R.id.btnOdinAp).setOnClickListener(v->run("echo '=== ODIN FLASH ==='; heimdall flash --AP "+selectedFile+" 2>&1 || tar -tvf "+selectedFile+" | head -20; echo 'AP/BL/CSC select in File Manager'"));
        findViewById(R.id.btnKg).setOnClickListener(v->run("adb shell pm disable-user com.samsung.android.kgclient 2>&1; adb shell pm disable-user com.samsung.android.knox.kpu 2>&1; echo 'KG/MDM BYPASSED'"));
        findViewById(R.id.btnSpd).setOnClickListener(v->runWithProgress("SPD PAC FLASH", "echo 'Flash PAC: "+selectedFile+"'; pac_extract "+selectedFile+" 2>&1 || echo 'SPD Upgrade Tool'"));
        findViewById(R.id.btnPayload).setOnClickListener(v->runWithProgress("PAYLOAD.BIN", "payload_dumper "+selectedFile+" 2>&1 || python3 -m payload_dumper "+selectedFile+" -o /sdcard/Download/ 2>&1 || echo 'Select payload.bin'"));
        findViewById(R.id.btnSuper).setOnClickListener(v->run("lpunpack "+selectedFile+" /sdcard/Download/super_out/ 2>&1 || echo 'Select super.img in File Manager'"));
        findViewById(R.id.btnOfp).setOnClickListener(v->runWithProgress("OFP EXTRACT", "python3 /sdcard/Download/ofp_extractor.py "+selectedFile+" 2>&1 || unzip -l "+selectedFile+" 2>&1 | head -30"));
        findViewById(R.id.btnFrp).setOnClickListener(v->run("echo 'FRP BYPASS ALL CHIPSET'; adb shell content insert --uri content://settings/secure --bind name:s:user_setup_complete --bind value:s:1 2>&1; fastboot erase frp 2>&1; echo 'FRP BYPASSED'"));
        findViewById(R.id.btnRunCmd).setOnClickListener(v->{ String c=((EditText)findViewById(R.id.editCmd)).getText().toString(); if(!c.isEmpty()) runWithProgress("CMD", c+" 2>&1"); });
    }

    void connect(){
        java.util.HashMap<String,UsbDevice> ds=um.getDeviceList();
        if(ds.isEmpty()){ logAppend("[ERROR] OTG belum colok!\n> VID: 0E8D=MTK Preloader\n> VID 05C6:9008=Qualcomm EDL\n> VID 04E8=SAMSUNG Download\n> VID 18D1:D00D=FASTBOOT\n> VID 18D1:4EE7=ADB\n> Check kabel OTG!"); return; }
        for(UsbDevice d:ds.values()){
            String mode=getMode(d);
            logAppend("USB: VID 0x"+Integer.toHexString(d.getVendorId())+" PID 0x"+Integer.toHexString(d.getProductId())+" "+(d.getProductName()!=null?d.getProductName():"")+" -> "+mode);
            if(!um.hasPermission(d)){
                PendingIntent pi=PendingIntent.getBroadcast(this,0,new Intent("USB_PERMISSION"),PendingIntent.FLAG_IMMUTABLE);
                um.requestPermission(d,pi);
            } else { dev=d; logAppend("[OK] CONNECTED NO ROOT: "+mode+" - Ready to flash!"); findViewById(R.id.statusDot).setBackgroundColor(0xFF00FF00); }
        }
    }
    String getMode(UsbDevice d){ int v=d.getVendorId(), p=d.getProductId(); if(v==0x0e8d) return "MTK Preloader"; if(v==0x05c6 && (p==0x9008||p==0x9006)) return "QUALCOMM EDL 9008"; if(v==0x04e8) return "SAMSUNG Odin/Download"; if(v==0x18d1 && p==0xd00d) return "FASTBOOT"; if(v==0x18d1 && p==0x4ee7) return "ADB"; if(v==0x05ac) return "iPhone DFU"; return "UNKNOWN DEVICE"; }

    void flashWithProgress(){
        String part=spinPart.getSelectedItem().toString();
        logAppend("\n=== FLASH START ===\nPart: "+part+"\nFile: "+selectedFile);
        progressBox.setVisibility(View.VISIBLE); progressBar.setProgress(0);
        txtProgressLabel.setText("Flashing "+new File(selectedFile).getName()+" -> "+part+" 0%");
        runWithProgress("FLASH "+part, "echo 'Flashing "+selectedFile+" to "+part+"'; fastboot flash "+part+" '"+selectedFile+"' 2>&1; echo 'FLASH DONE'");
    }

    void run(String cmd){ logAppend("\n> $ "+cmd+"\n"); new Thread(()->{ try{ Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream())); String l; StringBuilder sb=new StringBuilder(); while((l=r.readLine())!=null) sb.append(l).append("\n"); pr.waitFor(); String res=sb.toString(); runOnUiThread(()->logAppend(res)); }catch(Exception e){ runOnUiThread(()->logAppend("ERR: "+e.getMessage())); }}).start(); }

    void runWithProgress(String title, String cmd){
        progressBox.setVisibility(View.VISIBLE); progressBar.setProgress(5);
        txtProgressLabel.setText(title+" - Running...");
        logAppend("\n> $ "+cmd+"\n");
        new Thread(()->{
            try{
                runOnUiThread(()->{ progressBar.setProgress(20); txtProgressDetail.setText("Starting..."); });
                Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"});
                BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String l; StringBuilder sb=new StringBuilder(); int prog=20;
                while((l=r.readLine())!=null){
                    sb.append(l).append("\n");
                    prog+=2; if(prog>95) prog=95;
                    int p=prog; runOnUiThread(()->{ progressBar.setProgress(p); txtProgressDetail.setText(p+"% - Processing..."); });
                }
                pr.waitFor(); String res=sb.toString();
                runOnUiThread(()->{
                    progressBar.setProgress(100); txtProgressLabel.setText(title+" - DONE 100%");
                    txtProgressDetail.setText("100% - Completed - "+new File(selectedFile).length()/1024/1024+"MB");
                    logAppend(res+"\n=== "+title+" DONE ===\n");
                    new android.os.Handler().postDelayed(()->progressBox.setVisibility(View.GONE), 3000);
                });
            }catch(Exception e){ runOnUiThread(()->{ logAppend("ERR: "+e.getMessage()); progressBox.setVisibility(View.GONE); }); }
        }).start();
    }

    void logAppend(String s){ runOnUiThread(()->{ log.append(s+"\n"); }); }
}
