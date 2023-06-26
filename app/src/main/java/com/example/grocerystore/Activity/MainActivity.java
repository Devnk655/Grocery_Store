package com.example.grocerystore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.grocerystore.Fragment.HomeFragment;
import com.example.grocerystore.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    HomeFragment homeFragment;
    ActionBar actionBar;
    FirebaseAuth auth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        actionBar = getSupportActionBar();
        homeFragment = new HomeFragment();
        loadFragment(homeFragment);

        // this line will set the action bar color according to the hexcode provided
        ColorDrawable colorDrawable= new ColorDrawable(Color.parseColor("#76FF03"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //Custom Notification will apppear whenever you will start your application.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //3
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        //Notification code
        //1 Notification builder to create
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notification");
        builder.setContentTitle(" Hello Welcome To Your Grocery Store ");
        builder.setContentText("Get Good and Perfect Quality Groceries");
        //image for the icon that is been static.
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        //2 Notification Manager Compact we can tell the user about the notifiaction
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1, builder.build());
    }

    private void loadFragment(HomeFragment homeFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homeFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
           auth.signOut();
           startActivity(new Intent(MainActivity.this,SignUpActivity.class));
           finish();
        } else if(id==R.id.menu_my_cart){
            startActivity(new Intent(MainActivity.this,CartActivity.class));
        }
        return true;
    }
}
