package project.irfananda.moview.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import project.irfananda.moview.ClickListener;
import project.irfananda.moview.GlobalData;
import project.irfananda.moview.R;
import project.irfananda.moview.activity.DetailActivity;
import project.irfananda.moview.adapter.GridAdapter;
import project.irfananda.moview.model.ApiResponse;
import project.irfananda.moview.model.Film;
import project.irfananda.moview.network.DataService;
import project.irfananda.moview.recyclerView.CustomGridLayoutManager;
import project.irfananda.moview.recyclerView.EndlessRecyclerViewScrollListener;
import project.irfananda.moview.recyclerView.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GridFragment extends Fragment {

    private List<Film> mData = new ArrayList<>();
    private ApiResponse apiResponse;
    private RecyclerView rv;
    private GridAdapter gridAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CustomGridLayoutManager mLayoutManager;
    private int pageAPI;
    private String searchQuery;

    public GridFragment() {
        searchQuery="null";
    }

    public GridFragment(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridAdapter = new GridAdapter(mData,getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorCard1,
                R.color.colorCard2,
                R.color.colorCard3,
                R.color.colorCard4,
                R.color.colorCard5);
        rv = (RecyclerView) v.findViewById(R.id.rv);

        if(getResources().getConfiguration().orientation==1) {
            //portrait
            mLayoutManager = new CustomGridLayoutManager(getActivity(), 2);
            mLayoutManager.setOrientation(CustomGridLayoutManager.VERTICAL);
            rv.setLayoutManager(mLayoutManager);
        }else if(getResources().getConfiguration().orientation==2){
            //landscape
            mLayoutManager = new CustomGridLayoutManager(getActivity(), 3);
            mLayoutManager.setOrientation(CustomGridLayoutManager.VERTICAL);
            rv.setLayoutManager(mLayoutManager);
        }

        rv.setAdapter(gridAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageAPI = 1;
                mData.clear();
                mLayoutManager.setScrollEnable(false);
                loadData();
            }
        });

        rv.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Film film = mData.get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("filmID",film.getId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        rv.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                pageAPI++;
                if(pageAPI<=GlobalData.maxPage)
                    loadData();
            }
        });

        pageAPI = 1;
        mLayoutManager.setScrollEnable(false);
        loadData();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(GlobalData.refresh) {
            pageAPI = 1;
            mData.clear();
            mLayoutManager.setScrollEnable(false);
            loadData();
            GlobalData.refresh = false;
        }
    }

    private void loadData() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        setGlobalData();

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(DataService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<ApiResponse> listCall;
        if(searchQuery.equalsIgnoreCase("null")) {
            DataService.MovieDBApi simpleService = retrofit.create(
                    DataService.MovieDBApi.class);

            listCall = simpleService.getMovie(
                    GlobalData.key, DataService.API_KEY, pageAPI);
        }else {
            DataService.SearchMovieDBApi simpleService = retrofit.create(
                    DataService.SearchMovieDBApi.class);

            listCall = simpleService.getMovie(
                    DataService.API_KEY, searchQuery);
        }
        listCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    apiResponse = response.body();
                    for (int i = 0; i < apiResponse.getResults().size(); i++) {
                        Film film = apiResponse.getResults().get(i);
                        GlobalData.maxPage = apiResponse.getTotal_pages();
                        mData.add(film);
                    }
                    gridAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    mLayoutManager.setScrollEnable(true);
                    searchQuery="null";
                } else {
                    Snackbar.make(getView(), "Fail access server", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadData();
                                }
                            }).show();
                    swipeRefreshLayout.setRefreshing(false);
                    mLayoutManager.setScrollEnable(true);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("infoirfan", "Message : "+t.getMessage());
                Snackbar.make(getView(), "No internet connection", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadData();
                            }
                        }).show();
                swipeRefreshLayout.setRefreshing(false);
                mLayoutManager.setScrollEnable(true);
            }
        });
    }


    private void setGlobalData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        GlobalData.key = sharedPreferences.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_popular));
    }
}

