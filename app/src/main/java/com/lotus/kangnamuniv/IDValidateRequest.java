package com.lotus.kangnamuniv;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;



import java.util.HashMap;
import java.util.Map;

public class IDValidateRequest extends StringRequest {

    final static private String URL = "https://k-project-jgukj.run.goorm.io/chid";
    private Map<String, String> parameters;

    public IDValidateRequest(String Rid, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<String, String>();
        parameters.put("id", Rid);




    }


    @Override
    public Map<String, String> getParams() {
        return parameters;
    }


}
