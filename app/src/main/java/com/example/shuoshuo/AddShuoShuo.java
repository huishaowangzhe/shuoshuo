package com.example.shuoshuo;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shuoshuo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.Manifest;

public class AddShuoShuo extends AppCompatActivity {
    private Button takephoto;
    private ImageView picture;
    public static  final int TAKE_PHOTO=1;//两个常量用于onActivityResult回调方法
    public static final int CHOOSE_PHOTO=2;
    private Uri ImageUri;//创建图片的uri对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shuo_shuo);
        picture= (ImageView) findViewById(R.id.picture);
        takephoto= (Button) findViewById(R.id.take_photo);
        Log.d("AddShuoShuo",""+(takephoto==null));
        Log.d("AddShuoShuo",""+(picture==null));
        takephoto.setOnClickListener(new View.OnClickListener() {//点击调用相机
            @Override
            public void onClick(View v) {
                Log.d("AddShuoShuo","execuate");
                //创建file对象,用于存储拍照后的照片
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {//                      关联缓存目录
                    if (outputImage.exists()) {//看看文件是否已经存在，，，如果存在就删除
                        outputImage.delete();
                    }
                    outputImage.createNewFile();//把旧的删除了，，创建一个新的
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //下面是根据文件对象生成一个uri对象
                if (Build.VERSION.SDK_INT >= 24) {//判断安卓版本、sdk24以上安全性提高   封装uri
                    ImageUri = FileProvider.getUriForFile(AddShuoShuo.this, "com.example.shuoshuo.fileprovider", outputImage);
                }//            获取文件uri的方法，，，File.getUriForFile();，，传入context，authority，file对象
                else {
                    //sdk24以下uri尚未封装，，，直接用Uri.fromFile(file对象)获取 uri
                    ImageUri = Uri.fromFile(outputImage);
                }

                //启动相机                     启动相机的action，android.media.action.IMAGE_CAPTURE
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri);//EXTRA_OUTPUT是键，ImageUri是值，传给回调方法
                startActivityForResult(intent, TAKE_PHOTO);//startActivityResult与startActivity的区别就是有回调方法
            }
        });
        //这个调用相册图片是个重头戏
        findViewById(R.id.chose_from_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//由于WRITE_EXTERNAL_STORAGE是危险权限，，要获得需要用户同意//先判断用户是否已经给了权限，如果没有，，，就请求一下
                if (ContextCompat.checkSelfPermission(AddShuoShuo.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager/*用于检索与应用程序相关的各种信息的类*/.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions/*调用回调方法*/(AddShuoShuo.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {                                                                    //这个String里面放的都是各种权限      后面的1是id，辨识符
                    openAlbum();
                }
            }
        });
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
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 200);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
//        intent.putExtra("return_data",true);
//        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, 120);
    }

    private void openAlbum(){//选择图片，解析函数接力intent
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");//means:选择图片
        startActivityForResult(intent, CHOOSE_PHOTO);//此处会把intent传递给onActivityResult中的data
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode){//requestCode 就是Activity.compat()中的最后一个参数
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();//如果用户同意了，就打开album
                }
                else{
                    Toast.makeText(AddShuoShuo.this,"you denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    @Override//对于takephoto就是设置图片
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){//startActivityResult的第二个参数
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try{//解析uri生成BitMap对象，，，，，BItMap对象可以直接设置图片
                        crop(ImageUri);
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(ImageUri));
                        picture.setImageBitmap(bitmap);
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }

                }
                break;


            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    //判断手机型号
                    if(Build.VERSION.SDK_INT>=19){
                        //4.4以及以上系统使用这个方法处理图片，，，，
                        handleImageOnKitKat(data);//把这个intent传入handleImageOnKitKat中进行解析得到图片的uri对象
                    }
                    else{
                        //4.4以下系统使用这个方法
                        handleImageBeforeKitKat(data);
                    }
                }
            default:break;

        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){//document类型就是封装之后的uri
            //如果是document类型的uri,,,则通过document id进行处理
            String docuId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docuId.split(":")[1];//解析出数字格式的id
                String selection =MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docuId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，，，，使用普通方式处理
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){

            imagePath=uri.getPath();//如果是文件类型的uri，，，直接就能够获得path，调用Uri.getPath()
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){//前提是安卓4.4以下，，，才能获得真正的uri 否则获得的就是封装后的uri 需要进一步转化之后才能得到绝对路径
        Uri uri=data.getData();//Intent.getData(),返回一个uri对象
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection){
        String path=null;
        //通过uri和selection来获得真实的路径
        Cursor cursor=getContentResolver().query(uri, null, selection, null, null);
        if(cursor!=null){
            if(cursor.moveToNext()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath){//根据路径生成Bitmap对象并且设置为图片显示
        if(imagePath != null) {
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);//有了图片真是路径调用BitmaoFactory.decodeFile(String)即可获得Bitmap对象
//            Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(ImageUri));如果是uri就要调用getContentResolver.openInputStream(Uri)先获得一个stream对象，，再使用decodeStream解析来获得Bitmap对象
            picture.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }
}
