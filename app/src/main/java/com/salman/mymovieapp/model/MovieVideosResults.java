package com.salman.mymovieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieVideosResults implements Parcelable {
    private String id;
    private String name;
    private String key;
    private String site;
    private Integer size;
    private String type;

    protected MovieVideosResults(Parcel in) {
        id = in.readString();
        name = in.readString();
        key = in.readString();
        site = in.readString();
        if (in.readByte() == 0) {
            size = null;
        } else {
            size = in.readInt();
        }
        type = in.readString();
    }

    public static final Creator<MovieVideosResults> CREATOR = new Creator<MovieVideosResults>() {
        @Override
        public MovieVideosResults createFromParcel(Parcel in) {
            return new MovieVideosResults(in);
        }

        @Override
        public MovieVideosResults[] newArray(int size) {
            return new MovieVideosResults[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(key);
        dest.writeString(site);
        if (size == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(size);
        }
        dest.writeString(type);
    }
}
