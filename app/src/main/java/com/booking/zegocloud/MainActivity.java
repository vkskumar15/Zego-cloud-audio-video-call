package com.booking.zegocloud;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig;
import com.zegocloud.uikit.prebuilt.call.config.ZegoHangUpConfirmDialogInfo;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        button.setOnClickListener(v -> {
            MainActivity mainActivity = MainActivity.this;
            mainActivity.startmyService(mainActivity.editText.getText().toString());
            Intent intent = new Intent(MainActivity.this.getApplicationContext(), ProfileActivity.class);
            intent.putExtra("caller", MainActivity.this.editText.getText().toString());
            MainActivity.this.startActivity(intent);
        });
    }

    public void startmyService(String userid) {
        Application application = getApplication();
        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";
        // ZegoUIKitPrebuiltCallConfig config = ZegoUIKitPrebuiltCallConfig.oneOnOneVoiceCall(); audio call
        ZegoUIKitPrebuiltCallConfig config = ZegoUIKitPrebuiltCallConfig.oneOnOneVideoCall(); // video call
        config.hangUpConfirmDialogInfo = new ZegoHangUpConfirmDialogInfo();
        config.hangUpConfirmDialogInfo.title = "Hangup confirm";
        config.hangUpConfirmDialogInfo.message = "Do you want to hangup?";
        config.hangUpConfirmDialogInfo.cancelButtonName = "Cancel";
        config.hangUpConfirmDialogInfo.confirmButtonName = "Confirm";
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), 1876609586, "ae7967b4838703f3b324ce6b2881747562448f26aae01810d5f6f95f86624a86", editText.getText().toString(), "sanjay", callInvitationConfig);
    }

    public void onDestroy() {
        super.onDestroy();
        ZegoUIKitPrebuiltCallInvitationService.unInit();
    }
}