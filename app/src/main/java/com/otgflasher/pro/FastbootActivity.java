package com.otgflasher.pro;
import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*;
public class FastbootActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){ super.onCreate(b);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        TextView tv=new TextView(this); tv.setText("FastbootActivity V20 READY");
        Button bb=new Button(this); bb.setText("RUN FastbootActivity"); bb.setOnClickListener(v-> Toast.makeText(this,"FastbootActivity RUN",0).show());
        r.addView(tv); r.addView(bb); setContentView(r);
    }
}
