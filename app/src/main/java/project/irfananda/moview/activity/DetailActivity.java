package project.irfananda.moview.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import project.irfananda.moview.GlobalData;
import project.irfananda.moview.R;
import project.irfananda.moview.MySwatch;
import project.irfananda.moview.adapter.CompanyAdapter;
import project.irfananda.moview.model.SpecifiedFilm;
import project.irfananda.moview.network.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private SpecifiedFilm specifiedFilm;
    private CollapsingToolbarLayout toolbar_layout;
    private CoordinatorLayout coordinatorLayout;
    private int filmID;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ImageView img_poster;
    private TextView txt_overview;
    private TextView txt_title;
    private TextView txt_date;
    private TextView txt_genre;
    private TextView txt_lang;
    private TextView txt_duration;
    private TextView txt_homepage;
    private TextView txt_status;
    private TextView txt_rateGood;
    private TextView txt_vote;
    private TextView txt_countries;
    private RecyclerView rv;
    private DataService mService;
    private Call<SpecifiedFilm> mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(GlobalData.theme.equalsIgnoreCase("dark"))
            setTheme(R.style.AppThemeDark_Base);
        else if(GlobalData.theme.equalsIgnoreCase("light"))
            setTheme(R.style.AppThemeLight_Base);
        else
            setTheme(R.style.AppThemeDark_Base);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Loading..");

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        filmID = getIntent().getIntExtra("filmID",0);
        mService = new DataService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(filmID!=0) {
            loadData();
        }
    }

    private void loadData() {
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        DataService.SpecifiedMovieDBApi simpleService = mService.serviceSpecifiedMovieDBApi();

        mCall = simpleService.getMovie(
                filmID,DataService.API_KEY);

        mCall.enqueue(new Callback<SpecifiedFilm>() {
            @Override
            public void onResponse(Call<SpecifiedFilm> call, Response<SpecifiedFilm> response) {
                if (response.isSuccessful()) {
                    specifiedFilm = response.body();
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.GONE);
                    inflateContent();
                    populateData();
                } else {
                    Snackbar.make(coordinatorLayout, "Fail access server", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadData();
                                }
                            }).show();
                    getSupportActionBar().setTitle("Fail access server");
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SpecifiedFilm> call, Throwable t) {
                Log.i("infoirfan", "Message : "+t.getMessage());
                Snackbar.make(coordinatorLayout, "No internet connection", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadData();
                            }
                        }).show();
                getSupportActionBar().setTitle("No internet connection");
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home ) {
            finish();
        }else if(item.getItemId() == R.id.action_refresh ){
            loadData();
        }
        // other menu select events may be present here

        return super.onOptionsItemSelected(item);
    }

    private void inflateContent() {
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        if (stub != null) {
            stub.setLayoutResource(R.layout.content_detail);
            stub.inflate();
            img_poster = (ImageView) findViewById(R.id.img_poster);
            txt_overview = (TextView) findViewById(R.id.txt_overview);
            txt_title = (TextView) findViewById(R.id.txt_title);
            txt_date = (TextView) findViewById(R.id.txt_date);
            txt_genre = (TextView) findViewById(R.id.txt_genre);
            txt_lang = (TextView) findViewById(R.id.txt_lang);
            txt_duration = (TextView) findViewById(R.id.txt_duration);
            txt_homepage = (TextView) findViewById(R.id.txt_homepage);
            txt_status = (TextView) findViewById(R.id.txt_status);
            txt_rateGood = (TextView) findViewById(R.id.txt_rateGood);
            txt_vote = (TextView) findViewById(R.id.txt_vote);
            txt_countries = (TextView) findViewById(R.id.txt_countries);

            rv = (RecyclerView) findViewById(R.id.rv);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv.setLayoutManager(mLayoutManager);

            CompanyAdapter companyAdapter = new CompanyAdapter(specifiedFilm.getProduction_companies(),this);

            rv.setAdapter(companyAdapter);
        }
    }

    private void populateData(){
        txt_title.setText(specifiedFilm.getTitle());
        txt_date.setText(specifiedFilm.getRelease_date());
        Picasso.with(this)
                .load(DataService.IMG_URL+specifiedFilm.getBackdrop_path())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        toolbar_layout.setBackground(new BitmapDrawable(getResources(),bitmap));
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                MySwatch.setCollapsingToolbarSwatch( toolbar_layout, palette.getMutedSwatch());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Window window = getWindow();
                                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                    window.setStatusBarColor(palette.getDarkMutedColor(getResources().getColor(android.R.color.black)));
                                }
                                MySwatch.setTextViewSwatch( txt_title, palette.getMutedSwatch() );
                                MySwatch.setTextViewSwatch( txt_status, palette.getVibrantSwatch() );
                                MySwatch.setTextViewSwatch( txt_rateGood, palette.getVibrantSwatch() );
                                MySwatch.setTextViewSwatch( txt_vote, palette.getVibrantSwatch() );
                            }
                        });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
        Picasso.with(this)
                .load(DataService.IMG_URL+specifiedFilm.getPoster_path())
                .resize(280,400)
                .placeholder(R.drawable.imgnotfound)
                .into(img_poster);
        txt_overview.setText(specifiedFilm.getOverview());
        String genre = "";
        for (int i = 0; i < specifiedFilm.getGenres().size();i++){
            if(i==specifiedFilm.getGenres().size()-1)
                genre = genre + specifiedFilm.getGenres().get(i).getName();
            else
                genre = genre + specifiedFilm.getGenres().get(i).getName() + ", ";
        }
        txt_genre.setText(genre);
        String language = "";
        for (int i=0;i<specifiedFilm.getSpoken_languages().size();i++){
            if(i==specifiedFilm.getSpoken_languages().size()-1)
                language = language + specifiedFilm.getSpoken_languages().get(i).getName();
            else
                language = language + specifiedFilm.getSpoken_languages().get(i).getName() + ", ";
        }
        txt_lang.setText(language);
        toolbar_layout.setTitle(specifiedFilm.getTitle());
        txt_duration.setText(specifiedFilm.getRuntime()+" minutes");
        txt_homepage.setText(specifiedFilm.getHomepage());
        txt_status.setText(specifiedFilm.getStatus());
        String country = "";
        for (int i = 0; i < specifiedFilm.getProduction_countries().size();i++){
            if(i==specifiedFilm.getProduction_countries().size()-1)
                country = country + specifiedFilm.getProduction_countries().get(i).getName();
            else
                country = country + specifiedFilm.getProduction_countries().get(i).getName() + ", ";
        }
        txt_countries.setText(country);
        txt_rateGood.setText("Rating : "+specifiedFilm.getVote_average());
        txt_vote.setText(    "Vote     : "+specifiedFilm.getVote_count());
    }



}
