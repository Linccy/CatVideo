package com.catvideo.tv.catvideo.com.catvideo.Utils;

import android.widget.ImageView;

import com.catvideo.tv.catvideo.com.catvideo.imagehepler.ImageDownloader;


public class ImageUtils {

	public static void download(String picUrl, ImageView imageView) {
		ImageDownloader loader = new ImageDownloader();
		loader.download(picUrl, imageView);
	}
}
