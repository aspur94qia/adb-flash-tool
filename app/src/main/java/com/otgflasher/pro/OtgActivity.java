package com.otgflasher.pro;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class OtgActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout r=new LinearLayout(this);
        r.setOrientation(LinearLayout.VERTICAL);
        r.setPadding(30,30,30,30);
        TextView tv=new TextView(this);
        tv.setText("OTG FLASH V23 FIX GRADLE - IJO");
        Button b=new Button(this);
        b.setText("RUN OtgActivity");
        b.setOnClickListener(v-> Toast.makeText(OtgActivity.this,"OtgActivity OK",0).show());
        r.addView(tv);
        r.addView(b);
        setContentView(r);
    }
}
