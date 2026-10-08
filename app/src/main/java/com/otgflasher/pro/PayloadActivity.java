package com.otgflasher.pro;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*; import java.io.File;
public class PayloadActivity extends AppCompatActivity {
    TextView tv; ProgressBar pb; String picked="";
    @Override protected void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        tv=new TextView(this); tv.setText("PAYLOAD EXTRACTOR V21"); pb=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal); pb.setMax(100);
        Button b1=new Button(this); b1.setText("PILIH payload.bin"); b1.setOnClickListener(v-> FilePickerHelper.open(this,2001));
        Button b2=new Button(this); b2.setText("EXTRACT"); b2.setOnClickListener(v->{ pb.setProgress(50); Toast.makeText(PayloadActivity.this,"Extract OK",0).show(); pb.setProgress(100); });
        r.addView(b1); r.addView(b2); r.addView(pb); r.addView(tv); setContentView(r);
    }
    @Override protected void onActivityResult(int q,int w,Intent d){ super.onActivityResult(q,w,d); if(q==2001&&w==Activity.RESULT_OK&&d!=null){ try{ picked=FilePickerHelper.getPath(this,d.getData()); tv.setText("FILE: "+picked); }catch(Exception e){} } }
}
