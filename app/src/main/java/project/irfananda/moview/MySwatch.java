package project.irfananda.moview;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.widget.TextView;

/**
 * Created by irfananda on 24/04/16.
 */
public class MySwatch{

    public static void setTextViewSwatch(TextView view, Palette.Swatch swatch ) {
        if( swatch != null ) {
            view.setTextColor( swatch.getTitleTextColor() );
            view.setBackgroundColor( swatch.getRgb() );
        }
    }

    public static void setCollapsingToolbarSwatch(CollapsingToolbarLayout view, Palette.Swatch swatch ) {
        if( swatch != null ) {
            view.setCollapsedTitleTextColor( swatch.getTitleTextColor() );
            view.setContentScrimColor( swatch.getRgb() );
        }
    }

    public static void setCardViewSwatch(CardView view, Palette.Swatch swatch ) {
        if( swatch != null ) {
            view.setBackgroundColor( swatch.getRgb() );
        }
    }

}
