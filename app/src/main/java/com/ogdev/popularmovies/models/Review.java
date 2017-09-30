package com.ogdev.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Review implements Parcelable {

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    private static final String KEY_ID = "id";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_URL = "url";
    private static final String KEY_MOVIE_ID = "movieId";

    private String id;
    private String author;
    private String content;
    private String url;
    private int movieId;

    public Review() {

    }

    public Review(String id, String author, String content, String url) {
        this.setId(id);
        this.setAuthor(author);
        this.setContent(content);
        this.setUrl(url);
    }
    public Review(String id, String author, String content, String url, int movieId) {
        this.setId(id);
        this.setAuthor(author);
        this.setContent(content);
        this.setUrl(url);
        this.setMovieId(movieId);
    }

    private Review(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
        this.movieId = in.readInt();
    }

    public static Review deserialize(JSONObject reviewJsonObject) throws JSONException {
        String id = reviewJsonObject.getString(getKeyId());
        String author = reviewJsonObject.getString(getKeyAuthor());
        String content = reviewJsonObject.getString(getKeyContent());
        String url = reviewJsonObject.getString(getKeyUrl());
        return new Review(id, author, content, url);
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeyAuthor() {
        return KEY_AUTHOR;
    }

    public static String getKeyContent() {
        return KEY_CONTENT;
    }

    public static String getKeyUrl() {
        return KEY_URL;
    }

    public static String getKeyMovieId() {return KEY_MOVIE_ID; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
        dest.writeInt(this.movieId);
    }
}
