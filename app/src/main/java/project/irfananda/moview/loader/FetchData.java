package project.irfananda.moview.loader;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import io.realm.Realm;
import okhttp3.Request;
import project.irfananda.moview.GlobalData;
import project.irfananda.moview.data.DataManager;
import project.irfananda.moview.model.ApiResponse;
import project.irfananda.moview.model.Film;
import project.irfananda.moview.model.SpecifiedFilm;
import project.irfananda.moview.network.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by irfananda on 19/04/16.
 */
public class FetchData {

//    private DataService mService;
//    private Call<ApiResponse> mCall;
//    private Call<SpecifiedFilm> mCallSpecified;
//    private Context mContext;
//    private Realm mRealm;
//    private int pageAPI;
//
//    public FetchData() {
//        mRealm = Realm.getDefaultInstance();
//        mService = new DataService();
//    }
//
//    public FetchData(int pageAPI) {
//        mRealm = Realm.getDefaultInstance();
//        mService = new DataService();
//        this.pageAPI = pageAPI;
//    }
//
//    public void getListMovieData(String searchQuery){
//        if(searchQuery.equalsIgnoreCase("null")) {
//            DataService.MovieDBApi movieDBApi = mService.serviceMovieDBApi();
//            mCall = movieDBApi.getMovie(
//                    GlobalData.key, DataService.API_KEY, pageAPI
//            );
//        }else {
//            DataService.SearchMovieDBApi movieDBApi = mService.serviceSearchMovieDBApi();
//            mCall = movieDBApi.getMovie(
//                    DataService.API_KEY, searchQuery
//            );
//        }
//        mCall.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, final Response<ApiResponse> response) {
//                if (response.isSuccessful()) {
//                    mRealm.executeTransactionAsync(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            ApiResponse contentResponse = response.body();
//                            realm.copyToRealmOrUpdate(contentResponse);
//                            DataManager dataManager = new DataManager();
//                            int id;
//                            for (int i = 0; i < contentResponse.getResults().size(); i++){
//                                id = dataManager.findMovieId(i);
//                                DataService.SpecifiedMovieDBApi specifiedMovieDBApi = mService.serviceSpecifiedMovieDBApi();
//                                mCallSpecified = specifiedMovieDBApi.getMovie(
//                                        id,DataService.API_KEY
//                                );
//                                mCallSpecified.enqueue(new retrofit2.Callback<SpecifiedFilm>() {
//                                    @Override
//                                    public void onResponse(Call<SpecifiedFilm> call, final Response<SpecifiedFilm> response) {
//                                        if (response.isSuccessful()) {
//                                            mRealm.executeTransactionAsync(new Realm.Transaction() {
//                                                @Override
//                                                public void execute(Realm realm) {
//                                                    SpecifiedFilm specifiedFilm = response.body();
//                                                    realm.copyToRealmOrUpdate(specifiedFilm);
//                                                }
//                                            }, new OnSuccess() {
//                                                @Override
//                                                public void onSuccess() {
//                                                    Log.i("infoirfan","success execute to realm");
//                                                }
//                                            }, new OnError() {
//                                                @Override
//                                                public void onError(Throwable error) {
//                                                    Log.i("infoirfan","fail execute to realm");
//                                                }
//                                            });
//                                        }else{
//                                            Log.i("infoirfan","fail access server");
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<SpecifiedFilm> call, Throwable t) {
//                                        Log.i("infoirfan","fail response : "+t.getMessage());
//                                    }
//                                });
//                            }
//                        }
//                    }, new Realm.Transaction.OnSuccess() {
//                        @Override
//                        public void onSuccess() {
//                            Log.i("infoirfan","success execute to realm");
//                        }
//                    }, new Realm.Transaction.OnError() {
//                        @Override
//                        public void onError(Throwable error) {
//                            Log.i("infoirfan","fail execute to realm");
//                        }
//                    });
//                } else {
//                    Log.i("infoirfan","fail access server");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                Log.i("infoirfan","fail response : "+t.getMessage());
//            }
//        });
//
//    }

}
