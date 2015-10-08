package com.appnext.fidelio.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appnext.fidelio.ChattitApplication;
import com.example.manfredi.chattit.R;
import com.parse.LogInCallback;

import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends ActionBarActivity {

    protected TextView mSignUpTextView;

    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mLoginBotton;
    protected ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mProgressBar = (ProgressBar) findViewById(R.id.loginProgressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mSignUpTextView = (TextView) findViewById(R.id.signupText);
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        }); mUsername = (EditText) findViewById(R.id.usernameField);
        mPassword = (EditText) findViewById(R.id.passwordField);
        mLoginBotton = (Button) findViewById(R.id.loginButton);
        mLoginBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                username = username.trim();
                password = password.trim();

                if (username.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(getString(R.string.login_error_message))
                            .setTitle(getString(R.string.signup_error_title))
                            .setPositiveButton("ok", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    // Login
                    mProgressBar.setVisibility(View.VISIBLE);

                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            mProgressBar.setVisibility(View.INVISIBLE);

                            if (e == null) {
                                // Success!
                                ChattitApplication.updateParseInstallation(user);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle(getString(R.string.signup_error_title))
                                        .setPositiveButton("ok", null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        }
                    });
                }
            }
        });
    }
}