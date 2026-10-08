package com.otgflasher.pro;
import android.app.Activity; import android.content.*; import android.hardware.usb.*; import android.os.Bundle; import android.view.*; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import java.util.HashMap;
public class MainActivity extends AppCompatActivity {
    TextView tvUsb,tvFile,tvProg; ProgressBar pb; EditText edt; String picked=""; UsbManager um; BroadcastReceiver br;
    @Override protected void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
        ViewGroup vg=(ViewGroup)findViewById(android.R.id.content);
        LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(20,20,20,20);
        tvUsb=new TextView(this); tvUsb.setText("USB: DISCONNECTED"); tvUsb.setBackgroundColor(0xFF4CAF50); tvUsb.setTextColor(-1); tvUsb.setPadding(20,20,20,20);
        pb=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal); pb.setMax(100); tvProg=new TextView(this); tvProg.setText("PROGRESS 0%"); tvFile=new TextView(this); tvFile.setText("BELUM PILIH FILE");
        edt=new EditText(this); edt.setText("system");
        Button btnFile=new Button(this); btnFile.setText("PILIH FILE"); btnFile.setBackgroundColor(0xFFFF9800); btnFile.setOnClickListener(v-> FilePickerHelper.open(this,1001));
        root.addView(tvUsb); root.addView(pb); root.addView(tvProg); root.addView(edt); root.addView(tvFile); root.addView(btnFile);
        Button bPay=new Button(this); bPay.setText("PAYLOAD EXTRACTOR"); bPay.setBackgroundColor(0xFF2196F3); bPay.setTextColor(-1); bPay.setOnClickListener(v-> startActivity(new Intent(this,PayloadActivity.class))); root.addView(bPay);
        Button bMtk=new Button(this); bMtk.setText("MTK SP FLASH"); bMtk.setBackgroundColor(0xFF2196F3); bMtk.setTextColor(-1); bMtk.setOnClickListener(v-> startActivity(new Intent(this,MtkActivity.class))); root.addView(bMtk);
        Button bOfp=new Button(this); bOfp.setText("OFP DECRYPTOR"); bOfp.setBackgroundColor(0xFF2196F3); bOfp.setTextColor(-1); bOfp.setOnClickListener(v-> startActivity(new Intent(this,OfpActivity.class))); root.addView(bOfp);
        Button bEdl=new Button(this); bEdl.setText("EDL QUALCOMM"); bEdl.setBackgroundColor(0xFF2196F3); bEdl.setTextColor(-1); bEdl.setOnClickListener(v-> startActivity(new Intent(this,EdlActivity.class))); root.addView(bEdl);
        Button bFb=new Button(this); bFb.setText("FASTBOOT"); bFb.setBackgroundColor(0xFF2196F3); bFb.setTextColor(-1); bFb.setOnClickListener(v-> startActivity(new Intent(this,FastbootActivity.class))); root.addView(bFb);
        vg.addView(root);
        try{ um=(UsbManager)getSystemService(Context.USB_SERVICE); HashMap<String,UsbDevice> list=um.getDeviceList(); if(list.size()>0){ UsbDevice d=list.values().iterator().next(); tvUsb.setText("USB: "+d.getDeviceName()); } }catch(Exception e){}
        br=new BroadcastReceiver(){ public void onReceive(Context c, Intent i){ if(UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(i.getAction())){ UsbDevice d=i.getParcelableExtra(UsbManager.EXTRA_DEVICE); if(d!=null) tvUsb.setText("USB CONNECTED: "+d.getDeviceName()); }else if(UsbManager.ACTION_USB_DEVICE_DETACHED.equals(i.getAction())) tvUsb.setText("USB DISCONNECTED"); } };
        IntentFilter f=new IntentFilter(); f.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED); f.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED); registerReceiver(br,f);
    }
    protected void onActivityResult(int q,int w,Intent d){ super.onActivityResult(q,w,d); if(q==1001&&w==RESULT_OK&&d!=null){ try{ picked=FilePickerHelper.getPath(this,d.getData()); tvFile.setText("FILE: "+picked); pb.setProgress(10); }catch(Exception e){} } }
    @Override protected void onDestroy(){ try{ unregisterReceiver(br); }catch(Exception e){} super.onDestroy(); }
}
