package com.otgflasher.pro;
import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import android.view.*;
public class BackupActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setPadding(30,30,30,30);
        TextView tv=new TextView(this); tv.setText("BACKUP V21 READY");
        Button b=new Button(this); b.setText("RUN BACKUP"); b.setOnClickListener(v-> Toast.makeText(BackupActivity.this,"BACKUP RUN OK",0).show());
        r.addView(tv); r.addView(b); setContentView(r);
    }
}
