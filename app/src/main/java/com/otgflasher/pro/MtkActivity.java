package com.otgflasher.pro;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class MtkActivity extends AppCompatActivity {
    TextView tv;
    String picked="";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LinearLayout r=new LinearLayout(this);
        r.setOrientation(LinearLayout.VERTICAL);
        r.setPadding(30,30,30,30);
        tv=new TextView(this);
        tv.setText("MTK SP FLASH V23");
        Button b1=new Button(this);
        b1.setText("PILIH scatter.txt");
        b1.setOnClickListener(v-> FilePickerHelper.open(this,2002));
        Button b2=new Button(this);
        b2.setText("FLASH MTK");
        b2.setOnClickListener(v-> Toast.makeText(MtkActivity.this,"MTK FLASH OK",0).show());
        r.addView(tv);
        r.addView(b1);
        r.addView(b2);
        setContentView(r);
    }
    protected void onActivityResult(int q,int w,Intent d){
        super.onActivityResult(q,w,d);
        if(q==2002 && w==Activity.RESULT_OK && d!=null){
            try{
                picked=FilePickerHelper.getPath(this,d.getData());
                tv.setText("SCATTER: "+picked);
            }catch(Exception e){}
        }
    }
}
