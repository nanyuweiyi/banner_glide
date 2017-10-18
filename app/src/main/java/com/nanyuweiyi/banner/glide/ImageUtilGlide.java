package com.nanyuweiyi.banner.glide;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.nanyuweiyi.banner.R;

/**
 * 加载图片工具类，使用glide
 * 官网：https://github.com/bumptech/glide
 * Created by caibing.zhang on 2017/6/20.
 *
 * 其它说明：
 * 1：清除缓存的方法
        Glide.get(this).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
        Glide.get(this).clearMemory();//清理内存缓存  可以在UI主线程中进行
   2：清除掉所有的图片加载请求
        Glide.clear()
   3：动态的GIF图片加载
         Glide.with(context).load(gifUrl).asBitmap().into(imageview); //显示gif静态图片
         Glide.with(context).load(gifUrl).asGif().into(imageview); //显示gif动态图片
   4：滚动加载，不滚动时不加载，提高ListView的效率
     public void onScrollStateChanged(AbsListView view, int scrollState) {
         switch (scrollState){
         case SCROLL_STATE_FLING:
             Log.i("ListView","用户在手指离开屏幕之前，由于滑了一下，视图仍然依靠惯性继续滑动");
             Glide.with(getApplicationContext()).pauseRequests();
         //刷新
         break;
         case SCROLL_STATE_IDLE:
             Log.i("ListView", "视图已经停止滑动");
             Glide.with(getApplicationContext()).resumeRequests();
         break;
         case SCROLL_STATE_TOUCH_SCROLL:
             Log.i("ListView","手指没有离开屏幕，视图正在滑动");
             Glide.with(getApplicationContext()).resumeRequests();
         break;
         }
     }
 */

public class ImageUtilGlide {

    //默认圆角大小
    public static final int DEFAULT_ROUNDSIZE = 45;
    public static final int DEFAULT_AVATAR_ROUNDSIZE = 4;

    /**
     * 加载网络(普通)图片
     * @param imageView
     * @param imageUrl
     */
    public static void displayImage(ImageView imageView, String imageUrl){
        log(imageUrl);
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.building) //设置占位图
                .error(R.mipmap.building) //设置错误图片
                .crossFade()  //设置淡入淡出效果，默认300ms，可以传参
                .centerCrop()  //Glide有两个方法可以设置图片剪裁的策略 ①fitCenter(), ②centerCrop()
                .into(imageView);
    }

    /**
     * 加载网络(普通)图片，
     * @param imageView
     * @param imageUrl
     * @param roundSize 圆角大小
     */
    public static void displayImage(ImageView imageView, String imageUrl, int roundSize){
        log(imageUrl);
        if(roundSize<=0){
            roundSize = DEFAULT_ROUNDSIZE;
        }
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.building) //设置占位图
                .error(R.mipmap.building) //设置错误图片
                .crossFade()  //设置淡入淡出效果，默认300ms，可以传参
                .transform(new GlideRoundTransform(imageView.getContext(),roundSize))
                .centerCrop()
                .into(imageView);
    }


    /**
     * 加载网络(普通)图片 监听，
     * @param imageView
     * @param imageUrl
     * @param customRequestListener
     */
    public static void displayImageToListener(ImageView imageView, String imageUrl, final CustomRequestListener customRequestListener){
        log(imageUrl);
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.building) //设置占位图
                .error(R.mipmap.building) //设置错误图片
                .crossFade()  //设置淡入淡出效果，默认300ms，可以传参
                .centerCrop()
                .into(new GlideDrawableImageViewTarget(imageView){
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        getView().setVisibility(View.VISIBLE);
                        // 开始加载图片
                        customRequestListener.onLoadStarted(placeholder);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        customRequestListener.onLoadFailed(e,errorDrawable);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        customRequestListener.onResourceReady(getView(),resource,glideAnimation);
                    }
                });
    }

    /**
     * 加载网络(头像)图片
     * @param imageView
     * @param imageUrl
     */
    public static void displayAvatarImage(ImageView imageView, String imageUrl){
        log(imageUrl);
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.dft_avatar) //设置占位图
                .error(R.mipmap.dft_avatar) //设置错误图片
//                .crossFade()  //设置淡入淡出效果，默认300ms，可以传参
                .transform(new GlideRoundTransform(imageView.getContext(),DEFAULT_AVATAR_ROUNDSIZE))
                .into(imageView);
    }

    /**
     * 加载网络(头像)图片，
     * @param imageView
     * @param imageUrl
     * @param roundSize 圆角大小
     */
    public static void displayAvatarImage(ImageView imageView, String imageUrl, int roundSize){
        log(imageUrl);
        if(roundSize<=0){
            roundSize = DEFAULT_AVATAR_ROUNDSIZE;
        }
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.dft_avatar) //设置占位图
                .error(R.mipmap.dft_avatar) //设置错误图片
                .crossFade()  //设置淡入淡出效果，默认300ms，可以传参
                .transform(new GlideRoundTransform(imageView.getContext(),roundSize))
                .into(imageView);
    }

    /**
     * 加载assets中图片，
     * @param imageView
     * @param assetsPath 路径：如：file:///android_asset/xxx.jpg
     */
    public static void displayImageToAsset(ImageView imageView, String assetsPath){
        log(assetsPath);
        Glide.with(imageView.getContext())
                .load(assetsPath)
                .crossFade()  //设置淡入淡出效果，默认300ms，可以传参
                .into(imageView);
    }

    /**
     * 加载assets中图片，
     * @param imageView
     * @param assetsPath 路径：如：file:///android_asset/xxx.jpg
     * @param roundSize 圆角大小
     */
    public static void displayImageToAsset(ImageView imageView, String assetsPath, int roundSize){
        log(assetsPath);
        if(roundSize<=0){
            roundSize = DEFAULT_ROUNDSIZE;
        }
        Glide.with(imageView.getContext())
                .load(assetsPath)
                .crossFade()  //设置淡入淡出效果，默认300ms，可以传参
                .transform(new GlideRoundTransform(imageView.getContext(),roundSize))
                .into(imageView);
    }

    /**
     * 加载File中图片，
     * @param imageView
     * @param filePath 路径：如："file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg"
     */
    public static void displayImageToFile(ImageView imageView, String filePath){
        log(filePath);
        Glide.with(imageView.getContext())
                .load(filePath)
                .crossFade()  //设置淡入淡出效果，默认300ms，可以传参
                .into(imageView);
    }

    /**
     * 加载File中图片，
     * @param imageView
     * @param filePath 路径：如："file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg"
     * @param roundSize 圆角大小
     */
    public static void displayImageToFile(ImageView imageView, String filePath, int roundSize){
        log(filePath);
        if(roundSize<=0){
            roundSize = DEFAULT_ROUNDSIZE;
        }
        Glide.with(imageView.getContext())
                .load(filePath)
                .crossFade()  //设置淡入淡出效果，默认300ms，可以传参
                .transform(new GlideRoundTransform(imageView.getContext(),roundSize))
                .into(imageView);
    }

    public static void log(String url){
        Log.e("ImageUtilGlide --> : ", url);
    }

    /**
     * 图片加载监听，接口回调
     */
    public interface CustomRequestListener{

        /**
         * 开始加载图片
         * @param placeholder
         */
        void onLoadStarted(Drawable placeholder);

        /**
         * 图片加载失败
         * @param e
         * @param errorDrawable
         */
        void onLoadFailed(Exception e, Drawable errorDrawable);

        /**
         * 图片加载完成
         * @param imageView
         * @param resource
         * @param glideAnimation
         */
        void onResourceReady(ImageView imageView, GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation);
    }

}
