package com.example.apple.shopphonee.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apple.shopphonee.activity.DetailProduct;
import com.example.apple.shopphonee.activity.MainActivity;
import com.example.apple.shopphonee.activity.ShoppingActivity;
import com.example.apple.shopphonee.model.APIService;
import com.example.apple.shopphonee.model.Account;
import com.example.apple.shopphonee.model.Cart;
import com.example.apple.shopphonee.model.DataLogin;
import com.example.apple.shopphonee.model.Product;
import com.example.apple.shopphonee.model.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiUtils {
    //192.168.100.31
//
//192.168.0.104
    //10.22.185.162
    public final static String BASE_URL = "http://192.168.1.5/server/";

    public static APIService getAPIService() {
        return RetrofitClient.getRetrofitClient(BASE_URL).create(APIService.class);
    }

    public static void loadImage(String url, ImageView imageView, Context context) {
        Glide.with(context).load(url).into(imageView);
    }


    public static void addItemToCart(Context context, Product product){
        if(MainActivity.cartList.size()<10){

            if (MainActivity.cartList.size() > 0) {
                boolean exist =false ;
                for (int i = 0; i < MainActivity.cartList.size(); i++) {
                    Cart c =MainActivity.cartList.get(i);
                    if (product.getId().equals(c.getProductId()))  {
                        c.setQuantily(c.getQuantily() + 1);
                        if (c.getQuantily() > 10) {
                            c.setQuantily(10);
                        }
                        exist = true;
                    }
                }
                if (!exist) {
                    DetailProduct.addToList(1,product);
                }
            } else {
                DetailProduct.addToList(1,product);
                Log.i("3", "3");
            }
            Intent intent = new Intent(context, ShoppingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

        }else {
            Toast.makeText(context,"Cart has been full",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ShoppingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    /// get path API >19 KITKAT
    public static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return "image";
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}



