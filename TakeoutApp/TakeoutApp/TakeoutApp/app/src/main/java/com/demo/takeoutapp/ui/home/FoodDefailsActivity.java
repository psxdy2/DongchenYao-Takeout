package com.demo.takeoutapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.adapter.HomeCommentAdapter;
import com.demo.takeoutapp.adapter.HomeFoodAdapter;
import com.demo.takeoutapp.adapter.HomeTypeAdapter;
import com.demo.takeoutapp.adapter.TypeAdapter;
import com.demo.takeoutapp.data.GWCbean;
import com.demo.takeoutapp.data.TakeComment;
import com.demo.takeoutapp.data.TakeFoods;
import com.demo.takeoutapp.data.TakeFoodtype;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.data.UserinfoComment;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.ui.person.LoginActivity;
import com.demo.takeoutapp.ui.store.MyStoreActivity;
import com.demo.takeoutapp.ui.store.TypeActivity;
import com.demo.takeoutapp.util.Constant;
import com.kproduce.roundcorners.RoundButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shaohui.bottomdialog.BottomDialog;
import okhttp3.Call;

import static com.demo.takeoutapp.util.ToastUtil.toast;

/**
 * 食物详情
 */
public class FoodDefailsActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private ImageView image;
    private TextView storename;
    private RoundButton collection;
    private TextView item1;
    private TextView item2;
    private TextView item3;
    private LinearLayout card1;
    private ListView types;
    private ListView foods;
    private LinearLayout card2;
    private ListView comment;
    private LinearLayout card3;
    private ImageView gwc;
    private TextView money;
    private TextView settlement;

    //总金额
    public Double SumMoney = 0.00;
    public Map<Integer, String> caches = new HashMap<>();
    //选中的菜品集合
    public static List<GWCbean> gwCbeans = new ArrayList<>();
    //收藏id
    private String collectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_defails);
        gwCbeans.clear();
        initView();
        getTypes();
        EqCollection();
        getAllComment();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        image = (ImageView) findViewById(R.id.image);
        storename = (TextView) findViewById(R.id.storename);
        collection = (RoundButton) findViewById(R.id.collection);
        item1 = (TextView) findViewById(R.id.item1);
        item2 = (TextView) findViewById(R.id.item2);
        item3 = (TextView) findViewById(R.id.item3);
        card1 = (LinearLayout) findViewById(R.id.card1);
        types = (ListView) findViewById(R.id.types);
        foods = (ListView) findViewById(R.id.foods);
        card2 = (LinearLayout) findViewById(R.id.card2);
        comment = (ListView) findViewById(R.id.comment);
        card3 = (LinearLayout) findViewById(R.id.card3);
        gwc = (ImageView) findViewById(R.id.gwc);
        money = (TextView) findViewById(R.id.money);
        settlement = (TextView) findViewById(R.id.settlement);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText(getIntent().getStringExtra("storename") + "店铺");
        Glide.with(this).load(getIntent().getStringExtra("storeimage")).into(image);
        //intent.putExtra("id",takeUserinfo.getId().toString());
        //getIntent().getStringExtra("storename")
        storename.setText(getIntent().getStringExtra("storename"));

        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        gwc.setOnClickListener(this);
        settlement.setOnClickListener(this);
        collection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collection:
                if (collection.getText().equals("取消收藏")) {
                    removeCollection();
                } else {
                    addCollection();
                }
                break;
            case R.id.item1:
                SelectItem(0);
                break;
            case R.id.item2:
                SelectItem(1);
                break;
            case R.id.item3:
                SelectItem(2);
                break;
            case R.id.gwc:
                //showGWC();
                break;
            case R.id.settlement:
                //结算
                if (gwCbeans.size() == 0){
                    toast("请选择菜品");
                    return;
                }
                Intent intent = new Intent(FoodDefailsActivity.this, SettlementActivity.class);
                intent.putExtra("summoney", money.getText().toString());
                //店铺id
                intent.putExtra("id",getIntent().getStringExtra("id"));
                //user id
                intent.putExtra("userid",getIntent().getStringExtra("userid"));
                startActivity(intent);
                break;
        }
    }

    /**
     * 选item
     */
    private void SelectItem(int index) {
        card1.setVisibility(index == 0 ? View.VISIBLE : View.GONE);
        card2.setVisibility(index == 1 ? View.VISIBLE : View.GONE);
        card3.setVisibility(index == 2 ? View.VISIBLE : View.GONE);
        item1.setTextColor(index == 0 ? 0xfffa4169 : 0xff333333);
        item2.setTextColor(index == 1 ? 0xfffa4169 : 0xff333333);
        item3.setTextColor(index == 2 ? 0xfffa4169 : 0xff333333);
    }

    /**
     * 加载店铺下的所有菜品分类
     */
    private void getTypes() {
        show();
        MyHttpUtil.getInstance().GET(Apis.SearchFoodTypes + "?id=" + getIntent().getStringExtra("id"), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<TakeFoodtype> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        TakeFoodtype takeFoodtype = new TakeFoodtype();
                        takeFoodtype.setId(array.getJSONObject(i).getInt("id"));
                        takeFoodtype.setStoreid(array.getJSONObject(i).getInt("storeid"));
                        takeFoodtype.setTypename(array.getJSONObject(i).getString("typename"));
                        list.add(takeFoodtype);
                    }
                    initTypeAadapter(list);
                    if (list.size() > 0) {
                        getFoods(list.get(0).getId());
                    }
                }
            }
        });
    }

    private void initTypeAadapter(List<TakeFoodtype> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HomeTypeAdapter adapter = new HomeTypeAdapter(FoodDefailsActivity.this, list);
                adapter.setCallback(new HomeTypeAdapter.OperationCallback() {
                    @Override
                    public void call(TakeFoodtype takeFoodtype) {
                        getFoods(takeFoodtype.getId());
                    }
                });
                types.setAdapter(adapter);
            }
        });
    }

    /**
     * 查询指定分类下的所有菜品
     */
    private void getFoods(int id) {
        show();
        MyHttpUtil.getInstance().GET(Apis.SearchFoods + "?id=" + id, new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<TakeFoods> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        TakeFoods takeFoodtype = new TakeFoods();
                        takeFoodtype.setId(array.getJSONObject(i).getInt("id"));
                        takeFoodtype.setFoodimage(array.getJSONObject(i).getString("foodimage"));
                        takeFoodtype.setFoodname(array.getJSONObject(i).getString("foodname"));
                        takeFoodtype.setFoodinfo(array.getJSONObject(i).getString("foodinfo"));
                        takeFoodtype.setFoodprice(array.getJSONObject(i).getString("foodprice"));
                        takeFoodtype.setFoodnum(array.getJSONObject(i).getInt("foodnum"));
                        list.add(takeFoodtype);
                    }
                    initFoodAadapter(list);
                }
            }
        });
    }

    private void initFoodAadapter(List<TakeFoods> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HomeFoodAdapter adapter = new HomeFoodAdapter(FoodDefailsActivity.this, list);
                foods.setAdapter(adapter);
            }
        });
    }


    /**
     * 加载评价
     */
    private void getAllComment(){
        show();
        MyHttpUtil.getInstance().GET(Apis.getAllComment + "?id=" + getIntent().getStringExtra("id"), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<UserinfoComment> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        UserinfoComment userinfoComment = new UserinfoComment();
                        userinfoComment.setStoreid(array.getJSONObject(i).getInt("storeid"));
                        userinfoComment.setUsername(array.getJSONObject(i).getString("username"));
                        userinfoComment.setComment(array.getJSONObject(i).getString("comment"));
                        userinfoComment.setPortrait(array.getJSONObject(i).getString("portrait"));
                        userinfoComment.setAccount(array.getJSONObject(i).getString("account"));
                        list.add(userinfoComment);
                    }
                    initCommentAadapter(list);
                }
            }
        });
    }


    private void initCommentAadapter(List<UserinfoComment> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HomeCommentAdapter adapter = new HomeCommentAdapter(FoodDefailsActivity.this, list);
                comment.setAdapter(adapter);
            }
        });
    }


    /**
     * 添加收藏 取消收藏
     */
    private void addCollection() {
        show();
        JSONObject req = new JSONObject();
        try {
            req.put("userid", Constant.userinfo.getId());
            req.put("storeid", getIntent().getStringExtra("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.addCollection, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    collection.setText("取消收藏");
                    collectionId = String.valueOf(rep.getJSONObject("data").getInt("id"));
                } else {
                    collection.setText("收藏");
                }
                toast(rep.getString("message"), FoodDefailsActivity.this);
            }
        });
    }

    /**
     * 取消收藏
     */
    private void removeCollection() {
        show();
        JSONObject req = new JSONObject();
        try {
            req.put("id", collectionId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.removeCollection, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    collection.setText("收藏");
                } else {
                    collection.setText("取消收藏");
                }
                toast(rep.getString("message"), FoodDefailsActivity.this);
            }
        });
    }

    /**
     * 检查是否收藏
     */
    private void EqCollection() {
        show();
        JSONObject req = new JSONObject();
        try {
            req.put("userid", Constant.userinfo.getId());
            req.put("storeid", getIntent().getStringExtra("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.SearchCollection, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject rep = new JSONObject(res);
                            collection.setEnabled(true);
                            if (rep.getInt("code") == 200) {
                                collection.setText("取消收藏");
                                collectionId = String.valueOf(rep.getJSONObject("data").getInt("id"));
                            } else {
                                collection.setText("收藏");
                            }
                            toast(rep.getString("message"), FoodDefailsActivity.this);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    /**
     * 更新金额
     */
    public void reshMoney() {
        money.setText("¥" + SumMoney.toString());
    }

    /**
     * 底部购物车弹出暂时不开放
     */
    private void showGWC() {
        BottomDialog.create(getSupportFragmentManager())
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        ListView gwclist = v.findViewById(R.id.gwc_list);

                    }
                })
                .setLayoutRes(R.layout.dialog_gwc)
                .setDimAmount(0.1f)            // Dialog window dim amount(can change window background color）, range：0 to 1，default is : 0.2f
                .setCancelOutside(false)     // click the external area whether is closed, default is : true
                .setTag("购物车")     // setting the DialogFragment tag
                .show();
    }
}