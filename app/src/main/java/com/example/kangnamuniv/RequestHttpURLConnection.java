package com.example.kangnamuniv;

import static android.os.Build.ID;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import static com.example.kangnamuniv.MainActivity.id;
import static com.example.kangnamuniv.MainActivity.password;

public class RequestHttpURLConnection {

    public String request(String _url, ContentValues _params) {
        //HttpURLConnection 참조 변수
        HttpURLConnection urlConn = null;
        //URL 뒤에 붙여서 보낼 파라미터
        StringBuffer sbParams = new StringBuffer();

        if (_params == null)
            sbParams.append("uid="+id+"&upw="+password);
        else {
            boolean isAnd = false;
            String key;
            String value;

            for (Map.Entry<String, Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();

                if (isAnd)
                    sbParams.append("&");

                sbParams.append(key).append("=").append(value);

                if (!isAnd)
                    if (_params.size() >= 2)
                        isAnd = true;
            }
        }

        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setConnectTimeout(15000);

            urlConn.setReadTimeout(5000);

            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Accept-Charset", "UTF-8");
            urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencode");

            String strParams = sbParams.toString();
            OutputStream os = urlConn.getOutputStream();
            os.write(strParams.getBytes("UTF-8"));
            os.flush();
            os.close();

            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            // [2-4]. 읽어온 결과물 리턴.
            // 요청한 URL의 출력물을 BufferedReader로 받는다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            // 출력물의 라인과 그 합에 대한 변수.
            String line;
            String page = "";

            // 라인을 받아와 합친다.
            while ((line = reader.readLine()) != null){
                page += line;
            }

            return page;

        }  catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(urlConn != null)
                urlConn.disconnect();
        }
        return null;
    }
}
