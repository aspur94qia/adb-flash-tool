package com.otgflasher.pro;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class FastbootPcActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout r=new LinearLayout(this);
        r.setOrientation(LinearLayout.VERTICAL);
        r.setPadding(30,30,30,30);
        TextView tv=new TextView(this);
        tv.setText("FASTBOOT PC V23 FIX GRADLE - IJO");
        Button b=new Button(this);
        b.setText("RUN FastbootPcActivity");
        b.setOnClickListener(v-> Toast.makeText(FastbootPcActivity.this,"FastbootPcActivity OK",0).show());
        r.addView(tv);
        r.addView(b);
        setContentView(r);
    }
}
