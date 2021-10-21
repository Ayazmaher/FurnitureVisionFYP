package com.example.furniturevision;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.furniturevision.fargment.Products;
import com.example.furniturevision.fargment.logout;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;


public class navdrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private AppBarConfiguration mAppBarConfiguration;
   private TextView userprofilename;
   private TextView userprofileaddress;
   GoogleSignInClient mGoogleSignInClient;
   CircleImageView imageView;
    private FirebaseAuth auth ;
    String userID;
    private FrameLayout frameLayout;
    public NavigationView navigationView;
    public View navHeaderView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdrawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setCheckable(true);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.products,R.id.favourite,R.id.logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navHeaderView = navigationView.getHeaderView(0);
        userprofilename = navHeaderView.findViewById(R.id.nav_name);
        userprofileaddress =navHeaderView.findViewById(R.id.nav_email);
        imageView= navHeaderView.findViewById(R.id.profile_image);
        frameLayout = findViewById(R.id.mainframe_layout);
        auth= FirebaseAuth.getInstance();
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();

        if(FirebaseAuth.getInstance().getCurrentUser() !=null)
        {
            try{
                userID = auth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("users").document(userID);
                 documentReference.addSnapshotListener(this, (value, error) -> {
                     userprofilename.setText(auth.getCurrentUser().getDisplayName());
                     userprofileaddress.setText(auth.getCurrentUser().getEmail());
                     imageView.setImageResource(R.drawable.ic_baseline_person_24);
                 });

            }
            catch (Exception e)
            {
                Toast.makeText(navdrawer.this,"Something went wrong"+e.getMessage(),Toast.LENGTH_LONG).show();
            }

        }

        else
            {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(navdrawer.this);
                if (acct != null)
                {

                String uid= acct.getId();

                    fstore.collection("users").document(uid).get().addOnCompleteListener(task -> {

                Glide.with(navdrawer.this).load(acct.getPhotoUrl()).into(imageView);
                userprofileaddress.setText(acct.getEmail());
                userprofilename.setText(  acct.getDisplayName());
                    }) ;
                }
            }
        }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.navdrawer, menu);
        return true;

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void setFragment(Fragment fragment)
    {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.products)
        {
            setFragment(new Products());
        }
        else if (id == R.id.logout)
        {

            setFragment(new logout());
        }
        return true;


    }

}