package me.zwh.mvvm.aurora.mvvm.view.adapter.base;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

import me.zwh.mvvm.aurora.R;


/**
 * @author zwh
 * @date 2018\3\6 0001
 */
public class BaseBindHolder extends BaseViewHolder {

    public BaseBindHolder(View view) {
        super(view);
    }

    public ViewDataBinding getBinding() {
        return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
    }
}
