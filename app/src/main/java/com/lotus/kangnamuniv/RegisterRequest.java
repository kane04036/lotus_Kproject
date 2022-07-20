package com.lotus.kangnamuniv;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;



import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "https://k-project-jgukj.run.goorm.io/register";
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
