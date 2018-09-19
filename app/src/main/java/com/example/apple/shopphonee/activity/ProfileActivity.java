package com.example.apple.shopphonee.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.utils.ApiUtils;
import com.example.apple.shopphonee.utils.Constant;
import com.example.apple.shopphonee.utils.UtilsSharePref;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    TextView tvName, tvPhone, tvEmail, tvAdress, tvChooseImg;
    ImageView avatar;
    Toolbar toolbar;
    Button btnSignout, btnEdit, btnHome, btnHistory;
    SharedPreferences sharedPreferences;
    private static final int GALLARY = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private String path;
    private  String imgName = "";
   static Bitmap bitmap;
     String imgCode="image";
    int userID =0;
    String imageUrl;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @Override
    void initView() {

        tvName = this.findViewById(R.id.tv_name_profile);
        tvPhone = this.findViewById(R.id.tv_phone_number_profile);
        tvEmail = this.findViewById(R.id.tv_email_profile);
        tvAdress = this.findViewById(R.id.tv_address_profile);
        toolbar = this.findViewById(R.id.toolbar_profile);
        btnSignout = this.findViewById(R.id.btn_signout_profile);
        btnEdit = this.findViewById(R.id.btn_edit_profile);
        btnHistory = this.findViewById(R.id.btn_history_profile);
        btnHome = this.findViewById(R.id.btn_home_profile);
        tvChooseImg = findViewById(R.id.tv_choose_iamge);
        avatar = findViewById(R.id.im_avatar);

        //get info
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = UtilsSharePref.getSharedPreferences(this);

        tvName.setText(sharedPreferences.getString(Constant.NAME, ""));
        tvPhone.setText(sharedPreferences.getString(Constant.PHONE_NUMBER, ""));
        tvAdress.setText(sharedPreferences.getString(Constant.ADDRESS, ""));
        tvEmail.setText(sharedPreferences.getString(Constant.EMAIL, ""));
        userID = sharedPreferences.getInt(Constant.USER_ID,1);
        imageUrl = sharedPreferences.getString(Constant.IMAGE,"");
        Glide.with(this).load(imageUrl).into(avatar);



    }

    @Override
    int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    void setAction() {

        btnHome.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnSignout.setOnClickListener(this);
        tvChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPictureDialog();
            }
        });
    }

    private void showPictureDialog() {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};

        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
                        choosePhotoFromCamera();
                }
            }
        });
        pictureDialog.show();

    }

    private void choosePhotoFromCamera() {
        captureImage();

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);

    }

    private void captureImage() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        CAMERA_REQUEST_CODE);
            } else {
                // Open your camera here.
            }
        }
    }

    private void choosePhotoFromGallary() {
        Intent gallaryIntent = new Intent();
        gallaryIntent.setType("image/*");
         gallaryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryIntent, GALLARY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLARY) {
            if (data != null){
                Uri uri = data.getData();
                path = ApiUtils.getPath(ProfileActivity.this,uri);
// Get name
                imgName = path.substring(path.lastIndexOf("/")+1);
                try {
                     bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

//                    Toast.makeText(ProfileActivity.this, imageUri.getPath(), Toast.LENGTH_SHORT).show();
                  avatar.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }

            }
        } else if (requestCode == CAMERA_REQUEST_CODE) {
            try{
                if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {

                Uri uri = data.getData();
                path = ApiUtils.getPath(ProfileActivity.this,uri);
// Get name
                imgName = path.substring(path.lastIndexOf("/")+1);
                    bitmap =  (Bitmap)data.getExtras().get("data");
                    avatar.setImageBitmap(bitmap);
                    saveImage(bitmap);
                    Toast.makeText(ProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception ex){
                Toast.makeText(ProfileActivity.this, "No image choosed", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(ProfileActivity.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }


    private String saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public String getBitMap(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.btn_home_profile:
//                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
//                startActivity(intent);
                    uploadImage();



                break;
            case R.id.btn_edit_profile:
                Intent intent1 = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_history_profile:
                Intent intent3 = new Intent(ProfileActivity.this, ListBills.class);
                startActivity(intent3);
                break;
            case R.id.btn_signout_profile:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent2 = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void uploadImage() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploadRetrofit(userID,imgCode);
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                imgCode = getBitMap(bitmap);

                return imgCode;
            }
        }

        UploadImage ui = new UploadImage();
        if(bitmap !=null){
            ui.execute(bitmap);
        }else{
            Toast.makeText(ProfileActivity.this,"Please choose image",Toast.LENGTH_SHORT).show();
        }

    }

    public void uploadRetrofit(int id ,String imgCode){

    class UploadRetrofit extends AsyncTask<String,Void,String>{
           private ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(ProfileActivity.this, "Uploading Image", "Please wait...", true, true);
                loading.setCanceledOnTouchOutside(false);
            }

            @Override
            protected String doInBackground(String... strings) {
                String imgCodeRe = strings[0];
                String strUserId =strings[2];
                String imageNameRe = strings[1];
                int id = Integer.parseInt(strUserId);
                ApiUtils.getAPIService().uploadImage(imgCodeRe,imageNameRe,id).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try {

                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String status = jsonObject.getString("success");
                                if (status.equals("Success")) {
                                    String urlImage = jsonObject.getString("url");
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(Constant.IMAGE,urlImage);
                                    editor.apply();
                                    Log.i("url", urlImage);

                                } else {

                                    Toast.makeText(ProfileActivity.this, "Upload fail", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception ex){
                                Log.i("exception",ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                        Log.i("error",t.getMessage());
                    }
                });
                return imageNameRe;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Toast.makeText(ProfileActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        }

        UploadRetrofit ur = new UploadRetrofit();
        ur.execute(imgCode,imgName,String.valueOf(id));
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}
