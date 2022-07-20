package com.lotus.kangnamuniv;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NicknameValidateRequest extends StringRequest {

    final static private String URL = "https://k-project-jgukj.run.goorm.io/chnickname";
    private Map<String, String> parameters;

    public NicknameValidateRequest(String Rnickname, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<String, String>();
        parameters.put("nickname", Rnickname);
        Log.d("test", "NicknameValidateRequest: " + Rnickname);

    }


    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
