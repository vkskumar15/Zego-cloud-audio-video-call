package com.booking.zegocloud;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

public class ProfileActivity extends AppCompatActivity {
    ZegoSendCallInvitationButton callBtn;
    TextView caller;
    EditText targetuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        caller = findViewById(R.id.username);
        targetuser = findViewById(R.id.usertext);

        callBtn = findViewById(R.id.callbtn);
        caller.setText("You are:" + getIntent().getStringExtra("caller"));

        targetuser.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ProfileActivity profile = ProfileActivity.this;
                startVideoCall(targetuser.getText().toString().trim());
            }

            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void startVideoCall(String targetUserId) {
        callBtn.setIsVideoCall(true); // if true then video call else audio call
        callBtn.setResourceID("zego_uikit_call");
        callBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserId, targetUserId)));
    }

    public void startRecording() {


        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://cloudrecord-api.zego.im/?Action=StartRecord&AppId=1876609586&SignatureNonce=15215528852396&Timestamp=" + (System.currentTimeMillis() / 1000) + "&Signature=ae7967b4838703f3b324ce6b2881747562448f26aae01810d5f6f95f86624a86&SignatureVersion=2.0&IsTest=true").openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseData = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        break;
                    }
                    responseData.append(line);
                }
                reader.close();
                Log.d("API Response", responseData.toString());
            } else {
                Log.e("API Request Failed", "Response Code: " + responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}