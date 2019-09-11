package com.materialuiux.androidpaginglibraryexample.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.materialuiux.androidpaginglibraryexample.database.AppDatabase;
import com.materialuiux.androidpaginglibraryexample.model.Emoji;

public class EmojiViewModel extends AndroidViewModel {

    //the size of a page that we want
    private static final int PAGE_SIZE = 10;

    private AppDatabase appDatabase;

    //creating livedata for PagedList
    private LiveData<PagedList<Emoji>> liveResults;

    //constructor
    public EmojiViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(this.getApplication());
        // get all emojies from database and apply it to the paging library
        DataSource.Factory factory = appDatabase.postDao().getAllEmoji();
        //Building the paged list
        LivePagedListBuilder pagedListBuilder = new LivePagedListBuilder(factory, PAGE_SIZE);
        liveResults = pagedListBuilder.build();
    }

    public LiveData<PagedList<Emoji>> getLiveResults()   {
        return liveResults;
    }

}
