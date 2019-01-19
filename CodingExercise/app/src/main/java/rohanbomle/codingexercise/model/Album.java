package rohanbomle.codingexercise.model;

import com.google.gson.annotations.SerializedName;

/**
 * Album Model
 * USE TO : Define album characteristics
 * Shared amount all the views and viewmodels
 */
public class Album {

    @SerializedName("userId")
    private int mUserId;

    @SerializedName("id")
    private int mId;

    @SerializedName("title")
    private String mTitle;

    public Album(int userId, int id, String title){
        mUserId = userId;
        mId = id;
        mTitle = title;
    }


    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }


    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getTitle() {
        return mTitle;
    }
}
