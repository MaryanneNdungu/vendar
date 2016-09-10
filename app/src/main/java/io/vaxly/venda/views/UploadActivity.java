package io.vaxly.venda.views;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.vaxly.venda.R;
import io.vaxly.venda.utils.TwoStepsListener;

public class UploadActivity extends AppCompatActivity implements TwoStepsListener {

    private Submit login_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        login_view = (Submit) findViewById(R.id.submit_view);

        login_view.setListener(this);
        login_view.setActivity(this);
        login_view.setSecond_step_background_color(Color.WHITE);

        //Register Button background
        //login_view.setRegister_background(R.drawable.rounded_white_stroke_button);
        login_view.setButton_register_text_color(Color.WHITE);

        //EditText Backgounds
        //login_view.setEdittext_password_background(R.drawable.edittext_unipiazza_background);
        //login_view.setEdittext_email_background(R.drawable.edittext_unipiazza_background);

        //Button Backgrounds
        //login_view.setButton_login_background(R.drawable.rounded_orange_light_btn);
        //login_view.setButton_next_background(R.drawable.rounded_orange_btn);
        //Button Text Color
        login_view.setButton_next_text_color(Color.WHITE);
        login_view.setButton_login_text_color(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onNextClicked(String email) {
        /*TODO Check if email is corrected
            ...
            if it is not call login_view.setNotVerified();
        */
        login_view.setInfosAfterNext(email
                , "Material Two Steps Demo"
                , BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
    }

    @Override
    public void onLoginClicked(String password) {
        /*TODO Check if password is corrected
            ...
            if it is not call login_view.setPasswordWrong();
            otherwise go on with your Activities
        */
    }

    @Override
    public void onRecoverPasswordClicked() {
        //Called when user click to recover password
    }

    @Override
    public void onBackToMail() {
        //Called when user click to back button
    }

    @Override
    public void onRegisterClicked() {
        //Called when user click to register
    }
}