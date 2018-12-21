package com.aimi.wanandroid_mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseActivity;
import com.aimi.wanandroid_mvp.contract.WebContract;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.entity.BannerBean;
import com.aimi.wanandroid_mvp.presenter.WebPresenter;
import com.aimi.wanandroid_mvp.utils.ConstantUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class WebActivity extends RxBaseActivity<WebPresenter> implements WebContract.View {
    @BindView(R.id.tool_bar_web)
    Toolbar mToolbar;
    @BindView(R.id.tv_tool_bar_title)
    TextView mTvTitle;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progress_bar_web)
    ProgressBar mProgressBar;
    @BindView(R.id.edt_url)
    EditText mEdtUrl;
    @BindView(R.id.btn_url_go)
    TextView mBtnGo;

    private BannerBean mBannerBean;
    private ArticleEntity.ArticleBean mArticleBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initToolBar() {
        mToolbar.setTitle("");
        if (mBannerBean != null) {
            mTvTitle.setText(mBannerBean.getTitle());
        } else {
            mTvTitle.setText(mArticleBean.getTitle());
        }
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mBannerBean = (BannerBean) intent.getSerializableExtra(ConstantUtils.EXTRA_BANNER_BEAN);
        mArticleBean = (ArticleEntity.ArticleBean) intent.getSerializableExtra(ConstantUtils.EXTRA_ARTICLE_BEAN);
        //webView全屏加载
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            /* 获得网页的加载进度 */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mProgressBar != null){
                    if (newProgress == 100) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                    } else {
                        mProgressBar.setVisibility(View.VISIBLE);
                        mProgressBar.setProgress(newProgress);
                    }
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient());
        presenter = new WebPresenter(this);
        presenter.loadUrl(mBannerBean != null ? mBannerBean.getUrl() : mArticleBean.getLink());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            String url;
            if (mBannerBean != null){
                url = String.format(getString(R.string.share_content), mBannerBean.getDesc(), mBannerBean.getUrl());
            } else {
                url = String.format(getString(R.string.share_content), mArticleBean.getTitle(), mArticleBean.getLink());
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, url);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, getString(R.string.share_to_friends)));
        }
        return true;
    }

    @OnLongClick(R.id.tv_tool_bar_title)
    public boolean onLongClick(View view){
        mEdtUrl.setVisibility(View.VISIBLE);
        mBtnGo.setVisibility(View.VISIBLE);
        mTvTitle.setVisibility(View.GONE);
        return true;
    }

    @OnClick(R.id.btn_url_go)
    public void onClick(View view){
        if(!TextUtils.isEmpty(mEdtUrl.getText())){
            presenter.loadUrl("https://"+mEdtUrl.getText().toString());
        } else {
            presenter.loadUrl("https://www.baidu.com");
        }
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWebPage(String url) {
        mWebView.loadUrl(url);
    }

    public static void launch(Context context, BannerBean bannerBean) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(ConstantUtils.EXTRA_BANNER_BEAN, bannerBean);
        context.startActivity(intent);
    }

    public static void launch(Context context, ArticleEntity.ArticleBean articleBean) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(ConstantUtils.EXTRA_ARTICLE_BEAN, articleBean);
        context.startActivity(intent);
    }
}
