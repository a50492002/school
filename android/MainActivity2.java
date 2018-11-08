package com.example.administrator.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //帶東西來//

        Intent it2=this.getIntent();
        String sid=it2.getStringExtra("sno");
        String spass=it2.getStringExtra("spw");

        //視窗元件抓到//
        Button b1=(Button)findViewById(R.id.button);
        Button b2=(Button)findViewById(R.id.button2);


        final TextView msg=(TextView)findViewById(R.id.textView5);
        final EditText id=(EditText)findViewById(R.id.editText1);
        final EditText password=(EditText)findViewById(R.id.editText2);
        final EditText pna=(EditText)findViewById(R.id.editText3);
        final EditText name=(EditText)findViewById(R.id.editText4);


            //東西塞進editText//
            id.setText(sid);
            password.setText(spass);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cancel//
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      String sid = id.getText().toString();
                                      String spass = password.getText().toString();

                                      //.............//
                                      final OkHttpClient client = new OkHttpClient();

                                      final Request request = new Request.Builder()
                                              .url("http://192.192.140.246/reguser.php?sno=" + sid + "spass=" + spass)
                                              .build();

                                      Thread okHttpExecuteThread = new Thread() {
                                          @Override
                                          public void run() {
                                              try {
                                                  Response response = client.newCall(request).execute();//連線
                                                  final String responseData = response.body().string();//答案
                                                  Log.e("RES", responseData);
                                                  runOnUiThread(new Runnable() {
                                                      @Override
                                                      public void run() {

                                                         // msg.setText(responseData);//
                                                          String ai=responseData;
                                                          String []a=ai.split(",");


                                                          msg.setText(a[0]);
                                                          if(a[0].trim().equals("1"))
                                                          {
                                                             msg.setText("註冊成功");
                                                          }
                                                      }
                                                  });
                                                  //
                                              } catch (IOException e) {
                                                  Log.e(" TAG ", "error in getting response get request okhttp");//執行連線到物件
                                              }
                                          }

                                      };
                                      okHttpExecuteThread.start();
                                  }

        });
       //ok


    }
}

