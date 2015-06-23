package com.tommeng.simplefastscrollersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tommeng.simplefastscroller.LlmScrollPercentCalculator;
import com.tommeng.simplefastscroller.SimpleFastScroller;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SimpleFastScroller simpleFastScroller;
    private SimpleNumberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        simpleFastScroller = (SimpleFastScroller) findViewById(R.id.sfs);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new SimpleNumberAdapter(this);
        recyclerView.setAdapter(adapter);

        simpleFastScroller.setRecyclerView(recyclerView);
        simpleFastScroller.setFastScrollCalculator(new LlmScrollPercentCalculator());
    }
}
