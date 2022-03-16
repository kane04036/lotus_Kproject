package com.example.kangnamuniv;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NicknameValidateRequest extends StringRequest {

    final static private String URL = "http://34.64.49.11/chnickname";
    private Map<String, String> parameters;

    public NicknameValidateRequest(String Rnickname, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<String, String>();
        parameters.put("nickname", Rnickname);

    }


    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
