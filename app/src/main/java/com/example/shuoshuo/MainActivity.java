package com.example.shuoshuo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
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

    }



    private void addData(){
        Bmob.initialize(this, "c3b3364ff35ed7bca7d202f18ec01f20");
        Content content=new Content();
        content.save();
    }
    private void requestData(){
        BmobQuery<Content>query=new BmobQuery<Content>();
        query.order("createdAt");
        query.setLimit(5);
        query.findObjects(new FindListener<Content>() {
            @Override
            public void done(List<Content> list, BmobException e) {
                contentList.addAll(list);
                init(contentList);
            }
        });
    }

    private void init(List<Content>list){

    }



}
