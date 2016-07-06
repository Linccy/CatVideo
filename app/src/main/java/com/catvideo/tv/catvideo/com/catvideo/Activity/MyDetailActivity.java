package com.catvideo.tv.catvideo.com.catvideo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.catvideo.tv.catvideo.R;
import com.catvideo.tv.catvideo.com.catvideo.Model.Video;
import com.catvideo.tv.catvideo.com.catvideo.PlayVideoHelper;
import com.catvideo.tv.catvideo.com.catvideo.Utils.ImageUtils;

/**
 * Created by Lcc on 2016/6/6.
 */

public class MyDetailActivity extends AppCompatActivity {

    private ImageView Igv_pic;
    private TextView tv_VideoName, tv_VideoTime, tv_VideoArea, tv_Introduction;
    private TextView introductiontv;
    private Button bt_play;
    private Video video;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findViews();
        video = (Video) getIntent().getSerializableExtra("video");
        if (video != null) {
            initViews();
        } else {
            Toast.makeText(MyDetailActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        if (video != null) {
            tv_VideoName.setText(video.getVideo_name());
            tv_VideoArea.setText(video.getArea().equals("''")?video.getArea():"未知");
//            tv_VideoTime.setText(video.getYear());
            tv_Introduction.setText("        " + video.getVideo_desc());
            introductiontv.setTextColor(getResources().getColor(R.color.colorTitle));
            ImageUtils.download(video.getPic(), Igv_pic);
            bt_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayVideoHelper.playSohuOnlineVideo(MyDetailActivity.this, video.getAid(), video.getVid(),
                            video.getSite(), 0);
                }
            });
        }
    }

    private void findViews() {
        Igv_pic = (ImageView) findViewById(R.id.pic);
        tv_VideoName = (TextView) findViewById(R.id.videoname);
        tv_VideoTime = (TextView) findViewById(R.id.videotime);
        tv_VideoArea = (TextView) findViewById(R.id.videoarea);
        tv_Introduction = (TextView) findViewById(R.id.introduction);
        introductiontv = (TextView) findViewById(R.id.introductiontv);
        bt_play = (Button) findViewById(R.id.play);
    }
}
