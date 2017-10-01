package com.ogdev.popularmovies.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ogdev.popularmovies.R;
import com.ogdev.popularmovies.models.Review;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private final String LOG_TAG = ReviewsAdapter.class.getSimpleName();
    private final Context mContext;
    private ArrayList<Review> mReviews;

    public ReviewsAdapter(Context context, ArrayList<Review> reviews) {
        mContext = context;
        mReviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_review_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = mReviews.get(position);

        holder.mAuthorTextView.setText(review.getAuthor());
        holder.mContentTextView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_review_preview_author)
        TextView mAuthorTextView;
        @BindView(R.id.card_review_preview_content)
        TextView mContentTextView;

        public ViewHolder (View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.card_review_preview)
        public void onClick() {
            Review review = mReviews.get(getAdapterPosition());
            String mAuthor = review.getAuthor();
            String mContent = review.getContent();
            final String mUrl = review.getUrl();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.Theme_AppCompat_Light_Dialog_Alert);
            alertDialogBuilder.setTitle(mAuthor);
            alertDialogBuilder.setMessage(mContent);
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setPositiveButton(mContext.getString(R.string.review_read_online),
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Uri uri = Uri.parse(mUrl);
                    Intent readOnlineIntent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(readOnlineIntent);
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();
        }
    }
}
