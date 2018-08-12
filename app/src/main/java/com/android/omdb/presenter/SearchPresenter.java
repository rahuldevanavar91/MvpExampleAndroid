package com.android.omdb.presenter;

import com.android.omdb.model.SearchItem;
import com.android.omdb.model.SearchResponse;
import com.android.omdb.network.ApiClient;
import com.android.omdb.network.ApiInterface;
import com.android.omdb.view.MainActivity;
import com.android.omdb.view.adapter.SearchAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchPresenter {

    private final ApiInterface mApiService;
    private ArrayList<SearchItem> mSearchList;
    private SearchResultListener mSearchResultListener;

    public SearchPresenter(SearchResultListener resultListener) {
        mApiService = ApiClient.getClient().create(ApiInterface.class);
        mSearchResultListener = resultListener;
    }

    public void getSearchResult(String searchTerm, final int page) {
        mApiService.getSearchResult(searchTerm, MainActivity.API_KEY, String.valueOf(page)).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, retrofit2.Response<SearchResponse> response) {
                if (response != null && response.body().getResponse().equalsIgnoreCase("true")) {
                    prepareList(response.body().getSearch(), response.body().getTotalResults(),page==1);
                } else {
                    mSearchResultListener.onError(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                mSearchResultListener.onError("Something went wrong");

            }
        });

    }

    private void prepareList(ArrayList<SearchItem> search, String totalResults,boolean isAddAll) {
        if (mSearchList != null && !isAddAll) {
            if (mSearchList.get(mSearchList.size() - 1).getViewType() == SearchAdapter.VIEW_TYPE_MORE_LOADING) {
                mSearchList.remove(mSearchList.size() - 1);
            }
            mSearchList.addAll(search);
        } else {
            mSearchList = new ArrayList<>();
            mSearchList.addAll(search);
        }
        if (Integer.parseInt(totalResults) > mSearchList.size()) {
            SearchItem viewMoreItem = new SearchItem();
            viewMoreItem.setViewType(SearchAdapter.VIEW_TYPE_MORE_LOADING);
            mSearchList.add(viewMoreItem);
        }
        mSearchResultListener.onResult(mSearchList);
    }


    public interface SearchResultListener {
        void onResult(ArrayList<SearchItem> result);

        void onError(String msg);
    }
}
