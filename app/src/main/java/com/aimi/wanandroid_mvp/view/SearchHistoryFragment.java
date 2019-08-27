package com.aimi.wanandroid_mvp.view;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.internal.FlowLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.SearchHistoryContract;
import com.aimi.wanandroid_mvp.presenter.SearchHistoryPresenter;
import com.aimi.wanandroid_mvp.utils.ConstantUtils;
import com.aimi.wanandroid_mvp.utils.DisplayUtils;
import com.aimi.wanandroid_mvp.utils.PreferenceUtils;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchHistoryFragment extends RxBaseFragment<SearchHistoryPresenter> implements SearchHistoryContract.View {
    @BindView(R.id.flow_layout_search_hot_keys)
    FlowLayout mLayoutHotKeys;
    @BindView(R.id.flow_layout_search_history)
    FlowLayout mLayoutHistory;

    private OnItemClickListener mOnItemClickListener;

    interface OnItemClickListener {
        void onClick(String name);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_history;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new SearchHistoryPresenter(this);
        presenter.getHotSearch(this);
        presenter.getSearchHistory();
    }

    @Override
    public void showSearchHistory(List<String> search) {
        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,
                FlowLayout.LayoutParams.WRAP_CONTENT);
        for (String name : search) {
            TextView tv = new TextView(getActivity());
            tv.setText(name);
            tv.setTextSize(16);
            tv.setPadding(DisplayUtils.dp2Px(8), DisplayUtils.dp2Px(2), DisplayUtils.dp2Px(8), DisplayUtils.dp2Px(2));
            int randomNum = new Random().nextInt(7);
            tv.setTextColor(getResources().getColor(ConstantUtils.TREE_NODE_TITLE_COLORS.get(randomNum)));
            tv.setBackgroundResource(R.drawable.shape_navi_detail_item);
            tv.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(name);
                }
            });
            mLayoutHistory.addView(tv, lp);
        }
    }

    @Override
    public void showHotSearch(List<String> search) {
        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,
                FlowLayout.LayoutParams.WRAP_CONTENT);
        for (String name : search) {
            TextView tv = new TextView(getActivity());
            tv.setText(name);
            tv.setTextSize(16);
            tv.setTextColor(Color.WHITE);
            tv.setPadding(DisplayUtils.dp2Px(8), DisplayUtils.dp2Px(2), DisplayUtils.dp2Px(8), DisplayUtils.dp2Px(2));
            int randomNum = new Random().nextInt(7);
            tv.setBackgroundColor(getResources().getColor(ConstantUtils.TREE_NODE_TITLE_COLORS.get(randomNum)));
            tv.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(name);
                }
            });
            mLayoutHotKeys.addView(tv, lp);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public void refreshHistory() {
        mLayoutHistory.removeAllViews();
        List<String> history = PreferenceUtils.getSearchHistory();
        showSearchHistory(history);
    }

    @OnClick(R.id.img_delete_history)
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.delete_all_search_history))
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    boolean isDelete = PreferenceUtils.deleteSearchHistory();
                    if (isDelete) {
                        mLayoutHistory.removeAllViews();
                    } else {
                        showToast(R.string.delete_failed);
                    }
                })
                .setNegativeButton(getString(R.string.cancle), (dialog, which) -> dialog.dismiss());
        builder.create();
        builder.show();
    }
}
