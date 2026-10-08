
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import android.view.View;import java.io.*;import java.util.*;import java.util.zip.*;import android.content.*;
public class FileManagerActivity extends Activity {
    TextView txtPath, txtSelected; ListView listFiles;
    File cur = new File("/sdcard/Download");
    List<File> files = new ArrayList<>();
    File selected = null;
    String clipboardPath = ""; boolean isCut = false;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b); setContentView(R.layout.activity_file_manager);
        txtPath=findViewById(R.id.txtPath); txtSelected=findViewById(R.id.txtSelected); listFiles=findViewById(R.id.listFiles);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        findViewById(R.id.btnUp).setOnClickListener(v->{ if(cur.getParentFile()!=null){ cur=cur.getParentFile(); list(); }});
        findViewById(R.id.btnHome).setOnClickListener(v->{ cur=new File("/sdcard/Download"); list(); });
        findViewById(R.id.btnExtract).setOnClickListener(v->extract());
        findViewById(R.id.btnCreateZip).setOnClickListener(v->createZip());
        findViewById(R.id.btnCreateTar).setOnClickListener(v->createTarGz());
        findViewById(R.id.btnCopy).setOnClickListener(v->copy());
        findViewById(R.id.btnCut).setOnClickListener(v->cut());
        findViewById(R.id.btnDelete).setOnClickListener(v->delete());
        findViewById(R.id.btnRename).setOnClickListener(v->rename());
        findViewById(R.id.btnProps).setOnClickListener(v->props());
        findViewById(R.id.btnFlashHere).setOnClickListener(v->flashHere());
        findViewById(R.id.btnPushPull).setOnClickListener(v->pushPull());

        listFiles.setOnItemClickListener((a,vw,pos,id)->{
            File f=files.get(pos);
            if(f.isDirectory()){ cur=f; list(); }
            else { selected=f; txtSelected.setText("SELECTED: "+f.getName()+" | "+(f.length()/1024/1024)+"MB | "+f.getAbsolutePath()); Toast.makeText(this,"Selected: "+f.getName(),Toast.LENGTH_SHORT).show(); }
        });
        listFiles.setOnItemLongClickListener((a,vw,pos,id)->{
            File f=files.get(pos); selected=f; txtSelected.setText("LONG PRESS: "+f.getName()+" - pilih action di bawah"); showContextMenu(f); return true;
        });
        list();
    }

    void list(){
        File[] arr=cur.listFiles();
        files.clear();
        List<String> names=new ArrayList<>();
        if(arr!=null){
            Arrays.sort(arr, (a,b)->{ if(a.isDirectory()&&!b.isDirectory()) return -1; if(!a.isDirectory()&&b.isDirectory()) return 1; return a.getName().compareToIgnoreCase(b.getName()); });
            // add..
            for(File f:arr){
                if(f.getName().startsWith(".")) continue;
                files.add(f);
                String icon = f.isDirectory()? "[DIR] 📁 " : getIcon(f);
                String size = f.isDirectory()? "<DIR>" : formatSize(f.length());
                String date = new java.text.SimpleDateFormat("dd/MM/yy").format(new java.util.Date(f.lastModified()));
                names.add(icon+f.getName()+"\n "+size+" | "+date);
            }
        }
        txtPath.setText(cur.getAbsolutePath()+" ("+files.size()+" items)");
        listFiles.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names));
        if(!clipboardPath.isEmpty()){
            txtSelected.setText("CLIPBOARD: "+new File(clipboardPath).getName()+" ("+(isCut?"CUT":"COPY")+") -> Paste di folder ini? Klik PASTE");
        }
    }

    String getIcon(File f){ String n=f.getName().toLowerCase(); if(n.endsWith(".zip")||n.endsWith(".rar")||n.endsWith(".7z")||n.endsWith(".tar")||n.endsWith(".gz")) return "[ZIP] 📦 "; if(n.endsWith(".img")||n.endsWith(".bin")||n.endsWith(".elf")) return "[IMG] 💾 "; if(n.endsWith(".apk")) return "[APK] 📱 "; if(n.endsWith(".txt")||n.endsWith(".log")) return "[TXT] 📄 "; return "[FILE] 📄 "; }
    String formatSize(long s){ if(s<1024) return s+" B"; if(s<1024*1024) return s/1024+" KB"; return s/1024/1024+" MB"; }

    void extract(){
        if(selected==null){ Toast.makeText(this,"Pilih file zip/tar/gz/ofp/pac dulu",Toast.LENGTH_SHORT).show(); return; }
        new Thread(()->{
            try{
                String name=selected.getName().toLowerCase();
                if(name.endsWith(".zip")){
                    ZipInputStream zis=new ZipInputStream(new FileInputStream(selected)); ZipEntry ze; int c=0; while((ze=zis.getNextEntry())!=null){ File out=new File(cur, ze.getName()); if(ze.isDirectory()) out.mkdirs(); else { out.getParentFile().mkdirs(); FileOutputStream fos=new FileOutputStream(out); byte[] b=new byte[8192]; int l; while((l=zis.read(b))>0) fos.write(b,0,l); fos.close(); c++; } zis.closeEntry(); } zis.close(); toastOnUi("EXTRACT ZIP DONE: "+c+" files");
                } else if(name.endsWith(".tar")){
                    // simple tar extract via shell tar
                    Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","tar -xvf '"+selected.getAbsolutePath()+"' -C '"+cur.getAbsolutePath()+"' 2>&1"}); p.waitFor(); toastOnUi("TAR EXTRACT DONE");
                } else if(name.endsWith(".tar.gz")||name.endsWith(".tgz")||name.endsWith(".gz")){
                    if(name.endsWith(".gz") &&!name.endsWith(".tar.gz")){
                        File out=new File(cur, selected.getName().replace(".gz","")); GZIPInputStream gis=new GZIPInputStream(new FileInputStream(selected)); FileOutputStream fos=new FileOutputStream(out); byte[] b=new byte[8192]; int l; while((l=gis.read(b))>0) fos.write(b,0,l); fos.close(); gis.close(); toastOnUi("GUNZIP DONE: "+out.getName());
                    } else {
                        Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","tar -xzf '"+selected.getAbsolutePath()+"' -C '"+cur.getAbsolutePath()+"' 2>&1"}); p.waitFor(); toastOnUi("TAR.GZ EXTRACT DONE");
                    }
                } else {
                    toastOnUi("Support: zip, tar, tar.gz, gz. File ini: "+name+" - coba unzip");
                }
                runOnUiThread(()->list());
            }catch(Exception e){ toastOnUi("EXTRACT ERR: "+e.getMessage()); }
        }).start();
    }

    void createZip(){
        new Thread(()->{
            try{
                File zipFile=new File(cur.getParent(), cur.getName()+".zip");
                if(selected!=null && selected.isFile()) zipFile=new File(cur, selected.getName()+".zip");
                ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(zipFile));
                if(selected!=null && selected.isFile()){
                    zos.putNextEntry(new ZipEntry(selected.getName())); FileInputStream fis=new FileInputStream(selected); byte[] b=new byte[8192]; int l; while((l=fis.read(b))>0) zos.write(b,0,l); fis.close(); zos.closeEntry();
                } else {
                    File[] fs=cur.listFiles(); if(fs!=null) for(File f:fs){ if(f.isFile()){ zos.putNextEntry(new ZipEntry(f.getName())); FileInputStream fis=new FileInputStream(f); byte[] b=new byte[8192]; int l; while((l=fis.read(b))>0) zos.write(b,0,l); fis.close(); zos.closeEntry(); } }
                }
                zos.close(); toastOnUi("CREATE ZIP DONE: "+zipFile.getAbsolutePath()+" "+zipFile.length()/1024+"KB"); runOnUiThread(()->list());
            }catch(Exception e){ toastOnUi("ZIP ERR: "+e.getMessage()); }
        }).start();
    }

    void createTarGz(){
        if(selected==null){ Toast.makeText(this,"Pilih file dulu",Toast.LENGTH_SHORT).show(); return; }
        new Thread(()->{
            try{
                File tarGz=new File(selected.getAbsolutePath()+".tar.gz");
                Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","cd '"+cur.getAbsolutePath()+"' && tar -czf '"+tarGz.getAbsolutePath()+"' '"+selected.getName()+"' 2>&1"}); p.waitFor(); toastOnUi("TAR.GZ DONE: "+tarGz.getName());
                runOnUiThread(()->list());
            }catch(Exception e){ toastOnUi("TAR.GZ ERR: "+e.getMessage()); }
        }).start();
    }

    void copy(){ if(selected==null) return; clipboardPath=selected.getAbsolutePath(); isCut=false; txtSelected.setText("COPIED: "+selected.getName()+" -> buka folder tujuan & paste"); Toast.makeText(this,"Copied: "+selected.getName(),Toast.LENGTH_SHORT).show(); }
    void cut(){ if(selected==null) return; clipboardPath=selected.getAbsolutePath(); isCut=true; txtSelected.setText("CUT: "+selected.getName()+" -> buka folder tujuan & paste"); Toast.makeText(this,"Cut: "+selected.getName(),Toast.LENGTH_SHORT).show(); }

    void delete(){ if(selected==null) return; new android.app.AlertDialog.Builder(this).setTitle("Delete?").setMessage("Delete "+selected.getName()+"?").setPositiveButton("Yes",(d,w)->{ new Thread(()->{ deleteRec(selected); runOnUiThread(()->{ list(); toastOnUi("Deleted: "+selected.getName()); selected=null; }); }).start(); }).setNegativeButton("No",null).show(); }
    void deleteRec(File f){ if(f.isDirectory()) for(File c:f.listFiles()) deleteRec(c); f.delete(); }
    void rename(){ if(selected==null) return; EditText et=new EditText(this); et.setText(selected.getName()); new android.app.AlertDialog.Builder(this).setTitle("Rename").setView(et).setPositiveButton("OK",(d,w)->{ String nn=et.getText().toString(); File nf=new File(selected.getParent(), nn); if(selected.renameTo(nf)){ list(); selected=nf; } }).setNegativeButton("Cancel",null).show(); }
    void props(){ if(selected==null) return; String info="Name: "+selected.getName()+"\nPath: "+selected.getAbsolutePath()+"\nSize: "+formatSize(selected.length())+" ("+selected.length()+" bytes)\nDate: "+new java.util.Date(selected.lastModified())+"\nCanRead: "+selected.canRead()+" CanWrite: "+selected.canWrite()+"\nIsDir: "+selected.isDirectory()+"\nMD5: (hitung...)\n"; new android.app.AlertDialog.Builder(this).setTitle("Properties").setMessage(info).setPositiveButton("OK",null).show(); }
    void flashHere(){ if(selected==null) return; String n=selected.getName().toLowerCase(); String part="boot"; if(n.contains("recovery")) part="recovery"; else if(n.contains("vbmeta")) part="vbmeta"; else if(n.contains("super")) part="super"; else if(n.contains("system")) part="system"; else if(n.contains("vendor")) part="vendor"; new android.app.AlertDialog.Builder(this).setTitle("Flash?").setMessage("Flash "+selected.getName()+" as "+part+"?\nfastboot flash "+part+" "+selected.getAbsolutePath()).setPositiveButton("Flash",(d,w)->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","fastboot flash "+part+" '"+selected.getAbsolutePath()+"' 2>&1"}); p.waitFor(); }catch(Exception e){} }).setNegativeButton("Cancel",null).show(); }
    void pushPull(){ if(selected==null) return; Toast.makeText(this,"PUSH to OTG target: adb push "+selected.getName()+" /sdcard/Download/ (via UsbManager no root)",Toast.LENGTH_LONG).show(); }

    void showContextMenu(File f){
        String[] ops={"Extract Here (ZArchiver)","Compress to ZIP","Compress to TAR.GZ","Copy","Cut","Delete","Rename","Properties","Flash as Boot/Recovery","MD5 Checksum"};
        new android.app.AlertDialog.Builder(this).setTitle(f.getName()).setItems(ops,(d,which)->{
            switch(which){ case 0: extract(); break; case 1: createZip(); break; case 2: createTarGz(); break; case 3: copy(); break; case 4: cut(); break; case 5: delete(); break; case 6: rename(); break; case 7: props(); break; case 8: flashHere(); break; case 9: md5(); break; }
        }).show();
    }
    void md5(){ if(selected==null) return; new Thread(()->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","md5sum '"+selected.getAbsolutePath()+"' 2>&1"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String s=r.readLine(); p.waitFor(); String res=s!=null?s:"MD5: "+selected.length(); toastOnUi(res); }catch(Exception e){ toastOnUi("MD5 ERR: "+e.getMessage()); }}).start(); }
    void toastOnUi(String s){ runOnUiThread(()->{ txtSelected.setText(s); Toast.makeText(this,s,Toast.LENGTH_LONG).show(); }); }
}
