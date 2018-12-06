package com.example.shuoshuo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 29208 on 2018/8/10.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {


    private List<Content> mContentList;
    private Context context;
    public ContentAdapter(List<Content> mContentList, Context context) {
    //把所有的数据源传进来，并放在list当中
        this.mContentList = mContentList;
        this.context=context;
}

    @Override//用于创建ViewHolder实例
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shsuoshuo_recyclerview_item,parent,false);
//    private LinearLayout ll=view.findViewById(R.id.itemview);
//
        final ViewHolder holder=new ViewHolder(view);//定义一个ViewHolder
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
//                Intent intent =new Intent (context,ContentActivity.class);
//                context.startActivity(intent);
//                Toast.makeText(context,"djks",Toast.LENGTH_SHORT).show();
//               ContentActivity ca=new ContentActivity();
//                ca.actionStart(context,mContentList.get(position).getImageId(),
//                        mContentList.get(position).getContent());
            }
        });

        return holder;
    }

    @Override//用于对RecyclerVIew子项进行赋值，自动调用，自动传入position也就是index
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Content content =mContentList.get(position);
//        holder.contentImage.setImageResource(content.getImageId());
        Glide.with(context).load(R.drawable.item).into(holder.contentImage);
//        holder.contentName.setText(content.getContent());
    }

    @Override
    public int getItemCount() {
//        return mContentList.size();
        return 10;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView contentImage;
        TextView contentName;
        View contentView;
        public ViewHolder(View itemView) {
            super(itemView);
            contentView=itemView;
            contentImage=itemView.findViewById(R.id.item_image);
            contentName=itemView.findViewById(R.id.item_content);
        }
    }


}
