package ru.mrwinwon.poltindex.util;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class RecyclerViewScrollAnimator implements RecyclerViewItemOnScrollAnimator {
    private final LinearLayoutManager mLayoutManager;
    private final RecyclerView mRecyclerView;
    int mLastAnimatedPosition = RecyclerView.NO_POSITION;

    public RecyclerViewScrollAnimator(RecyclerView recyclerView) {
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        mRecyclerView = recyclerView;
    }

    @Override
    public void onAnimateViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        onPrepareToAnimateViewHolder(viewHolder);
        if (shouldAnimateOrPrepare(position)) {
            ViewCompat.animate(viewHolder.itemView)
                    .alpha(0.5f)
                    .setDuration(1000).start();
        }
        mLastAnimatedPosition = position;
    }

    private boolean shouldAnimateOrPrepare(int position) {
        return mLastAnimatedPosition == RecyclerView.NO_POSITION || mLastAnimatedPosition < position;
    }

    @Override
    public void onPrepareToAnimateViewHolder(RecyclerView.ViewHolder viewHolder) {
    }

    //clear animation if needed
    private void cancelAnimationAt(int position) {
        RecyclerView.ViewHolder v = mRecyclerView.findViewHolderForPosition(position);
        if (!isVisible(position) || v != null) {
            if (v != null) {
                Log.i("cancelAnimationAt", " canceling " + position);
                //cancelAnimation(v.itemView);
            }
        }

    }

    private void cancelAnimation(View v) {
        if (v != null) {
            v.clearAnimation();
            v.setAnimation(null);
            ViewCompat.animate(v).cancel();
        }
    }

    private boolean isVisible(int position) {
        return position >= mLayoutManager.findFirstVisibleItemPosition() && position <= mLayoutManager.findLastVisibleItemPosition();
    }

}
