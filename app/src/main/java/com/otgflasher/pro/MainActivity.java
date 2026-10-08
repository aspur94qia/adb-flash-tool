package com.otgflasher.pro;
import android.content.*; import android.os.Bundle; import android.view.*; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import com.otgflasher.pro.root.RootUtil;
public class MainActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
        ViewGroup vg=(ViewGroup)findViewById(android.R.id.content);
        LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(20,20,20,20);
        TextView tvRoot=new TextView(this); boolean rooted=RootUtil.isRooted(); tvRoot.setText(rooted?"🔓 ROOT GRANTED - REAL FLASH MODE":"🔒 NO ROOT - NEED ROOT FOR REAL FLASH"); tvRoot.setBackgroundColor(rooted?0xFF4CAF50:0xFFF44336); tvRoot.setTextColor(-1); tvRoot.setPadding(20,20,20,20);
        root.addView(tvRoot);
        Button bPay=new Button(this); bPay.setText("📦 PAYLOAD REAL EXTRACTOR (ROOT)"); bPay.setBackgroundColor(0xFF2196F3); bPay.setTextColor(-1); bPay.setOnClickListener(v-> startActivity(new Intent(this,PayloadActivity.class))); root.addView(bPay);
        Button bFb=new Button(this); bFb.setText("🚀 FASTBOOT REAL FLASH (ROOT + OTG)"); bFb.setBackgroundColor(0xFFF44336); bFb.setTextColor(-1); bFb.setOnClickListener(v-> startActivity(new Intent(this,FastbootActivity.class))); root.addView(bFb);
        Button bMtk=new Button(this); bMtk.setText("⚡ MTK (COMING V26)"); bMtk.setOnClickListener(v-> startActivity(new Intent(this,MtkActivity.class))); root.addView(bMtk);
        Button bOfp=new Button(this); bOfp.setText("🔓 OFP (COMING V27)"); bOfp.setOnClickListener(v-> startActivity(new Intent(this,OfpActivity.class))); root.addView(bOfp);
        Button bEdl=new Button(this); bEdl.setText("🔥 EDL QUALCOMM"); bEdl.setOnClickListener(v-> startActivity(new Intent(this,EdlActivity.class))); root.addView(bEdl);
        TextView tvInfo=new TextView(this); tvInfo.setText("\nV25 REAL ROOT:\n- payload-dumper-go ARM64 embedded\n- fastboot ARM64 embedded\n- flash via su -c dd\n- flash via fastboot flash\n\nButuh: HP ROOT + OTG + Kabel OTG\nUntuk flash HP lain via HP ini");
        root.addView(tvInfo);
        vg.addView(root);
    }
}
