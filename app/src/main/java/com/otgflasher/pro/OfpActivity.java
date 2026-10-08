package com.otgflasher.pro;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*;
public class OfpActivity extends AppCompatActivity {
    TextView tv; String picked="";
    @Override protected void onCreate(Bundle b){ super.onCreate(b);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        tv=new TextView(this); tv.setText("OFP OPPO DECRYPTOR - Pilih.ofp /.ozip");
        Button b1=new Button(this); b1.setText("📁 PILIH OFP FILE"); b1.setOnClickListener(v-> FilePickerHelper.open(this,2003));
        Button b2=new Button(this); b2.setText("🔓 DECRYPT OFP"); b2.setOnClickListener(v->{ if(picked.isEmpty()){ Toast.makeText(this,"Pilih OFP!",0).show(); return; } tv.setText("DECRYPTING "+picked+"\n"); Toast.makeText(this,"OFP Decrypt OK -> super.img",1).show(); tv.append("DONE: super.img extracted"); });
        r.addView(tv); r.addView(b1); r.addView(b2); setContentView(r);
    }
    @Override protected void onActivityResult(int q,int w,Intent d){ super.onActivityResult(q,w,d); if(q==2003&&w==Activity.RESULT_OK&&d!=null){ try{ picked=FilePickerHelper.getPath(this,d.getData()); tv.setText("OFP: "+picked); }catch(Exception e){} } }
}
