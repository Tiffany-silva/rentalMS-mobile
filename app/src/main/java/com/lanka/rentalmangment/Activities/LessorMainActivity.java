package com.lanka.rentalmangment.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.Fragments.AddProductFragment;
import com.lanka.rentalmangment.Fragments.CategoryFragment;
import com.lanka.rentalmangment.Fragments.HomeFragment;
//import com.lanka.rentalmangment.Fragments.OrdersFragment;
//import com.lanka.rentalmangment.Fragments.ProfileFragment;
import com.lanka.rentalmangment.Fragments.OrdersFragment;
import com.lanka.rentalmangment.Fragments.ProfileFragment;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.ActivityLessorMainBinding;
import com.lanka.rentalmangment.databinding.DrawerHeaderBinding;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class LessorMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout lessordrawerLayout;
    ActivityLessorMainBinding activityLessorMainBinding;
    ImageView profile;
    TextView name, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLessorMainBinding=activityLessorMainBinding.inflate(getLayoutInflater());
        View view=activityLessorMainBinding.getRoot();
        setContentView(view);
        toolbar = activityLessorMainBinding.appNavBar.tbToolBar;
        setSupportActionBar(toolbar);
        LoginResponse loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).userget();

        NavigationView navigationView = activityLessorMainBinding.lessorNavigationView;
        View headerView = activityLessorMainBinding.lessorNavigationView.getHeaderView(0);
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
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navLessorHome));

        navigationView.setCheckedItem(R.id.navLessorHome);

        NavigationView navView = activityLessorMainBinding.lessorNavigationView;
        lessordrawerLayout = activityLessorMainBinding.LessordrawerLayout;
        toggle = new ActionBarDrawerToggle(this, lessordrawerLayout, toolbar, R.string.open, R.string.close);
        lessordrawerLayout.addDrawerListener(toggle);
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
        NavigationView navigationView = findViewById(R.id.lessorNavigationView);
        DrawerLayout drawerLayout = activityLessorMainBinding.LessordrawerLayout;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        if (toggle.isDrawerIndicatorEnabled()
                && toggle.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()) {
            case R.id.navLessorHome:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment current_fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (activityLessorMainBinding.LessordrawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityLessorMainBinding.LessordrawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (current_fragment instanceof HomeFragment) {
            super.onBackPressed();
        }
        else {
            NavigationView navigationView = findViewById(R.id.lessorNavigationView);
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navLessorHome));
            navigationView.setCheckedItem(R.id.navLessorHome);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = new Fragment();

        //fragments are created according to user demand
        if (id == R.id.navLessorHome) {
            fragment = new HomeFragment();
        } else if (id == R.id.navMangeItems) {
            fragment = new AddProductFragment();
        } else if (id == R.id.navLessorProfile) {
            fragment = new ProfileFragment();
        } else if (id == R.id.navLessorOrders) {
            fragment = new OrdersFragment();
        } else if(id == R.id.navCategoryLessor){
            fragment= new CategoryFragment();
        } else if (id == R.id.navlessorlogout) {
            onLogout();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        activityLessorMainBinding.LessordrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
