package com.joshdonlan.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by jdonlan on 7/21/15.
 */
public class AndroidIO {

    public static final String TAG = "AndroidIO";

    public static <T> boolean writeInternal(Context _context, String _filename, T _data){
        return write(_context, _filename, _data);
    }

    //Write to Protected External Storage
    public static <T> boolean writeProtected(Context _context, String _filename, T _data){
        File file = new File(_context.getExternalFilesDir(null), _filename);
        return write(_context, _filename, _data, file);
    }

    //Write to External Public Storage with Environment Path
    public static <T> boolean writeExternal(Context _context, String _publicdir, String _filename, T _data){
        File external = Environment.getExternalStoragePublicDirectory(_publicdir);
        if (!external.exists()) {
            external.mkdir();
        }
        return write(_context, _filename, _data, external);
    }

    //Write to External Storage
    public static <T> boolean writeExternal(Context _context, String _filename, T _data){
        File external = Environment.getExternalStorageDirectory();
        return write(_context, _filename, _data, external);
    }

    private static <T> boolean write(Context _context, String _filename, T _data) {
        return write(_context, _filename, _data, null);
    }

    private static <T> boolean write(Context _context, String _filename, T _data, File _external){
        try  {
            FileOutputStream fos;
            if(_external == null){
                fos = _context.openFileOutput(_filename, Context.MODE_PRIVATE);
            } else {
                fos = new FileOutputStream(_external);
            }
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_data);
            oos.close();
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found.");
        } catch (IOException e) {
            Log.e(TAG, "Error accessing output destination.");
        }
        return false;
    }

    public static <T> T readInternal(Context _context, String _filename){
        return read(_context, _filename, null);
    }

    public static <T> T readExternal(Context _context, String _filename){
        File external = _context.getExternalFilesDir(null);
        return read(_context, _filename, external);
    }

    public static <T> T readExternal(Context _context, String _publicdir, String _filename) {
        File external = Environment.getExternalStoragePublicDirectory(_publicdir);
        if (!external.exists()) return null;
        return read(_context, _filename, external);
    }

    private static <T> T read(Context _context, String _filename, File _external){
        try {
            FileInputStream fis;
            if(_external == null) {
                fis = _context.openFileInput(_filename);
            } else {
                File file = new File(_external, _filename);
                fis = new FileInputStream(file);
            }
            ObjectInputStream ois = new ObjectInputStream(fis);
            T data = (T) ois.readObject();
            ois.close();
            fis.close();

            return data;
        } catch (FileNotFoundException e) {
            Log.e(TAG,"File not found.");
        } catch (IOException e) {
            Log.e(TAG, "Error reading file.");
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Uknown data type in file.");
        }
        return null;
    }
}
