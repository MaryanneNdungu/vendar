package io.vaxly.venda.views;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import io.vaxly.venda.R;

public class DetailsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mTitle;
    private SimpleDraweeView draweeView;
    private ViewGroup mDetailsContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initUii();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initUii(){
        mToolbar = (Toolbar) findViewById(R.id.detToolbar);
        mDetailsContent= (ViewGroup) findViewById(R.id.details_content);
        mTitle = (TextView) findViewById(R.id.detTitle);
        draweeView = (SimpleDraweeView) findViewById(R.id.detHeader);

        mTitle.setText(getIntent().getStringExtra("title"));
        Uri uri = Uri.parse(getIntent().getStringExtra("url"));
        draweeView.setImageURI(uri);

        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_up);

        mDetailsContent.setAnimation(slide_up);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

}
