package io.vaxly.venda.fragments;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import io.vaxly.venda.R;
import io.vaxly.venda.camera.ImageUtility;


public class FirstSubmit extends Fragment {

    private  Button   submitButton, moreButton;
    private  EditText eTitle, ePrice;
    private  Bitmap bitmap;
    private  Point  mSize;
    private RelativeLayout layout;

    public FirstSubmit() {
        // Required empty public constructor
    }

    public static FirstSubmit newInstance() {

        Bundle args = new Bundle();

        FirstSubmit fragment = new FirstSubmit();
        fragment.setArguments(args);
        return fragment;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_first_submit, container, false);

        // Handle navigation view item clicks here.
        final Transition enterTransition   = TransitionInflater.from(getContext()).inflateTransition(android.R.transition.slide_right);
        final Transition exitTransition    = new AutoTransition();

        submitButton = (Button) rootView.findViewById(R.id.buttonSubmit);
        moreButton = (Button) rootView.findViewById(R.id.buttonMore);

          eTitle = (EditText) rootView.findViewById(R.id.itemTitle);
          ePrice = (EditText) rootView.findViewById(R.id.itemPrice);

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinalSubmit fragment = new FinalSubmit();

                Bundle args = new Bundle();
                args.putString("title", eTitle.getText().toString());
                args.putString("price", ePrice.getText().toString());
                fragment.setArguments(args);

                fragment.setEnterTransition(enterTransition);
                fragment.setExitTransition(exitTransition);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, "Now").addToBackStack(null).commit();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        return rootView;
    }

    private void getBitmap(){
        String path = getActivity().getIntent().getStringExtra("bitmapUrl");
        Uri uri = Uri.parse(path);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        mSize = new Point();
        display.getSize(mSize);
        bitmap = ImageUtility.decodeSampledBitmapFromPath(uri.getPath(), mSize.x, mSize.x);
    }

    private void upload(){

        final String title = eTitle.getText().toString();
        final String price = ePrice.getText().toString();

        getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
        final ParseFile pFile = new ParseFile(stream.toByteArray());

        pFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                ParseObject list = new ParseObject("Listings");

                list.put("image",     pFile);
                list.put("price",     Double.valueOf(price));
                list.put("title",     title );

                list.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            getActivity().finish();
                        }else {
                            Toast.makeText(getContext(), "Error:" + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });




    }

}

