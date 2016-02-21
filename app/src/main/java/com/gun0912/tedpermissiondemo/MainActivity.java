package com.gun0912.tedpermissiondemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.btn_nomessage,R.id.btn_only_deny_message,R.id.btn_only_rationale_message,R.id.btn_rationale_deny})
    public void onButtonClick(View view){

        int id = view.getId();

        Intent intent=null;

        switch (id){
            case R.id.btn_nomessage:
                intent = new Intent(this,NoDialogActivity.class);
                break;

            case R.id.btn_only_deny_message:
                intent = new Intent(this,DenyActivity.class);
                break;

            case R.id.btn_only_rationale_message:
                intent = new Intent(this,RationaleActivity.class);
                break;

            case R.id.btn_rationale_deny:
                intent = new Intent(this,RationaleDenyActivity.class);
                break;


        }

        if(intent!=null){
            startActivity(intent);
        }



    }


}
