package com.materialuiux.androidpaginglibraryexample;


import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.materialuiux.androidpaginglibraryexample.adapter.Ad_Emoji;
import com.materialuiux.androidpaginglibraryexample.database.AppDatabase;
import com.materialuiux.androidpaginglibraryexample.model.Emoji;
import com.materialuiux.androidpaginglibraryexample.viewmodel.EmojiViewModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EmojiViewModel viewModel;
    Ad_Emoji adapter;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize the EmojiCompat
        EmojiCompat.Config config= new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);

        setContentView(R.layout.activity_main);



        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        viewModel = ViewModelProviders.of(this).get(EmojiViewModel.class);
        adapter = new Ad_Emoji(MainActivity.this);

        RecyclerView recipeRecyclerView = findViewById(R.id.rv);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recipeRecyclerView.setAdapter(adapter);

        // get the live data from the database and submit it to the adapter
        viewModel.getLiveResults().observe(this, new Observer<PagedList<Emoji>>() {
            @Override
            public void onChanged(PagedList<Emoji> pagedList) {
                if (pagedList != null) {
                    adapter.submitList(pagedList);
                }
            }
        });
        // check if the data was whiten in the database
        boolean loaded = prefs.getBoolean("loaded", false);
        if (!loaded){
            // if its not loaded then write the data again
            loadEmojiJSONFromAsset();
        }
    }


    public void loadEmojiJSONFromAsset() {
        String json = null;
        ArrayList<Emoji> emojiArrayList = new ArrayList<>();
        try {
            InputStream is = getAssets().open("emoji.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.getJSONArray("Smileys");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = (JSONObject) array.get(i);
                ArrayList<String> keywordsList = new ArrayList<>();
                JSONArray jArray = object.getJSONArray("keywords");
                final int numberOfItemsInResp = jArray.length();
                for (int j = 0; j < numberOfItemsInResp; j++) {
                    keywordsList.add(jArray.getString(j));
                }
                emojiArrayList.add(new Emoji(0, object.getString("code"), fromArray(keywordsList)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppDatabase.getInstance(this).postDao().insertAll(emojiArrayList);
        prefs.edit().putBoolean("loaded", true).apply();
    }

    public String fromArray(ArrayList<String> strings) {
        StringBuilder string = new StringBuilder();
        for (String s : strings) string.append(s).append(" ");
        return string.toString();
    }

}
