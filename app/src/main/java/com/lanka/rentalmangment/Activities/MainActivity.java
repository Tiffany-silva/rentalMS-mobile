package com.lanka.rentalmangment.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.Fragments.CategoryFragment;
import com.lanka.rentalmangment.Fragments.HomeFragment;
//import com.lanka.rentalmangment.Fragments.OrdersFragment;
//import com.lanka.rentalmangment.Fragments.ProfileFragment;
import com.lanka.rentalmangment.Fragments.OrdersFragment;
import com.lanka.rentalmangment.Fragments.ProfileFragment;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.RetrofitAPIService.UserService;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.lanka.rentalmangment.databinding.DrawerHeaderBinding;
import com.lanka.rentalmangment.databinding.NavHeaderMainBinding;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.InputStream;
import java.net.URL;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding activityMainBinding;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    ImageView profile;
    TextView name, email;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        toolbar = activityMainBinding.appNavBar.tbToolBar;
        setSupportActionBar(toolbar);

        LoginResponse loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).userget();

        NavigationView navigationView = activityMainBinding.navigationView;
        View headerView = activityMainBinding.navigationView.getHeaderView(0);
        DrawerHeaderBinding headerBinding = DrawerHeaderBinding.bind(headerView);
        name=headerBinding.profileName;
        profile=headerBinding.profileImageId;
        email=headerBinding.profileEmail;
        Picasso.get()
                .load(loginResponse.getImageUrl())
                .transform(new CropCircleTransformation())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(profile);

        name.setText(loginResponse.getUsername());
        email.setText(loginResponse.getEmail());
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navHome));

        navigationView.setCheckedItem(R.id.navHome);

        NavigationView navView = activityMainBinding.navigationView;
        DrawerLayout drawerLayout = activityMainBinding.drawerLayout;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);

    }
    public void onLogout() {
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).clear();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavigationView navigationView = findViewById(R.id.navigationView);
        DrawerLayout drawerLayout = activityMainBinding.drawerLayout;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        if (toggle.isDrawerIndicatorEnabled()
                && toggle.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment current_fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (current_fragment instanceof HomeFragment) {
            super.onBackPressed();
        }
        else {
            NavigationView navigationView = findViewById(R.id.navigationView);
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navHome));
            navigationView.setCheckedItem(R.id.navHome);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = new Fragment();

        //fragments are created according to user demand
        if (id == R.id.navHome) {
            fragment = new HomeFragment();
        }  else if (id == R.id.navProfile) {
            fragment = new ProfileFragment();
        } else if (id == R.id.navRentals) {
            fragment = new OrdersFragment();
        } else if (id == R.id.navLogout) {
            onLogout();
        }else if(id == R.id.navCategory){
            fragment= new CategoryFragment();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
