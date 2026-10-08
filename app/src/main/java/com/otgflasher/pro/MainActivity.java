package com.otgflasher.pro;
import android.content.Intent; import android.os.Bundle; import android.view.ViewGroup; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import com.otgflasher.pro.root.RootUtil;
public class MainActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        ViewGroup vg=(ViewGroup)findViewById(android.R.id.content);
        LinearLayout r=new LinearLayout(this); r.setOrientation(1); r.setPadding(20,20,20,20);
        boolean root=RootUtil.isRoot();
        TextView tv=new TextView(this); tv.setText(root?"🔓 ROOT DETECTED - FULL FLASH AKTIF":"🔒 NON-ROOT MODE - EXTRACT ONLY"); tv.setBackgroundColor(root?0xFF4CAF50:0xFFFF9800); tv.setTextColor(-1); tv.setPadding(20,20,20,20);
        Button p=new Button(this); p.setText("📦 PAYLOAD (DUAL)"); p.setOnClickListener(v-> startActivity(new Intent(this,PayloadActivity.class)));
        Button f=new Button(this); f.setText("🚀 FASTBOOT (DUAL)"); f.setOnClickListener(v-> startActivity(new Intent(this,FastbootActivity.class)));
        Button m=new Button(this); m.setText("⚡ MTK"); m.setOnClickListener(v-> startActivity(new Intent(this,MtkActivity.class)));
        Button o=new Button(this); o.setText("🔓 OFP"); o.setOnClickListener(v-> startActivity(new Intent(this,OfpActivity.class)));
        r.addView(tv); r.addView(p); r.addView(f); r.addView(m); r.addView(o); vg.addView(r);
    }
}
