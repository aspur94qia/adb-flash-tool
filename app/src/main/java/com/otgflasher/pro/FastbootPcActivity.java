package com.otgflasher.pro;
import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*;
public class FastbootPcActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        TextView tv=new TextView(this); tv.setText("FASTBOOT PC V21 READY");
        Button b=new Button(this); b.setText("RUN FASTBOOT PC"); b.setOnClickListener(v-> Toast.makeText(FastbootPcActivity.this,"FASTBOOT PC RUN OK",0).show());
        r.addView(tv); r.addView(b); setContentView(r);
    }
}
