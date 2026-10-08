
package com.otgflasher.pro;
import android.app.Activity;import android.app.PendingIntent;import android.os.Bundle;import android.widget.*;import android.hardware.usb.*;import android.content.*;import android.view.View;import java.io.*;import java.util.*;
public class MainActivity extends Activity {
    TextView log, txtProgressLabel, txtProgressDetail; ProgressBar progressBar; LinearLayout progressBox;
    Spinner spinPart; UsbManager um; UsbDevice dev;
    String[] parts={"boot_a","boot_b","boot","recovery","system","vendor","vbmeta","super"};
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); progressBar=findViewById(R.id.progressBar); progressBox=findViewById(R.id.progressBox);
        txtProgressLabel=findViewById(R.id.txtProgressLabel); txtProgressDetail=findViewById(R.id.txtProgressDetail);
        spinPart=findViewById(R.id.spinPart);
        ArrayAdapter<String> ad=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parts);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spinPart.setAdapter(ad);
        um=(UsbManager)getSystemService(Context.USB_SERVICE);
        findViewById(R.id.btnUsb).setOnClickListener(v->connect());
        findViewById(R.id.btnFileMgr).setOnClickListener(v->startActivity(new Intent(this, FileManagerActivity.class)));
        findViewById(R.id.btnFlash).setOnClickListener(v->flashWithProgress());
        findViewById(R.id.btnRebootBL).setOnClickListener(v->run("adb reboot bootloader"));
        findViewById(R.id.btnMtkBypass).setOnClickListener(v->runWithProgress("MTK BYPASS", "mtk payload || python3 -m mtk payload"));
        findViewById(R.id.btnMtkFlash).setOnClickListener(v->run("mtk w boot /sdcard/Download/boot.img"));
        findViewById(R.id.btnEdl).setOnClickListener(v->runWithProgress("EDL 9008", "edl --loader /sdcard/Download/prog_firehose_ddr.elf --print-gpt"));
        findViewById(R.id.btnQcn).setOnClickListener(v->run("dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/modemst1.img"));
        findViewById(R.id.btnOdinAp).setOnClickListener(v->run("heimdall flash --AP /sdcard/Download/AP.tar.md5"));
        findViewById(R.id.btnKg).setOnClickListener(v->run("adb shell pm disable-user com.samsung.android.kgclient"));
        findViewById(R.id.btnSpd).setOnClickListener(v->run("echo SPD PAC FLASH"));
        findViewById(R.id.btnPayload).setOnClickListener(v->run("payload_dumper /sdcard/Download/payload.bin"));
        findViewById(R.id.btnFrp).setOnClickListener(v->run("adb shell content insert --uri content://settings/secure --bind name:s:user_setup_complete --bind value:s:1"));
        findViewById(R.id.btnRunCmd).setOnClickListener(v->{ String c=((EditText)findViewById(R.id.editCmd)).getText().toString(); if(!c.isEmpty()) runWithProgress("CMD", c); });
    }
    void connect(){ java.util.HashMap<String,UsbDevice> ds=um.getDeviceList(); if(ds.isEmpty()){ logAppend("OTG belum colok!\n0E8D=MTK\n05C6:9008=EDL\n04E8=SAMSUNG"); return; } for(UsbDevice d:ds.values()){ logAppend("USB: VID 0x"+Integer.toHexString(d.getVendorId())+" PID 0x"+Integer.toHexString(d.getProductId())+" -> "+getMode(d)); if(!um.hasPermission(d)){ PendingIntent pi=PendingIntent.getBroadcast(this,0,new Intent("USB_PERMISSION"),PendingIntent.FLAG_IMMUTABLE); um.requestPermission(d,pi); } else { dev=d; logAppend("CONNECTED: "+getMode(d)); } } }
    String getMode(UsbDevice d){ int v=d.getVendorId(), p=d.getProductId(); if(v==0x0e8d) return "MTK Preloader"; if(v==0x05c6 && (p==0x9008||p==0x9006)) return "EDL 9008"; if(v==0x04e8) return "SAMSUNG Odin"; if(v==0x18d1 && p==0xd00d) return "FASTBOOT"; return "UNKNOWN"; }
    void flashWithProgress(){ String part=spinPart.getSelectedItem().toString(); logAppend("\n=== FLASH "+part+" ===\n"); progressBox.setVisibility(View.VISIBLE); progressBar.setProgress(0); txtProgressLabel.setText("Flashing "+part+" 0%"); runWithProgress("FLASH "+part, "fastboot flash "+part+" /sdcard/Download/boot.img"); }
    void run(String cmd){ logAppend("\n> $ "+cmd+"\n"); new Thread(()->{ try{ Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream())); String l; StringBuilder sb=new StringBuilder(); while((l=r.readLine())!=null) sb.append(l).append("\n"); pr.waitFor(); String res=sb.toString(); runOnUiThread(()->logAppend(res)); }catch(Exception e){ runOnUiThread(()->logAppend("ERR: "+e.getMessage())); }}).start(); }
    void runWithProgress(String title, String cmd){ progressBox.setVisibility(View.VISIBLE); progressBar.setProgress(5); txtProgressLabel.setText(title+" Running..."); logAppend("\n> $ "+cmd+"\n"); new Thread(()->{ try{ Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream())); String l; StringBuilder sb=new StringBuilder(); int prog=20; while((l=r.readLine())!=null){ sb.append(l).append("\n"); prog+=2; if(prog>95) prog=95; int p=prog; runOnUiThread(()->{ progressBar.setProgress(p); txtProgressDetail.setText(p+"% - Processing"); }); } pr.waitFor(); String res=sb.toString(); runOnUiThread(()->{ progressBar.setProgress(100); txtProgressLabel.setText(title+" DONE 100%"); txtProgressDetail.setText("100% Completed"); logAppend(res); new android.os.Handler().postDelayed(()->progressBox.setVisibility(View.GONE), 3000); }); }catch(Exception e){ runOnUiThread(()->{ logAppend("ERR: "+e.getMessage()); progressBox.setVisibility(View.GONE); }); } }).start(); }
    void logAppend(String s){ runOnUiThread(()->{ log.append(s+"\n"); }); }
}
