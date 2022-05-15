package com.lotus.kangnamuniv;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;



import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = "http://34.64.49.11/login";
    private Map<String, String> parameters;

    public LoginRequest(String Rid, String Rpassword, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<String, String>();
        parameters.put("uid", Rid);
        parameters.put("upw", Rpassword);
    }


    @Override
    public Map<String, String> getParams() {
        return parameters;
    }


}
