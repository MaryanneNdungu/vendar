package io.vaxly.venda.views;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.vaxly.venda.R;
import io.vaxly.venda.fragments.FirstSubmit;

public class UploadActivity  extends AppCompatActivity {


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        FirstSubmit fragment = FirstSubmit.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, "Now").addToBackStack(null).commit();


    }
}
