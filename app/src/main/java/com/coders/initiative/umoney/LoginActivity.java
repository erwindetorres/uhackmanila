package com.coders.initiative.umoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kira on 8/28/2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private View decorView;
    private TextView btnForgotPassword;
    private Button btnLogin, btnSignUp, btnGuest;
    private EditText editTextUsername, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logint_activity);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignup);
        btnForgotPassword = (TextView) findViewById(R.id.btnForgotPassword);

        editTextUsername = (EditText) findViewById(R.id.loginUsername);
        editTextPassword = (EditText) findViewById(R.id.loginPassword);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                initLogin();
                break;
            case R.id.btnSignup:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btnForgotPassword:
                Toast.makeText(LoginActivity.this, "Forgot Password", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initLogin(){
        boolean isValid = true;
        String email = editTextUsername.getText().toString();
        //TEMP BYPASS
        /*if(!isValidPassword(email)){
            editTextUsername.setError(String.valueOf("Invalid Username"));
            isValid = false;
        }

        String password = editTextPassword.getText().toString();
        if(!isValidPassword(password)){
            editTextPassword.setError(String.valueOf("Invalid Password"));
            isValid = false;
        }*/

        if(isValid==true){
            try{
                //proceedLogin(email, password);
                proceedLogin();
            }catch (Exception e){
                //eat
            }
        }
    }

    private void proceedLogin(){
        Intent i =  new Intent(LoginActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 3) {
            return true;
        }
        return false;
    }

}
