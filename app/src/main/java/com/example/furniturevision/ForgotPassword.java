package com.example.furniturevision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    private EditText email ;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.Emailid);
        auth= FirebaseAuth.getInstance();
    }

    public void ForgotPassword(View view)
    {
      String emailaddress = email.getText().toString();

      if (emailaddress.isEmpty())
      {
          email.setError("Please Filled an Email Address");

      }

     else
          {
            auth.sendPasswordResetEmail(emailaddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(ForgotPassword.this,"Please check your Email",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ForgotPassword.this,MainActivity.class));
                    }

                    else {
                        Toast.makeText(ForgotPassword.this,"Some thing went Wrong",Toast.LENGTH_LONG).show();
                    }

                }
            });
      }

    }
}