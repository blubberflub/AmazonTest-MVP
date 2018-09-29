
package com.blub.amazontest.contacts.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Phone implements Parcelable {

    @SerializedName("work")

    private String work;
    @SerializedName("home")

    private String home;
    @SerializedName("mobile")

    private String mobile;
    public final static Creator<Phone> CREATOR = new Creator<Phone>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        public Phone[] newArray(int size) {
            return (new Phone[size]);
        }

    };

    protected Phone(Parcel in) {
        this.work = ((String) in.readValue((String.class.getClassLoader())));
        this.home = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Phone() {
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(work);
        dest.writeValue(home);
        dest.writeValue(mobile);
    }

    public int describeContents() {
        return 0;
    }

}
