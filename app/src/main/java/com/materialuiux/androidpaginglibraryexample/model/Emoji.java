package com.materialuiux.androidpaginglibraryexample.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Emoji")
public class Emoji {

    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "code")
    String code;

    @ColumnInfo(name = "keywords")
    String keywords;


    public Emoji(int uid, String code, String keywords) {
        this.uid = uid;
        this.code = code;
        this.keywords = keywords;
    }

    public int getUid() {
        return uid;
    }


    public String getCode() {
        return code;
    }


    public String getKeywords() {
        return keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Emoji emoji = (Emoji) o;

        if (uid != emoji.uid) {
            return false;
        }

        return code != null ? code.equals(emoji.code) : emoji.code == null;
    }

    public static final DiffUtil.ItemCallback<Emoji> DIFF_CALLBACK = new DiffUtil.ItemCallback<Emoji>() {
        @Override
        public boolean areItemsTheSame(@NonNull Emoji oldItem, @NonNull Emoji newItem) {
            return oldItem.getUid() == newItem.getUid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Emoji oldItem, @NonNull Emoji newItem) {
            return oldItem.equals(newItem);
        }
    };


}
