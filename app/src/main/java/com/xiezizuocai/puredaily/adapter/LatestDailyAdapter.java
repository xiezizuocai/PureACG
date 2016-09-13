package com.xiezizuocai.puredaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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


public class LatestDailyAdapter extends BaseAdapter<LatestDailyAdapter.MyViewHolder> {

    private ArrayList<LatestInfo> mLatestInfos;
    private Context mContext;

    public LatestDailyAdapter(Context context, ArrayList<LatestInfo> latestInfos) {
        this.mContext = context;
        mLatestInfos = latestInfos;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        LatestInfo latestInfo = mLatestInfos.get(position);
        String imageUrl = latestInfo.getCover();
        String title = latestInfo.getTitle();
        String time = latestInfo.getTime();

        Glide.with(mContext)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(holder.image);

        holder.image.setVisibility(View.VISIBLE);
        holder.title.setText(title);
        holder.time.setText(time);

        // 开启动画
        setAnimation(holder.itemView);
    }

    public void syncData(ArrayList<LatestInfo> latestInfos) {
        this.mLatestInfos = latestInfos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_latest_info, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mLatestInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.latest_info_image);
            title = (TextView) itemView.findViewById(R.id.latest_info_title);
            time = (TextView) itemView.findViewById(R.id.latest_info_time);
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
