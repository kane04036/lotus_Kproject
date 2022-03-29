package com.example.kangnamuniv;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyInfoCheckRequest extends StringRequest {

    final static private String URL = "http://34.64.49.11/lectures";
    private Map<String, String> parameters;

    public MyInfoCheckRequest(String schoolID, String schoolPW, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<String, String>();
        parameters.put("id", schoolID);
        parameters.put("pw", schoolPW);
    }


    @Override
    public Map<String, String> getParams() {
        return parameters;
    }


}
