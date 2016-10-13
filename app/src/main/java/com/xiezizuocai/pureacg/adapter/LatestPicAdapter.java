package com.xiezizuocai.pureacg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiezizuocai.pureacg.R;
import com.xiezizuocai.pureacg.entity.Wallpaper;

import java.util.ArrayList;


public class LatestPicAdapter extends BaseAdapter<LatestPicAdapter.MyViewHolder>  {

    private ArrayList<Wallpaper> mWallpapers;
    private Context mContext;

    public LatestPicAdapter(Context context, ArrayList<Wallpaper> wallpapers) {
        this.mContext = context;
        mWallpapers = wallpapers;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Wallpaper wallpaper = mWallpapers.get(position);
        String imageUrl = wallpaper.getUrl();

        Glide.with(mContext)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(holder.image);

        holder.image.setVisibility(View.VISIBLE);

        // 开启动画
        setAnimation(holder.itemView);

    }

    public void syncData(ArrayList<Wallpaper> wallpapers) {
        this.mWallpapers = wallpapers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_latest_pic, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mWallpapers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.latest_pic_image);
        }

    }

    private void setAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(400);
        view.startAnimation(anim);
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        holder.itemView.clearAnimation();
    }

}
