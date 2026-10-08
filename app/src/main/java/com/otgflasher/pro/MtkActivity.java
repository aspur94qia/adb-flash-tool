package com.otgflasher.pro;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*;
public class MtkActivity extends AppCompatActivity {
    TextView tv; String picked=""; EditText edt;
    @Override protected void onCreate(Bundle b){ super.onCreate(b);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        tv=new TextView(this); tv.setText("MTK SP FLASH"); edt=new EditText(this); edt.setText("system");
        Button b1=new Button(this); b1.setText("📁 PILIH scatter.txt"); b1.setOnClickListener(v-> FilePickerHelper.open(this,2002));
        Button b2=new Button(this); b2.setText("⚡ FLASH MTK"); b2.setOnClickListener(v->{ Toast.makeText(this,"Flash "+edt.getText()+" OK",0).show(); });
        r.addView(tv); r.addView(edt); r.addView(b1); r.addView(b2); setContentView(r);
    }
    @Override protected void onActivityResult(int q,int w,Intent d){ super.onActivityResult(q,w,d); if(q==2002&&w==Activity.RESULT_OK&&d!=null){ try{ picked=FilePickerHelper.getPath(this,d.getData()); tv.setText("SCATTER: "+picked); }catch(Exception e){} } }
}
