package com.otgflasher.pro;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*; import com.otgflasher.pro.root.*;
import java.io.File;
public class PayloadActivity extends AppCompatActivity {
    TextView tvLog; ProgressBar pb; String picked=""; String binPath="";
    @Override protected void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        ScrollView sv=new ScrollView(this); TextView title=new TextView(this); title.setText("PAYLOAD EXTRACTOR V25 REAL ROOT\n"+(RootUtil.isRooted()?"ROOT: YES 🔓":"ROOT: NO - NEED ROOT"));
        tvLog=new TextView(this); tvLog.setText("LOG:\n"); tvLog.setTextIsSelectable(true);
        pb=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal); pb.setMax(100);
        Button b1=new Button(this); b1.setText("1. INSTALL BINARY (ROOT)"); b1.setOnClickListener(v->{
            binPath=BinaryInstaller.install(this,"payload-dumper-go");
            tvLog.append("\nBIN: "+binPath+"\n"+RootUtil.execRoot("ls -lh "+binPath));
        });
        Button b2=new Button(this); b2.setText("2. PILIH payload.bin"); b2.setOnClickListener(v-> FilePickerHelper.open(this,2001));
        Button b3=new Button(this); b3.setText("3. EXTRACT REAL (boot,system,vendor)"); b3.setBackgroundColor(0xFF4CAF50); b3.setTextColor(-1);
        b3.setOnClickListener(v->{
            if(picked.isEmpty()){ Toast.makeText(this,"PILIH FILE DULU",0).show(); return; }
            if(binPath.isEmpty()) binPath=BinaryInstaller.install(this,"payload-dumper-go");
            tvLog.append("\nEXTRACTING: "+picked+"\n");
            new Thread(()->{
                String outDir=new File(getExternalFilesDir(null),"payload_out").getAbsolutePath();
                RootUtil.execRoot("mkdir -p "+outDir);
                String cmd=binPath+" -o "+outDir+" "+picked;
                String res=RootUtil.execRoot(cmd+" 2>&1");
                runOnUiThread(()->{ tvLog.append(res+"\nDONE: "+outDir+"\n"); pb.setProgress(100); Toast.makeText(this,"EXTRACT SELESAI cek "+outDir,1).show(); });
            }).start();
        });
        r.addView(title); r.addView(b1); r.addView(b2); r.addView(b3); r.addView(pb); r.addView(tvLog); sv.addView(r); setContentView(sv);
    }
    protected void onActivityResult(int q,int w,Intent d){ super.onActivityResult(q,w,d); if(q==2001&&w==Activity.RESULT_OK&&d!=null){ try{ picked=FilePickerHelper.getPath(this,d.getData()); tvLog.append("\nFILE: "+picked+"\n"); pb.setProgress(10); }catch(Exception e){} } }
}
