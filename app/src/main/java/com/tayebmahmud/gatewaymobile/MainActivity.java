package com.tayebmahmud.gatewaymobile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tayebmahmud.mobile.SendSmsVerify;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //send sms
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", "PHONE_NUMBER");
        map.put("phone", "PHONE_NUMBER");
        map.put("phone", "PHONE_NUMBER");
        map.put("phone", "PHONE_NUMBER");
        map.put("phone", "PHONE_NUMBER");
        //etc...
        SendSmsVerify sms = new SendSmsVerify(this, "API_KEY", map, "URl");
        sms.sendSMS(success -> {
            if (success){
                //successfully
            } else {
                //unSuccessfully
            }
        });

    }
}