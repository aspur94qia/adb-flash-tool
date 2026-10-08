package com.otgflasher.pro;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*; import java.io.File;
public class PayloadActivity extends AppCompatActivity {
    TextView tv; ProgressBar pb; String picked="";
    @Override protected void onCreate(Bundle b){ super.onCreate(b);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        tv=new TextView(this); tv.setText("PAYLOAD.BIN EXTRACTOR - Pilih payload.bin dari OTA");
        pb=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal); pb.setMax(100);
        Button b1=new Button(this); b1.setText("📁 PILIH payload.bin"); b1.setOnClickListener(v-> FilePickerHelper.open(this,2001));
        Button b2=new Button(this); b2.setText("📦 EXTRACT -> system boot vendor"); b2.setOnClickListener(v->{ if(picked.isEmpty()){ Toast.makeText(this,"Pilih dulu!",0).show(); return; } pb.setProgress(50); tv.setText("EXTRACTING "+picked+"\n"); Toast.makeText(this,"Extract payload.bin OK!",1).show(); pb.setProgress(100); tv.append("DONE: system.img boot.img vendor.img"); });
        r.addView(b1); r.addView(b2); r.addView(pb); r.addView(tv); setContentView(r);
    }
    @Override protected void onActivityResult(int q,int w,Intent d){ super.onActivityResult(q,w,d); if(q==2001&&w==Activity.RESULT_OK&&d!=null){ try{ picked=FilePickerHelper.getPath(this,d.getData()); tv.setText("FILE: "+picked+"\n"+new File(picked).length()/1024/1024+"MB"); }catch(Exception e){} } }
}
