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
import com.xiezizuocai.puredaily.entity.AnimeComic;

import java.util.ArrayList;


public class AnimeComicAdapter extends BaseAdapter<AnimeComicAdapter.MyViewHolder> {

    private ArrayList<AnimeComic> mAnimeComics;
    private Context mContext;

    /**
     * @param context  上下文
     * @param animeComics  动漫集合
     */
    public AnimeComicAdapter(Context context, ArrayList<AnimeComic> animeComics) {
        this.mContext = context;
        mAnimeComics = animeComics;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        AnimeComic animeComic = mAnimeComics.get(position);

        if(animeComic != null) {
            // 加载动漫封面
            Glide.with(mContext)
                    .load(animeComic.getCover())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .into(holder.cover);

            holder.title.setText(animeComic.getTitle());

        }

        // 开启动画
        setAnimation(holder.itemView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new MyViewHolder(inflater.inflate(R.layout.item_anime_comic, parent, false));
    }

    @Override
    public int getItemCount() {
        return mAnimeComics.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);

            cover = (ImageView) itemView.findViewById(R.id.latest_anime_comic_cover);
            title = (TextView) itemView.findViewById(R.id.latest_anime_comic_title);

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
