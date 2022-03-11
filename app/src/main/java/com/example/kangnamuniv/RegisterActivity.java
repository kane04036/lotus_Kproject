package com.example.kangnamuniv;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    public EditText edtRegID, edtRegPW, edtRegPWcheck, edtRegNickname;
    public Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtRegID = findViewById(R.id.edtRegID);
        edtRegPW = findViewById(R.id.edtRegPW);
        edtRegNickname = findViewById(R.id.edtRegPWcheck);
        edtRegNickname = findViewById(R.id.edtRegNickname);
        btnRegister = findViewById(R.id.btnRegister);



    }
}
