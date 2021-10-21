package com.example.furniturevision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mail, pass ;

    private FirebaseAuth mAuth;

    GoogleSignInClient mGoogleSignInClient;

    private static final int  RC_SIGN_IN=12501;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = findViewById(R.id.email);
      pass = findViewById(R.id.userpassword);
        TextView forgot = findViewById(R.id.forgetpassword);


        mAuth= FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(v -> signIn());
    }
    private void signIn()
    {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                Toast.makeText(MainActivity.this, "You are Successfully Login with this Email: "  +personEmail,Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, navdrawer.class));


            }


        }
        catch (ApiException e) {

            Toast.makeText(MainActivity.this, "Some thing went Wrong"+e.getMessage(),Toast.LENGTH_LONG).show();

        }
    }


    public void  loginuser(View view)
    {
        String authmail = mail.getText().toString().trim();
        String authpass = pass.getText().toString().trim();

        if (authmail.isEmpty()|| authpass.isEmpty())
        {
            Toast.makeText(MainActivity.this, "Please Filled All Details", Toast.LENGTH_LONG).show();
        }

        else{

            mAuth.signInWithEmailAndPassword(authmail,authpass).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "User Login SuccessFully", Toast.LENGTH_LONG).show();
                    startActivity( new Intent(MainActivity.this,navdrawer.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Some thing Went Wrong"+task.getException(), Toast.LENGTH_LONG).show();
                }
            });

        }



    }

    public  void  movingscreen(View view)
    {
        startActivity( new Intent(MainActivity.this, Register.class));
    }

    public void Forgotpass (View view)
    {

        startActivity(new Intent(this,ForgotPassword.class));
    }


}