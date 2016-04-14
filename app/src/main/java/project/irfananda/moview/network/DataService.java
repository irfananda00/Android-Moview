package project.irfananda.moview.network;

import project.irfananda.moview.model.ApiResponse;
import project.irfananda.moview.model.Company;
import project.irfananda.moview.model.SpecifiedFilm;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class DataService {
    public static final String API_KEY = "1b2f29d43bf2e4f3142530bc6929d341";
    public static final String API_URL = "http://api.themoviedb.org/3/";
    public static final String IMG_URL = "http://image.tmdb.org/t/p/w500/";

    public interface MovieDBApi {
        @GET("movie/{sort_by}?")
        Call<ApiResponse> getMovie(
                @Path("sort_by") String sort,
                @Query("api_key") String appid,
                @Query("page") int page
        );
    }

    public interface SpecifiedMovieDBApi {
        @GET("movie/{id}?")
        Call<SpecifiedFilm> getMovie(
                @Path("id") int id,
                @Query("api_key") String appid
        );
    }

    public interface CompanyDBApi {
        @GET("company/{id}?")
        Call<Company> getCompany(
                @Path("id") int id,
                @Query("api_key") String appid
        );
    }
}
