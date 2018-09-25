
package com.blub.amazontest.Contacts.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "contacts")
public class ContactDto extends ViewItem implements Parcelable {

    @SerializedName("name")
    private String name;
    @SerializedName("id")
    @NonNull
    @PrimaryKey
    private String id;
    @SerializedName("companyName")
    private String companyName;
    @SerializedName("isFavorite")
    private boolean isFavorite;
    @SerializedName("smallImageURL")
    private String smallImageURL;
    @SerializedName("largeImageURL")
    private String largeImageURL;
    @SerializedName("emailAddress")
    private String emailAddress;
    @SerializedName("birthdate")
    private String birthdate;
    @SerializedName("phone")
    @Embedded
    private Phone phone;
    @SerializedName("address")
    @Embedded
    private Address address;

    public final static Creator<ContactDto> CREATOR = new Creator<ContactDto>() {
        public ContactDto createFromParcel(Parcel in) {
            return new ContactDto(in);
        }

        public ContactDto[] newArray(int size) {
            return (new ContactDto[size]);
        }

    };

    public ContactDto() {
        super(ViewItem.CONTACT);
    }

    public ContactDto(@Nullable Parcel in) {
        super(ViewItem.CONTACT);

        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.companyName = ((String) in.readValue((String.class.getClassLoader())));
        this.isFavorite = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.smallImageURL = ((String) in.readValue((String.class.getClassLoader())));
        this.largeImageURL = ((String) in.readValue((String.class.getClassLoader())));
        this.emailAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.birthdate = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((Phone) in.readValue((Phone.class.getClassLoader())));
        this.address = ((Address) in.readValue((Address.class.getClassLoader())));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(id);
        dest.writeValue(companyName);
        dest.writeValue(isFavorite);
        dest.writeValue(smallImageURL);
        dest.writeValue(largeImageURL);
        dest.writeValue(emailAddress);
        dest.writeValue(birthdate);
        dest.writeValue(phone);
        dest.writeValue(address);
    }

    public int describeContents() {
        return 0;
    }

}
