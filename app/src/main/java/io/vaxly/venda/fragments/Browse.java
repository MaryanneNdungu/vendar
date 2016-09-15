package io.vaxly.venda.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import io.vaxly.venda.R;
import io.vaxly.venda.adapters.ListingsAdapter;
import io.vaxly.venda.models.Listings;

/**
 * Created by vaxly on 8/26/16.
 */
public class Browse extends Fragment {


    private List<Listings> lists = new ArrayList<>();
    private RecyclerView recycler;
    private LinearLayoutManager layoutManager;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private ListingsAdapter listingsAdapter;


    private RelativeLayout layout;

    public Browse() {
        // Required empty public constructor
    }

    public static Browse newInstance() {

        Bundle args = new Bundle();

        Browse fragment = new Browse();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_browse, container, false);


        recycler = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setLayoutManager(mStaggeredLayoutManager);
        recycler.setHasFixedSize(true);
        listingsAdapter = new ListingsAdapter(getContext(), lists);
        recycler.setAdapter(listingsAdapter);

        loadData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        lists.clear();
        loadData();
    }

    private void loadData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Listings");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    lists.clear();
                    for (int i = 0; i < objects.size(); i++){
                        ParseObject object = objects.get(i);
                        Listings list = new Listings();
                        list.setImageUrl(object.getParseFile("image").getUrl());
                        list.setPrice(object.getNumber("price").toString());
                        list.setTitle(object.getString("title"));

                        lists.add(list);
                    }
                }
                listingsAdapter.notifyDataSetChanged();
            }
        });
    }


}