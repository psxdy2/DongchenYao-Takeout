package com.demo.takeoutapp.ui.person;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.app.MyApplication;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.ui.store.AddFoodActivity;
import com.demo.takeoutapp.util.Constant;
import com.demo.takeoutapp.util.FileUtil;
import com.demo.takeoutapp.util.GlideEngine;
import com.kproduce.roundcorners.RoundButton;
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

public class PersonInfoActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private ImageView portrait;
    private EditText username;
    private EditText sex;
    private EditText brithday;
    private RoundButton next;

    private String PortraitUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        portrait = (ImageView) findViewById(R.id.portrait);
        username = (EditText) findViewById(R.id.username);
        sex = (EditText) findViewById(R.id.sex);
        brithday = (EditText) findViewById(R.id.brithday);
        next = (RoundButton) findViewById(R.id.next);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("????????????");

        PortraitUrl = Constant.userinfo.getPortrait();
        Glide.with(PersonInfoActivity.this).load(PortraitUrl).into(portrait);
        username.setText(Constant.userinfo.getUsername());
        sex.setText(Constant.userinfo.getSex());
        brithday.setText(Constant.userinfo.getBirthday());

        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPhoto();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PortraitUrl)) {
                    toast("??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(username.getText())) {
                    toast("??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(sex.getText())) {
                    toast("??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(brithday.getText())) {
                    toast("??????????????????");
                    return;
                }
                updateInfo();
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
                                dismiss();
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
                dismiss();
                if (e == null) {
                    Glide.with(PersonInfoActivity.this).load(bmobFile.getFileUrl()).into(portrait);
                    PortraitUrl = bmobFile.getFileUrl();
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


    /**
     * ??????????????????
     */
    private void updateInfo(){
        show();
        JSONObject req = new JSONObject();
        try {
            req.put("id", Constant.userinfo.getId());
            req.put("username", username.getText().toString());
            req.put("portrait", PortraitUrl);
            req.put("sex", sex.getText().toString());
            req.put("birthday", brithday.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.UpdateUserInfo, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    Constant.userinfo.setUsername(username.getText().toString());
                    Constant.userinfo.setPortrait(PortraitUrl);
                    Constant.userinfo.setSex(sex.getText().toString());
                    Constant.userinfo.setBirthday(brithday.getText().toString());
                    List<TakeUserinfo> userinfos = MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().loadAll();
                    if (userinfos.size() != 0) {
                        MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().deleteAll();
                    }
                    MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().save(Constant.userinfo);
                    finish();
                }
                toast(rep.getString("message"), PersonInfoActivity.this);
            }
        });
    }
}