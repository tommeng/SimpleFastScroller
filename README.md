# SimpleFastScroller

A fast scroller for RecyclerView.

##Set up
###Set up example (xml):
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:sfs="http://schemas.android.com/apk/res-auto"
                android:layout_width="match_parent"
                android:layout_height="match_parent">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

    <com.tommeng.simplefastscroller.SimpleFastScroller
        android:id="@+id/sfs"
        android:layout_width="8dp"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true"/>

</RelativeLayout>
```

###Set up example (java):
```
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
```

##SimpleFastScrollCalculator
A couple operations may need the RecyclerView's LayoutManager to compute. Since you may use a custom layout manager, you may need to create a custom SimpleFastScrollCalculator to calculate scroll percentage and how many items are visible along the scroll axis.

For LinearLayoutManager, LlmSimpleFastScrollCalculator has been provided.

```
/**
         * How far down as the recycler been scrolled?
         *
         * @param recyclerView
         * @return 0.0 -> 1.0
         */
        float getScrollPercentage(RecyclerView recyclerView);
```

```
/**
         * How many items are visible along the scroll line?
         *
         * @param recyclerView
         * @return
         */
        int getItemsVisible(RecyclerView recyclerView);
```
