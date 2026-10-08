package com.otgflasher.pro.root;
import android.content.Context;
import java.io.*;
public class BinaryInstaller {
    public static String install(Context ctx, String assetName){
        try{
            File out=new File(ctx.getFilesDir(), assetName);
            InputStream in=ctx.getAssets().open("bin/"+assetName);
            FileOutputStream fos=new FileOutputStream(out);
            byte[] buf=new byte[8192];
            int len;
            while((len=in.read(buf))!=-1) fos.write(buf,0,len);
            fos.close(); in.close();
            RootUtil.execRoot("chmod 755 "+out.getAbsolutePath());
            RootUtil.execRoot("chmod +x "+out.getAbsolutePath());
            return out.getAbsolutePath();
        }catch(Exception e){ return ""; }
    }
}
