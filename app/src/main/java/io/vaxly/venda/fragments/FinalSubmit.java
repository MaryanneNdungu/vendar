package io.vaxly.venda.fragments;


import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import io.vaxly.venda.R;
import io.vaxly.venda.utils.MultiSpinner;


public class FinalSubmit extends Fragment {


    private MultiSpinner catSpinner;
    private Spinner      conSpinner;
    private ArrayAdapter<String> catadapter, conAdapter;


    public FinalSubmit() {
        // Required empty public constructor
    }

    public static FinalSubmit newInstance() {

        Bundle args = new Bundle();

        FinalSubmit fragment = new FinalSubmit();
        fragment.setArguments(args);
        return fragment;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_final_submit, container, false);

        EditText title = (EditText) rootView.findViewById(R.id.finalItemTitle);
        EditText price = (EditText) rootView.findViewById(R.id.finalItemPrice);

        title.setText(getArguments().getString("title"));
        price.setText(getArguments().getString("price"));

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Register_Bold.ttf");
        Typeface type1 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Register.ttf");
        title.setTypeface(type);
        price.setTypeface(type1);


        String[] spinnerConditions = getResources().getStringArray(R.array.conditions);
        String[] spinnerCategories = getResources().getStringArray(R.array.categories);

        // create spinner list categories
        catadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,spinnerCategories);
        catSpinner = (MultiSpinner) rootView.findViewById(R.id.spinnerMulti);
        catSpinner.setAdapter(catadapter,false,null);
        boolean[] selectedItems = new boolean[catadapter.getCount()];
        selectedItems[1] = true; // select second item
        catSpinner.setSelected(selectedItems);
        catSpinner.setOnItemsSelectedListener(new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                StringBuilder builder = new StringBuilder();


                for (int i = 0; i < selected.length; i++) {
                    if (selected[i]) {
                        builder.append(catadapter.getItem(i)).append(" ");
                    }
                }

                Toast.makeText(getContext(), builder.toString(), Toast.LENGTH_SHORT).show();

            }
        });





        // create spinner list conditions
        conSpinner = (Spinner) rootView.findViewById(R.id.spinner);
        conAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerConditions);
        conAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conSpinner.setAdapter(conAdapter);

        // Spinner click listener
        conSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item (Favourite Language)
                Toast.makeText(parent.getContext(), "Android Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return rootView;

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onDetach() {
        super.onDetach();

        final Transition enterTransition   = TransitionInflater.from(getContext()).inflateTransition(android.R.transition.slide_left);
        final Transition exitTransition    = new AutoTransition();

        FirstSubmit fragment = new FirstSubmit();

        fragment.setEnterTransition(enterTransition);
        fragment.setExitTransition(exitTransition);
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, "Now").addToBackStack(null).commit();
    }



}

