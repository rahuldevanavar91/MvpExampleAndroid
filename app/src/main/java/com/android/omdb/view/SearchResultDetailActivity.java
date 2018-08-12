package com.android.omdb.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.omdb.R;
import com.android.omdb.model.MovieResponse;
import com.android.omdb.presenter.SearchDetailPresenter;
import com.squareup.picasso.Picasso;

public class SearchResultDetailActivity extends AppCompatActivity implements SearchDetailPresenter.SearchDetailListener {

    private TextView mRelaseDate;
    private TextView mGenre;
    private TextView mDirector;
    private TextView mWriter;
    private TextView mActors;
    private TextView mPlot;
    private TextView mLangauge;
    private TextView mRating;
    private TextView mAwards;
    private ImageView mPoster;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        getWidgets();
        initPresenter();
    }

    private void initPresenter() {
        SearchDetailPresenter mResultDetailPresenter = new SearchDetailPresenter(this);
        mResultDetailPresenter.getDetails(getIntent().getStringExtra(getString(R.string.movie_id)));
    }

    private void getWidgets() {
        mRelaseDate = findViewById(R.id.release);
        mGenre = findViewById(R.id.genre);
        mDirector = findViewById(R.id.director);
        mWriter = findViewById(R.id.writer);
        mActors = findViewById(R.id.actors);
        mPlot = findViewById(R.id.plot);
        mLangauge = findViewById(R.id.language);
        mRating = findViewById(R.id.rating);
        mAwards = findViewById(R.id.awards);
        mPoster = findViewById(R.id.posterr);
        mProgressBar = findViewById(R.id.progress_bar);
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setTitle(getIntent().getStringExtra(getString(R.string.movie_name)));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPoster.setTransitionName(getIntent().getStringExtra(getString(R.string.movie_id)));
        }
        setPoster(getIntent().getStringExtra(getString(R.string.poster_image)));
    }

    private void setPoster(String image) {
        Picasso.get()
                .load(image)
                .into(mPoster);
    }

    @Override
    public void onSuccessListener(MovieResponse movieResponse) {
        mProgressBar.setVisibility(View.GONE);
        mRelaseDate.setText("Release Date : " + movieResponse.getReleased());
        mGenre.setText("Genre : " + movieResponse.getGenre());
        mDirector.setText("Director : " + movieResponse.getDirector());
        mWriter.setText("Writer : " + movieResponse.getWriter());
        mActors.setText("Actors : " + movieResponse.getActors());
        mLangauge.setText("Language : " + movieResponse.getLanguage());
        mRating.setText("Rating : " + movieResponse.getImdbRating());
        mAwards.setText("Awards : " + movieResponse.getAwards());
        mPlot.setText("Plot : " + movieResponse.getPlot());
        setPoster(movieResponse.getPoster());
    }

    @Override
    public void onError(String msg) {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
