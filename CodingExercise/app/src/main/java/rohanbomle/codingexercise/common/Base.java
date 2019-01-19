package rohanbomle.codingexercise.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/***
 * Base class
 * USE TO : define all static keywords, parameter name used in app
 */
public class Base {

    public static final String ONLINE_MUSIC = ".online_music";
    public static final String OFFLINE_MUSIC = ".offline_music";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AlbumCollection.db";

    public static final String DATABASE_SORT_ASC = "ASC";
    public static final String DATABASE_SORT_DESC = "DESC";


    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

}
