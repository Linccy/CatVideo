package com.catvideo.tv.catvideo.com.catvideo.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.catvideo.tv.catvideo.R;
import com.catvideo.tv.catvideo.com.catvideo.Model.ContentModel;
import com.catvideo.tv.catvideo.com.catvideo.Model.PgcChannel;
import com.catvideo.tv.catvideo.com.catvideo.Model.VideoList;
import com.catvideo.tv.catvideo.com.catvideo.PlayVideoHelper;
import com.catvideo.tv.catvideo.com.catvideo.adapter.ContentAdapter;
import com.catvideo.tv.catvideo.com.catvideo.adapter.VideoRecyclerContentAdapter;
import com.catvideo.tv.catvideo.com.catvideo.net.VideoListProtocol;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private RelativeLayout leftLayout;//左侧滑栏
    private ContentAdapter adapter;

    private VideoRecyclerContentAdapter mRecyclerAdapter;
    private RecyclerView mColumnRecyclerView;

    private ViewPager viewPager;//页卡内容
    private ImageView imageView;// 动画图片
    private TextView textView1, textView2, textView3;
    private List<RecyclerView> views;// Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private View view1, view2, view3;//各个页卡
    private List<PgcChannel> pgcChannelList;
    private int tagNo = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findviews();
        InitDrawLayoutLeft();
        InitImageView();
        InitTextView();
        InitViewPager();
        setSupportActionBar(toolbar);//
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener(this);
//        new ShowContentTask().execute(this);
    }

    private void InitDrawLayoutLeft() {

        ListView listView = (ListView) leftLayout.findViewById(R.id.left_listview);

        ArrayList<ContentModel> list = new ArrayList<ContentModel>();

        list.add(new ContentModel(R.drawable.doctoradvice2, "新闻"));
        list.add(new ContentModel(R.drawable.infusion_selected, "订阅"));
        list.add(new ContentModel(R.drawable.mypatient_selected, "图片"));
        list.add(new ContentModel(R.drawable.mywork_selected, "视频"));
        list.add(new ContentModel(R.drawable.nursingcareplan2, "跟帖"));
        list.add(new ContentModel(R.drawable.personal_selected, "投票"));
        adapter = new ContentAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContentModel contentModel = (ContentModel) parent.getItemAtPosition(position);
//                TextView tv1=(TextView)findViewById(R.id.tv1);
//                tv1.setText(contentModel.getText());
//                switch(contentModel.getText()){
//                    case "新闻":
//                        Log.d("myclick:","新闻");
//                }
                Toast.makeText(MainActivity.this, contentModel.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void InitViewPager() {
        viewPager = (ViewPager) findViewById(R.id.vPager);
        views = new ArrayList<RecyclerView>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.tab_1, null);
        view2 = inflater.inflate(R.layout.tab_2, null);
        view3 = inflater.inflate(R.layout.tab_3, null);

        RecyclerView V1 = new RecyclerView(this);
        RecyclerView V2 = new RecyclerView(this);
        RecyclerView V3 = new RecyclerView(this);

        PgcChannel mPgcChannel1 = new PgcChannel(30, 127100, "随身数码");
        PgcChannel mPgcChannel2 = new PgcChannel(95, 194100, "做饭");
        PgcChannel mPgcChannel3 = new PgcChannel(28, 125100, "体育资讯");
        pgcChannelList = new ArrayList<PgcChannel>();
        pgcChannelList.add(mPgcChannel1);
        pgcChannelList.add(mPgcChannel2);
        pgcChannelList.add(mPgcChannel3);
//        try {
//            VideoList obj = new ShowContentTask().execute(this).get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        views.add(V1);
        views.add(V2);
        views.add(V3);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化头标
     */

    private void InitTextView() {
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);

        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
        textView3.setOnClickListener(new MyOnClickListener(2));
    }

    /**
     * 2      * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     * 3
     */

    private void InitImageView() {
        imageView = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.tag).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 头标点击监听 3
     */
    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<RecyclerView> mListViews;

        public MyViewPagerAdapter(List<RecyclerView> mListViews) {
            this.mListViews = mListViews;
        }

        private RecyclerView mCurrentView;

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mCurrentView = (RecyclerView) object;
        }

        public View getPrimaryItem() {
            return mCurrentView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            mColumnRecyclerView = mListViews.get(position);
            try {
                new ShowContentTask().execute(MainActivity.this).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        public void onPageScrollStateChanged(int arg0) {


        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        public void onPageSelected(int arg0) {
            /*两种方法，这个是一种，下面还有一种，显然这个比较麻烦
            Animation animation = null;
            switch (arg0) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                break;
            case 2:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }
                break;

            }
            */
            Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);//显然这个比较简洁，只有一行代码。
            currIndex = arg0;
            tagNo = viewPager.getCurrentItem();
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            imageView.startAnimation(animation);
            Toast.makeText(MainActivity.this, "您选择了" + viewPager.getCurrentItem() + "页卡", Toast.LENGTH_SHORT).show();
        }

    }


    private void findviews() {
        View tab1 = getLayoutInflater().inflate(R.layout.tab_1, null);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mColumnRecyclerView = (RecyclerView) tab1.findViewById(R.id.mid);
//        userIcon = (ImageView) findViewById(R.id.userIcon);
        leftLayout = (RelativeLayout) findViewById(R.id.left);
//        setOnclick();
    }

    private void userIconOnclick(View view) {
        Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override//当客户点击MENU按钮的时候，调用该方法
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.title_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //当客户点击菜单当中的某一个选项时，会调用该方法
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.e("关于", "actionBar?");
//        Toast.makeText(MainActivity.this, "sasas", Toast.LENGTH_SHORT).show();

        //noinspection SimplifiableIfStatement
        if (id == R.id.user_p) {
            Intent intent = new Intent(this, UserLoginActivity.class);
            startActivityForResult(intent, 0);//开启的页面调用finish()后会执行本Activity的onActivityResult()
            return true;
        } else if (id == R.id.write_p) {
//            Intent intent = new Intent(this, RegisterActivity.class);
//            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    *当这个Text2Activity调用了finish()方法以后，程序会自动跳转回T1Activity，并调用前一个T1Activity中的onActivityResult( )方法。
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private class ShowContentTask extends AsyncTask<Context, Integer, VideoList> {
        private Context context;

        @Override
        protected VideoList doInBackground(Context... params) {
            this.context = params[0];
            PgcChannel mPgcChannel = pgcChannelList.get(tagNo);
//            mPgcChannel.first_cate_code = 30;
//            mPgcChannel.second_cate_code = 127100;
//            mPgcChannel.second_cate_name = "随身数码";
            VideoListProtocol videoListProtocol = new VideoListProtocol(MainActivity.this, mPgcChannel,
                    PlayVideoHelper.APIKEY);
            final VideoList videoList = videoListProtocol.request();
            return videoList;
        }

        @Override
        protected void onPostExecute(VideoList videoList) {
            mRecyclerAdapter = new VideoRecyclerContentAdapter(this.context, videoList.getVideos());
            mColumnRecyclerView.setLayoutManager(
                    new StaggeredGridLayoutManager(
                            2, StaggeredGridLayoutManager.HORIZONTAL));
            mColumnRecyclerView.setAdapter(mRecyclerAdapter);
        }
    }
}
