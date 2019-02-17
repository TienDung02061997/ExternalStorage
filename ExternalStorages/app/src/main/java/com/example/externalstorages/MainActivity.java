package com.example.externalstorages;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnsavedata,btnreaddata;
    TextView tvdata;
    private final String filename="nguyentiendung";
    private final String connet="coder";
    private final String TAG=getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions();

        setweigth();
        setClick();
    }
    public  void setweigth(){
        btnreaddata= (Button) findViewById(R.id.btn_read_data);
        btnsavedata= (Button) findViewById(R.id.btn_save_data);
        tvdata= (TextView) findViewById(R.id.tv_data);
    }
    public void setClick(){
        btnsavedata.setOnClickListener(this);
        btnreaddata.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_save_data:
                saveData();
                //   saveData2();
                break;
            case R.id.btn_read_data:
                readdata();
                break;
            default:
                break;

        }
    }
    public void saveData(){
        if(isExternalStorageReadable()) {
            FileOutputStream fileOutputStream = null;
            File file;
            try {
                file = new File(Environment.getExternalStorageDirectory(), filename);
                //hien thi dg dan
                Log.d(TAG, "'saveData : " + Environment.getExternalStorageDirectory().getAbsolutePath());
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(connet.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(this, "can't save file ", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveData2(){
        if(isExternalStorageReadable()) {
            FileOutputStream fileOutputStream = null;
            File file;
            try {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
                //hien thi dg dan
                Log.d(TAG, "'saveData : " + Environment.getExternalStorageDirectory().getAbsolutePath());
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(connet.getBytes());
                fileOutputStream.close();
                Toast.makeText(this, "thanh cong", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(this, "can't save file ", Toast.LENGTH_SHORT).show();
        }
    }

    public void readdata(){
        BufferedReader bf=null;
        File file =null;


        try {
            file =new File(Environment.getExternalStorageDirectory(),filename);
            bf= new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line="";
            StringBuilder builder =new StringBuilder();
            while ((line =bf.readLine()) !=null){
                builder.append(line);

            }
            tvdata.setText(builder.toString());
            Log.d(TAG,builder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }












    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }



    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
}
