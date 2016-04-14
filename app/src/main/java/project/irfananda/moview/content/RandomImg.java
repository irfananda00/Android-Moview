package project.irfananda.moview.content;

import java.util.Random;

import project.irfananda.moview.R;

public class RandomImg {
    private static int[] colorID = {
            R.color.colorCard1,
            R.color.colorCard2,
            R.color.colorCard3,
            R.color.colorCard4,
            R.color.colorCard5,
            R.color.colorCard6,
            R.color.colorCard7,
            R.color.colorCard8,
            R.color.colorCard9,
            R.color.colorCard10,
            R.color.colorCard11
    };

    public static int getColorID() {
        Random rand = new Random();
        int randomNum = rand.nextInt(10);
        return colorID[randomNum];
    }
}
