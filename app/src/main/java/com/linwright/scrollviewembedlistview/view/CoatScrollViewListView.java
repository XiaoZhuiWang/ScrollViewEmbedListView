/**
 *  @FileName: CoatScrollViewListView.java
 *  @Package: com.example.scrollviewandlistview
 *
 * @Copyright Information: 青年微视科技（深圳）有限公司版权所有
 *  @Description: TODO
 *  @author: Lin Wright
 *  @date: 2015年11月12日
 *  @version: V1.0
 * @modify Description:
 * @modify author:
 * @modify date:
 * @Description:
 */
/**
 *@Description:
 *
 */
package com.linwright.scrollviewembedlistview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 内部拦截法：解决ListView嵌入到ScrollView的滑动冲突
 *
 * @author: Lin Wright
 * @Description: TODO
 */
public class CoatScrollViewListView extends ListView {

    private static final String TAG = null;
    private int mLastYIntercept;
    private boolean isListViewTop = true;
    private ScrollView mScrollView;

    private int scrollViewChildHeight;
    private int scrollViewHeight;

    public CoatScrollViewListView(Context context, AttributeSet attrs,
                                  int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public CoatScrollViewListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CoatScrollViewListView(Context context) {
        this(context, null);

    }

/*	
 * 采用内部拦截法时，除了重写子View的dispatchTouchEvent()方法，还需要重写父View的
 * onInterceptTouchEvent()方法，但由于ScrollView的特殊性，在能滑动时，它默认拦截
 * ACTION_MOVE，所以不需要重写ScrollView的onInterceptTouchEvent()。
 * 父View的onInterceptTouchEvent()方法按照如下格式重写：
 * 
 * 
 * @Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		int action = ev.getAction();
		if(action == MotionEvent.ACTION_DOWN){
			return false;
		}else{
			return true;
		}
	}*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (mScrollView == null) {
            return false;
        }
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mScrollView.requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:

                int deltaY = y - mLastYIntercept;
                if (deltaY < 0) {// 上滑

                    // ScrollView没有滑动到底部:拦截
                    if (mScrollView.getScrollY() + scrollViewHeight < scrollViewChildHeight) {

                        mScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;
                    }
                } else if (deltaY > 0) {// 下滑

                    // ListView到达顶部:拦截
                    if (isListViewTop) {

                        mScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:

                break;

        }

        mLastYIntercept = y;
        return super.dispatchTouchEvent(ev);
    }

    public void setCoatScrollView(ScrollView scrollView) {
        if (scrollView != null) {

            mScrollView = scrollView;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollViewHeight = mScrollView.getHeight();
                    scrollViewChildHeight = mScrollView.getChildAt(0).getMeasuredHeight();
                }
            });


        } else {
            throw new NullPointerException("ScrollView is NULL!");
        }

        this.setOnScrollListener(new OnScrollListener() {

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
}
