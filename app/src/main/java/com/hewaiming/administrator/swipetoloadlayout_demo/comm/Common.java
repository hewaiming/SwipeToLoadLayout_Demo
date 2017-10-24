package com.hewaiming.administrator.swipetoloadlayout_demo.comm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hewaiming.administrator.swipetoloadlayout_demo.bean.AlarmRecord;
import com.hewaiming.administrator.swipetoloadlayout_demo.bean.City;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/23 0023.
 */


public class Common {
    //从网络取数据
    public static String netLink(String URL) {
        HttpClient httpClient = new DefaultHttpClient();
        //访问指定的服务器
        HttpGet httpGet = new HttpGet(URL);
        HttpResponse httpResponse = null;
        String response = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            //200说明请求和响应都是成功的
            HttpEntity entity = httpResponse.getEntity();
            try {
                response = EntityUtils.toString(entity, "utf-8");

                response = "[" + response.substring(0,response.length()-1) + "]";
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return response;
    }

    public static List<AlarmRecord> parseJsonWithGson(String jsonData) {
        Type listType = new TypeToken<ArrayList<AlarmRecord>>() {
        }.getType();
        Gson gson = new Gson();
        List<AlarmRecord> lists = gson.fromJson(jsonData, listType);
        System.out.println(lists);
        return lists;
    }

    public static String JsonToList(){
        Gson gson = new Gson();
        List<City> cityList = new ArrayList<City>();
        for (int i = 0; i < 3; i++) {
            City p = new City();
            p.setId(i + "");
            p.setName("name" + i);
            cityList.add(p);
        }
        String str = gson.toJson(cityList);
    return str;
    }
}
