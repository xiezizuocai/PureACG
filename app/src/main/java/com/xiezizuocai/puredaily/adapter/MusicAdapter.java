package com.xiezizuocai.puredaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.xiezizuocai.puredaily.R;
import com.xiezizuocai.puredaily.entity.HotSong;

import java.util.ArrayList;


public class MusicAdapter extends BaseAdapter<MusicAdapter.MyViewHolder> {

    private ArrayList<HotSong> mSongs;
    private Context mContext;

    public MusicAdapter(Context context, ArrayList<HotSong> songs) {
        this.mContext = context;
        mSongs = songs;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        HotSong song = mSongs.get(position);

        String artists = song.getArtists();
        String alias = song.getAlias();
        String name = song.getName();

        holder.artists.setText(position + 1 + ". " + artists);
        holder.alias.setText(alias);
        holder.name.setText(name);

        // 开启动画
        setAnimation(holder.itemView);
    }

    public void syncData(ArrayList<HotSong> songs) {
        this.mSongs = songs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_music, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView artists;
        public TextView alias;
        public TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            artists = (TextView) itemView.findViewById(R.id.song_artists);
            alias = (TextView) itemView.findViewById(R.id.song_alias);
            name = (TextView) itemView.findViewById(R.id.song_name);
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
