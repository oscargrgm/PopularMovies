package com.ogdev.popularmovies.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie implements Parcelable {

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER = "poster_path";
    private static final String KEY_BACKDROP = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_POPULARITY = "popularity";

    private long id;
    private String title;
    private String overview;
    private String releaseDate;
    private String posterPath;
    private String backdropPath;
    private double voteAverage;
    private long voteCount;
    private float popularity;

    public Movie(long id, String title, String overview, String releaseDate, String posterPath, String backdropPath, double voteAverage, long voteCount, float popularity) {
        this.setId(id);
        this.setTitle(title);
        this.setOverview(overview);
        this.setReleaseDate(releaseDate);
        this.setPosterPath(posterPath);
        this.setBackdropPath(backdropPath);
        this.setVoteAverage(voteAverage);
        this.setVoteCount(voteCount);
        this.setPopularity(popularity);
    }

    private Movie(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.voteAverage = in.readDouble();
        this.voteCount = in.readLong();
        this.popularity = in.readFloat();
    }

    public static Movie deserialize(JSONObject movieJsonObject) throws JSONException {

        Long id = movieJsonObject.getLong(getKeyId());
        String title = movieJsonObject.getString(getKeyTitle());
        String overview = movieJsonObject.getString(getKeyOverview());
        String release_date = movieJsonObject.getString(getKeyReleaseDate());
        String poster_path = movieJsonObject.getString(getKeyPoster());
        String backdrop_path = movieJsonObject.getString(getKeyBackdrop());
        double vote_average = movieJsonObject.getDouble(getKeyVoteAverage());
        long vote_count = movieJsonObject.getLong(getKeyVoteCount());
        float popularity = movieJsonObject.getLong(getKeyPopularity());

        return new Movie(id, title, overview, release_date, poster_path, backdrop_path, vote_average, vote_count, popularity);
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeyTitle() {
        return KEY_TITLE;
    }

    public static String getKeyOverview() {
        return KEY_OVERVIEW;
    }

    public static String getKeyReleaseDate() {
        return KEY_RELEASE_DATE;
    }

    public static String getKeyPoster() {
        return KEY_POSTER;
    }

    public static String getKeyBackdrop() {
        return KEY_BACKDROP;
    }

    public static String getKeyVoteAverage() {
        return KEY_VOTE_AVERAGE;
    }

    public static String getKeyVoteCount() {
        return KEY_VOTE_COUNT;
    }

    public static String getKeyPopularity() {
        return KEY_POPULARITY;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeDouble(this.voteAverage);
        dest.writeLong(this.voteCount);
        dest.writeFloat(this.popularity);
    }

    public Uri getPosterURI(String size, String type) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        String image_path = null;
        if (type.equals("poster")) {
            image_path = getPosterPath();
        } else if (type.equals("backdrop")) {
            image_path = getBackdropPath();
        }

        return Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(size)
                .appendEncodedPath(image_path)
                .build();
    }
}
