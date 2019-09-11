package com.materialuiux.androidpaginglibraryexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.emoji.widget.EmojiTextView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.materialuiux.androidpaginglibraryexample.R;
import com.materialuiux.androidpaginglibraryexample.model.Emoji;

public class Ad_Emoji extends PagedListAdapter<Emoji, Ad_Emoji.viewHolder> {

    private final Context context;

    public Ad_Emoji(Context context) {
        super(Emoji.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emoji, parent, false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Emoji emoji = getItem(position);
        if (emoji != null) {
            holder.bindTo(emoji);
        } else {
            holder.clear();
        }
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView keywords;
        private EmojiTextView emoji;

        viewHolder(@NonNull View itemView) {
            super(itemView);
            emoji = itemView.findViewById(R.id.tv_emoji);
            keywords = itemView.findViewById(R.id.tv_keywords);
        }

        void bindTo(Emoji emojis) {
            this.itemView.setTag(emojis.getUid());
            try {
                char[] chars = Character.toChars(Integer.parseInt(emojis.getCode().substring(2), 16));
                this.emoji.setText(new String(chars));
                this.emoji.setTextSize(22);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            this.keywords.setText(emojis.getKeywords());
        }

        void clear() {
            itemView.invalidate();
            emoji.invalidate();
            keywords.invalidate();
        }

    }
}
