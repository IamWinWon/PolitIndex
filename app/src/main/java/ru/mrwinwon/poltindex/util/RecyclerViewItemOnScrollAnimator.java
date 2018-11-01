package ru.mrwinwon.poltindex.util;

import android.support.v7.widget.RecyclerView;

public interface RecyclerViewItemOnScrollAnimator {
    void onAnimateViewHolder(RecyclerView.ViewHolder viewHolder, int position);
    void onPrepareToAnimateViewHolder(RecyclerView.ViewHolder viewHolder);
}
