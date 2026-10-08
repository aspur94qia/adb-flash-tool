package com.otgflasher.pro;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
public class MainActivity extends Activity{
@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_main);
findViewById(R.id.cFastboot).setOnClickListener(v->startActivity(new Intent(this, FastbootActivity.class)));
findViewById(R.id.cMtk).setOnClickListener(v->startActivity(new Intent(this, MtkActivity.class)));
findViewById(R.id.cEdl).setOnClickListener(v->startActivity(new Intent(this, EdlActivity.class)));
findViewById(R.id.cOdin).setOnClickListener(v->startActivity(new Intent(this, OdinActivity.class)));
findViewById(R.id.cSpd).setOnClickListener(v->startActivity(new Intent(this, SpdActivity.class)));
findViewById(R.id.cPayload).setOnClickListener(v->startActivity(new Intent(this, PayloadActivity.class)));
findViewById(R.id.cSuper).setOnClickListener(v->startActivity(new Intent(this, SuperActivity.class)));
findViewById(R.id.cOfp).setOnClickListener(v->startActivity(new Intent(this, OfpActivity.class)));
findViewById(R.id.cFrp).setOnClickListener(v->startActivity(new Intent(this, FrpActivity.class)));
findViewById(R.id.cMi).setOnClickListener(v->startActivity(new Intent(this, MiActivity.class)));
findViewById(R.id.cQcn).setOnClickListener(v->startActivity(new Intent(this, QcnActivity.class)));
findViewById(R.id.cKg).setOnClickListener(v->startActivity(new Intent(this, KgActivity.class)));
findViewById(R.id.cUnlock).setOnClickListener(v->startActivity(new Intent(this, UnlockActivity.class)));
findViewById(R.id.cPart).setOnClickListener(v->startActivity(new Intent(this, PartActivity.class)));
}}