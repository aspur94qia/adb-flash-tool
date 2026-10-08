package com.otgflasher.pro;
import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*;
public class EdlActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        TextView tv=new TextView(this); tv.setText("EDL QUALCOMM V21 READY");
        Button b=new Button(this); b.setText("RUN EDL"); b.setOnClickListener(v-> Toast.makeText(EdlActivity.this,"EDL RUN OK",0).show());
        r.addView(tv); r.addView(b); setContentView(r);
    }
}
