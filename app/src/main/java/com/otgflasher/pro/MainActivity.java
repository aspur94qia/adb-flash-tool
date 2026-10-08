package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.widget.*;import android.content.Intent;import android.net.Uri;import java.io.*;
public class MainActivity extends Activity {
    TextView log, fileTxt; String filePath="";
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); fileTxt=findViewById(R.id.fileTxt);
        Button btnPick=findViewById(R.id.btnPick); Button btnFlash=findViewById(R.id.btnFlash); Button btnAdb=findViewById(R.id.btnAdb);
        btnPick.setOnClickListener(v->{ Intent i=new Intent(Intent.ACTION_GET_CONTENT); i.setType("*/*"); startActivityForResult(i,99); });
        btnFlash.setOnClickListener(v->{
            if(filePath.isEmpty()){ Toast.makeText(this,"Pilih file img dulu bos!",Toast.LENGTH_SHORT).show(); return; }
            log.setText(">> FLASHING: "+filePath+"\n");
            try{ Process p=Runtime.getRuntime().exec(new String[]{"su","-c","ls -l /dev/block/bootdevice/by-name/"}); BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); String l; while((l=r.readLine())!=null){ log.append(l+"\n"); } p.waitFor(); log.append("\n=== READY TO FLASH ===\nFile: "+filePath+"\nGunakan: dd if="+filePath+" of=/dev/block/bootdevice/by-name/boot"); }catch(Exception e){ log.append("ERROR: "+e.getMessage()); }
        });
        btnAdb.setOnClickListener(v->{ log.setText(">> MODE ADB OTG\nColok HP target pake OTG\nAktifin USB Debugging\n"); });
    }
    @Override protected void onActivityResult(int c,int r,Intent d){ super.onActivityResult(c,r,d); if(c==99 && r==RESULT_OK && d!=null){ Uri uri=d.getData(); filePath="/sdcard/"+new File(uri.getPath()).getName(); fileTxt.setText("File: "+uri.getLastPathSegment()); log.setText("File dipilih: "+uri+"\nPath: "+filePath); } }
}
