package com.example.motionet;

import android.os.Parcel;
import android.os.Parcelable;

import org.opencv.core.Mat;

import java.util.ArrayList;

public class Information implements Parcelable {

    public long totalDuration;
    public double totalFPS;
    public ArrayList<Mat> frames;

    public Information(long totalDuration, double totalFPS, ArrayList<Mat> frames) {
        this.totalDuration = totalDuration;
        this.totalFPS = totalFPS;
        this.frames = frames;
    }

    protected Information(Parcel in) {
        totalDuration = in.readLong();
        totalFPS = in.readDouble();
    }

    public static final Creator<Information> CREATOR = new Creator<Information>() {
        @Override
        public Information createFromParcel(Parcel in) {
            return new Information(in);
        }

        @Override
        public Information[] newArray(int size) {
            return new Information[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(totalDuration);
        dest.writeDouble(totalFPS);
    }
}
