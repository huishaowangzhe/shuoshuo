package com.example.shuoshuo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;



import java.util.List;

/**
 * Created by 29208 on 2018/8/10.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private int mPosition;
    private List<Content> mContentList;
    private Context context;
    public ContentAdapter(List<Content> mContentList, Context context) {
    //把所有的数据源传进来，并放在list当中
        this.mContentList = mContentList;
        this.context=context;
}



    @Override//用于创建ViewHolder实例
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("adapter",mContentList.equals(null)+"=="+mContentList.size());
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shsuoshuo_recyclerview_item,parent,false);
//    private LinearLayout ll=view.findViewById(R.id.itemview);
//
        final ViewHolder holder=new ViewHolder(view);//定义一个ViewHolder
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Position=holder.getAdapterPosition();

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
        Glide.with(context).load( R.drawable.item).into(holder.contentImage);
        Glide.with(context).load(R.drawable.item).into(holder.iconImage);

        Content content=mContentList.get(position);
        holder.time.setText(content.getTime());
        Glide.with(context).load(content.getPersonIcon()).into(holder.iconImage);
        Glide.with(context).load(content.getImageUrl()).into(holder.contentImage);
        holder.contentName.setText(content.getTitle());
        holder.countZan.setText(content.getCountZan()+"人觉得很赞");
        holder.personName.setText(content.getName());


//        holder.contentName.setText(content.getContent());
    }

    @Override
    public int getItemCount() {
        return mContentList.size();

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View contentView;
        ImageView iconImage; TextView personName;
        TextView contentName; ImageView contentImage;
        TextView countZan;Button zan;Button commit;



        TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            contentView=itemView;
            contentImage=itemView.findViewById(R.id.item_image);
            contentName=itemView.findViewById(R.id.item_content);
            iconImage=itemView.findViewById(R.id.icon_image);
            personName=itemView.findViewById(R.id.person_name);
            zan=itemView.findViewById(R.id.zan);
            commit=itemView.findViewById(R.id.commit);
            countZan=itemView.findViewById(R.id.zan_count);
            time=itemView.findViewById(R.id.time);
            //设置点赞的颜色变化
            zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(zan.isSelected()){
                        zan.setSelected(false);
                    }
                    else{
                        zan.setSelected(true);
                    }
                }
            });
        }
    }


}
