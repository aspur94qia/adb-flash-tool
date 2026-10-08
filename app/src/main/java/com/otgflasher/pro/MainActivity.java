
package com.otgflasher.pro;
import android.app.Activity;import android.app.PendingIntent;import android.os.Bundle;import android.widget.*;import android.hardware.usb.*;import android.content.*;import java.io.*;import java.util.*;
public class MainActivity extends Activity {
    TextView log; Spinner spinPart; UsbManager um; UsbDevice dev; String[] parts={"boot","recovery","system","vendor","vbmeta","super","dtbo","userdata"};
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); spinPart=findViewById(R.id.spinPart);
        ArrayAdapter<String> ad=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parts); ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spinPart.setAdapter(ad);
        um=(UsbManager)getSystemService(Context.USB_SERVICE);
        findViewById(R.id.btnUsb).setOnClickListener(v->connect());
        findViewById(R.id.btnFileMgr).setOnClickListener(v->{ startActivity(new Intent(this, FileManagerActivity.class)); });
        findViewById(R.id.btnFlash).setOnClickListener(v->run("fastboot flash "+spinPart.getSelectedItem().toString()+" /sdcard/Download/boot.img 2>&1 || echo 'Pilih file di File Manager dulu'"));
        findViewById(R.id.btnRebootBL).setOnClickListener(v->run("adb reboot bootloader"));
        findViewById(R.id.btnGsi).setOnClickListener(v->run("fastboot reboot fastboot; fastboot flash system /sdcard/Download/system.img"));
        findViewById(R.id.btnMtkBypass).setOnClickListener(v->run("mtk payload || python3 -m mtk payload"));
        findViewById(R.id.btnMtkFlash).setOnClickListener(v->run("mtk w boot /sdcard/Download/boot.img --preloader preloader.bin"));
        findViewById(R.id.btnEdlSahara).setOnClickListener(v->run("edl --loader /sdcard/Download/prog_firehose_ddr.elf --print-gpt"));
        findViewById(R.id.btnEdlQcn).setOnClickListener(v->run("dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/modemst1.img; echo backup done"));
        findViewById(R.id.btnOdinAp).setOnClickListener(v->run("heimdall flash --AP /sdcard/Download/AP.tar.md5 || odin4 -a /sdcard/Download/AP.tar.md5"));
        findViewById(R.id.btnOdinCsc).setOnClickListener(v->run("heimdall flash --CSC /sdcard/Download/CSC.tar.md5"));
        findViewById(R.id.btnKg).setOnClickListener(v->run("adb shell pm disable-user com.samsung.android.kgclient; echo KG bypassed"));
        findViewById(R.id.btnFrp).setOnClickListener(v->run("adb shell content insert --uri content://settings/secure --bind name:s:user_setup_complete --bind value:s:1; echo FRP bypassed"));
        findViewById(R.id.btnPayload).setOnClickListener(v->run("payload_dumper /sdcard/Download/payload.bin || echo select payload.bin in file manager"));
        findViewById(R.id.btnSuper).setOnClickListener(v->run("lpunpack /sdcard/Download/super.img /sdcard/Download/super_out/"));
        findViewById(R.id.btnOfp).setOnClickListener(v->run("python3 ofp_extractor.py /sdcard/Download/*.ofp"));
        findViewById(R.id.btnUfs).setOnClickListener(v->run("echo UFS ISP FTDI 0403:6001; ls /dev/ttyUSB*"));
        findViewById(R.id.btnRunCmd).setOnClickListener(v->{ String c=((EditText)findViewById(R.id.editCmd)).getText().toString(); if(!c.isEmpty()) run(c); });
    }
    void connect(){ java.util.HashMap<String,UsbDevice> ds=um.getDeviceList(); if(ds.isEmpty()){ logAppend("OTG belum colok! 0E8D=MTK 05C6:9008=EDL 04E8=SAMSUNG"); return; } for(UsbDevice d:ds.values()){ logAppend("USB: VID 0x"+Integer.toHexString(d.getVendorId())+" PID 0x"+Integer.toHexString(d.getProductId())+" -> "+getMode(d)); if(!um.hasPermission(d)){ PendingIntent pi=PendingIntent.getBroadcast(this,0,new Intent("USB_PERMISSION"),PendingIntent.FLAG_IMMUTABLE); um.requestPermission(d,pi); } else { dev=d; logAppend("CONNECTED NO ROOT: "+getMode(d)); } } }
    String getMode(UsbDevice d){ int v=d.getVendorId(), p=d.getProductId(); if(v==0x0e8d) return "MTK Preloader"; if(v==0x05c6 && (p==0x9008||p==0x9006)) return "EDL 9008"; if(v==0x04e8) return "SAMSUNG Odin"; if(v==0x18d1 && p==0xd00d) return "FASTBOOT"; if(v==0x18d1 && p==0x4ee7) return "ADB"; return "UNKNOWN"; }
    void run(String cmd){ logAppend("\n> $ "+cmd+"\n"); new Thread(()->{ try{ Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream())); String l; StringBuilder sb=new StringBuilder(); while((l=r.readLine())!=null) sb.append(l).append("\n"); pr.waitFor(); String res=sb.toString(); runOnUiThread(()->logAppend(res)); }catch(Exception e){ runOnUiThread(()->logAppend("ERR: "+e.getMessage())); }}).start(); }
    void logAppend(String s){ runOnUiThread(()->{ log.append(s+"\n"); }); }
}
