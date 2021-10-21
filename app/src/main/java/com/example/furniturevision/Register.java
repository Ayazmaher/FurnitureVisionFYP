package com.example.furniturevision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private  EditText name ,email, password,confirmpassword;
    TextView movelogin;

    private FirebaseAuth mAuth;

    FirebaseFirestore fstore ;

    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        name = findViewById(R.id.userpassword);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        movelogin= findViewById(R.id.alreadyaccount);
        confirmpassword = findViewById(R.id.confrompassword);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        movelogin.setOnClickListener(v -> startActivity(new Intent(Register.this,MainActivity.class)));

    }
    public void  Registeruser(View view)
    {
        String username = name.getText().toString().trim();
        String useremail = email.getText().toString().trim();
        String userpassword = password.getText().toString().trim();
        String userconfirmpassword = confirmpassword.getText().toString().trim();

        if (username.isEmpty() || useremail.isEmpty() || userpassword.isEmpty() || userconfirmpassword.isEmpty() )
        {
            Toast.makeText(Register.this, "Please Filled all Details",Toast.LENGTH_LONG).show();
        }

       else {
            mAuth.createUserWithEmailAndPassword(useremail,userpassword).
                    addOnCompleteListener(task -> {

                        if (task.isSuccessful())
                        {

                           // Toast.makeText(Register.this, "Thanks for Registration and You can on Login Screen", Toast.LENGTH_LONG).show();
                            userID = mAuth.getCurrentUser().getUid();

                            DocumentReference documentReference = fstore.collection("users").document(userID);

                            Map <String ,Object > user = new HashMap<>();

                            user.put("Fname ", username);
                            user.put("uemail",useremail );


                            documentReference.set(user).addOnSuccessListener(aVoid -> {
                                Toast.makeText(Register.this, "Thanks for your Registration and User Profile Successfully Created " + userID, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Register.this,MainActivity.class));
                            });



                        }
                        else
                        {
                            Toast.makeText(Register.this, "Some thing went Wrong"+ task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        }




                    });

        }


    }




}