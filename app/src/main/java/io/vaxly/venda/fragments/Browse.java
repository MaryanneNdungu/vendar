package io.vaxly.venda.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
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
import io.vaxly.venda.views.DetailsActivity;

/**
 * Created by vaxly on 8/26/16.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Browse extends Fragment {

    View rootView;
    private List<Listings> lists = new ArrayList<>();
    private ListingsAdapter articleAdapter;
    private RecyclerView recyclerView;

    private RelativeLayout layout;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

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
        rootView = inflater.inflate(R.layout.fragment_browse, container, false);

        loadData();
        initializeList(lists);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initializeList(final List<Listings> articles) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        assert recyclerView != null;
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setHasFixedSize(true);
        articleAdapter = new ListingsAdapter(articles);
        articleAdapter.setOnArticleClickedListener(new ListingsAdapter.OnArticleClickedListener() {

            @Override
            public void onArticleClicked(Listings article, ListingsAdapter.ViewHolder articleViewHolder, PointF touchPoint) {

                final Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("title", article.getTitle());
                intent.putExtra("url", article.getImageUrl());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(articleAdapter);
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
                articleAdapter.notifyDataSetChanged();
            }
        });
    }





}