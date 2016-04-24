package project.irfananda.moview.network;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;
import project.irfananda.moview.model.ApiResponse;
import project.irfananda.moview.model.Company;
import project.irfananda.moview.model.SpecifiedFilm;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class DataService {
    public static final String API_KEY = "136451254291f50e7661446b9450ede6";
    public static final String API_URL = "http://api.themoviedb.org/3/";
    public static final String IMG_URL = "http://image.tmdb.org/t/p/w500/";

    private Retrofit mRetrofit;

    public DataService() {
        Gson mGson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    }

    public MovieDBApi serviceMovieDBApi(){
        return mRetrofit.create(MovieDBApi.class);
    }

    public interface MovieDBApi {
        @GET("movie/{sort_by}?")
        Call<ApiResponse> getMovie(
                @Path("sort_by") String sort,
                @Query("api_key") String appid,
                @Query("page") int page
        );
    }

    public SearchMovieDBApi serviceSearchMovieDBApi(){
        return mRetrofit.create(SearchMovieDBApi.class);
    }

    public interface SearchMovieDBApi {
        @GET("search/movie?")
        Call<ApiResponse> getMovie(
                @Query("api_key") String appid,
                @Query("query") String query
        );
    }

    public SpecifiedMovieDBApi serviceSpecifiedMovieDBApi(){
        return mRetrofit.create(SpecifiedMovieDBApi.class);
    }

    public interface SpecifiedMovieDBApi {
        @GET("movie/{id}?")
        Call<SpecifiedFilm> getMovie(
                @Path("id") int id,
                @Query("api_key") String appid
        );
    }

    public CompanyDBApi serviceCompanyDBApi(){
        return mRetrofit.create(CompanyDBApi.class);
    }

    public interface CompanyDBApi {
        @GET("company/{id}?")
        Call<Company> getCompany(
                @Path("id") int id,
                @Query("api_key") String appid
        );
    }
}
