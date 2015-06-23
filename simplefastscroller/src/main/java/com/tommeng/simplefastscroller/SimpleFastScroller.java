package com.tommeng.simplefastscroller;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Fast Scroller for RecyclerView
 */
public class SimpleFastScroller extends FrameLayout {
    private View thumb;
    private View track;

    private FastScrollCalculator fastScrollCalculator;

    private int thumbInactiveColor;
    private int thumbActiveColor;
    private RecyclerView recyclerView;
    private boolean isPressed;

    public SimpleFastScroller(Context context) {
        this(context, null);
    }

    public SimpleFastScroller(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleFastScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        track = new View(context);
        LayoutParams trackLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        track.setLayoutParams(trackLayoutParams);

        thumb = new View(context);
        LayoutParams thumbLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        thumb.setLayoutParams(thumbLayoutParams);

        int trackBackgroundRes = 0;
        if (!isInEditMode()) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleFastScroller);
            try {
                thumbInactiveColor = array.getColor(R.styleable.SimpleFastScroller_sfs_thumb_inactive, android.R.color.darker_gray);
                thumbActiveColor = array.getColor(R.styleable.SimpleFastScroller_sfs_thumb_active, android.R.color.holo_blue_bright);
                trackBackgroundRes = array.getColor(R.styleable.SimpleFastScroller_sfs_track, android.R.color.background_light);
            } finally {
                array.recycle();
            }
        }

        thumb.setBackgroundResource(thumbInactiveColor);
        track.setBackgroundResource(trackBackgroundRes);

        removeAllViews();
        addView(track);
        addView(thumb);
    }

    public void setFastScrollCalculator(FastScrollCalculator fastScrollCalculator) {
        this.fastScrollCalculator = fastScrollCalculator;
        requestLayout();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.addOnScrollListener(recyclerScrollListener);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (fastScrollCalculator == null || recyclerView == null || recyclerView.getAdapter() == null)
            return;
        float itemsVisible = fastScrollCalculator.getItemsVisible(recyclerView);
        float totalItems = recyclerView.getAdapter().getItemCount();
        thumb.getLayoutParams().height = (int) (getMeasuredHeight() * (itemsVisible / totalItems));
        thumb.requestLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isPressed = true;
                thumb.setBackgroundColor(getContext().getResources().getColor(thumbActiveColor));
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isPressed = false;
                thumb.setBackgroundColor(getContext().getResources().getColor(thumbInactiveColor));
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                moveHandle(y, true);
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void moveHandle(float y, boolean notifyRecyclerView) {
        float newY;
        if (y <= 0) {
            newY = 0;
        } else if (y >= this.getHeight() - thumb.getHeight()) {
            newY = this.getHeight() - thumb.getHeight();
        } else {
            newY = y;
        }
        thumb.setY(newY);

        if (notifyRecyclerView) {
            float scrollPercentage = newY / (this.getHeight() - thumb.getHeight());
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter == null) return;
            float scrollToPosition = adapter.getItemCount() * scrollPercentage;
            recyclerView.scrollToPosition((int) scrollToPosition);
        }
    }

    private RecyclerView.OnScrollListener recyclerScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!isPressed && fastScrollCalculator != null && recyclerView.getAdapter() != null) {
                float scrollPercentage = fastScrollCalculator.getScrollPercentage(recyclerView);
                float y = (SimpleFastScroller.this.getHeight() - thumb.getHeight()) * scrollPercentage;
                moveHandle(y, false);
            }
        }
    };

    public interface FastScrollCalculator {

        /**
         * How far down as the recycler been scrolled?
         *
         * @param recyclerView
         * @return 0.0 -> 1.0
         */
        float getScrollPercentage(RecyclerView recyclerView);

        /**
         * How many items are visible along the scroll line?
         *
         * @param recyclerView
         * @return
         */
        int getItemsVisible(RecyclerView recyclerView);
    }
}