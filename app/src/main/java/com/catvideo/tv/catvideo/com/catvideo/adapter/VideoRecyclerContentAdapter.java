package com.catvideo.tv.catvideo.com.catvideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.catvideo.tv.catvideo.R;
import com.catvideo.tv.catvideo.com.catvideo.Activity.MyDetailActivity;
import com.catvideo.tv.catvideo.com.catvideo.Model.Video;
import com.catvideo.tv.catvideo.com.catvideo.Utils.ImageUtils;

import java.util.List;

/**
 * Created by Lcc on 2016/6/6.
 */

public class VideoRecyclerContentAdapter extends RecyclerView.Adapter<VideoRecyclerContentAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<Video> mVideos;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mImageView = (ImageView) itemView.findViewById(R.id.img_icon);
            mTextView = (TextView) itemView.findViewById(R.id.txt_name);
        }

    }

    public VideoRecyclerContentAdapter(Context context, List<Video> mVideos) {
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.mVideos = mVideos;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = layoutInflater.inflate(
                R.layout.grid_item_video, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoRecyclerContentAdapter.ViewHolder holder, int position) {
        final Video video = mVideos.get(position);
        String picUrl = video.getPic();
        ImageUtils.download(picUrl, holder.mImageView);//下载图片并显示
        String videoName = video.getVideo_name();
        holder.mTextView.setText(videoName);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("video", video);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
//                PlayVideoHelper.playSohuOnlineVideo(mContext, video.getAid(), video.getVid(),
//                        video.getSite(), 0);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public void addAll(List<Video> videoList) {
        mVideos.addAll(videoList);
    }

    public void clear() {
        mVideos.clear();
    }
}
