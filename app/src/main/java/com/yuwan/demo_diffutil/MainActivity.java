package com.yuwan.demo_diffutil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.yuwan.demo_diffutil.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding viewBinding;
    private MyRecyclerViewAdapter myAdapter;
    private List<TestBean> oldList = new ArrayList<>();
    private List<TestBean> newList;
    private String refresh_time;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        initView();
        initData();
        downTimer();
    }

    private void downTimer02() {
        new CountDownTimer(10000, 500) {
            int i = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                newList.get(0).setName("格莱芬---" + (i++));
            }

            @Override
            public void onFinish() {
                refresh();
            }
        };
    }

    private void initData() {
        for (int i = 0; i < 4; i++) {
            TestBean testBean = new TestBean("格莱芬多战队" + i, i);
            oldList.add(testBean);
        }

        newList = new ArrayList<>();
        for (TestBean testBean : oldList) {
            try {
                newList.add((TestBean) testBean.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        if (newList.size() > 2) {
            newList.get(0).setName("格莱芬---" + 0);
            newList.get(1).setName("格莱芬---" + 1);
        }
    }

    private void downTimer() {
        new CountDownTimer(3000, 1000) {
            int n = 3;

            @Override
            public void onTick(long millisUntilFinished) {
                refresh_time = "(" + --n + "秒)";
                dialog.setMessage(refresh_time);
                dialog.show();
            }

            @Override
            public void onFinish() {
                dialog.dismiss();
                refresh();
            }
        }.start();
    }

    private void initView() {
        myAdapter = new MyRecyclerViewAdapter(this, oldList);
        viewBinding.recyclerview.setAdapter(myAdapter);
        viewBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("刷新倒计时")
                .create();
    }

    private void refresh() {
        myAdapter.setDatas(newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(oldList, newList), true);
        diffResult.dispatchUpdatesTo(myAdapter);
    }
}