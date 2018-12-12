package com.example.shuoshuo.writeShuoSHuo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shuoshuo.MainActivity;
import com.example.shuoshuo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddShuoShuo extends AppCompatActivity {

    private Button btn;
    private ImageView img;
    private File tempFile;
    public static String PHOTO_FILE_NAME="myPhoto";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shuo_shuo);
        btn= (Button) findViewById(R.id.take_photo);
        img= (ImageView) findViewById(R.id.picture);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraAction();
            }
        });
    }


    private void showCameraAction() {
        Log.d("write","showCameraAction");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (hasSdcard()) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this,
                    this.getApplicationContext().getPackageName() + ".provider",
                    new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
        }
        startActivityForResult(cameraIntent, 110);
    }

    /**
     * 照片裁剪
     * @param uri
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Log.d("write","crop");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, 120);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("write","onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110&&resultCode==RESULT_OK) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                if (tempFile != null) {
                    crop(FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", tempFile));
                } else {
                    Toast.makeText(this,"未获取到拍摄的图片",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this,"未找到存储卡，无法存储照片！",Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 120 && data != null &&resultCode==RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                if (photo != null) {
                    Uri uri = saveBitmap(photo);
                    //发送图片的方法
//                    sendimage(uri.getPath());
                    Glide.with(this).load(uri).into(img);

                } else {

                    Toast.makeText(this,"未获取到拍摄的图片",Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(this,"照片获取失败请稍后再试",Toast.LENGTH_SHORT).show();


            }
        }
    }


    private boolean hasSdcard(){
        Log.d("write","hasSdcard");
        //判断是否有sdCard
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }


    /**
     * 将Bitmap转成file的Uri
     *
     * @param bitmap
     * @return
     */
    private Uri saveBitmap(Bitmap bitmap) {
        Log.d("write","saveBitmap");
        File file = new File(Environment.getExternalStorageDirectory() + "/image");
        if (!file.exists())
            file.mkdirs();
        File imgFile = new File(file.getAbsolutePath() + PHOTO_FILE_NAME);
        if (imgFile.exists())
            imgFile.delete();
        try {
            FileOutputStream outputStream = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
            outputStream.flush();
            outputStream.close();
            Uri uri = Uri.fromFile(imgFile);
            return uri;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
