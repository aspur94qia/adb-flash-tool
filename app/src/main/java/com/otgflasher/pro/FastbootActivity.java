package com.otgflasher.pro;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class FastbootActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout r=new LinearLayout(this);
        r.setOrientation(LinearLayout.VERTICAL);
        r.setPadding(30,30,30,30);
        TextView tv=new TextView(this);
        tv.setText("FASTBOOT V23 FIX GRADLE - IJO");
        Button b=new Button(this);
        b.setText("RUN FastbootActivity");
        b.setOnClickListener(v-> Toast.makeText(FastbootActivity.this,"FastbootActivity OK",0).show());
        r.addView(tv);
        r.addView(b);
        setContentView(r);
    }
}
