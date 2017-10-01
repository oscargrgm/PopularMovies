package com.ogdev.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ogdev.popularmovies.R;
import com.ogdev.popularmovies.models.Video;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {

    private static final String LOG_TAG = VideosAdapter.class.getSimpleName();
    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/";
    private static final String PATH_WATCH = "watch";
    private static final String PARAM_VIDEO_KEY = "v";
    private final Context mContext;
    private ArrayList<Video> mVideos;

    public VideosAdapter(Context context, ArrayList<Video> videos) {
        mContext = context;
        mVideos = videos;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_video_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Video video = mVideos.get(position);

        holder.mTitleTextView.setText(video.getName());
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_video_title)
        TextView mTitleTextView;

        public ViewHolder (View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.card_video_preview)
        public void onClick() {
            Video video = mVideos.get(getAdapterPosition());
            String key = video.getKey();

            Uri youtubeUri = Uri.parse(YOUTUBE_BASE_URL).buildUpon()
                    .appendPath(PATH_WATCH)
                    .appendQueryParameter(PARAM_VIDEO_KEY, key)
                    .build();

            Intent intent = new Intent(Intent.ACTION_VIEW, youtubeUri);
            mContext.startActivity(intent);
        }
    }
}
