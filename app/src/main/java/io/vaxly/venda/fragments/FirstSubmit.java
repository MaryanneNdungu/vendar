package io.vaxly.venda.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import java.util.regex.Pattern;

import io.vaxly.venda.R;
import io.vaxly.venda.utils.LoadingView;
import io.vaxly.venda.utils.TwoStepsListener;
import io.vaxly.venda.views.Submit;
import jp.wasabeef.fresco.processors.BlurPostprocessor;

public class FirstSubmit extends Fragment {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
    private static final int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 123;

    private TwoStepsListener mListener;
    private EditText email;
    private Submit mtsl;
    private Button next;
    private ProgressBar progressBarFirst;
    private LinearLayout layoutFirst;
    private ImageView logo;
    private TextView insert_email_login;
    private TextView registerText;
    private Button buttonRegistra;
    private SimpleDraweeView draweeView;
    private  Point mSize;
    private LoadingView loadingView;
    private Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.first_submit, null);

        email = (EditText) view.findViewById(R.id.email);
        next = (Button) view.findViewById(R.id.buttonNext);
        layoutFirst = (LinearLayout) view.findViewById(R.id.layoutFirst);
        buttonRegistra = (Button) view.findViewById(R.id.buttonRegistra);
        draweeView = (SimpleDraweeView) view.findViewById(R.id.drawee);
        loadingView = (LoadingView) view.findViewById(R.id.loading_view);





        loadingView.start();
        if (mtsl != null) {

            //Get passed image and load it
            String path = getActivity().getIntent().getStringExtra("bitmapUrl");
            Uri uri = Uri.parse(path);
            Display display = getActivity().getWindowManager().getDefaultDisplay();

            mSize = new Point();
            display.getSize(mSize);


            /**
            if (draweeView != null) {
                draweeView.setImageURI(uri);
            }
             **/

            Postprocessor processor = new BlurPostprocessor(getContext(), 50);

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(processor)
                    .build();




            ControllerListener controllerListener = new ControllerListener() {
                @Override
                public void onSubmit(String id, Object callerContext) {

                }

                @Override
                public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {

                    loadingView.stop();
                }

                @Override
                public void onIntermediateImageSet(String id, Object imageInfo) {

                }

                @Override
                public void onIntermediateImageFailed(String id, Throwable throwable) {

                }

                @Override
                public void onFailure(String id, Throwable throwable) {

                }

                @Override
                public void onRelease(String id) {

                }
            };

            PipelineDraweeController controller =
                    (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setOldController(draweeView.getController())
                            .setControllerListener(controllerListener)
                            .build();
            draweeView.setController(controller);



            view.setBackgroundColor(mtsl.getFirst_step_background_color());
            buttonRegistra.setText("Add More Details");
            buttonRegistra.setBackgroundResource(mtsl.getRegister_background());
            if (mtsl.getEdittext_email_background() != 0)
                email.setBackgroundResource(mtsl.getEdittext_email_background());
            next.setBackgroundResource(mtsl.getButton_next_background());
            if (mtsl.getButton_next_text_color() != 0)
                next.setTextColor(mtsl.getButton_next_text_color());
            if (mtsl.getButton_register_text_color() != 0)
                buttonRegistra.setTextColor(mtsl.getButton_register_text_color());
        }

        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    next.performClick();
                }
                return false;
            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNextClicked(email.getText().toString());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        return view;
    }


    public void setListener(Submit mtsl, TwoStepsListener listener) {
        this.mtsl = mtsl;
        mListener = listener;
    }

    public void emailVerified() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        mtsl.getSecondSubmit().setListener(mtsl, mListener);
        fm.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragmentView, mtsl.getSecondSubmit()).addToBackStack("secondStepLogin")
                .commit();
    }

    public void notVerified() {
        progressBarFirst.setVisibility(View.GONE);
        layoutFirst.setVisibility(View.VISIBLE);
    }


}
