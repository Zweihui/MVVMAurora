package me.zwh.mvvm.aurora.mvvm.view.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideCircleTransform;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;

import java.util.List;

import me.zwh.mvvm.aurora.BR;
import me.zwh.mvvm.aurora.R;
import me.zwh.mvvm.aurora.app.utils.StringUtils;
import me.zwh.mvvm.aurora.mvvm.model.entry.VideoListInfo;
import me.zwh.mvvm.aurora.mvvm.view.adapter.base.BaseBindAdapter;
import me.zwh.mvvm.aurora.mvvm.view.adapter.base.BaseBindHolder;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class VideoAdapter extends BaseBindAdapter<VideoListInfo.Video> {
    public VideoAdapter(@LayoutRes int layoutResId, @Nullable List<VideoListInfo.Video> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseBindHolder helper, VideoListInfo.Video item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.video, item);
        binding.executePendingBindings();
        AppComponent mAppComponent = ((App)helper.getView(R.id.img_main).getContext().getApplicationContext())
                .getAppComponent();
        ImageView imgMian = helper.getView(R.id.img_main);
        ImageView imgAutor = helper.getView(R.id.img_author);
        Context context = imgMian.getContext();
        Glide.with(context).load(item.getData().getCover().getFeed())
                .into(imgMian);
        mAppComponent.imageLoader().loadImage(context,
                ImageConfigImpl
                        .builder()
                        .url(item.getData().getCover().getFeed())
                        .imageView(imgMian)
                        .build());
        try {
            ((App)context.getApplicationContext())
                    .getAppComponent().imageLoader().loadImage(context,
                    ImageConfigImpl
                            .builder()
                            .transformation(new GlideCircleTransform())
                            .url(StringUtils.replaceNull(item.getData().getAuthor().getIcon()))
                            .imageView(helper.getView(R.id.img_author))
                            .build());
        }catch (NullPointerException e){

        }
    }
}
