package com.xiezizuocai.pureacg.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected BaseAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder viewHolder, int position);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder, holder.getLayoutPosition());
                }
            });
        }
    }

    /**
     * 注意这里使用了ViewHolder的getLayoutPosition方法，此方法返回的pos值与onBindViewHolder方法传入的position值有可能不同。
     * 根据SDK中的解释，在Recyclerview 进行添加、移除item等操作时，position位置可能会变化，而所有的adapter的刷新并不总是及时的，
     * 只有这个方法返回的才是当前item经过一些变换后所处的真正位置。
     */

}
