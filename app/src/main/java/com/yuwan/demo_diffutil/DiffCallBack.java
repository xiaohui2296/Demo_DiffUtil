package com.yuwan.demo_diffutil;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class DiffCallBack extends DiffUtil.Callback {
    private List<TestBean> mOldDatas, mNewDatas;

    public DiffCallBack(List<TestBean> mOldDatas, List<TestBean> mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
    }

    @Override
    public int getOldListSize() {
        return mOldDatas == null ? 0 : mOldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return mNewDatas == null ? 0 : mNewDatas.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        TestBean oldBean = mOldDatas.get(oldItemPosition);
        TestBean newBean = mNewDatas.get(newItemPosition);
        return oldBean.getId() == newBean.getId();
//        return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        TestBean oldBean = mOldDatas.get(oldItemPosition);
        TestBean newBean = mNewDatas.get(newItemPosition);
        if (!TextUtils.equals(oldBean.getName(), newBean.getName())) {
            return false;
        }
        return true;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        TestBean oldBean = mOldDatas.get(oldItemPosition);
        TestBean newBean = mNewDatas.get(newItemPosition);
        Bundle payload = new Bundle();
        if (!TextUtils.equals(oldBean.getName(), newBean.getName())) {
            payload.putString("KEY_NAME", newBean.getName());
        }
        if (payload.size() == 0) {
            return null;
        }
        return payload;
    }
}
