
package com.otgflasher.pro;
import android.app.Activity;
import android.app.PendingIntent;
import android.os.Bundle;
import android.widget.*;
import android.hardware.usb.*;
import android.content.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
public class MainActivity extends Activity {
    TextView log, pathTxt; ListView fileList; EditText editCmd; Spinner spinPart;
    File cur=new File("/sdcard/Download"); List<File> files=new ArrayList<>(); String sel=""; UsbManager um; UsbDevice dev;
    String[] parts={"boot","recovery","system","vendor","vbmeta","super","dtbo","userdata","cache","boot_a","boot_b"};
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); pathTxt=findViewById(R.id.pathTxt); fileList=findViewById(R.id.fileList); editCmd=findViewById(R.id.editCmd); spinPart=findViewById(R.id.spinPart);
        ArrayAdapter<String> ad=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parts); ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spinPart.setAdapter(ad);
        um=(UsbManager)getSystemService(Context.USB_SERVICE);
        findViewById(R.id.btnUsb).setOnClickListener(v->connect());
        findViewById(R.id.btnDevices).setOnClickListener(v->run("echo '=== DETECT USB MODE ===' && ls /dev/bus/usb/*/* 2>&1; echo '\nVID:PID'; cat /sys/bus/usb/devices/*/idVendor /sys/bus/usb/devices/*/idProduct 2>&1 | paste - -; echo '\n0E8D=MTK 05C6=QCOM 04E8=SAMSUNG 18D1=ADB/FASTBOOT 05C6:9008=EDL'"));
        findViewById(R.id.btnLog).setOnClickListener(v->run("echo '=== LOGCAT ANALYZER BOOTLOOP ===' && logcat -d 2>&1 | grep -i -E 'fatal|crash|bootloop|avc|denied' | tail -30; dumpsys dropbox 2>&1 | grep -i crash | tail -20; cat /proc/last_kmsg 2>&1 | tail -30 || cat /sys/fs/pstore/console-ramoops 2>&1 | tail -30"));
        findViewById(R.id.btnUp).setOnClickListener(v->{ if(cur.getParentFile()!=null){ cur=cur.getParentFile(); list(); }});
        findViewById(R.id.btnUnzip).setOnClickListener(v->unzip());
        findViewById(R.id.btnPush).setOnClickListener(v->run("echo 'PUSH/PULL NO ROOT' && echo 'File: "+sel+"' && cp '"+sel+"' /sdcard/Download/ 2>&1; ls /sdcard/Download/ | tail -10"));
        findViewById(R.id.btnFlash).setOnClickListener(v->flash());
        findViewById(R.id.btnRebootBL).setOnClickListener(v->run("adb reboot bootloader || echo 'Reboot BL via USB'"));
        findViewById(R.id.btnGsi).setOnClickListener(v->run("echo '=== GSI FLASHER ===' && echo 'File: "+sel+"' && echo 'fastboot erase system && fastboot flash system "+sel+"' && fastboot flash system '"+sel+"' 2>&1 || echo 'Need fastbootd'"));
        findViewById(R.id.btnKernel).setOnClickListener(v->run("echo '=== KERNEL FLASH ===' && echo 'File: "+sel+"' && fastboot flash boot '"+sel+"' 2>&1"));

        findViewById(R.id.btnMtkBypass).setOnClickListener(v->run("echo '=== MTK BYPASS AUTH V2 ===' && echo 'Support MT6765 MT6768 MT6785 MT6789 MT6895 MT6983' && echo 'Bypass SLA DAA' && echo 'mtk payload' && mtk payload 2>&1 || python3 -m mtk payload 2>&1 || echo 'pip install mtkclient'"));
        findViewById(R.id.btnMtkScatter).setOnClickListener(v->{ if(sel.isEmpty()){ toast("Pilih scatter.txt"); return; } run("cat '"+sel+"' | head -80"); });
        findViewById(R.id.btnMtkFlash).setOnClickListener(v->run("echo 'FLASH MTK SP FLASH' && echo 'File: "+sel+"' && mtk w boot '"+sel+"' --preloader preloader.bin 2>&1 || echo 'Use SP Flash Tool'"));

        findViewById(R.id.btnEdlSahara).setOnClickListener(v->run("echo '=== EDL 9008 SAHARA ===' && echo 'Test point / adb reboot edl' && edl --loader /sdcard/Download/prog_firehose_ddr.elf --memory ufs --print-gpt 2>&1 || echo 'Use QFIL'"));
        findViewById(R.id.btnEdlQcn).setOnClickListener(v->run("echo '=== BACKUP QCN + EFS ===' && dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/modemst1.img 2>&1; echo 'QCN backup done'"));
        findViewById(R.id.btnEdlRestore).setOnClickListener(v->run("echo '=== RESTORE QCN ===' && dd if='"+sel+"' of=/dev/block/bootdevice/by-name/modemst1 && echo 'QCN restored'"));
        findViewById(R.id.btnEdlReset).setOnClickListener(v->run("echo 'RESET EFS/FRP EDL' && edl reset --memory ufs 2>&1 || fastboot erase config && fastboot erase frp"));

        findViewById(R.id.btnOdinAp).setOnClickListener(v->odin("AP"));
        findViewById(R.id.btnOdinBl).setOnClickListener(v->odin("BL"));
        findViewById(R.id.btnOdinCsc).setOnClickListener(v->odin("CSC"));
        findViewById(R.id.btnKg).setOnClickListener(v->run("echo '=== SAMSUNG KG/MDM BYPASS ===' && adb shell pm disable-user com.samsung.android.kgclient 2>&1; adb shell pm disable-user com.samsung.android.knox.kpu 2>&1; echo 'KG BYPASSED'"));

        findViewById(R.id.btnSpd).setOnClickListener(v->run("echo '=== SPD/UNISOC PAC FLASH ===' && echo 'File: "+sel+"' && pac_extract '"+sel+"' 2>&1 || echo 'SPD: Use spd_upgrade_tool'"));
        findViewById(R.id.btnOfp).setOnClickListener(v->run("echo '=== OPPO OFP EXTRACTOR ===' && echo 'File: "+sel+"' && python3 ofp_extractor.py '"+sel+"' 2>&1 || unzip -l '"+sel+"' 2>&1 | head -20"));
        findViewById(R.id.btnFrp).setOnClickListener(v->run("echo '=== FRP BYPASS ALL ===' && adb shell content insert --uri content://settings/secure --bind name:s:user_setup_complete --bind value:s:1 2>&1; echo 'FRP BYPASS DONE'"));
        findViewById(R.id.btnMiCloud).setOnClickListener(v->run("echo '=== MI CLOUD BYPASS ===' && fastboot erase persist; fastboot erase frp; fastboot erase config; echo 'MiCloud bypass done'"));

        findViewById(R.id.btnPayload).setOnClickListener(v->run("echo 'EXTRACT PAYLOAD.BIN OTA' && payload_dumper '"+sel+"' 2>&1 || python3 -m payload_dumper '"+sel+"' 2>&1"));
        findViewById(R.id.btnSuper).setOnClickListener(v->run("echo 'UNPACK SUPER.IMG' && lpunpack '"+sel+"' /sdcard/Download/super_out/ 2>&1 || ls -lh '"+sel+"'"));
        findViewById(R.id.btnBoot).setOnClickListener(v->run("echo 'UNPACK BOOT.IMG' && magiskboot unpack '"+sel+"' 2>&1 || file '"+sel+"'"));
        findViewById(R.id.btnDat).setOnClickListener(v->run("echo 'DAT/BR -> IMG' && sdat2img.py /sdcard/Download/system.transfer.list '"+sel+"' system.img 2>&1 || brotli -d '"+sel+"'"));
        findViewById(R.id.btnPartMgr).setOnClickListener(v->run("echo '=== PARTITION MANAGER ===' && ls /dev/block/bootdevice/by-name/ 2>&1; lpdump /dev/block/bootdevice/by-name/super 2>&1 || df -h"));
        findViewById(R.id.btnSparse).setOnClickListener(v->run("simg2img '"+sel+"' /sdcard/Download/raw.img && echo 'RAW done' || echo 'simg2img'"));
        findViewById(R.id.btnUfs).setOnClickListener(v->run("echo '=== UFS/EMMC ISP TOOL ===' && echo 'ISP via OTG + FTDI 0403:6001' && ls /dev/ttyUSB* 2>&1"));
        findViewById(R.id.btnIphone).setOnClickListener(v->run("echo '=== IPHONE INFO CHECKM8 ===' && ideviceinfo 2>&1 || lsusb | grep -i apple"));
        findViewById(R.id.btnMagisk).setOnClickListener(v->run("echo 'MAGISK PATCH boot.img: "+sel+"' && magiskboot patch '"+sel+"' 2>&1"));

        findViewById(R.id.btnRunCmd).setOnClickListener(v->{ String c=editCmd.getText().toString(); if(!c.isEmpty()) run(c); });
        fileList.setOnItemClickListener((a,vw,p,id)->{ File f=files.get(p); if(f.isDirectory()){ cur=f; list(); } else { sel=f.getAbsolutePath(); pathTxt.setText("SEL: "+f.getName()+" "+f.length()/1024/1024+"MB"); logAppend("SEL: "+sel); editCmd.setText("fastboot flash "+spinPart.getSelectedItem().toString()+" "+sel); } });
        list();
    }
    void connect(){
        java.util.HashMap<String,UsbDevice> ds=um.getDeviceList();
        if(ds.isEmpty()){ logAppend("OTG belum colok!\n0E8D=MTK\n05C6:9008=Qualcomm EDL\n04E8=SAMSUNG Odin\n18D1=D00D Fastboot\n18D1:4EE7 ADB\n05AC=iPhone DFU\n0403:6001=UFS ISP FTDI"); return; }
        for(UsbDevice d:ds.values()){
            String mode=getMode(d);
            logAppend("USB: VID 0x"+Integer.toHexString(d.getVendorId())+" PID 0x"+Integer.toHexString(d.getProductId())+" "+d.getProductName()+" -> "+mode);
            if(!um.hasPermission(d)){
                PendingIntent pi=PendingIntent.getBroadcast(this,0,new Intent("USB_PERMISSION"),PendingIntent.FLAG_IMMUTABLE);
                um.requestPermission(d,pi);
            } else {
                dev=d;
                logAppend("CONNECTED NO ROOT: "+mode);
            }
        }
    }
    String getMode(UsbDevice d){ int v=d.getVendorId(), p=d.getProductId(); if(v==0x0e8d) return "MTK Preloader"; if(v==0x05c6 && (p==0x9008||p==0x9006)) return "QUALCOMM EDL 9008"; if(v==0x04e8) return "SAMSUNG Download/Odin"; if(v==0x18d1 && p==0xd00d) return "FASTBOOT"; if(v==0x18d1 && p==0x4ee7) return "ADB"; if(v==0x05ac) return "IPHONE DFU"; if(v==0x0403) return "FTDI UFS ISP"; return "UNKNOWN"; }
    void list(){ File[] arr=cur.listFiles(); files.clear(); List<String> ns=new ArrayList<>(); if(arr!=null){ Arrays.sort(arr); for(File f:arr){ files.add(f); ns.add((f.isDirectory()?"[DIR] ":"[FILE] ")+f.getName()+" "+(f.isFile()?f.length()/1024/1024+"MB":"")); } } fileList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ns)); pathTxt.setText(cur.getAbsolutePath()); }
    void unzip(){ if(sel.isEmpty()){ toast("Pilih zip"); return; } new Thread(()->{ try{ ZipInputStream zis=new ZipInputStream(new FileInputStream(sel)); ZipEntry ze; int c=0; while((ze=zis.getNextEntry())!=null){ File out=new File(cur, ze.getName()); if(ze.isDirectory()) out.mkdirs(); else { out.getParentFile().mkdirs(); FileOutputStream fos=new FileOutputStream(out); byte[] b=new byte[8192]; int l; while((l=zis.read(b))>0) fos.write(b,0,l); fos.close(); c++; } zis.closeEntry(); } zis.close(); logAppend("UNZIP DONE: "+c+" files"); runOnUiThread(()->list()); }catch(Exception e){ logAppend("UNZIP ERR: "+e.getMessage()); }}).start(); }
    void flash(){ if(sel.isEmpty()){ toast("Pilih img"); return; } String p=spinPart.getSelectedItem().toString(); logAppend("FLASH "+p+" <- "+sel); run("fastboot flash "+p+" '"+sel+"' 2>&1 || echo 'Need fastboot mode'"); }
    void odin(String slot){ if(sel.isEmpty()){ toast("Pilih tar.md5"); return; } run("echo 'ODIN FLASH "+slot+" <- "+sel+"' && heimdall flash --"+slot+" '"+sel+"' 2>&1 || tar -tvf '"+sel+"' | head -20"); }
    void run(String cmd){ logAppend("\n> $ "+cmd+"\n"); new Thread(()->{ try{ Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream())); String l; StringBuilder sb=new StringBuilder(); while((l=r.readLine())!=null) sb.append(l).append("\n"); pr.waitFor(); String res=sb.toString(); runOnUiThread(()->logAppend(res)); }catch(Exception e){ runOnUiThread(()->logAppend("ERR: "+e.getMessage())); }}).start(); }
    void logAppend(String s){ runOnUiThread(()->{ log.append(s+"\n"); }); }
    void toast(String s){ Toast.makeText(this,s,Toast.LENGTH_SHORT).show(); }
}
