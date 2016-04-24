package project.irfananda.moview.data;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import project.irfananda.moview.model.ApiResponse;
import project.irfananda.moview.model.Film;
import project.irfananda.moview.model.SpecifiedFilm;

/**
 * Created by irfananda on 20/04/16.
 */
public class DataManager {

//    private Realm mRealm;
//    private RealmResults<ApiResponse> mResults;
//
//    public DataManager() {
//        mRealm = Realm.getDefaultInstance();
//    }
//
//    public boolean checkRecord() {
//        mResults = mRealm.where(ApiResponse.class).findAll();
//        return mResults.size() != 0;
//    }
//
//    public RealmResults<ApiResponse> dataAll() {
//        mResults = mRealm.where(ApiResponse.class).findAll();
//        return mResults;
//    }
//
//    public static boolean existsRecord() {
////        Realm realm = Realm.getInstance(context);
//        Realm realm = Realm.getDefaultInstance();
//        RealmResults<ApiResponse> contents = realm.allObjects(ApiResponse.class);
//        return contents.size() != 0;
//    }
//
//    public ApiResponse findData() {
////        Realm realm = Realm.getInstance(context);
////        Realm realm = Realm.getDefaultInstance();
////        RealmResults<ApiContent> apiResponses = realm.allObjects(ApiContent.class);
//
//        // Build the query looking at all object:
//        RealmQuery<ApiResponse> query = mRealm.where(ApiResponse.class);
//        //Execute the query
//        RealmResults<ApiResponse> results = query.findAll();
//
//        return results.first();
//    }
//
//    public int findMovieId(int idx) {
//        RealmResults<ApiResponse> results = mRealm.where(ApiResponse.class).findAll();
//        int specifiedFilmId = results.first().getResults().get(idx).getId();
//
//        return  specifiedFilmId;
//    }
//
//    public List<Film> getAllFilm(int page){
//        RealmResults<ApiResponse> results = mRealm.where(ApiResponse.class).findAll();
//        Log.i("infoirfan","size apiresponse realm : "+results.size());
//        List<Film> filmList = results.first().getResults();
//
//        return filmList;
//    }
//
////    public SpecifiedFilm getFilm(int id){
////        RealmResults<SpecifiedFilm> results = mRealm.where(SpecifiedFilm.class).findAll();
////        SpecifiedFilm specifiedFilm = results.;
////    }
}
