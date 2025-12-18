package com.example.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

public class JemsProvider {
   public static  boolean isInternetAvailable(Context context) {
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);

            if (networkCapabilities != null) {
                isConnected = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
            }
        } else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo != null) {
                isConnected = activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI ||
                        activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE ||
                        activeNetworkInfo.getType() == ConnectivityManager.TYPE_ETHERNET;
            }
        }
        if (!isConnected) {
            showNoInternetDialog(context);
        }
        return isConnected;
    }

    static AlertDialog alertDialog = null;

    private static void showNoInternetDialog(Context context) {
        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Open device settings to enable internet
                        dialog.dismiss();
                        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                })
                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Exit the app or take any other action
                        dialog.dismiss();
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }

}
