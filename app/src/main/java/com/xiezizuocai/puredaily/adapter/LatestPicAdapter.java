package com.xiezizuocai.puredaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiezizuocai.puredaily.R;
import com.xiezizuocai.puredaily.entity.Wallpaper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaohailong on 2016/5/17.
 */
public class LatestPicAdapter extends BaseAdapter<LatestPicAdapter.MyViewHolder>  {

    private List<Integer> mHeights;

    private ArrayList<Wallpaper> mLatests;
    private Context mContext;

    public LatestPicAdapter(Context context, ArrayList<Wallpaper> latests) {
        this.mContext = context;
        mLatests = latests;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Wallpaper latestGanHuo = mLatests.get(position);
        String imageUrl = latestGanHuo.getUrl();

        // 有图模式
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(mContext)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .into(holder.image);

        } else {
            holder.image.setImageDrawable(null);
        }
        holder.image.setVisibility(View.VISIBLE);

        // 开启动画
        setAnimation(holder.itemView);

    }

    public void syncData(ArrayList<Wallpaper> latests) {
        this.mLatests = latests;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_latest_pic, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mLatests.size();
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
