package com.materialuiux.androidpaginglibraryexample.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.materialuiux.androidpaginglibraryexample.model.Emoji;
import java.util.List;

@Dao
public interface EmojiDao {

    @Query("SELECT * FROM Emoji")
    DataSource.Factory<Integer, Emoji> getAllEmoji();

    @Insert
    void insertAll(List<Emoji> persons);

}
