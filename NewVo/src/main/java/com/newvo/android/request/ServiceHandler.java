package com.newvo.android.request;

import com.newvo.android.util.HttpPatch;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static com.newvo.android.request.AbstractRequest.*;

/**
 * Created by David on 4/4/2014.
 */
public class ServiceHandler {

    private String response = null;

    public ServiceHandler() {

    }

    public String makeServiceCall(String url, String method, NameValuePair... params){
        return makeServiceCall(url, method, Arrays.asList(params));
    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public static String makeServiceCall(String url, String method,
                                  List<NameValuePair> params) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null && !params.isEmpty()) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                if (params != null && !params.isEmpty()) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            } else if(method == DELETE){
                // appending params to url
                if (params != null && !params.isEmpty()) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpDelete httpDelete = new HttpDelete(url);

                httpResponse = httpClient.execute(httpDelete);
            } else if(method == PATCH){
                // appending params to url
                if (params != null && !params.isEmpty()) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpPatch httpPatch = new HttpPatch(url);

                httpResponse = httpClient.execute(httpPatch);
            }
            httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
