package com.example.kangnamuniv;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;



import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://34.64.49.11/register";
    private Map<String, String> parameters;

    public RegisterRequest(String Rid, String Rpassword, String Rnickname, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<String, String>();
        parameters.put("uid", Rid);
        parameters.put("upw", Rpassword);
        parameters.put("unickname", Rnickname);

    }


    @Override
    public Map<String, String> getParams() {
        return parameters;
    }


}
