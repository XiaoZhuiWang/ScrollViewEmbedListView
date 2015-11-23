package com.linwright.scrollviewembedlistview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 外部拦截法，解决ScrollView嵌套ListView的滑动冲突
 *
 * @author: Lin Wright
 * @Description: TODO
 */
public class EmbedListViewScrollView extends ScrollView {

    private static final String TAG = "MyScrollView";
    private boolean isScrollViewBottom = false;
    private boolean isListViewTop = true;

    private int mLastYIntercept;

    public EmbedListViewScrollView(Context context) {
        this(context, null);

    }

    public EmbedListViewScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public EmbedListViewScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setInnerListView(ListView listView) throws NullPointerException {
        if (listView == null) {

            throw new NullPointerException("ListView is NULL!");
        }

        listView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (view.getFirstVisiblePosition() == 0) {
                    // ListView 滑动到了顶部
                    isListViewTop = true;

                } else {
                    // ListView 未滑动到顶部
                    isListViewTop = false;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        if ((t + getHeight()) >= computeVerticalScrollRange()) {
            // 滑动到scrollView的底部
            isScrollViewBottom = true;

        } else {
            // 没有滑动到scrollView的底部
            isScrollViewBottom = false;
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        boolean intercepted = false;
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                intercepted = super.onInterceptTouchEvent(event);
                break;

            case MotionEvent.ACTION_MOVE:

                int deltaY = y - mLastYIntercept;

                if (deltaY < 0) {//上滑
                    if (!isScrollViewBottom) {// ScrollView没有滑动到底部:拦截（ScrollView在可以滑动的情况下默认拦截ACTION_MOVE事件）

                        intercepted = super.onInterceptTouchEvent(event);
                    } else {//ScrollView滑动到底部:不拦截
                        intercepted = false;
                    }
                } else if (deltaY > 0) {//下滑
                    if (isListViewTop) { // ListView到达顶部:拦截

                        intercepted = super.onInterceptTouchEvent(event);
                    } else {// ListView没有到达顶部:不拦截

                        intercepted = false;
                    }
                }

                break;

            case MotionEvent.ACTION_UP:

                intercepted = super.onInterceptTouchEvent(event);
                break;

        }

        mLastYIntercept = y;
        return intercepted;

    }
}
