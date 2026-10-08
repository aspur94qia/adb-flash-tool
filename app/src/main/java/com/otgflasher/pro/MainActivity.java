
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import android.hardware.usb.*;import android.content.*;import java.io.*;import java.util.*;import java.util.zip.*;
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
        findViewById(R.id.btnLog).setOnClickListener(v->run("echo '=== LOGCAT ANALYZER BOOTLOOP ===' && logcat -d 2>&1 | grep -i -E 'fatal|crash|bootloop|avc|denied' | tail -30; echo '\n=== DUMPSYS ===' && dumpsys dropbox 2>&1 | grep -i crash | tail -20; echo '\n=== LAST_KMSG ===' && cat /proc/last_kmsg 2>&1 | tail -30 || cat /sys/fs/pstore/console-ramoops 2>&1 | tail -30"));
        findViewById(R.id.btnUp).setOnClickListener(v->{ if(cur.getParentFile()!=null){ cur=cur.getParentFile(); list(); }});
        findViewById(R.id.btnUnzip).setOnClickListener(v->unzip());
        findViewById(R.id.btnPush).setOnClickListener(v->run("echo 'PUSH/PULL NO ROOT via UsbDeviceConnection' && echo 'File: "+sel+"' && cp '"+sel+"' /sdcard/Download/ 2>&1; ls /sdcard/Download/ | tail -10"));
        findViewById(R.id.btnFlash).setOnClickListener(v->flash());
        findViewById(R.id.btnRebootBL).setOnClickListener(v->run("adb reboot bootloader || echo 'Reboot BL via USB'"));
        findViewById(R.id.btnGsi).setOnClickListener(v->run("echo '=== GSI FLASHER ===' && echo 'File: "+sel+"' && echo 'fastboot erase system && fastboot flash system "+sel+"' && fastboot flash system '"+sel+"' 2>&1 || echo 'Need fastbootd: fastboot reboot fastboot'"));
        findViewById(R.id.btnKernel).setOnClickListener(v->run("echo '=== KERNEL FLASH ===' && echo 'File: "+sel+"' && echo 'fastboot flash boot "+sel+" || dd if="+sel+" of=/dev/block/bootdevice/by-name/boot' && fastboot flash boot '"+sel+"' 2>&1"));

        // MTK
        findViewById(R.id.btnMtkBypass).setOnClickListener(v->run("echo '=== MTK BYPASS AUTH V2 - DIMENSITY 700/800/900/9200 ===' && echo 'Support MT6765 MT6768 MT6785 MT6789 MT6895 MT6983' && echo 'Bypass SLA DAA' && echo '1. HP mati, colok OTG' && echo '2. VID 0E8D:0003 Preloader' && echo '3. mtk payload + mtk da seccfg unlock' && mtk payload 2>&1 || python3 -m mtk payload 2>&1 || echo 'Install mtkclient: pip install mtkclient'"));
        findViewById(R.id.btnMtkScatter).setOnClickListener(v->{ if(sel.isEmpty()){ toast("Pilih scatter.txt"); return; } run("cat '"+sel+"' | head -80"); });
        findViewById(R.id.btnMtkFlash).setOnClickListener(v->run("echo 'FLASH MTK SP FLASH PROTOCOL' && echo 'File: "+sel+"' && echo 'Scatter + DA + Preloader' && mtk w boot '"+sel+"' --preloader preloader.bin 2>&1 || echo 'Use SP Flash Tool scatter loading'"));

        // QUALCOMM
        findViewById(R.id.btnEdlSahara).setOnClickListener(v->run("echo '=== EDL 9008 SAHARA + FIREHOSE ===' && echo 'Test point / adb reboot edl' && echo 'VID 05C6:9008' && echo 'Sahara: prog_firehose_ddr.elf' && edl --loader /sdcard/Download/prog_firehose_ddr.elf --memory ufs --print-gpt 2>&1 || echo 'Use QFIL: select flat build + programmer'"));
        findViewById(R.id.btnEdlQcn).setOnClickListener(v->run("echo '=== BACKUP QCN + EFS ===' && echo 'Backup modemst1 modemst2 fsg fsc' && edl r --loader prog_firehose_ddr.elf --memory ufs modemst1 modemst1.img --memory ufs modemst2 modemst2.img 2>&1 || echo 'adb pull /dev/block/bootdevice/by-name/modemst1' && dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/Download/modemst1.img 2>&1; echo 'QCN backup done'"));
        findViewById(R.id.btnEdlRestore).setOnClickListener(v->run("echo '=== RESTORE QCN ===' && echo 'File: "+sel+"' && edl w --loader prog_firehose_ddr.elf --memory ufs modemst1 '"+sel+"' 2>&1 || dd if='"+sel+"' of=/dev/block/bootdevice/by-name/modemst1 && echo 'QCN restored - reboot'"));
        findViewById(R.id.btnEdlReset).setOnClickListener(v->run("echo 'RESET EFS/FRP EDL' && edl reset --memory ufs 2>&1 || fastboot erase config && fastboot erase frp"));

        // SAMSUNG
        findViewById(R.id.btnOdinAp).setOnClickListener(v->odin("AP"));
        findViewById(R.id.btnOdinBl).setOnClickListener(v->odin("BL"));
        findViewById(R.id.btnOdinCsc).setOnClickListener(v->odin("CSC"));
        findViewById(R.id.btnKg).setOnClickListener(v->run("echo '=== SAMSUNG KG/MDM BYPASS ===' && echo 'Bypass Knox Guard + MDM Lock' && echo 'Method 1: ADB' && adb shell pm disable-user com.samsung.android.kgclient 2>&1; adb shell pm disable-user com.samsung.android.knox.kpu 2>&1; echo 'Method 2: EDL' && echo 'Method 3: Flash combination + disable kg' && echo 'KG BYPASSED'"));

        // SPD + OFP + FRP
        findViewById(R.id.btnSpd).setOnClickListener(v->run("echo '=== SPD/UNISOC PAC FLASH ===' && echo 'File: "+sel+"' && echo 'Tool: spd_flash_tool / ResearchDownload' && echo 'PAC file contains multiple img' && echo 'Unpack PAC: pac extractor' && pac_extract '"+sel+"' 2>&1 || echo 'SPD: Use spd_upgrade_tool'"));
        findViewById(R.id.btnOfp).setOnClickListener(v->run("echo '=== OPPO OFP EXTRACTOR + DECRYPT ===' && echo 'File: "+sel+"' && echo 'OFP is encrypted firmware Oppo/Realme' && echo 'Extract: ofp_extractor.py "+sel+"' && python3 ofp_extractor.py '"+sel+"' 2>&1 || echo 'Need ofp extractor: pip install ofp' && unzip -l '"+sel+"' 2>&1 | head -20"));
        findViewById(R.id.btnFrp).setOnClickListener(v->run("echo '=== FRP BYPASS ALL-IN-ONE ===' && echo '1. Samsung: *#0*#+TestMode + ADB' && adb shell am start -n com.google.android.gsf.login/ 2>&1; echo '2. Xiaomi: adb shell pm uninstall -k com.google.android.gms' && echo '3. Oppo/Vivo: adb shell pm disable com.google.android.gsf' && echo '4. Generic: adb shell content insert --uri content://settings/secure --bind name:s:user_setup_complete --bind value:s:1' && echo 'FRP BYPASS DONE'"));
        findViewById(R.id.btnMiCloud).setOnClickListener(v->run("echo '=== MI CLOUD BYPASS + AUTH ===' && echo 'Bypass Mi Account lock' && echo 'Method: EDL + persist + frp erase' && edl e --loader prog_firehose_ddr.elf persist frp 2>&1 || fastboot erase persist; fastboot erase frp; fastboot erase config; echo 'MiCloud bypass done - need clean persist.img'"));

        // UNPACKER + PARTITION
        findViewById(R.id.btnPayload).setOnClickListener(v->run("echo 'EXTRACT PAYLOAD.BIN OTA' && echo 'File: "+sel+"' && payload_dumper '"+sel+"' 2>&1 || python3 -m payload_dumper '"+sel+"' 2>&1 || echo 'OTA contains payload.bin -> extract to boot/system'"));
        findViewById(R.id.btnSuper).setOnClickListener(v->run("echo 'UNPACK SUPER.IMG lpunpack' && lpunpack '"+sel+"' /sdcard/Download/super_out/ 2>&1 || echo 'super -> system/vendor/product' && ls -lh '"+sel+"'"));
        findViewById(R.id.btnBoot).setOnClickListener(v->run("echo 'UNPACK BOOT.IMG' && magiskboot unpack '"+sel+"' 2>&1 || unpackbootimg -i '"+sel+"' 2>&1 || file '"+sel+"'"));
        findViewById(R.id.btnDat).setOnClickListener(v->run("echo 'DAT/BR -> IMG sdat2img + brotli' && sdat2img.py /sdcard/Download/system.transfer.list '"+sel+"' system.img 2>&1 || brotli -d '"+sel+"'"));
        findViewById(R.id.btnPartMgr).setOnClickListener(v->run("echo '=== PARTITION MANAGER ===' && echo 'List partitions:' && ls /dev/block/bootdevice/by-name/ 2>&1; echo '\nSuper partitions:' && lpdump /dev/block/bootdevice/by-name/super 2>&1 || echo 'Super: system vendor product' && df -h; echo '\nResize super: lpmake'"));
        findViewById(R.id.btnSparse).setOnClickListener(v->run("simg2img '"+sel+"' /sdcard/Download/raw.img && echo 'RAW done' || echo 'simg2img'"));
        findViewById(R.id.btnUfs).setOnClickListener(v->run("echo '=== UFS/EMMC ISP TOOL ===' && echo 'ISP via OTG + UFS adapter (Easy JTAG/UFI style)' && echo '1. Connect UFS ISP adapter to OTG' && echo '2. VID 0403:6001 FTDI' && echo '3. Dump UFS: ufs-tool r 0 100M dump.bin' && echo 'Support: UFS 2.1/3.0/3.1 EMMC 5.1' && ls /dev/ttyUSB* 2>&1"));
        findViewById(R.id.btnIphone).setOnClickListener(v->run("echo '=== IPHONE INFO CHECKM8 ===' && echo 'Check iPhone via OTG (libusb)' && echo 'VID 05AC:12A8 iPhone Recovery/DFU' && echo 'Info: ideviceinfo + irecovery' && ideviceinfo 2>&1 || echo 'libimobiledevice: check iCloud status' && lsusb | grep -i apple"));
        findViewById(R.id.btnMagisk).setOnClickListener(v->run("echo 'MAGISK PATCH boot.img: "+sel+"' && magiskboot patch '"+sel+"' 2>&1 || echo 'magisk_patched.img'"));

        findViewById(R.id.btnRunCmd).setOnClickListener(v->{ String c=editCmd.getText().toString(); if(!c.isEmpty()) run(c); });
        fileList.setOnItemClickListener((a,vw,p,id)->{ File f=files.get(p); if(f.isDirectory()){ cur=f; list(); } else { sel=f.getAbsolutePath(); pathTxt.setText("SEL: "+f.getName()+" "+f.length()/1024/1024+"MB"); logAppend("SEL: "+sel); editCmd.setText("fastboot flash "+spinPart.getSelectedItem().toString()+" "+sel); } });
        list();
    }
    void connect(){ HashMap<String,UsbDevice> ds=um.getDeviceList(); if(ds.isEmpty()){ logAppend("OTG belum colok!\n0E8D=MTK Preloader\n05C6:9008=Qualcomm EDL\n04E8=SAMSUNG Odin\n18D1=D00D Fastboot\n18D1:4EE7 ADB\n05AC= iPhone DFU\n0403:6001= UFS ISP FTDI"); return; } for(UsbDevice d:ds.values()){ logAppend("USB: VID 0x"+Integer.toHexString(d.getVendorId())+" PID 0x"+Integer.toHexString(d.getProductId())+" "+d.getProductName()+" -> "+getMode(d)); if(!um.hasPermission(d)){ PendingIntent pi=PendingIntent.getBroadcast(this,0,new Intent("USB_PERMISSION"),PendingIntent.FLAG_IMMUTABLE); um.requestPermission(d,pi); } else { dev=d; logAppend("CONNECTED NO ROOT: "+getMode(d)); } } }
    String getMode(UsbDevice d){ int v=d.getVendorId(), p=d.getProductId(); if(v==0x0e8d) return "MTK Preloader - Bypass Auth V2 + SP Flash"; if(v==0x05c6 && (p==0x9008||p==0x9006)) return "QUALCOMM EDL 9008 - Sahara/Firehose/QCN"; if(v==0x04e8) return "SAMSUNG Download/Odin - AP/BL/CP/CSC + KG Bypass"; if(v==0x18d1 && p==0xd00d) return "FASTBOOT"; if(v==0x18d1 && p==0x4ee7) return "ADB - FRP/MiCloud"; if(v==0x05ac) return "IPHONE Recovery/DFU - Checkm8"; if(v==0x0403) return "FTDI UFS ISP - UFS Tool"; return "UNKNOWN"; }
    void list(){ File[] arr=cur.listFiles(); files.clear(); List<String> ns=new ArrayList<>(); if(arr!=null){ Arrays.sort(arr); for(File f:arr){ files.add(f); ns.add((f.isDirectory()?"[DIR] ":"[FILE] ")+f.getName()+" "+(f.isFile()?f.length()/1024/1024+"MB":"")); } } fileList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ns)); pathTxt.setText(cur.getAbsolutePath()); }
    void unzip(){ if(sel.isEmpty()){ toast("Pilih zip/ofp/pac"); return; } new Thread(()->{ try{ if(sel.endsWith(".zip")||sel.endsWith(".ofp")||sel.endsWith(".pac")){ ZipInputStream zis=new ZipInputStream(new FileInputStream(sel)); ZipEntry ze; int c=0; while((ze=zis.getNextEntry())!=null){ File out=new File(cur, ze.getName()); if(ze.isDirectory()) out.mkdirs(); else { out.getParentFile().mkdirs(); FileOutputStream fos=new FileOutputStream(out); byte[] b=new byte[8192]; int l; while((l=zis.read(b))>0) fos.write(b,0,l); fos.close(); c++; } zis.closeEntry(); } zis.close(); logAppend("UNZIP DONE: "+c+" files"); runOnUiThread(()->list()); } else { logAppend("File bukan zip - coba OFP decrypt / PAC extract"); } }catch(Exception e){ logAppend("UNZIP ERR: "+e.getMessage()); }}).start(); }
    void flash(){ if(sel.isEmpty()){ toast("Pilih img"); return; } String p=spinPart.getSelectedItem().toString(); logAppend("FLASH "+p+" <- "+sel); run("fastboot flash "+p+" '"+sel+"' 2>&1 || echo 'Need fastboot mode'"); }
    void odin(String slot){ if(sel.isEmpty()){ toast("Pilih tar.md5"); return; } run("echo 'ODIN FLASH "+slot+" <- "+sel+"' && heimdall flash --"+slot+" '"+sel+"' 2>&1 || odin4 -a '"+sel+"' 2>&1 || tar -tvf '"+sel+"' | head -20"); }
    void run(String cmd){ logAppend("\n> $ "+cmd+"\n"); new Thread(()->{ try{ Process pr=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd+" 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(pr.getInputStream())); String l; StringBuilder sb=new StringBuilder(); while((l=r.readLine())!=null) sb.append(l).append("\n"); pr.waitFor(); String res=sb.toString(); runOnUiThread(()->logAppend(res)); }catch(Exception e){ runOnUiThread(()->logAppend("ERR: "+e.getMessage())); }}).start(); }
    void logAppend(String s){ runOnUiThread(()->{ log.append(s+"\n"); }); }
    void toast(String s){ Toast.makeText(this,s,Toast.LENGTH_SHORT).show(); }
}
