package com.coders.initiative.umoney;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kira on 8/28/2016.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private View decorView;
    private TextView btnRegister;
    private EditText editTextRegMobile, editTextRegUsername, editTextRegConfirmPassword, editTextRegPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        btnRegister = (TextView) findViewById(R.id.btnRegister);

        editTextRegMobile = (EditText) findViewById(R.id.registerMobile);
        editTextRegUsername = (EditText) findViewById(R.id.registerUsername);
        editTextRegPassword = (EditText) findViewById(R.id.registerPassword);
        editTextRegConfirmPassword = (EditText) findViewById(R.id.registerConfirmPassword);

        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnRegister:
                Toast.makeText(RegisterActivity.this, "Register", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
