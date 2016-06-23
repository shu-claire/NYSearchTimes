package com.claireshu.nysearchtimes_;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by claireshu on 6/20/16.
 */

@Parcel
public class Article {

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    String webUrl;
    String headline;
    String thumbNail;

    public Article () {
    }

    public Article(JSONObject jsonObject, boolean initialLoad) {
        try {
            if (initialLoad) {
                this.webUrl = jsonObject.getString("url");
                this.headline = jsonObject.getString("title");

            } else {
                this.webUrl = jsonObject.getString("web_url");
                this.headline = jsonObject.getJSONObject("headline").getString("main");
            }

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");

            if (multimedia.length() > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                if (initialLoad) {
                    this.thumbNail = multimediaJson.getString("url");
                } else {
                    this.thumbNail = "http://www.nytimes.com/" + multimediaJson.getString("url");
                }
            } else {
                this.thumbNail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public static ArrayList<Article> fromJSONArray(JSONArray array, boolean initialLoad) {
        ArrayList<Article> results = new ArrayList<>();
        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Article(array.getJSONObject(x), initialLoad));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
