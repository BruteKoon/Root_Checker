package com.example.rootcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context mContext = null;

        // Make RootChecker Object
        final RootChecker checker = new RootChecker(mContext);

        final TextView logview = findViewById(R.id.LogView);


        // Check_button1
        Button button_1 = findViewById(R.id.Check_Button1);
        button_1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = checker.checkForDangerousProps();
                logview.setText(String.valueOf(result));
            }
        });

    }
}
