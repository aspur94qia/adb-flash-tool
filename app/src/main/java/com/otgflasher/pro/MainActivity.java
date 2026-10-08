package com.otgflasher.pro;
import android.app.Activity;
import android.content.*;
import android.hardware.usb.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity {
    TextView tvUsb,tvFile,tvProgress; ProgressBar pb; EditText edtPart; String picked=""; UsbManager um; BroadcastReceiver br;
    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        try{
            ViewGroup vg=(ViewGroup)findViewById(android.R.id.content);
            LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(20,20,20,20);
            tvUsb=new TextView(this); tvUsb.setText("USB: DISCONNECTED - COLOK OTG"); tvUsb.setBackgroundColor(0xFF4CAF50); tvUsb.setTextColor(-1); tvUsb.setPadding(20,20,20,20);
            pb=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal); pb.setMax(100);
            tvProgress=new TextView(this); tvProgress.setText("PROGRESS 0%");
            tvFile=new TextView(this); tvFile.setText("BELUM PILIH FILE");
            edtPart=new EditText(this); edtPart.setText("system"); edtPart.setHint("PARTISI");
            Button btn=new Button(this); btn.setText("📁 PILIH FILE"); btn.setBackgroundColor(0xFFFF9800);
            btn.setOnClickListener(v-> FilePickerHelper.open(this,1001));
            root.addView(tvUsb); root.addView(pb); root.addView(tvProgress); root.addView(edtPart); root.addView(tvFile); root.addView(btn);
            String[] cmds={"flash boot","flash system","flash vendor","flash super","flash vbmeta","erase cache","reboot"};
            for(String s:cmds){ Button bb=new Button(this); bb.setText("FASTBOOT "+s); String cmd=s; bb.setOnClickListener(v->{ Toast.makeText(this,"RUN "+cmd+" "+picked+" PARTISI:"+edtPart.getText(),0).show(); pb.setProgress(50); tvProgress.setText(cmd+" 50%"); }); root.addView(bb); }
            vg.addView(root);
            try{ um=(UsbManager)getSystemService(Context.USB_SERVICE); HashMap<String,UsbDevice> list=um.getDeviceList(); if(list.size()>0){ UsbDevice d=list.values().iterator().next(); tvUsb.setText("USB: "+d.getDeviceName()); } }catch(Exception e){}
            br=new BroadcastReceiver(){ public void onReceive(Context c, Intent i){ if(UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(i.getAction())){ UsbDevice d=i.getParcelableExtra(UsbManager.EXTRA_DEVICE); if(d!=null) tvUsb.setText("USB CONNECTED: "+d.getDeviceName()); }else if(UsbManager.ACTION_USB_DEVICE_DETACHED.equals(i.getAction())) tvUsb.setText("USB DISCONNECTED"); } };
            IntentFilter f=new IntentFilter(); f.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED); f.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED); registerReceiver(br,f);
        }catch(Exception e){ Toast.makeText(this,"ERR:"+e.getMessage(),1).show(); }
    }
    @Override
    protected void onActivityResult(int q,int w,Intent d){ super.onActivityResult(q,w,d); if(q==1001&&w==RESULT_OK&&d!=null){ try{ picked=FilePickerHelper.getPath(this,d.getData()); tvFile.setText("FILE: "+picked); pb.setProgress(10); }catch(Exception e){} } }
    @Override
    protected void onDestroy(){ try{ unregisterReceiver(br); }catch(Exception e){} super.onDestroy(); }
}
