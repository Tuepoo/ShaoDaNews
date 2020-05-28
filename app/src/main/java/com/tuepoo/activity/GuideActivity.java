package com.tuepoo.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.tuepoo.R;
import com.tuepoo.activity.base.BaseActivity;
import com.tuepoo.view.fragment.guide.GuideFragment;

public class GuideActivity extends BaseActivity {

    private static final int NUM_PAGES = 2;

    /**
     * UI
     */
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    private boolean isOpaque = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hiddenStatusBar();
        setContentView(R.layout.activity_guide_layout);
        initView();
    }

    private void initView() {
        changeStatusBarColor(R.color.transparent);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        /**
         * 为viewpager的滑动添加自定义的动画效果
         */
        mViewPager.setPageTransformer(true, new CrossfadePageTransformer());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == NUM_PAGES - 2 && positionOffset > 0) {
                    if (isOpaque) {
                        mViewPager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
                        mViewPager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == NUM_PAGES - 2) {
                } else if (position < NUM_PAGES - 2) {
                } else if (position == NUM_PAGES - 1) {
                    endTutorial();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewPager != null) {
            mViewPager.clearOnPageChangeListeners();
        }
    }

    private void endTutorial() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            GuideFragment tp = null;
            switch (position) {
                case 0:
                    tp = GuideFragment.newInstance(R.layout.fragment_welcome_one);
                    break;
                case 1:
                    tp = GuideFragment.newInstance(R.layout.fragment_welcome_two);
                    break;
            }
            return tp;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    /**
     * @description 自定义PageTransformer实现自定义动画
     */
    public class CrossfadePageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            View backgroundView = page.findViewById(R.id.welcome_fragment);
            View text_head = page.findViewById(R.id.heading);
            View text_content = page.findViewById(R.id.content);
            View object1 = page.findViewById(R.id.a000);
            View object2 = page.findViewById(R.id.a001);
            View object3 = page.findViewById(R.id.a002);
            View object4 = page.findViewById(R.id.a003);
            View object5 = page.findViewById(R.id.a004);
            View object6 = page.findViewById(R.id.a005);
            View object7 = page.findViewById(R.id.a006);

            if (0 <= position && position < 1) {
                /**
                 * [1 , 0]右侧page处理,抵消page本身的滑动动画
                 */
                ViewHelper.setTranslationX(page, pageWidth * (-position));
            }

            if (-1 < position && position < 0) {
                /**
                 * [-1 , 0]左侧page处理,抵消page本身的滑动动画
                 */
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }
            /*************************************************************************************************************************/

            if (position <= -1.0f || position >= 1.0f) {
                /**
                 * (-& ~ -1),(1 ~ +&)不可见部分不作处理
                 */
            } else if (position == 0.0f) {
            } else {
                /**
                 * 针对具体的View,移动产生视差
                 */
                if (backgroundView != null) {
                    ViewHelper.setAlpha(backgroundView, 1.0f - Math.abs(position));
                }

                if (text_head != null) {
                    ViewHelper.setTranslationX(text_head, pageWidth * position);
                    ViewHelper.setAlpha(text_head, 1.0f - Math.abs(position));
                }

                if (text_content != null) {
                    ViewHelper.setTranslationX(text_content, pageWidth * position);
                    ViewHelper.setAlpha(text_content, 1.0f - Math.abs(position));
                }

                if (object1 != null) {
                    ViewHelper.setTranslationX(object1, pageWidth * position);
                }

                // parallax effect
                if (object2 != null) {
                    ViewHelper.setTranslationX(object2, pageWidth * position);
                }

                if (object4 != null) {
                    ViewHelper.setTranslationX(object4, pageWidth / 2 * position);
                }
                if (object5 != null) {
                    ViewHelper.setTranslationX(object5, pageWidth / 2 * position);
                }
                if (object6 != null) {
                    ViewHelper.setTranslationX(object6, pageWidth / 2 * position);
                }
                if (object7 != null) {
                    ViewHelper.setTranslationX(object7, pageWidth / 2 * position);
                }
                if (object3 != null) {
                    ViewHelper.setTranslationX(object3, (float) (pageWidth / 1.2 * position));
                }
            }
        }
    }
}