package com.android.omdb.presenter;

import com.android.omdb.model.MovieResponse;
import com.android.omdb.network.ApiClient;
import com.android.omdb.network.ApiInterface;
import com.android.omdb.view.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDetailPresenter {

    private final ApiInterface mApiService;
    private final SearchDetailListener mListener;

    public SearchDetailPresenter(SearchDetailListener listener) {
        mApiService = ApiClient.getClient().create(ApiInterface.class);
        mListener = listener;
    }


    public void getDetails(String movieId) {
        mApiService.getSearchResultDetail(movieId, MainActivity.API_KEY)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response != null && response.body().getResponse().equalsIgnoreCase("true")) {
                            mListener.onSuccessListener(response.body());
                        } else {
                            mListener.onError(response.body().getError());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        mListener.onError("Something went wrong");
                    }
                });

    }

    public interface SearchDetailListener {
        void onSuccessListener(MovieResponse movieResponse);

        void onError(String msg);

    }
}
