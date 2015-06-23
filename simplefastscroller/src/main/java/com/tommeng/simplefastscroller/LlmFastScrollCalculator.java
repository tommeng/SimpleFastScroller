package com.tommeng.simplefastscroller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * A fast scroll calculator for {@link android.support.v7.widget.LinearLayoutManager}
 */
public class LlmFastScrollCalculator implements SimpleFastScroller.FastScrollCalculator {
    @Override
    public float getScrollPercentage(RecyclerView recyclerView) {
        if (recyclerView == null) return 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            throw new RuntimeException("LlScrollPercentCalculator needs a LinearLayoutManager.");
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;

        float lastFullyVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
        float visibleItems = linearLayoutManager.findLastCompletelyVisibleItemPosition() - linearLayoutManager.findFirstCompletelyVisibleItemPosition();

        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) return 0;
        float itemCount = adapter.getItemCount();
        return lastFullyVisiblePosition / (itemCount - visibleItems);
    }

    @Override
    public int getItemsVisible(RecyclerView recyclerView) {
        if (recyclerView == null) return 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            throw new RuntimeException("LlScrollPercentCalculator needs a LinearLayoutManager.");
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        return linearLayoutManager.findLastCompletelyVisibleItemPosition() - linearLayoutManager.findFirstCompletelyVisibleItemPosition();
    }
}
