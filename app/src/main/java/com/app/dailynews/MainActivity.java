package com.app.dailynews;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;

@SuppressLint("InlinedApi")
public class MainActivity extends FragmentActivity {

    private ArrayList<Fragment> fragments;

    private ViewPager viewPager;

    private TextView tab_game;
    private TextView tab_app;
    private TextView tab_study;

    private int line_width;
    private View line;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置状态栏颜色
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_main);
        // 初始化各种控件
        initData();

        // 初始化TextView动画
        ViewPropertyAnimator.animate(tab_app).scaleX(1.3f).setDuration(0);
        ViewPropertyAnimator.animate(tab_app).scaleY(1.3f).setDuration(0);

        fragments = new ArrayList<Fragment>();
        fragments.add(new NewsFragment());
        fragments.add(new ImageFragment());
        fragments.add(new TopicFragment());
        line_width = getWindowManager().getDefaultDisplay().getWidth()
                / fragments.size();
        line.getLayoutParams().width = line_width;
        line.requestLayout();

        viewPager.setAdapter(new FragmentStatePagerAdapter(
                getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }
            @Override
            public Fragment getItem(int arg0) {
                return fragments.get(arg0);
            }
        });

        /**
         * ViewPager滑动事件讲解
         * onPageScrollStateChanged(int arg0)，此方法是在状态改变的时候调用，其中arg0这个参数
         * 有三种状态（0，1，2）。arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
         * 当页面开始滑动的时候，三种状态的变化顺序为（1，2，0）
         * */
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                //arg0是表示你当前选中的页面，这事件是在你页面跳转完毕的时候调用的。
                changeState(arg0);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                //当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到调用。
                //arg0 :当前页面，及你点击滑动的页面,arg1:当前页面偏移的百分比,arg2:当前页面偏移的像素位置
                float tagerX = arg0 * line_width + arg2 / fragments.size();
                ViewPropertyAnimator.animate(line).translationX(tagerX)
                        .setDuration(0);
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                //arg0 ==1的时候表示正在滑动，arg0==2的时候表示滑动完毕了，arg0==0的时候表示什么都没做，就是停在那。
            }
        });

    }

    private void initData() {
        tab_game = (TextView) findViewById(R.id.tab_game);
        tab_app = (TextView) findViewById(R.id.tab_app);
        tab_study = (TextView) findViewById(R.id.tab_study);
        line = findViewById(R.id.line);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tab_game.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                viewPager.setCurrentItem(1);
            }
        });
        tab_app.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                viewPager.setCurrentItem(0);
            }
        });
        tab_study.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                viewPager.setCurrentItem(2);
            }
        });

    }

    /**
     * 导航栏字体的颜色大小改变
     * 根据传入的值来改变状态
     * */
    private void changeState(int arg0) {
        if (arg0 == 0) {
            tab_app.setTextColor(getResources().getColor(R.color.green));
            tab_game.setTextColor(getResources().getColor(R.color.gray_white));
            tab_study.setTextColor(getResources().getColor(R.color.gray_white));
            ViewPropertyAnimator.animate(tab_app).scaleX(1.3f).setDuration(200);
            ViewPropertyAnimator.animate(tab_app).scaleY(1.3f).setDuration(200);
            ViewPropertyAnimator.animate(tab_game).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tab_game).scaleY(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tab_study).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tab_study).scaleY(1.0f).setDuration(200);

        } else if(arg0 == 1) {
            tab_game.setTextColor(getResources().getColor(R.color.green));
            tab_app.setTextColor(getResources().getColor(R.color.gray_white));
            tab_study.setTextColor(getResources().getColor(R.color.gray_white));
            ViewPropertyAnimator.animate(tab_app).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tab_app).scaleY(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tab_game).scaleX(1.3f).setDuration(200);
            ViewPropertyAnimator.animate(tab_game).scaleY(1.3f).setDuration(200);
            ViewPropertyAnimator.animate(tab_study).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tab_study).scaleY(1.0f).setDuration(200);
        } else {
            tab_game.setTextColor(getResources().getColor(R.color.gray_white));
            tab_app.setTextColor(getResources().getColor(R.color.gray_white));
            tab_study.setTextColor(getResources().getColor(R.color.green));
            ViewPropertyAnimator.animate(tab_app).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tab_app).scaleY(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tab_game).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tab_game).scaleY(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tab_study).scaleX(1.3f).setDuration(200);
            ViewPropertyAnimator.animate(tab_study).scaleY(1.3f).setDuration(200);
        }
    }

}

