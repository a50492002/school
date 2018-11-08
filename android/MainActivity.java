package com.example.administrator.androidtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1=(Button)findViewById(R.id.button);
        Button b2=(Button)findViewById(R.id.button2);
        final EditText id=(EditText)findViewById(R.id.editText);
        final EditText password=(EditText)findViewById(R.id.editText2);
        final TextView msg=(TextView)findViewById(R.id.textView5);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid=id.getText().toString();
                String spass=password.getText().toString();

                //.............//
                final OkHttpClient client = new OkHttpClient();

                final Request request = new Request.Builder()
                        .url("http://192.192.140.246/checkuser.php?sno="+sid+"spass="+spass)
                        .build();

                Thread okHttpExecuteThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Response response = client.newCall(request).execute();//連線
                            final String responseData = response.body().string();//答案
                            Log.e("RES",responseData);
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   msg.setText("NOT MEMBER"+responseData);//
                                 }
                           });
                            //
                        } catch (IOException e) {
                            Log.e(" TAG ", "error in getting response get request okhttp");//執行連線到物件
                        }
                    }
                };                    // Start the child thread.                    okHttpExecuteThread.start();

                okHttpExecuteThread.start();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //reg
                String sid=id.getText().toString();
                String spass=password.getText().toString();

                //帶東西走且出門//

                Intent it=new Intent();
                it.setClass(MainActivity.this, MainActivity2.class);
                    it.putExtra("sno",sid);
                    it.putExtra("spw",spass);
                startActivity(it);
            }
        });

    }
}
