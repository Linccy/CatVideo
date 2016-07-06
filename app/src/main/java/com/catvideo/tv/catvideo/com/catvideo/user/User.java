package com.catvideo.tv.catvideo.com.catvideo.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lcc 957109587@qq.com
 * @version 2016��5��31�� ����12:09:20
 * @Description
 */
public class User implements Parcelable {
    int userId;
    private String userName;
    private String passWord;
    private String email;
    boolean state;

    public User() {

    }

    public User(String email, String passWord) {
        this.email = email;
        this.passWord = passWord;
    }

    public User(int userId, String userName, String passWord, boolean state) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.state = state;
    }

    public User(int userId, String userName, String passWord) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.state = false;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        boolean[] states = {state};
        dest.writeString(userName);
        dest.writeInt(getUserId());
        dest.writeBooleanArray(states);
    }

//    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
////重写Creator
//
//        @Override
//        public User createFromParcel(Parcel source) {
//            User user = new User();
////            user.userName=source.readString()
//
////            p.map = source.readHashMap(HashMap.class.getClassLoader());
////            p.name = source.readString();
//            return p;
//        }
//
//        @Override
//        public User[] newArray(int size) {
//            // TODO Auto-generated method stub
//            return null;
//        }
//    };
}
