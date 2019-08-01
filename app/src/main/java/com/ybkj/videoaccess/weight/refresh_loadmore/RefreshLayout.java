package com.ybkj.videoaccess.weight.refresh_loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;

/**
 * 下拉刷新View
 * <p>
 * Created by HH on 2016/9/6.
 */
public class RefreshLayout extends PtrClassicFrameLayout implements PtrHandler, OnLoadMoreListener {
    private OnRefreshListener onRefreshListener;
    private boolean isRefreshEnable = true;  //是否可用下拉刷新
    private int myDurationToCloseHeader = 1000;  //头部回弹时间
    private int myDurationToClose = 200;  //回弹延时
    private int loadDataTime = 300;  //数据加载延时

    public RefreshLayout(Context context) {
        super(context);
        init();
    }

    public RefreshLayout(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    public void setMyDurationToClose(int myDurationToClose) {
        this.myDurationToClose = myDurationToClose;
        setDurationToClose(myDurationToClose);
    }

    public void setMyDurationToCloseHeader(int myDurationToCloseHeader) {
        this.myDurationToCloseHeader = myDurationToCloseHeader;
        setDurationToCloseHeader(myDurationToCloseHeader);
    }

    public void setLoadDataTime(int loadDataTime) {
        this.loadDataTime = loadDataTime;
    }

    private void init() {
        //自定义下拉刷新头
        setPtrHandler(this);
        PtrClassicMyHeader header = new PtrClassicMyHeader(getContext());
        header.setLastUpdateTimeKey("refresh_time_key");
        setHeaderView(header);
        addPtrUIHandler(header);
        disableWhenHorizontalMove(true);
        setDurationToClose(myDurationToClose);
        setDurationToCloseHeader(myDurationToCloseHeader);

        //自定义加载更多
        setFooterView(new DBDefaultLoadMoreViewFooter());
        setOnLoadMoreListener(this);
    }

    /**
     * 设置是否可用下拉刷新
     *
     * @param refreshEnable
     */
    public void setRefreshEnable(boolean refreshEnable) {
        isRefreshEnable = refreshEnable;
    }

    /**
     * 设置是否可加载更多
     *
     * @param loadMoreEnable
     */
    public void setLoadMoreEnables(boolean loadMoreEnable) {
        setLoadMoreEnable(loadMoreEnable);
    }

    public void setOnRefresh(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    /**
     * 刷新监听
     */
    public interface OnRefreshListener {
        /**
         * 下拉刷新
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();
    }

    /**
     * 自动下拉刷新
     */
    public void autoRefresh() {
        /*if (isRefreshing()) {
            onRefreshListener.onRefresh();
            onFinish();
            return;
        }*/

        postDelayed((() -> autoRefresh(true)), 50);
    }

    /**
     * 下拉刷新或者加载更多结束
     */
    public void onFinish() {

        if (isRefreshing()) {
            refreshComplete();
        }

        if (isLoadingMore()) {
            loadMoreComplete(true);
        }
    }

    @Override
    protected void onPtrScrollFinish() {
        super.onPtrScrollFinish();
    }

    @Override
    public void loadMore() {
        if (onRefreshListener != null) {
            postDelayed(() -> onRefreshListener.onLoadMore(), loadDataTime);
        }
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return isRefreshEnable && PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if (onRefreshListener != null) {
            postDelayed(() -> onRefreshListener.onRefresh(), loadDataTime);
        }
    }

}
