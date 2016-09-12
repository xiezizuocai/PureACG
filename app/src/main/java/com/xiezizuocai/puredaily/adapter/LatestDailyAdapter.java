package com.xiezizuocai.puredaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiezizuocai.puredaily.R;
import com.xiezizuocai.puredaily.entity.LatestInfo;


import java.util.ArrayList;

/**
 * 最新日报适配器
 * <p/>
 * Created by hanjie on 2016/5/31.
 */
public class LatestDailyAdapter extends BaseAdapter<LatestDailyAdapter.MyViewHolder> {

    private ArrayList<LatestInfo> mLatests;
    private Context mContext;

    public LatestDailyAdapter(Context context, ArrayList<LatestInfo> latests) {
        this.mContext = context;
        mLatests = latests;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        LatestInfo latest = mLatests.get(position);
        String imageUrl = latest.getCover();
        String title = latest.getTitle();
        String time = latest.getTime();

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

        holder.title.setText(title);

        holder.time.setText(time);

        // 开启动画
        setAnimation(holder.itemView);
    }

    public void syncData(ArrayList<LatestInfo> latests) {
        this.mLatests = latests;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_latest_daily, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mLatests.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.latest_daily_image);
            title = (TextView) itemView.findViewById(R.id.latest_daily_title);
            time = (TextView) itemView.findViewById(R.id.latest_daily_time);
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
