package io.vaxly.venda.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import io.vaxly.venda.R;
import io.vaxly.venda.models.Listings;

/**
 * Created by vax on 14.9.2016.
 */
public class ListingsAdapter extends RecyclerView.Adapter<ListingsAdapter.ViewHolder> {

    private List<Listings> list;
    private Context context;

    public ListingsAdapter(Context context, List<Listings> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ListingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing, parent, false);
        return new ViewHolder(view, context, list);
    }

    @Override
    public void onBindViewHolder(final ListingsAdapter.ViewHolder holder, int position) {

        final Listings post = list.get(position);

        Uri uri = Uri.parse(post.getImageUrl());
        holder.draweeView.setImageURI(uri);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Setup Views
        public SimpleDraweeView draweeView;


        List<Listings> itemPosts = new ArrayList<Listings>();
        Context ctx;


        public ViewHolder(final View itemView, Context ctx, List<Listings> itemPosts) {

            super(itemView);
            this.itemPosts = itemPosts;
            this.ctx = ctx;
            itemView.setOnClickListener(this);


            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.placeImage);

        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();

        }
    }


}