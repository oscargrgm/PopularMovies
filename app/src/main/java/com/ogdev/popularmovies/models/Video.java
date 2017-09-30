package com.ogdev.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Video implements Parcelable {

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    private static final String KEY_ID = "id";
    private static final String KEY_ISO_639_1 = "iso_639_1";
    private static final String KEY_ISO_3166_1 = "iso_3166_1";
    private static final String KEY_KEY = "key";
    private static final String KEY_NAME = "name";
    private static final String KEY_SITE = "site";
    private static final String KEY_SIZE = "size";
    private static final String KEY_TYPE = "type";
    private static final String KEY_MOVIE_ID = "movie_id";

    private String id;
    private String iso6391;
    private String iso31661;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;
    private int movieId;

    public Video() {

    }

    public Video(String id, String iso6391, String iso31661, String key, String name, String site, int size, String type) {
        this.setId(id);
        this.setIso6391(iso6391);
        this.setIso31661(iso31661);
        this.setKey(key);
        this.setName(name);
        this.setSite(site);
        this.setSize(size);
        this.setType(type);
    }

    public Video(String id, String iso6391, String iso31661, String key, String name, String site, int size, String type, int movieId) {
        this.setId(id);
        this.setIso6391(iso6391);
        this.setIso31661(iso31661);
        this.setKey(key);
        this.setName(name);
        this.setSite(site);
        this.setSize(size);
        this.setType(type);
        this.setMovieId(movieId);
    }

    private Video(Parcel in) {
        this.id = in.readString();
        this.iso6391 = in.readString();
        this.iso31661 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.type = in.readString();
        this.movieId = in.readInt();
    }

    public static Video deserialize(JSONObject videoJsonObject) throws JSONException {
        String id = videoJsonObject.getString(getKeyId());
        String iso6391 = videoJsonObject.getString(getKeyIso6391());
        String iso31661 = videoJsonObject.getString(getKeyIso31661());
        String key = videoJsonObject.getString(getKeyKey());
        String name = videoJsonObject.getString(getKeyName());
        String site = videoJsonObject.getString(getKeySite());
        int size = videoJsonObject.getInt(getKeySize());
        String type = videoJsonObject.getString(getKeyType());
        return new Video(id, iso6391, iso31661, key, name, site, size, type);
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeyIso6391() {
        return KEY_ISO_639_1;
    }

    public static String getKeyIso31661() {
        return KEY_ISO_3166_1;
    }

    public static String getKeyKey() {
        return KEY_KEY;
    }

    public static String getKeyName() {
        return KEY_NAME;
    }

    public static String getKeySite() {
        return KEY_SITE;
    }

    public static String getKeySize() {
        return KEY_SIZE;
    }

    public static String getKeyType() {
        return KEY_TYPE;
    }

    public static String getKeyMovieId() { return KEY_MOVIE_ID; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        dest.writeString(this.iso6391);
        dest.writeString(this.iso31661);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.type);
    }
}
