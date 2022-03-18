package com.caipiao.live.common.util;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class SendPostMethod {

	//模拟表单请求
	public static String methodPost(String url, NameValuePair[] data){
        String response= "";
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", 
               PostMethod.FORM_URL_ENCODED_CONTENT_TYPE + "; charset=UTF-8");
        postMethod.setRequestBody(data);
        int statusCode = 0;
        try {
            statusCode = httpClient.executeMethod(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
            Header locationHeader = postMethod.getResponseHeader("location");
            String location = null;
            if (locationHeader != null) {
                location = locationHeader.getValue();
                System.out.println("The page was redirected to:" + location);
                response= methodPost(location,data);
            } else {
                System.err.println("Location field value is null.");
            }
        } else {
            System.out.println(postMethod.getStatusLine());
            try {
                response= postMethod.getResponseBodyAsString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            postMethod.releaseConnection();
        }
        return response;
    }

	
	public static String methodGet(String url, NameValuePair[] data){
		String response= "";
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        int statusCode = 0;
        try {
            statusCode = httpClient.executeMethod(getMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
            Header locationHeader = getMethod.getResponseHeader("location");
            String location = null;
            if (locationHeader != null) {
                location = locationHeader.getValue();
                System.out.println("The page was redirected to:" + location);
                response= methodPost(location,data);
            } else {
                System.err.println("Location field value is null.");
            }
        } else {
            System.out.println(getMethod.getStatusLine());
            try {
                response= getMethod.getResponseBodyAsString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getMethod.releaseConnection();
        }
        return response;
	}
}
