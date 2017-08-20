package com.gun0912.tedpermissiondemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_normal).setOnClickListener(this);
        findViewById(R.id.btn_rxjava1).setOnClickListener(this);
        findViewById(R.id.btn_rxjava2).setOnClickListener(this);
        findViewById(R.id.btn_windowPermission).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        Intent intent=null;

        switch (id){
            case R.id.btn_normal:
                intent = new Intent(this,NormalActivity.class);
                break;

            case R.id.btn_rxjava1:
                intent = new Intent(this,RxJava1Activity.class);
                break;

            case R.id.btn_rxjava2:
                intent = new Intent(this,RxJava2Activity.class);
                break;



            case R.id.btn_windowPermission:
                intent = new Intent(this,WindowPermissionActivity.class);
                break;

        }

        if(intent!=null){
            startActivity(intent);
        }

    }
}
