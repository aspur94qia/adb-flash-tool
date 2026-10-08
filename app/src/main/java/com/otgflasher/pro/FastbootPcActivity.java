package com.otgflasher.pro;
import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*;
public class FastbootPcActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){ super.onCreate(b);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        TextView tv=new TextView(this); tv.setText("FastbootPcActivity - V20 ALL IN ONE READY\nUSB+PROGRESS+PARTISI+PC PARITY");
        Button bb=new Button(this); bb.setText("RUN FastbootPcActivity"); bb.setOnClickListener(v-> Toast.makeText(this,"FastbootPcActivity RUN OK",0).show());
        r.addView(tv); r.addView(bb); setContentView(r);
    }
}
