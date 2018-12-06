package com.example.shuoshuo;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by 29208 on 2018/10/1.
 */
public class Content extends BmobObject {
//    private String  imageUrl=null;
    private List<String>imageUrlList=new ArrayList<>();
    private String title=" ";
    private int countZan=0;
    private List<String> commitList=new ArrayList<>();

    public  Content(){}
    public Content(List<String>imageUrlList,String title,int countZan,List<String> commitlist){
        this.imageUrlList=imageUrlList;
        this.title=title;
        this.countZan=countZan;
        this.commitList=commitlist;

    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCountZan() {
        return countZan;
    }

    public void setCountZan(int countZan) {
        this.countZan = countZan;
    }

    public List<String> getCommitList() {
        return commitList;
    }

    public void setCommitList(List<String> commitList) {
        this.commitList = commitList;
    }
}
