package development.dreamcatcher.endclothingapp.utils

import android.content.Context
import android.util.DisplayMetrics

class Converters {

    companion object {

        fun convertPxToDp(px: Int, context: Context): Int {
            return Integer.valueOf(px / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT))
        }
    }
}