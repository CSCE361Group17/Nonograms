package com.example.bensalitros.nonograms.fileIO;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Ben Salitros on 4/15/2018.
 */

public class MoveAssets {

    private Context context;

    public MoveAssets(Context context) {
        this.context = context;

        moveAssets();
    }

    private void moveAssets() {
        File dir = context.getDir("Puzzles", Context.MODE_PRIVATE); //Creating an internal directry
        //check if not created then create the firectory
        //if (!dir.exists())     {
            Log.i("Creating Dir!", "DOH!");
            dir.mkdir();
            try {
                copyAssetFolder(context.getAssets(), "Puzzles", context.getFilesDir().getCanonicalPath());
                Log.i("PATH:", context.getFilesDir().getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        //}

    }

        private static boolean copyAssetFolder(AssetManager assetManager,
                String fromAssetPath, String toPath) {
            try {
                String[] files = assetManager.list(fromAssetPath);
                new File(toPath).mkdirs();
                boolean res = true;
                for (String file : files)
                    if (file.contains("."))
                        res &= copyAsset(assetManager,
                                fromAssetPath + "/" + file,
                                toPath + "/" + file);
                    else
                        res &= copyAssetFolder(assetManager,
                                fromAssetPath + "/" + file,
                                toPath + "/" + file);
                return res;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        private static boolean copyAsset(AssetManager assetManager,
                String fromAssetPath, String toPath) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(fromAssetPath);
                new File(toPath).createNewFile();
                out = new FileOutputStream(toPath);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
                return true;
            } catch(Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        private static void copyFile(InputStream in, OutputStream out) throws IOException {
            byte[] buffer = new byte[1024];
            int read;
            while((read = in.read(buffer)) != -1){
                out.write(buffer, 0, read);
            }
        }

}
