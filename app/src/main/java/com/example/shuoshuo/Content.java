package com.example.shuoshuo;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by 29208 on 2018/10/1.
 */
public class Content extends BmobObject {
    private int imageId;
    private int content;


    Content(int imageId,int content){
        this.imageId=imageId;
        this.content=content;

    }
    Content (){}

    public int getImageId() {
        return imageId;
    }

    public int  getContent() {
        return content;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setContent(int  content) {
        this.content = content;
    }
}
