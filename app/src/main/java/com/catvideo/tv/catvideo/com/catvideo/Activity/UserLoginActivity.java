package com.catvideo.tv.catvideo.com.catvideo.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.catvideo.tv.catvideo.R;
import com.catvideo.tv.catvideo.com.catvideo.net.Common;
import com.catvideo.tv.catvideo.com.catvideo.net.NetUtil;
import com.catvideo.tv.catvideo.com.catvideo.user.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lcc on 2016/6/3.
 */

public class UserLoginActivity extends AppCompatActivity {
    EditText etUserEmail, etPassword;
    Button btLogin, btRegister;
    ProgressDialog progressDialog;
    String url;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_login);
        findViews();
        initOnclick();
    }

    private void findViews() {
        etUserEmail = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassWord);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegister = (Button) findViewById(R.id.btRegister);
    }

    private void initOnclick() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.checkNetState(UserLoginActivity.this)) {
                    user = new User(etUserEmail.getText().toString(), etPassword.getText().toString());
                    url = Common.URL + "User_CatVideoServlet";
//                    Toast.makeText(UserLoginActivity.this, "sasas", Toast.LENGTH_SHORT).show();
                    new UserLoginTask().execute(url);//获取登录
                }
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private class UserLoginTask extends AsyncTask<String, Integer, User> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UserLoginActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected User doInBackground(String... params) {
            String url = params[0];
            String jsonIn;
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "userlogin");
            jsonObject.addProperty("user", gson.toJson(user));
            try {
                jsonIn = getRemoteData(url, jsonObject.toString());
            } catch (IOException e) {
                Log.e("userLoginactity", e.toString());
                return null;
            }
            return gson.fromJson(jsonIn, User.class);
        }

        @Override
        protected void onPostExecute(User user) {
            progressDialog.cancel();
            if (user.isState()) {
                Intent intent = new Intent();
                intent.putExtra("user", user);
                setResult(1, intent);
                finish();
            } else {
                Toast.makeText(UserLoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getRemoteData(String url, String jsonOut) throws IOException {
        StringBuilder jsonIn = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setDoInput(true); // allow inputs
        connection.setDoOutput(true); // allow outputs
        connection.setUseCaches(false); // do not use a cached copy
        connection.setRequestMethod("POST");
        connection.setRequestProperty("charset", "UTF-8");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        bw.write(jsonOut);
        Log.d("userLoginactity", "jsonOut: " + jsonOut);
        bw.close();

        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                jsonIn.append(line);
            }
        }
        connection.disconnect();
        Log.d("userLoginactity", "jsonIn: " + jsonIn);
        return jsonIn.toString();
    }
}
