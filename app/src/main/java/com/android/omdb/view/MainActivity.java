package com.android.omdb.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.omdb.R;
import com.android.omdb.model.SearchItem;
import com.android.omdb.presenter.SearchPresenter;
import com.android.omdb.util.OnItemClickListener;
import com.android.omdb.view.adapter.SearchAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchPresenter.SearchResultListener, OnItemClickListener {
    public static final String API_KEY = "c5f0faf0";

    private String mSearchTerm;
    private SearchPresenter mSearchPresenter;
    private int mPage;
    private SearchAdapter mSearchAdapter;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWidgets();
    }

    private void getWidgets() {
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final SearchView searchView = findViewById(R.id.search_bar);
        mProgressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.button)
                .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (checkSearchTerm()) {
                                                mPage = 1;
                                                mProgressBar.setVisibility(View.VISIBLE);
                                                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                                                requestForSearchResult();
                                            }
                                        }
                                    }
                );
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchTerm = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchTerm = newText;
                return false;
            }
        });

    }

    private boolean checkSearchTerm() {
        if (mSearchTerm == null || mSearchTerm.trim().isEmpty()) {
            Toast.makeText(getBaseContext(), "Seach term can not be empty", Toast.LENGTH_SHORT).show();

            return false;
        } else if (mSearchTerm.trim().length() < 3) {
            Toast.makeText(getBaseContext(), "Please enter atleat 3 chars", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void requestForSearchResult() {
        if (mSearchPresenter == null) {
            mSearchPresenter = new SearchPresenter(this);
        }
        mSearchPresenter.getSearchResult(mSearchTerm, mPage);
    }

    public void requestForMoreItems() {
        mPage++;
        requestForSearchResult();
    }

    @Override
    public void onResult(ArrayList<SearchItem> result) {
        mProgressBar.setVisibility(View.GONE);
        if (mSearchAdapter == null) {
            mSearchAdapter = new SearchAdapter(this, result);
            mRecyclerView.setAdapter(mSearchAdapter);
        } else {
            mSearchAdapter.upateList(result);
        }
    }

    @Override
    public void onError(String msg) {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setOnItemClick(View view, int position, Object data) {
        Intent intent = new Intent(this, SearchResultDetailActivity.class);
        SearchItem searchItem = (SearchItem) data;
        intent.putExtra(getString(R.string.movie_id), searchItem.getImdbId());
        intent.putExtra(getString(R.string.movie_name), searchItem.getTitle());
        intent.putExtra(getString(R.string.poster_image), searchItem.getPoster());
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, searchItem.getImdbId());
        startActivity(intent, optionsCompat.toBundle());

    }
}
