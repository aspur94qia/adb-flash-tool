package com.otgflasher.pro;
import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*;
public class OtgActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){ super.onCreate(b);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        TextView tv=new TextView(this); tv.setText("OtgActivity V20 READY");
        Button bb=new Button(this); bb.setText("RUN OtgActivity"); bb.setOnClickListener(v-> Toast.makeText(this,"OtgActivity RUN",0).show());
        r.addView(tv); r.addView(bb); setContentView(r);
    }
}
