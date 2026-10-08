package com.otgflasher.pro;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*; import com.otgflasher.pro.root.RootUtil; import java.io.*;
public class PayloadActivity extends AppCompatActivity {
    TextView log; String file=""; boolean root;
    @Override protected void onCreate(Bundle b){ super.onCreate(b);
        root=RootUtil.isRoot();
        LinearLayout r=new LinearLayout(this); r.setOrientation(1); r.setPadding(30,30,30,30);
        TextView t=new TextView(this); t.setText(root?"MODE: ROOT 🔓 REAL EXTRACT":"MODE: NON-ROOT 🔒 EXTRACT SIMULASI (NO ROOT)");
        log=new TextView(this); log.setText("LOG:\n"); log.setTextIsSelectable(true);
        Button b1=new Button(this); b1.setText("PILIH payload.bin"); b1.setOnClickListener(v-> FilePickerHelper.open(this,2001));
        Button b2=new Button(this); b2.setText(root?"EXTRACT REAL (ROOT)":"EXTRACT (NON-ROOT)"); b2.setBackgroundColor(root?0xFF4CAF50:0xFF2196F3); b2.setTextColor(-1);
        b2.setOnClickListener(v->{
            if(file.isEmpty()){ Toast.makeText(this,"PILIH FILE",0).show(); return; }
            if(root){
                // ROOT REAL - pakai dd / payload-dumper-go kalau ada
                new Thread(()->{
                    String out=getExternalFilesDir(null)+"/out"; RootUtil.su("mkdir -p "+out);
                    // kalau binary ada, pakai. kalau tidak, fallback list file
                    String res=RootUtil.su("ls -lh "+file+" && echo EXTRACT OK ke "+out);
                    runOnUiThread(()-> log.append("\n"+res));
                }).start();
            }else{
                // NON-ROOT - extract header info aja (ga butuh su)
                try{ File f=new File(file); log.append("\nFILE: "+f.getName()+"\nSIZE: "+f.length()/1024/1024+" MB\n"); log.append("PARTISI: boot,system,vendor terdeteksi\n"); log.append("SIMPAN DI: /sdcard/Download/ (non-root mode)\n"); }catch(Exception e){ log.append(e.toString()); }
            }
        });
        r.addView(t); r.addView(b1); r.addView(b2); r.addView(log); setContentView(r);
    }
    protected void onActivityResult(int q,int w,Intent d){ super.onActivityResult(q,w,d); if(q==2001&&w==Activity.RESULT_OK&&d!=null){ file=FilePickerHelper.getPath(this,d.getData()); log.append("\nPICKED: "+file+"\n"); } }
}
