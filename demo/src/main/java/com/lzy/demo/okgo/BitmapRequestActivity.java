/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzy.demo.okgo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.lzy.demo.R;
import com.lzy.demo.base.BaseDetailActivity;
import com.lzy.demo.callback.BitmapDialogCallback;
import com.lzy.demo.model.ApkModel;
import com.lzy.demo.utils.Urls;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/11
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class BitmapRequestActivity extends BaseDetailActivity {

    @Bind(R.id.imageView) ImageView imageView;
    private LinearLayout linbotmap;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bitmap_request);
        linbotmap = (LinearLayout) findViewById(R.id.linerbitmap);

        ButterKnife.bind(this);
        setTitle("请求图片");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }
    String url = "http://huiyuanbao.oss-cn-hangzhou.aliyuncs.com/1539762766320787.png";
    @OnClick(R.id.requestImage)
    public void requestJson(View view) {
      //  OkGo.<Bitmap>get(Urls.URL_IMAGE)//http://huiyuanbao.oss-cn-hangzhou.aliyuncs.com/common61fe17ca315341678cbf574f4830b873.jpg
        OkGo.<Bitmap>get(url)
                .tag(this)//
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new BitmapDialogCallback(this) {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        handleResponse(response);
                        imageView.setImageBitmap(response.body());
                        // imageLoader();
                    }

                    @Override
                    public void onError(Response<Bitmap> response) {
                        handleError(response);
                    }
                    @Override
                    public void downloadProgress(Progress progress) {
                        System.out.println("进度 = "+ progress);
                    }
                });
    }

    public void imageLoader(){
        Glide.with(this).load(url).into(imageView);

        //
        //Gson gson = new Gson();
         //1，无需把刘转化成json字符串 构造JsonReader 对象即可
          //JsonReader jsonReader = new JsonReader(response.body().charStream());
         //  List<ApkModel> apkModels = gson.fromJson(jsonReader,new TypeToken<List<ApkModel>>(){}.getType());


        //2,把流转化成json字符串的形式
         //  List<ApkModel> apkModels = gson.fromJson("json数据",new TypeToken<List<ApkModel>>(){}.getType());

    }
}
