package acc;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.io.IOException;

/**
 * @Auther: Lee
 * @Date 2020/6/18 11:47
 * @Description:
 */
public class HttpUtil {

    private static CloseableHttpClient httpClient;

    private static int HTTP_SUCCESS_CODE = 200;
    private static int HTTP_PARAM_ERROR_CODE = 400;
    private static int HTTP_PATH_ERROR_CODE = 404;
    private static int HTTP_WAY_ERROR_CODE = 405;
    private static int HTTP_SERVICE_ERROR_CODE = 500;


//    /**
//     * 获取HttpClient对象
//     *
//     * @return HttpClient
//     */
//    private static HttpClient getHttpClient() {
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setConnectionManager(httpClientPool)
//                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")//浏览器版本
//                .setRetryHandler(new DefaultHttpRequestRetryHandler(2, true))//请求超时后重发2次
//                .build();
//
//        return httpClient;
//    }


//    public static HttpResponse doGet(String uri, Cookie cookie) throws IOException {
//        HttpGet httpGet = new HttpGet(uri);
//        httpGet.setHeader("Cookie", cookie.getValue());
//        return doMethod(httpGet);
//    }

    public static JsonResult doGet(String url, Cookie cookie) {
        httpClient = HttpClientBuilder.create().build();
        Object object = null;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Cookie", cookie.getValue());
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HTTP_SUCCESS_CODE) {
                object = EntityUtils.toString(httpResponse.getEntity());
            } else {
                return JsonResult.failResult("请求失败，状态码：" + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResult.successResult(object);
    }

    public static void closeHttpRequest(DefaultHttpClient defaultHttpClient) {
        if (defaultHttpClient != null) {
            defaultHttpClient.close();
        }
    }
}
