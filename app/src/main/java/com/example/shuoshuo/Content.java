package com.example.shuoshuo;


import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by 29208 on 2018/10/1.
 */
public class Content extends BmobObject {
    private String  imageUrl=null;
//    private List<String>imageUrlList=new ArrayList<>();
    private String title=" ";
    private int countZan=0;
    private List<Comment> commitList=new ArrayList<Comment>();

    private String time;
    private String name;

    Comment comment;
    private String myName;

    private String personIcon;
    public  Content(){}
    public Content(String myname,String personIcon,String  imageUrl,String title,int countZan,List<Comment> commitlist,String time,String name){
        this.imageUrl=imageUrl;
        this.title=title;
        this.countZan=countZan;
        this.commitList=commitlist;
        this.name=name;
        this.time=time;
        this.personIcon=personIcon;
        this.myName=myname;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getPersonIcon() {
        return personIcon;
    }

    public void setPersonIcon(String personIcon) {
        this.personIcon = personIcon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
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

    public List<Comment> getCommitList() {
        return commitList;
    }

    public void setCommitList(List<Comment> commitList) {
        this.commitList = commitList;
    }
    public void appendCommitList(Comment c){
        commitList.add(c);
    }
}
