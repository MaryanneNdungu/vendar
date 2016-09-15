package io.vaxly.venda.adapters;

import android.graphics.PointF;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import io.vaxly.venda.R;
import io.vaxly.venda.models.Listings;

/**
 * Created by vax on 14.9.2016.
 */
public class ListingsAdapter extends RecyclerView.Adapter<ListingsAdapter.ViewHolder> {

    private List<Listings> mArticles;

    private OnArticleClickedListener mOnArticleClickedListener;

    /**
     * ArticleAdapter's constructor.
     *
     * @param articles Articles to display.
     */
    public ListingsAdapter(final List<Listings> articles) {
        this.mArticles = articles;
    }

    @Override
    public ListingsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Listings item = mArticles.get(position);

        Uri uri = Uri.parse(item.getImageUrl());
        holder.draweeView.setImageURI(uri);
        holder.ivCoveringImage.setImageURI(uri);

        holder.rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (mOnArticleClickedListener != null) {
                        mOnArticleClickedListener.onArticleClicked(item, holder, new PointF(event.getX(), event.getY()));
                    }
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    /**
     * Sets on article clicked listener.
     *
     * @param onArticleClickedListener Article clicked listener.
     */
    public void setOnArticleClickedListener(final OnArticleClickedListener onArticleClickedListener) {
        this.mOnArticleClickedListener = onArticleClickedListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View rootView;

        public SimpleDraweeView draweeView;
        public final SimpleDraweeView ivCoveringImage;

        public ViewHolder(final View view) {
            super(view);
            this.rootView = view;
            this.ivCoveringImage = (SimpleDraweeView) view.findViewById(R.id.thumbnail);
            this.draweeView = (SimpleDraweeView) itemView.findViewById(R.id.covering_image);
        }
    }

    /**
     * On item clicked callback.
     */
    public interface OnArticleClickedListener {

        void onArticleClicked(Listings article, ViewHolder articleViewHolder, PointF touchPoint);

    }

}