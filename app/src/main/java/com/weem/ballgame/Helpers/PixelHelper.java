package com.weem.ballgame.Helpers;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by shailendra on 11/12/17.
 */

public class PixelHelper {
     public static int PixelToDp(int px, Context context){

         return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                 px,context.getResources().getDisplayMetrics());





     }



}
