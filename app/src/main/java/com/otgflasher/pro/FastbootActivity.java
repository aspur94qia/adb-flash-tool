package com.otgflasher.pro;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*; import com.otgflasher.pro.root.*;
public class FastbootActivity extends AppCompatActivity {
    TextView tvLog; String picked=""; String fbPath=""; String part="system";
    @Override protected void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        ScrollView sv=new ScrollView(this);
        TextView title=new TextView(this); title.setText("FASTBOOT REAL ROOT V25\n"+(RootUtil.isRooted()?"ROOT YES 🔓":"NEED ROOT"));
        tvLog=new TextView(this); tvLog.setText("LOG:\n"); tvLog.setTextIsSelectable(true);
        EditText edtPart=new EditText(this); edtPart.setHint("partisi: system/boot/vendor/super"); edtPart.setText("system");
        Button b1=new Button(this); b1.setText("1. INSTALL FASTBOOT BIN"); b1.setOnClickListener(v->{
            fbPath=BinaryInstaller.install(this,"fastboot"); tvLog.append("\nFASTBOOT: "+fbPath+"\n"+RootUtil.execRoot(fbPath+" --version 2>&1"));
        });
        Button b2=new Button(this); b2.setText("2. PILIH IMG FILE"); b2.setOnClickListener(v-> FilePickerHelper.open(this,3001));
        Button b3=new Button(this); b3.setText("3. CHECK DEVICES"); b3.setOnClickListener(v->{
            if(fbPath.isEmpty()) fbPath=BinaryInstaller.install(this,"fastboot");
            String res=RootUtil.execRoot(fbPath+" devices 2>&1"); tvLog.append("\nDEVICES:\n"+res+"\n");
        });
        Button b4=new Button(this); b4.setText("4. FLASH REAL (ROOT + OTG)"); b4.setBackgroundColor(0xFFF44336); b4.setTextColor(-1);
        b4.setOnClickListener(v->{
            part=edtPart.getText().toString().trim(); if(picked.isEmpty()){ Toast.makeText(this,"PILIH IMG DULU",0).show(); return; }
            if(fbPath.isEmpty()) fbPath=BinaryInstaller.install(this,"fastboot");
            tvLog.append("\nFLASHING "+part+" <- "+picked+"\n");
            new Thread(()->{
                String cmd=fbPath+" flash "+part+" "+picked+" 2>&1";
                String res=RootUtil.execRoot(cmd);
                runOnUiThread(()->{ tvLog.append(res+"\n"); Toast.makeText(this,"FLASH DONE",1).show(); });
            }).start();
        });
        Button b5=new Button(this); b5.setText("5. FLASH VIA DD (EMMC DIRECT - ROOT ONLY!)"); b5.setBackgroundColor(0xFF9C27B0); b5.setTextColor(-1);
        b5.setOnClickListener(v->{
            part=edtPart.getText().toString().trim();
            new Thread(()->{
                String cmd="dd if="+picked+" of=/dev/block/by-name/"+part+" bs=4096 2>&1; echo EXIT:$?";
                String res=RootUtil.execRoot(cmd);
                runOnUiThread(()-> tvLog.append("\nDD FLASH:\n"+res+"\n"));
            }).start();
        });
        r.addView(title); r.addView(b1); r.addView(edtPart); r.addView(b2); r.addView(b3); r.addView(b4); r.addView(b5); r.addView(tvLog);
        sv.addView(r); setContentView(sv);
    }
    protected void onActivityResult(int q,int w,Intent d){ super.onActivityResult(q,w,d); if(q==3001&&w==Activity.RESULT_OK&&d!=null){ try{ picked=FilePickerHelper.getPath(this,d.getData()); tvLog.append("\nIMG: "+picked+"\n"); }catch(Exception e){} } }
}
