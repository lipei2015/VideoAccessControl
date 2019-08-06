package com.ybkj.videoaccess.weight.refresh_loadmore;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.MyApp;

/**
 * Created by HH on 2017/1/17 0017.
 */

public class DBDefaultLoadMoreViewFooter implements ILoadMoreViewFactory {
    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new LoadMoreHelper();
    }

    private class LoadMoreHelper implements ILoadMoreView {

        protected View footerView;
        protected TextView footerTv;
        protected ProgressBar footerBar;

        protected View.OnClickListener onClickRefreshListener;

        @Override
        public void init(FootViewAdder footViewHolder, View.OnClickListener onClickRefreshListener) {
            footerView = (TextView)footViewHolder.addFootView(com.chanven.lib.cptr.R.layout.loadmore_default_footer);
            footerTv = (TextView)footerView.findViewById(com.chanven.lib.cptr.R.id.loadmore_default_footer_tv);
            footerBar = (ProgressBar)footerView.findViewById(com.chanven.lib.cptr.R.id.loadmore_default_footer_progressbar);
            this.onClickRefreshListener = onClickRefreshListener;
            showNormal();
        }

        @Override
        public void showNormal() {
            footerTv.setText(MyApp.getAppContext().getString(R.string.load_more));
            footerBar.setVisibility(View.GONE);
            footerView.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showLoading() {
            footerTv.setText(MyApp.getAppContext().getString(R.string.loading));
            footerBar.setVisibility(View.VISIBLE);
            footerView.setOnClickListener(null);
        }

        @Override
        public void showFail(Exception exception) {
            footerTv.setText(MyApp.getAppContext().getString(R.string.load_fail));
            footerBar.setVisibility(View.GONE);
            footerView.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showNomore() {
            footerTv.setText(MyApp.getAppContext().getString(R.string.load_done));
            footerBar.setVisibility(View.GONE);
            footerView.setOnClickListener(null);
        }

        @Override
        public void setFooterVisibility(boolean isVisible) {
            footerView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }
}
