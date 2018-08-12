package com.android.omdb.view.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.omdb.R;
import com.android.omdb.model.SearchItem;
import com.android.omdb.util.OnItemClickListener;
import com.android.omdb.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public static final int VIEW_TYPE_MORE_LOADING = 1;
    private static final int SEARCH_ITEM = 0;
    private final Context mContext;
    private OnItemClickListener mClickListener;
    private ArrayList<SearchItem> mSearchItems;
    private int mLastMoreRequestPos;

    public SearchAdapter(Context context, ArrayList<SearchItem> items) {
        mContext = context;
        mSearchItems = items;
        mClickListener = (OnItemClickListener) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SEARCH_ITEM:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.search_list_item, parent, false));
            case VIEW_TYPE_MORE_LOADING:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.more_loading_layout, parent, false));
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder.getItemViewType() == SEARCH_ITEM) {
            final SearchItem item = mSearchItems.get(position);
            holder.moviePoster.setTag(item.getPoster());
            holder.moviePoster.setImageResource(R.color.smokey_white);

            holder.movieName.setText(item.getTitle());
            holder.movieYear.setText("Relase Year : " + item.getYear());
            holder.moviePoster.setTag(holder.getAdapterPosition());
            Picasso.get()
                    .load(item.getPoster())
                    .into(holder.moviePoster);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.moviePoster.setTransitionName(item.getImdbId());
            }
        } else {
            if (mLastMoreRequestPos != position) {
                ((MainActivity) mContext).requestForMoreItems();
                mLastMoreRequestPos = position;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mSearchItems != null ? mSearchItems.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mSearchItems.get(position).getViewType();
    }


    public void upateList(ArrayList<SearchItem> list) {
        mSearchItems = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView moviePoster;
        private TextView movieName;
        private TextView movieYear;

        public ViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_image);
            movieName = itemView.findViewById(R.id.movie_title);
            movieYear = itemView.findViewById(R.id.year);
            moviePoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    mClickListener.setOnItemClick(v, pos, mSearchItems.get(pos));
                }
            });
        }
    }
}
