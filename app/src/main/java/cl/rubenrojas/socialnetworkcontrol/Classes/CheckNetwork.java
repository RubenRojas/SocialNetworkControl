package cl.rubenrojas.socialnetworkcontrol.Classes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by rubro on 09-08-2016.
 */
public class CheckNetwork {
    private static final String TAG = "Develop";
    public static boolean isInternetAvailable(Context context)
    {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null)
        {
            //Log.d(TAG,"no internet connection");
            return false;
        }
        else
        {
            if(info.isConnected())
            {
                //Log.d(TAG," internet connection available...");
                return true;
            }
            else
            {
                //Log.d(TAG," internet connection");
                return true;
            }

        }
    }
}