package com.example.githubusers.Utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class GlideUtility
{
	private static final String TAG = GlideUtility.class.getSimpleName();

	public static void useGlide(Context context, String url, ImageView imageView, int placeholder, int error)
	{
		if (TextUtils.isEmpty(url))
		{
			if (imageView != null)
				imageView.setImageResource(placeholder);

			return;
		}

		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(error)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.fitCenter()
				.centerCrop();

		Glide.with(context)
				.load(url)
				.apply(options)
				.transition(new DrawableTransitionOptions().crossFade())
				.into(imageView);
	}

	public static void cancelRequest(Context context, ImageView imageView)
	{
		Glide.with(context).clear(imageView);
	}

	public static void useGlideWithFile(Context context, File file, ImageView imageView, int placeholder, int error)
	{
		if (file == null)
			return;

		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(error)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.fitCenter()
				.centerCrop();

		Glide.with(context)
				.load(file)
				.apply(options)
				.into(imageView);
	}

	public static int dpToPx(float dp, Resources resources)
	{
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
		return (int) px;
	}
}
