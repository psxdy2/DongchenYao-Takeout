package com.demo.takeoutapp.ui.store;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.person.LoginActivity;
import com.demo.takeoutapp.util.Constant;
import com.demo.takeoutapp.util.FileUtil;
import com.demo.takeoutapp.util.GlideEngine;
import com.kproduce.roundcorners.RoundButton;
import com.kproduce.roundcorners.RoundLinearLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import okhttp3.Call;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.demo.takeoutapp.util.ToastUtil.toast;

public class CreateStoreActivity extends AppCompatActivity {

    private ImageView logo;
    private EditText storename;
    private EditText emailcode;
    private RoundButton next;

    private String storeImage;

    private ProgressDialog mProgressDialog;
    private void show() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(CreateStoreActivity.this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setTitle("提示");
            mProgressDialog.setMessage("加载中...");
            mProgressDialog.show();
        } else {
            mProgressDialog.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        initView();
    }

    private void initView() {
        logo = (ImageView) findViewById(R.id.logo);
        storename = (EditText) findViewById(R.id.storename);
        emailcode = (EditText) findViewById(R.id.emailcode);
        next = (RoundButton) findViewById(R.id.next);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPhoto();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(storename.getText())){
                    toast("店铺名称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(emailcode.getText())){
                    toast("店铺描述不能为空");
                    return;
                }
                if (TextUtils.isEmpty(storeImage)){
                    toast("店铺logo不能为空");
                    return;
                }
                registerStore();
            }
        });
    }

    /**
     * 注册店铺信息
     */
    private void registerStore(){
        show();
        JSONObject req = new JSONObject();
        try {
            req.put("userid", Constant.userinfo.getId());
            req.put("storename",storename.getText().toString());
            req.put("storeinfo",emailcode.getText().toString());
            req.put("storeimage",storeImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.addstore, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                mProgressDialog.dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                mProgressDialog.dismiss();
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    finish();
                }
                toast(rep.getString("message"), CreateStoreActivity.this);
            }
        });
    }

    /**
     * 启动图库
     */
    private void startPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .loadImageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .enableCrop(true)// 是否裁剪
                .compress(false)// 是否压缩
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .rotateEnabled(false) // 裁剪是否可旋转图片
                .scaleEnabled(false)// 裁剪是否可放大缩小图片
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
                if (images.size() != 1) {
                    return;
                }
                show();
                String path = FileUtil.getPathFromInputStreamUri(this, Uri.parse(images.get(0).getPath()), new Date().toString() + ".png");
                Luban.with(this)
                        .load(path)                                   // 传人要压缩的图片列表
                        .ignoreBy(100)                                  // 忽略不压缩图片的大小
                        .setTargetDir(getExternalCacheDir().getPath())                        // 设置压缩后文件存储位置
                        .setCompressListener(new OnCompressListener() { //设置回调
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                uploadPortrait(file.getPath());
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                                mProgressDialog.dismiss();
                            }
                        }).launch();    //启动压缩
            }
        }
    }


    /**
     * 上传图片
     */
    private void uploadPortrait(String path) {
        BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                mProgressDialog.dismiss();
                if (e == null) {
                    Glide.with(CreateStoreActivity.this).load(bmobFile.getFileUrl()).into(logo);
                    storeImage = bmobFile.getFileUrl();
                } else {
                    toast("上传文件失败：" + e.getMessage());
                }
            }
            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }

}