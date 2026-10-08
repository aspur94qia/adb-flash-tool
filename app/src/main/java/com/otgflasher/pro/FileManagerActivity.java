
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import java.io.*;import java.util.*;import java.util.zip.*;
public class FileManagerActivity extends Activity {
    TextView txtPath, txtSelected; ListView listFiles; File cur=new File("/sdcard/Download"); java.util.List<File> files=new java.util.ArrayList<>(); File selected=null;
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_file_manager);
        txtPath=findViewById(R.id.txtPath); txtSelected=findViewById(R.id.txtSelected); listFiles=findViewById(R.id.listFiles);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        findViewById(R.id.btnUp).setOnClickListener(v->{ if(cur.getParentFile()!=null){ cur=cur.getParentFile(); list(); }});
        findViewById(R.id.btnExtract).setOnClickListener(v->extract());
        findViewById(R.id.btnCreateZip).setOnClickListener(v->createZip());
        findViewById(R.id.btnCreateTar).setOnClickListener(v->createTar());
        findViewById(R.id.btnDelete).setOnClickListener(v->{ if(selected!=null){ deleteRec(selected); list(); }});
        findViewById(R.id.btnRename).setOnClickListener(v->{ if(selected==null) return; EditText et=new EditText(this); et.setText(selected.getName()); new android.app.AlertDialog.Builder(this).setTitle("Rename").setView(et).setPositiveButton("OK",(d,w)->{ File nf=new File(selected.getParent(), et.getText().toString()); selected.renameTo(nf); list(); }).show(); });
        findViewById(R.id.btnFlashHere).setOnClickListener(v->{ if(selected==null) return; Toast.makeText(this,"Flash: "+selected.getName(),Toast.LENGTH_LONG).show(); });
        listFiles.setOnItemClickListener((a,vw,pos,id)->{ File f=files.get(pos); if(f.isDirectory()){ cur=f; list(); } else { selected=f; txtSelected.setText("SELECTED: "+f.getName()+" | "+f.length()/1024/1024+"MB"); }});
        list();
    }
    void list(){ File[] arr=cur.listFiles(); files.clear(); java.util.List<String> names=new java.util.ArrayList<>(); if(arr!=null){ java.util.Arrays.sort(arr,(x,y)->{ if(x.isDirectory()&&!y.isDirectory()) return -1; if(!x.isDirectory()&&y.isDirectory()) return 1; return x.getName().compareToIgnoreCase(y.getName()); }); for(File f:arr){ if(f.getName().startsWith(".")) continue; files.add(f); String icon=f.isDirectory()?"[DIR] ":"[FILE] "; names.add(icon+f.getName()+" "+(f.isDirectory()?"<DIR>":f.length()/1024/1024+"MB")); } } txtPath.setText(cur.getAbsolutePath()+" ("+files.size()+")"); listFiles.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names)); }
    void extract(){ if(selected==null) return; new Thread(()->{ try{ ZipInputStream zis=new ZipInputStream(new FileInputStream(selected)); ZipEntry ze; int c=0; while((ze=zis.getNextEntry())!=null){ File out=new File(cur, ze.getName()); if(ze.isDirectory()) out.mkdirs(); else { out.getParentFile().mkdirs(); FileOutputStream fos=new FileOutputStream(out); byte[] buf=new byte[8192]; int l; while((l=zis.read(buf))>0) fos.write(buf,0,l); fos.close(); c++; } zis.closeEntry(); } zis.close(); int cnt=c; runOnUiThread(()->{ txtSelected.setText("EXTRACT DONE: "+cnt+" files"); list(); }); }catch(Exception e){ runOnUiThread(()->txtSelected.setText("ERR: "+e.getMessage())); }}).start(); }
    void createZip(){ new Thread(()->{ try{ File out=new File(cur, "archive.zip"); if(selected!=null && selected.isFile()) out=new File(cur, selected.getName()+".zip"); ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(out)); if(selected!=null && selected.isFile()){ zos.putNextEntry(new ZipEntry(selected.getName())); FileInputStream fis=new FileInputStream(selected); byte[] buf=new byte[8192]; int l; while((l=fis.read(buf))>0) zos.write(buf,0,l); fis.close(); zos.closeEntry(); } zos.close(); runOnUiThread(()->{ txtSelected.setText("ZIP DONE: "+out.getName()); list(); }); }catch(Exception e){ runOnUiThread(()->txtSelected.setText("ERR: "+e.getMessage())); }}).start(); }
    void createTar(){ if(selected==null) return; new Thread(()->{ try{ Process p=Runtime.getRuntime().exec(new String[]{"sh","-c","cd '"+cur.getAbsolutePath()+"' && tar -czf '"+selected.getName()+".tar.gz' '"+selected.getName()+"' 2>&1"}); p.waitFor(); runOnUiThread(()->{ txtSelected.setText("TAR.GZ DONE"); list(); }); }catch(Exception e){ runOnUiThread(()->txtSelected.setText("ERR: "+e.getMessage())); }}).start(); }
    void deleteRec(File f){ if(f.isDirectory()){ File[] cs=f.listFiles(); if(cs!=null) for(File c:cs) deleteRec(c); } f.delete(); }
}
