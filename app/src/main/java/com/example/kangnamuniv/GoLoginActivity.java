package com.example.kangnamuniv;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GoLoginActivity extends AppCompatActivity {
    public EditText edtID;
    public EditText edtPW;
    public Button btnLogin, btnGoRegister;
    public static String id, password;
    public TextView tvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtID = findViewById(R.id.edtID);
        edtPW = findViewById(R.id.edtPW);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);
        tvResult = findViewById(R.id.tvResult);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = edtID.getText().toString();
                password = edtPW.getText().toString();
                String url = "https://sample-api-niksw.run.goorm.io/login?uid=아이디값&upw=비밀번호값";

                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

            }
        });


    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            tvResult.setText(s);
        }
    }
}
