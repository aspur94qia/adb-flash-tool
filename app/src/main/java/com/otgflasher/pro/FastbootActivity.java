package com.otgflasher.pro;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import com.otgflasher.pro.root.RootUtil;
public class FastbootActivity extends AppCompatActivity {
    TextView log; String img=""; EditText edt;
    @Override protected void onCreate(Bundle b){ super.onCreate(b);
        boolean root=RootUtil.isRoot();
        LinearLayout r=new LinearLayout(this); r.setOrientation(1); r.setPadding(30,30,30,30);
        TextView t=new TextView(this); t.setText(root?"FASTBOOT ROOT 🔓":"FASTBOOT NON-ROOT 🔒 (butuh root untuk flash)");
        log=new TextView(this); log.setTextIsSelectable(true); log.setText("LOG:\n");
        edt=new EditText(this); edt.setText("system"); edt.setHint("partisi");
        Button b1=new Button(this); b1.setText("PILIH.img"); b1.setOnClickListener(v-> FilePickerHelper.open(this,3001));
        Button b2=new Button(this); b2.setText(root?"FLASH VIA DD (ROOT REAL)":"INFO FLASH (NON-ROOT)"); b2.setBackgroundColor(root?0xFFF44336:0xFF9E9E9E); b2.setTextColor(-1);
        b2.setOnClickListener(v->{
            if(img.isEmpty()){ Toast.makeText(this,"PILIH IMG",0).show(); return; }
            String part=edt.getText().toString();
            if(root){
                new Thread(()->{
                    String cmd="dd if="+img+" of=/dev/block/by-name/"+part+" bs=4096 2>&1; echo DONE";
                    String res=RootUtil.su(cmd);
                    runOnUiThread(()-> log.append("\n"+res));
                }).start();
            }else{
                log.append("\n[NON-ROOT] Untuk flash butuh root:\n");
                log.append("1. Root HP ini pakai Magisk\n");
                log.append("2. Atau pindah file "+img+" ke PC\n");
                log.append("3. fastboot flash "+part+" "+img+"\n");
            }
        });
        r.addView(t); r.addView(edt); r.addView(b1); r.addView(b2); r.addView(log); setContentView(r);
    }
    protected void onActivityResult(int q,int w,Intent d){ super.onActivityResult(q,w,d); if(q==3001&&w==Activity.RESULT_OK&&d!=null){ img=FilePickerHelper.getPath(this,d.getData()); log.append("\nIMG: "+img+"\n"); } }
}
