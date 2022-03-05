package com.demo.takeoutapp.ui.order;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.adapter.OrderAdapter;
import com.demo.takeoutapp.adapter.TypeAdapter;
import com.demo.takeoutapp.data.TakeFoodtype;
import com.demo.takeoutapp.data.TakeOrder;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.person.RegisterActivity;
import com.demo.takeoutapp.ui.store.MyStoreActivity;
import com.demo.takeoutapp.ui.store.TypeActivity;
import com.demo.takeoutapp.util.Constant;
import com.dhh.websocket.RxWebSocket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

import static com.demo.takeoutapp.util.ToastUtil.toast;

public class FragmentOrder extends Fragment {
    private View view;
    private ListView orderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_order, container, false);
        initView();
        getAllOrder();
        return view;
    }

    private void initView() {
        orderList = (ListView) view.findViewById(R.id.order_list);
    }

    /**
     * 请求所有用户订单数据
     */
    private void getAllOrder() {
        MyHttpUtil.getInstance().GET(Apis.getAllOrder + "?id=" + Constant.userinfo.getId(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {

            }

            @Override
            public void Success(Call call, String res) throws Exception {
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<TakeOrder> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        TakeOrder takeOrder = new TakeOrder();
                        takeOrder.setId(array.getJSONObject(i).getInt("orderid"));
                        takeOrder.setUserid(array.getJSONObject(i).getInt("userid"));
                        takeOrder.setStoreuserid(array.getJSONObject(i).getInt("storeuserid"));
                        takeOrder.setStoreid(array.getJSONObject(i).getInt("storeid"));
                        takeOrder.setAddress(array.getJSONObject(i).getString("address"));
                        takeOrder.setPrice(array.getJSONObject(i).getString("price"));
                        takeOrder.setStatus(array.getJSONObject(i).getString("status"));
                        takeOrder.setFoods(array.getJSONObject(i).getString("foods"));
                        takeOrder.setCreatedtime(array.getJSONObject(i).getString("createdtime"));
                        takeOrder.setSendtime(array.getJSONObject(i).getString("sendtime"));
                        String temp = array.getJSONObject(i).getString("deliveryinfo");
                        if (!(temp.equals("null"))) {
                            takeOrder.setDeliveryinfo(temp);
                            JSONObject info = new JSONObject(temp);
                            if (info.getString("account").equals(Constant.userinfo.getAccount())) {
                                list.add(takeOrder);
                            }
                        }
                        list.add(takeOrder);
                    }
                    initAadapter(list);
                }
            }
        });
    }

    private void initAadapter(List<TakeOrder> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                OrderAdapter adapter = new OrderAdapter(getContext(), list);
                adapter.setCallback(new OrderAdapter.OperationCallback() {
                    @Override
                    public void confirm(TakeOrder takeOrder) {
                        updateOrder(takeOrder.getId().toString(), Constant.WAITCOMMENT);
                    }

                    @Override
                    public void comment(TakeOrder takeOrder) {
                        showInputDialog(takeOrder.getId().toString(), takeOrder.getStoreid().toString());
                    }

                    @Override
                    public void cancel(TakeOrder takeOrder) {
                        updateOrder(takeOrder.getId().toString(), Constant.COMPELE);
                        JSONObject json = new JSONObject();
                        try {
                            json.put("info","你有订单完成退款了");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String msg = "{\"type\":2,\"to\":\""+takeOrder.getStoreuserid()+"\",\"msg\":"+json+"}";
                        RxWebSocket.send(Constant.socketurl+Constant.userinfo.getId(), msg);
                    }
                });
                orderList.setAdapter(adapter);
            }
        });
    }

    private void showInputDialog(String id, String storeid) {
        final EditText editText = new EditText(getContext());
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(getContext());
        inputDialog.setTitle("请输入评价").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addComment(id, storeid, editText.getText().toString());
                    }
                }).show();
    }

    /**
     * 获取当前时间
     */
    private String getSendTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(java.util.Calendar.HOUR_OF_DAY, 0);
        return sdf.format(Cal.getTime());
    }

    /**
     * 修改订单状态
     */
    private void updateOrder(String id, String status) {
        JSONObject req = new JSONObject();
        try {
            req.put("id", id);
            req.put("status", status);
            req.put("sendtime", getSendTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.updateOrder, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {

            }

            @Override
            public void Success(Call call, String res) throws Exception {
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    getAllOrder();
                }
                toast(rep.getString("message"), getActivity());
            }
        });
    }

    /**
     * 增加一条评价
     */
    private void addComment(String orderid, String storeid, String comment) {
        JSONObject req = new JSONObject();
        try {
            req.put("userid", Constant.userinfo.getId());
            req.put("orderid", orderid);
            req.put("storeid", storeid);
            req.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.addComment, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {

            }

            @Override
            public void Success(Call call, String res) throws Exception {
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    updateOrder(orderid, Constant.COMPELE);
                }
                toast(rep.getString("message"), getActivity());
            }
        });
    }

}
