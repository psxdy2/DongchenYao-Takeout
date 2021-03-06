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
            mProgressDialog.setTitle("??????");
            mProgressDialog.setMessage("?????????...");
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
                    toast("????????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(emailcode.getText())){
                    toast("????????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(storeImage)){
                    toast("??????logo????????????");
                    return;
                }
                registerStore();
            }
        });
    }

    /**
     * ??????????????????
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
     * ????????????
     */
    private void startPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// ??????.PictureMimeType.ofAll()?????????.ofImage()?????????.ofVideo()?????????.ofAudio()
                .loadImageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)// ????????????????????????
                .minSelectNum(1)// ??????????????????
                .imageSpanCount(4)// ??????????????????
                .selectionMode(PictureConfig.SINGLE)// ?????? or ??????PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .previewImage(true)// ?????????????????????
                .isCamera(true)// ????????????????????????
                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                .enableCrop(true)// ????????????
                .compress(false)// ????????????
                //.sizeMultiplier(0.5f)// glide ?????????????????? 0~1?????? ????????? .glideOverride()??????
                .glideOverride(160, 160)// glide ???????????????????????????????????????????????????????????????????????????????????????
                .rotateEnabled(false) // ???????????????????????????
                .scaleEnabled(false)// ?????????????????????????????????
                .forResult(PictureConfig.CHOOSE_REQUEST);//????????????onActivityResult code
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
                        .load(path)                                   // ??????????????????????????????
                        .ignoreBy(100)                                  // ??????????????????????????????
                        .setTargetDir(getExternalCacheDir().getPath())                        // ?????????????????????????????????
                        .setCompressListener(new OnCompressListener() { //????????????
                            @Override
                            public void onStart() {
                                // TODO ???????????????????????????????????????????????? loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                // TODO ??????????????????????????????????????????????????????
                                uploadPortrait(file.getPath());
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO ????????????????????????????????????
                                mProgressDialog.dismiss();
                            }
                        }).launch();    //????????????
            }
        }
    }


    /**
     * ????????????
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
                    toast("?????????????????????" + e.getMessage());
                }
            }
            @Override
            public void onProgress(Integer value) {
                // ????????????????????????????????????
            }
        });
    }

}