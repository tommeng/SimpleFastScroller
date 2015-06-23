package com.tommeng.simplefastscrollersample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleNumberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    public SimpleNumberAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView itemView = new TextView(context);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        itemView.setTextColor(context.getResources().getColor(android.R.color.primary_text_light));
        return new NumberViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NumberViewHolder numberViewHolder = (NumberViewHolder) holder;
        numberViewHolder.numberTextView.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    private class NumberViewHolder extends RecyclerView.ViewHolder {
        TextView numberTextView;

        public NumberViewHolder(View itemView) {
            super(itemView);
            numberTextView = (TextView) itemView;
        }
    }
}
