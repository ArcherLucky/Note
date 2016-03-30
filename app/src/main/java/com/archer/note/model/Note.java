package com.archer.note.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 笔记Model
 * Created by archer on 2016/3/29.
 */
public class Note implements Parcelable {

    /**
     * 笔记ID，存储数据库用
     */
    private int id;
    /**
     * 笔记名称
     */
    private String name;
    /**
     * 笔记内容
     */
    private String textContent;

    /**
     * 语音时间
     */
    private int voiceTime;
    /**
     * 语音内容
     */
    private String voiceContent;

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public int getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(int voiceTime) {
        this.voiceTime = voiceTime;
    }

    public String getVoiceContent() {
        return voiceContent;
    }

    public void setVoiceContent(String voiceContent) {
        this.voiceContent = voiceContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.textContent);
        dest.writeInt(this.voiceTime);
        dest.writeString(this.voiceContent);
    }

    protected Note(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.textContent = in.readString();
        this.voiceTime = in.readInt();
        this.voiceContent = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
