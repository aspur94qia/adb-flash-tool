package com.otgflasher.pro;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.content.Context;
public class FilePickerHelper {
    public static void open(Activity act, int code){
        Intent i=new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("*/*");
        i.addCategory(Intent.CATEGORY_OPENABLE);
        try{ act.startActivityForResult(Intent.createChooser(i,"PILIH FILE FIRMWARE"),code); }catch(Exception e){}
    }
    public static String getPath(Context ctx, Uri uri){
        try{
            if(uri==null) return "";
            String path=uri.getPath();
            if(path!=null &&!path.contains(":")) return path;
            String[] proj={MediaStore.Images.Media.DATA};
            Cursor c=ctx.getContentResolver().query(uri,proj,null,null,null);
            if(c!=null){ c.moveToFirst(); int idx=c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); String res=c.getString(idx); c.close(); if(res!=null) return res; }
            return uri.getPath();
        }catch(Exception e){ return uri!=null?uri.getPath():""; }
    }
}
