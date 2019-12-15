/*
package com.he.quartz.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

*
 * @author heningm


@Slf4j
@Component
public class HttpSupport {

*
     * HTTP GET请求
     *
     * @param url url
     * @return response


    public String doGet(String url) {
        return doGet(url, null, null);
    }

*
     * HTTP GET请求
     *
     * @param url    url
     * @param params params
     * @return response


    public String doGet(String url, Map<String, String> params) {
        return doGet(url, params, null);
    }

*
     * HTTP GET请求
     *
     * @param url    url
     * @param params params
     * @return response


    public String doGet(String url, Map<String, String> params, Map<String, String> headers) {
        log.info("doGet url: {} params: {}", url, JSON.toJSONString(params));
        // 创建HTTP GET请求
        HttpGet httpGet = makeGetRequest(url, params, headers);
        // doRequest
        return doRequest(httpGet);
    }

    private HttpGet makeGetRequest(String url, Map<String, String> params, Map<String, String> headers) {
        HttpGet httpGet;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            builder.setCharset(StandardCharsets.UTF_8);
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            URI uri = builder.build();
            // 创建HTTP GET请求
            httpGet = new HttpGet(uri);
            // Headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }
            httpGet.setConfig(customRequestConfig());
        } catch (Exception e) {
          throw new RuntimeException();
        }
        return httpGet;
    }

*
     * HTTP POST请求
     *
     * @param url url
     * @return response


    public String doPost(String url) {
        return doPost(url, null);
    }

*
     * HTTP POST请求
     *
     * @param url    url
     * @param params params
     * @return response


    public String doPost(String url, Map<String, String> params) {
        return doPost(url, params, null);
    }

*
     * HTTP POST请求
     *
     * @param url     url
     * @param params  params
     * @param headers headers
     * @return response


    public String doPost(String url, Map<String, String> params, Map<String, String> headers) {
        log.info("doPost url: {} params: {} headers: {}", url, JSON.toJSONString(params),
                JSON.toJSONString(headers));
        // 创建Http Post请求
        HttpPost httpPost = makePostRequest(url, params, headers);
        // doRequest
        return doRequest(httpPost);
    }

    private HttpPost makePostRequest(String url, Map<String, String> params, Map<String, String> headers) {
        HttpPost httpPost;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            builder.setCharset(StandardCharsets.UTF_8);
            URI uri = builder.build();
            // 创建Http Post请求
            httpPost = new HttpPost(uri);
            httpPost.setConfig(customRequestConfig());

            // 创建参数列表
            if (params != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8);
                httpPost.setEntity(entity);
            }
            // Headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return httpPost;
    }

*
     * HTTP POST请求
     *
     * @param url    url
     * @param params params
     * @return response


    public String doPostFormData(String url, Map<String, String> params) {
        return doPostFormData(url, params, null);
    }

*
     * HTTP POST请求
     *
     * @param url    url
     * @param params params
     * @param files  files
     * @return response


    public String doPostFormData(String url, Map<String, String> params, Map<String, File> files) {
        log.info("doPostFormData url: {} params: {}", url, JSON.toJSONString(params));
        // 创建Http Post请求
        HttpPost httpPost = makePostFormDataRequest(url, params, files);
        // doRequest
        return doRequest(httpPost);
    }

    private HttpPost makePostFormDataRequest(String url, Map<String, String> params, Map<String, File> files) {
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(customRequestConfig());

            // 创建参数列表
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create().setCharset(
                    StandardCharsets.UTF_8);
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    entityBuilder.addPart(entry.getKey(), new StringBody(entry.getValue(),
                            ContentType.TEXT_PLAIN.withCharset(StandardCharsets.UTF_8)));
                }
            }
            // 文件参数
            if (files != null) {
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    entityBuilder.addBinaryBody(entry.getKey(), entry.getValue());
                }
            }
            httpPost.setEntity(entityBuilder.build());
            return httpPost;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

*
     * HTTP POST请求
     *
     * @param url  url
     * @param json json
     * @return response


    public String doPostJson(String url, String json) {
        return doPostJson(url, null, json);
    }

*
     * HTTP POST请求
     *
     * @param url    url
     * @param params params
     * @param json   json
     * @return response


    public String doPostJson(String url, Map<String, String> params, String json) {
        log.info("doPostJson url: {} params: {} json: {}", url, JSON.toJSONString(params), json);
        // 构建请求
        HttpPost httpPost = makePostJsonRequest(url, params, json);
        // doRequest
        return doRequest(httpPost);
    }

    private HttpPost makePostJsonRequest(String url, Map<String, String> params, String json) {
        HttpPost httpPost;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            builder.setCharset(StandardCharsets.UTF_8);
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            URI uri = builder.build();
            // 创建Http Post请求
            httpPost = new HttpPost(uri);
            httpPost.setConfig(customRequestConfig());

            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return httpPost;
    }

    private RequestConfig customRequestConfig() {
        return RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(
                5000).setConnectionRequestTimeout(5000).build();
    }

    private String doRequest(HttpUriRequest request) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // execute
        int status;
        String result;
        try (CloseableHttpResponse resp = httpClient.execute(request)) {
            // 处理status
            status = resp.getStatusLine().getStatusCode();
            // 处理请求结果
            result = EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        if (status != HttpStatus.SC_OK) {
            log.error("remote request faild. url: {}, response: {}", request.getURI().toString(), result);
            throw new RuntimeException(ErrorMessage.RESOPNSE_ERROR + " " + status);
        }
        return result;
    }

}
*/
