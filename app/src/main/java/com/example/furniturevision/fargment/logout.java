package com.example.furniturevision.fargment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.furniturevision.MainActivity;
import com.example.furniturevision.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class logout extends Fragment {

    public logout() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        mAuth = FirebaseAuth.getInstance();


        if (mAuth.getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getContext(), "Sign Out SuccessFully with Firebase :-", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getContext(), MainActivity.class));
        }

    else
        {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

            mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    Toast.makeText(getContext(), "Sign Out SuccessFully with Google:-", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            });
        }


        return view;





    }




        }







