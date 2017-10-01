package com.ogdev.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ogdev.popularmovies.R;
import com.ogdev.popularmovies.activities.MovieDetailActivity;
import com.ogdev.popularmovies.models.Movie;
import com.ogdev.popularmovies.models.Review;
import com.ogdev.popularmovies.models.Video;
import com.ogdev.popularmovies.utilities.DatabaseUtilities;
import com.ogdev.popularmovies.utilities.FetchReviewsTaskUtilities;
import com.ogdev.popularmovies.utilities.FetchVideosTaskUtilities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private final String LOG_TAG = MoviesAdapter.class.getSimpleName();
    public static final String EXTRA_MOVIE_KEY = "EXTRA_MOVIE_KEY";
    private final Context mContext;
    private ArrayList<Movie> mMovies;
    private final FragmentManager mFragmentManager;

    public MoviesAdapter(Context context, ArrayList<Movie> movies, FragmentManager fragmentManager) {
        mContext = context;
        mMovies = movies;
        mFragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie mMovie = mMovies.get(position);

        holder.mTitle.setText(mMovie.getTitle());
        Glide.with(mContext)
                .load(mMovie.getPosterURI("w185", "poster"))
                .placeholder(R.drawable.ic_movie_roll)
                .error(R.drawable.ic_filmstrip_off)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.mBackground);
    }

    public void updateData(ArrayList<Movie> mMovies) {
        this.mMovies = mMovies;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_movie_background_imageView)
        ImageView mBackground;
        @BindView(R.id.card_movie_title_textView)
        TextView mTitle;

        public ViewHolder (View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.card_movie)
        public void onClick() {
            int movieId = mMovies.get(getAdapterPosition()).getMovieId();
            fetchVideosFromMovie(movieId);
            fetchReviewsFromMovie(movieId);

            Intent detailIntent = new Intent(mContext, MovieDetailActivity.class);
            detailIntent.putExtra(EXTRA_MOVIE_KEY, mMovies.get(getAdapterPosition()));
            mContext.startActivity(detailIntent);
        }

        private void fetchVideosFromMovie(int movieId) {
            if (DatabaseUtilities.getVideosFromMovie(mContext, movieId).isEmpty()) {
                FetchVideosTaskUtilities videosTask = new FetchVideosTaskUtilities(mContext, movieId);
                ArrayList<Video> videos = new ArrayList<>();
                try {
                    videos = videosTask.execute().get();
                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                } finally {
                    if (!videos.isEmpty()) {
                        DatabaseUtilities.addVideosToDatabase(mContext, videos);
                    }
                }
            }
        }

        private void fetchReviewsFromMovie(int movieId) {
            if (DatabaseUtilities.getReviewsFromMovie(mContext, movieId).isEmpty()) {
                FetchReviewsTaskUtilities reviewsTask = new FetchReviewsTaskUtilities(mContext, movieId);
                ArrayList<Review> reviews = new ArrayList<>();
                try {
                    reviews = reviewsTask.execute().get();
                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                } finally {
                    if (!reviews.isEmpty()) {
                        DatabaseUtilities.addReviewsToDatabase(mContext, reviews);
                    }
                }
            }
        }
    }
}
