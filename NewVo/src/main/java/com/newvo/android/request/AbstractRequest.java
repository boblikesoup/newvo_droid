package com.newvo.android.request;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;
import com.newvo.android.NewVo;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 4/4/2014.
 */
abstract class AbstractRequest {

    final static String GET = "GET";
    final static String DELETE = "DELETE";
    final static String PATCH = "PATCH";

    static String authKey = "jJZxrA4NMpiCyyFgFTiXWh0VtV71aXN7";
    static String website = "http://newvo.herokuapp.com";

    private final String requestPattern;
    private final String requestType;

    private String urlData;

    private Map<String, String> params = new HashMap<String, String>();

    AbstractRequest(String requestPattern, String requestType){
        this.requestPattern = requestPattern;
        this.requestType = requestType;
        addUrlParam("newvo_token", authKey);

    }


    void addUrlParam(String name, String value){
        params.put(name, value);
    }

    void setUrlData(String urlData) {
        this.urlData = urlData;
    }

    void makeRequest(Class clazz, FutureCallback callback){
        String url = website;
        if(requestPattern != null){
            url += requestPattern;
        }
        if(urlData != null){
            url += urlData;
        }

        LoadBuilder<Builders.Any.B> builder = Ion.with(NewVo.CONTEXT);
        Builders.Any.B load;
        //Add URL params
        List<NameValuePair> params = getParams();
        if (params != null && !params.isEmpty()) {
            String paramString = URLEncodedUtils
                    .format(params, "utf-8");
            url += "?" + paramString;
        }
        load = builder.load(requestType, url);

        //Add miscellaneous data. Used by PostRequest
        addMiscData(load);

        if(clazz != null){
            load.as(clazz).setCallback(callback);
        } else {
            load.asString().setCallback(callback);
        }

    }

    void addMiscData(Builders.Any.B load) {
        //Do nothing. Override in PostRequest.
    }

    private List<NameValuePair> getParams(){
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for(Map.Entry<String, String> param : params.entrySet()){
            nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        return nameValuePairs;
    }
}
