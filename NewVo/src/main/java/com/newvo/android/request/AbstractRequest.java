package com.newvo.android.request;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 4/4/2014.
 */
abstract class AbstractRequest {

    final static String GET = "get";
    final static String POST = "post";
    final static String DELETE = "delete";
    final static String PATCH = "patch";

    static String authKey = "jJZxrA4NMpiCyyFgFTiXWh0VtV71aXN7";
    static String website = "http://newvo.herokuapp.com";

    private final ServiceHandler service = new ServiceHandler();

    private final String requestPattern;
    private final String requestType;

    private String urlData;

    private Map<String, String> params = new HashMap<String, String>();

    AbstractRequest(String requestPattern, String requestType){
        this.requestPattern = requestPattern;
        this.requestType = requestType;

    }


    void addParam(String name, String value){
        params.put(name, value);
    }

    void setUrlData(String urlData) {
        this.urlData = urlData;
    }

    String makeRequest(){
        return service.makeServiceCall(website + requestPattern + urlData, requestType, getParams());
    }

    private List<NameValuePair> getParams(){
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for(Map.Entry<String, String> param : params.entrySet()){
            nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        return nameValuePairs;
    }
}
