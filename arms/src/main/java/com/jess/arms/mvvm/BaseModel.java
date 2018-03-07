package com.jess.arms.mvvm;

import com.jess.arms.integration.IRepositoryManager;


/**
 * @author zwh
 * @date 2018/2/27
 * MVVM BaseModel
 */
public class BaseModel implements IModel {

    protected IRepositoryManager mRepositoryManager;


    public BaseModel(IRepositoryManager mRepositoryManager) {
        this.mRepositoryManager = mRepositoryManager;
    }

    @Override
    public void onDestroy() {
        this.mRepositoryManager = null;
    }
}
