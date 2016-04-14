package project.irfananda.moview.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import project.irfananda.moview.R;
import project.irfananda.moview.content.RandomImg;
import project.irfananda.moview.model.Film;
import project.irfananda.moview.network.DataService;


public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder>  {

    private List<Film> filmList;
    private Context context;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

    public GridAdapter(List<Film> filmList, Context context) {
        this.filmList = filmList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_film_grid, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Film film = filmList.get(position);
        holder.txt_title.setText(film.getTitle());
        String release_date = film.getRelease_date();
        try {
            Date date = parser.parse(release_date);
            calendar.setTime(date);
            holder.txt_release_date.setText(""+calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Picasso.with(context)
                .load(DataService.IMG_URL+film.getPoster_path())
                .resize(377,490)
                .placeholder(R.drawable.img_fail)
                .into(holder.img_poster);
        holder.bg_desc.setBackgroundResource(RandomImg.getColorID());
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_title;
        public TextView txt_release_date;
        public CardView bg_desc;
        public ImageView img_poster;

        public MyViewHolder(View view) {
            super(view);
            txt_title= (TextView) view.findViewById(R.id.txt_title);
            txt_release_date = (TextView) view.findViewById(R.id.txt_date);
            bg_desc= (CardView) view.findViewById(R.id.bg_desc);
            img_poster= (ImageView) view.findViewById(R.id.img_poster);
        }
    }

}
