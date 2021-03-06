package com.example.apple.shopphonee.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.adapter.NavigationAdapter;
import com.example.apple.shopphonee.adapter.HomeAdapter;
import com.example.apple.shopphonee.database.SQLiteUtils;
import com.example.apple.shopphonee.database.ShopSQLiteHelper;
import com.example.apple.shopphonee.model.APIService;
import com.example.apple.shopphonee.model.Account;
import com.example.apple.shopphonee.model.Cart;
import com.example.apple.shopphonee.model.Category;
import com.example.apple.shopphonee.model.OnPosListener;
import com.example.apple.shopphonee.model.Product;
import com.example.apple.shopphonee.utils.ApiUtils;
import com.example.apple.shopphonee.utils.Constant;
import com.example.apple.shopphonee.utils.UtilsSharePref;

import java.io.Serializable;
import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private ViewFlipper viewFlipper;
    NavigationView navigationView;
    private RecyclerView listView;
    private RecyclerView listPhone;
    private DrawerLayout drawerLayout;
    private APIService apiService;
    private NavigationAdapter adapter;
    private HomeAdapter phoneAdapter;
    TextView userNameNavTv;
    CircleImageView userAvatarIm;
    private List<Category> categories = new ArrayList<Category>();
    public static List<Product> phones = new ArrayList<Product>();
    private ApiUtils apiUtils;
    SharedPreferences sharedPreferences;
    public static List<Cart> cartList;
    public static int count=0;
    @Override
    public boolean isTaskRoot() {
        return super.isTaskRoot();
    }

    SQLiteUtils sqLiteUtils = new SQLiteUtils(this);
    ShopSQLiteHelper shopSQLiteHelper = new ShopSQLiteHelper(this);

    @Override
    void initView() {

        toolbar =  this.findViewById(R.id.toolbar_home);
        viewFlipper = this.findViewById(R.id.viewFliper_home);
        navigationView = this.findViewById(R.id.navigationview);
        listView =  this.findViewById(R.id.list_view_navigation);
        drawerLayout =  this.findViewById(R.id.drawer_layout);
        listPhone = this.findViewById(R.id.rv_recycler_view_home);
        userNameNavTv  = findViewById(R.id.tv_username_nav);
        userAvatarIm = findViewById(R.id.im_avatar_nav);
        sharedPreferences = UtilsSharePref.getSharedPreferences(this);
        sqLiteUtils.open();

        //init info in navigationminh1992

        userNameNavTv.setText(sharedPreferences.getString(Constant.NAME,""));
        Glide.with(this).load(sharedPreferences.getString(Constant.IMAGE,"")).into(userAvatarIm);

        count++;
        //intilizae listcart
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        //intilize apiservice
        if(count==1){
          cartList =sqLiteUtils.getAll();
        }
        apiService = ApiUtils.getAPIService();

        //navigation
        initViewNavigation();
        loadData();

        //list phone
        initViewListPhone();
        loadPhoneData();

        //setActionBar
        setActionBar();

        //ViewFliper
        setActionViewFliper();
    }

    //init view navigation
    void initViewNavigation() {

        adapter = new NavigationAdapter(categories, MainActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_item_diary));
        listView.addItemDecoration(itemDecoration);
        listView.setAdapter(adapter);
    }

    //init view phone
    void initViewListPhone() {
        phoneAdapter = new HomeAdapter(phones, getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        listPhone.setAdapter(phoneAdapter);
        listPhone.setLayoutManager(layoutManager);
    }

    private void setActionViewFliper() {

        ArrayList<String> listImage = new ArrayList<>();
        listImage.add("http://www.veganbeautyreview.com/wp-content/uploads/2015/05/advertise-here.jpg");
        listImage.add("http://www.qubsu.org/Advertisewithus/CentredImage,426468,en.jpg");
        listImage.add("https://cpb-us-w2.wpmucdn.com/u.osu.edu/dist/2/12114/files/2015/03/iPhone-650x400-1x0oxnh.jpg");
        listImage.add("https://image.thanhnien.vn/1600/uploaded/nthanhluan/2017_09_13/0_musk.jpg");
        for (String t : listImage) {
            ImageView imageView = new ImageView(this);
            Glide.with(this).load(t).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

//        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_Fliper_in = AnimationUtils.loadAnimation(this, R.anim.in_from_left);
        Animation animation_out = AnimationUtils.loadAnimation(this, R.anim.out_from_right);
        viewFlipper.setInAnimation(animation_Fliper_in);
        viewFlipper.setOutAnimation(animation_out);


    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    void setAction() {
        //action show navigationbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        phoneAdapter.getPositionHome(new OnPosListener() {
            @Override
            public void getPositioin(int position) {
                Product product = phones.get(position);
                Intent intent = new Intent(MainActivity.this,DetailProduct.class);
                intent.putExtra("product", (Serializable) product);
                startActivity(intent);
            }
        });
    }

    void loadData() {
        apiService.getCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categories = response.body();
                    adapter.updateList(categories);
                    adapter.notifyDataSetChanged();
                    // Log.i("Success", "Request success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                Log.i("Error", t.getMessage());
            }
        });
    }

    void loadPhoneData() {
        apiService.getLatest().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {

                if (response.isSuccessful()) {
                    phones = response.body();
                    phoneAdapter.updateList(phones);
                    phoneAdapter.notifyDataSetChanged();
                    Log.i("Success", String.valueOf(phones.size()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.shopping_cart:
                Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
                startActivity(intent);
                break;
            case R.id.profile:
                if(sharedPreferences.getBoolean(Constant.LOGGED_IN,false)){
                    Intent intent2 = new Intent(this,ProfileActivity.class);
                    startActivity(intent2);
                    break;
                }else {

                    Intent intent1 = new Intent(this,LoginActivity.class);
                    startActivity(intent1);
                    break;
                }
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
