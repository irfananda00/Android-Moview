package project.irfananda.moview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.irfananda.moview.R;
import project.irfananda.moview.model.Company;
import project.irfananda.moview.model.ProductionCompanies;
import project.irfananda.moview.network.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MyViewHolder>  {

    private List<ProductionCompanies> production_countries;
    private Context context;
    private Company company;

    public CompanyAdapter(List<ProductionCompanies> production_countries, Context context) {
        this.production_countries = production_countries;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_company_grid, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductionCompanies productionCompaniesBean = production_countries.get(position);
        holder.txt_name.setText(productionCompaniesBean.getName());
        loadData(productionCompaniesBean.getId(),holder);
    }

    @Override
    public int getItemCount() {
        return production_countries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name;
        public ImageView img_poster;

        public MyViewHolder(View view) {
            super(view);
            txt_name= (TextView) view.findViewById(R.id.txt_name);
            img_poster= (ImageView) view.findViewById(R.id.img_poster);
        }
    }

    private void loadData(int id, final MyViewHolder holder) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(DataService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataService.CompanyDBApi simpleService = retrofit.create(
                DataService.CompanyDBApi.class);

        Call<Company> listCall = simpleService.getCompany(
                id,DataService.API_KEY);

        listCall.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.isSuccessful()) {
                    company = response.body();
                    Picasso.with(context)
                            .load(DataService.IMG_URL + company.getLogo_path())
                            .resize(200, 200)
                            .placeholder(R.drawable.ic_do_not_disturb_grey_500_36dp)
                            .into(holder.img_poster);
                } else {
                    Log.i("infoirfan","Fail access company site");
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                Log.i("infoirfan", "Message : "+t.getMessage());
            }
        });
    }

}
