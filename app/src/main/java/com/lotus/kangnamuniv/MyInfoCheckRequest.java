package com.lotus.kangnamuniv;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import java.util.HashMap;
import java.util.Map;

public class MyInfoCheckRequest extends StringRequest {

    final static private String URL = "https://k-project-jgukj.run.goorm.io/lectures";
    private Map<String, String> parameters;

    public MyInfoCheckRequest(String schoolID, String schoolPW, Response.Listener<String> listener) {
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
