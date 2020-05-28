package com.tuepoo.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuepoo.R;
import com.tuepoo.activity.base.BaseActivity;
import com.tuepoo.adapter.NewsCommentAdapter;
import com.tuepoo.manager.UserManager;
import com.tuepoo.module.news.BaseNewsModel;
import com.tuepoo.module.news.NewsCommentValue;
import com.tuepoo.module.user.User;
import com.tuepoo.network.http.RequestCenter;
import com.tuepoo.okhttp.listener.DisposeDataListener;
import com.tuepoo.util.Util;
import com.tuepoo.view.news.NewsDetailFooterView;
import com.tuepoo.view.news.NewsDetailHeaderView;

/**
 * @function: 详情Activity, 展示详情,这个页面要用signalTop模式
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static String COURSE_ID = "courseID";

    /**
     * UI
     */
    private ImageView mBackView;
    private ListView mListView;
    private ImageView mLoadingView;
    private RelativeLayout mBottomLayout;
    private ImageView mJianPanView;
    private EditText mInputEditView;
    private TextView mSendView;
    private NewsDetailHeaderView headerView;
    private NewsDetailFooterView footerView;
    private NewsCommentAdapter mAdapter;
    /**
     * Data
     */
    private String mCourseID;
    private BaseNewsModel mData;
    private String tempHint = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_layout);
        initData();
        initView();
        requestDeatil();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
        initView();
        requestDeatil();
    }

    //初始化数据
    private void initData() {
        Intent intent = getIntent();
        mCourseID = intent.getStringExtra(COURSE_ID);
    }

    //初始化数据
    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mBackView.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.comment_list_view);
        //mListView.setOnItemClickListener(this);
        mListView.setVisibility(View.GONE);
        mLoadingView = (ImageView) findViewById(R.id.loading_view);
        mLoadingView.setVisibility(View.VISIBLE);
        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
        anim.start();

        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        mJianPanView = (ImageView) findViewById(R.id.jianpan_view);
        mJianPanView.setOnClickListener(this);
        mInputEditView = (EditText) findViewById(R.id.comment_edit_view);
        mSendView = (TextView) findViewById(R.id.send_view);
        mSendView.setOnClickListener(this);
        mBottomLayout.setVisibility(View.GONE);

        intoEmptyState();
    }

    private void requestDeatil() {

        RequestCenter.requestCourseDetail(mCourseID, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mData = (BaseNewsModel) responseObj;
                updateUI();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }

    //根据数据填充UI
    private void updateUI() {
        mLoadingView.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        mAdapter = new NewsCommentAdapter(this, mData.data.body);
        mListView.setAdapter(mAdapter);

        if (headerView != null) {
            mListView.removeHeaderView(headerView);
        }
        headerView = new NewsDetailHeaderView(this, mData.data.head);
        mListView.addHeaderView(headerView);
        if (footerView != null) {
            mListView.removeFooterView(footerView);
        }
        footerView = new NewsDetailFooterView(this, mData.data.footer);
        mListView.addFooterView(footerView);

        mBottomLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int cursor = position - mListView.getHeaderViewsCount();
        if (cursor >= 0 && cursor < mAdapter.getCommentCount()) {
            if (UserManager.getInstance().hasLogined()) {
                NewsCommentValue value = (NewsCommentValue) mAdapter.getItem(
                        position - mListView.getHeaderViewsCount());
                if (value.userId.equals(UserManager.getInstance().getUser().data.userId)) {
                    //自己的评论不能回复
                    intoEmptyState();
                    Toast.makeText(this, "不能回复自己!", Toast.LENGTH_SHORT).show();
                } else {
                    //不是自己的评论，可以回复
                    tempHint = getString(R.string.comment_hint_head).concat(value.name).
                            concat(getString(R.string.comment_hint_footer));
                    intoEditState(tempHint);
                }
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
    }

    /**
     * EditText进入编辑状态
     */
    private void intoEditState(String hint) {
        mInputEditView.requestFocus();
        mInputEditView.setHint(hint);
        Util.showSoftInputMethod(this, mInputEditView);
    }

    public void intoEmptyState() {
        tempHint = "";
        mInputEditView.setText("");
        mInputEditView.setHint(getString(R.string.input_comment));
        Util.hideSoftInputMethod(this, mInputEditView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_view:
                finish();
                break;
            case R.id.send_view:
                String comment = mInputEditView.getText().toString().trim();
                if (UserManager.getInstance().hasLogined()) {
                    if (!TextUtils.isEmpty(comment)) {
                        mAdapter.addComment(assembleCommentValue(comment));
                        intoEmptyState();
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }

                break;
            case R.id.jianpan_view:
                mInputEditView.requestFocus();
                Util.showSoftInputMethod(this, mInputEditView);
                break;
        }
    }

    /**
     * 组装CommentValue对象
     *
     * @return
     */
    private NewsCommentValue assembleCommentValue(String comment) {
        User user = UserManager.getInstance().getUser();
        NewsCommentValue value = new NewsCommentValue();
        value.name = user.data.name;
        value.logo = user.data.photoUrl;
        value.userId = user.data.userId;
        value.type = 1;
        value.text = tempHint + comment;
        return value;
    }
}
