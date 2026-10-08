
package com.otgflasher.pro;
import android.app.Activity;import android.os.Bundle;import android.content.Intent;import android.widget.*;import android.hardware.usb.*;
public class MainActivity extends Activity {
    TextView log; UsbManager um;
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        log=findViewById(R.id.logView); um=(UsbManager)getSystemService(USB_SERVICE);
        findViewById(R.id.btnUsb).setOnClickListener(v->connect());
        findViewById(R.id.btnFileMgr).setOnClickListener(v->startActivity(new Intent(this, FileManagerActivity.class)));
        findViewById(R.id.cardFastboot).setOnClickListener(v->startActivity(new Intent(this, FastbootActivity.class)));
        findViewById(R.id.cardMtk).setOnClickListener(v->startActivity(new Intent(this, MtkActivity.class)));
        findViewById(R.id.cardEdl).setOnClickListener(v->startActivity(new Intent(this, EdlActivity.class)));
        findViewById(R.id.cardOdin).setOnClickListener(v->startActivity(new Intent(this, OdinActivity.class)));
        findViewById(R.id.cardSpd).setOnClickListener(v->startActivity(new Intent(this, SpdActivity.class)));
        findViewById(R.id.cardPayload).setOnClickListener(v->startActivity(new Intent(this, PayloadActivity.class)));
        findViewById(R.id.cardSuper).setOnClickListener(v->startActivity(new Intent(this, SuperActivity.class)));
        findViewById(R.id.cardOfp).setOnClickListener(v->startActivity(new Intent(this, OfpActivity.class)));
        findViewById(R.id.cardFrp).setOnClickListener(v->startActivity(new Intent(this, FrpActivity.class)));
        findViewById(R.id.cardMi).setOnClickListener(v->startActivity(new Intent(this, MiActivity.class)));
        findViewById(R.id.cardQcn).setOnClickListener(v->startActivity(new Intent(this, QcnActivity.class)));
        findViewById(R.id.cardKg).setOnClickListener(v->startActivity(new Intent(this, KgActivity.class)));
        findViewById(R.id.cardUnlock).setOnClickListener(v->startActivity(new Intent(this, UnlockActivity.class)));
        findViewById(R.id.cardPart).setOnClickListener(v->startActivity(new Intent(this, PartActivity.class)));
    }
    void connect(){ java.util.HashMap<String,UsbDevice> ds=um.getDeviceList(); if(ds.isEmpty()){ log.setText("Ready.\nNo device connected.\nVID 0E8D=MTK\n05C6:9008=EDL\n04E8=SAMSUNG\n18D1=FASTBOOT"); return; } for(android.hardware.usb.UsbDevice d:ds.values()){ log.append("\nUSB: VID 0x"+Integer.toHexString(d.getVendorId())); } }
}
