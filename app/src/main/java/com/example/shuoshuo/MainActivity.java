    package com.example.shuoshuo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<Content> contentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recleView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        requestData();
        ContentAdapter  adapter=new ContentAdapter(contentList,this);
        recyclerView.setAdapter(adapter);
        Bmob.initialize(this, "0aa9c14b5406f384ecc21410d1968d8d");
    }


    private void addData(){
        final Content content=new Content(Arrays.asList("http://bmob-cdn-22722.b0.upaiyun.com/2018/12/04/bd40e3fb4023a0d5808f7888ff8303f5.png", "http://bmob-cdn-22722.b0.upaiyun.com/2018/12/04/7964a62540b3e2c380495d5c3dcc5218.jpg", "http://bmob-cdn-22722.b0.upaiyun.com/2018/12/04/84367c404056877d80a789ac8228a154.jpg"),"标题",4, Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));
        content.save();
    }

    private void requestData(){
        BmobQuery<Content>query=new BmobQuery<Content>();
        query.order("createdAt");
        query.setLimit(1);
        query.findObjects(new FindListener<Content>() {
            @Override
            public void done(List<Content> list, BmobException e) {
                contentList.addAll(list);
                Log.d("MainActivity",contentList.equals(true)+"");
                init(contentList);

            }
        });
    }

    private void init(List<Content>list){

    }



}
