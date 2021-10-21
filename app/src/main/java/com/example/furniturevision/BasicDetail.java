package com.example.furniturevision;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BasicDetail extends AppCompatActivity {

    TextView name, email,city,add,phone;
    Button final1;
    String name11, email11,city11,add11;
    int phone11;
    Intent intent2,intent1;
    RadioGroup radioGroup;
    RadioButton radioButton;
     List<Cart_item_model> cart_item_modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_detail);

        name=findViewById(R.id.basic_name);
        email=findViewById(R.id.basic_email);
        city=findViewById(R.id.basic_city);
        add=findViewById(R.id.basic_Address);
        phone=findViewById(R.id.basic_Phone);
        final1=findViewById(R.id.final_confirmation);
        radioGroup = findViewById(R.id.radioGroup);
        cart_item_modelList= new ArrayList<>();


          final1.setOnClickListener(v -> {


          name11=name.getText().toString();
          email11=email.getText().toString();
          city11=city.getText().toString();
          add11=add.getText().toString();
          phone11= Integer.parseInt( phone.getText().toString());
          int radioId = radioGroup.getCheckedRadioButtonId();
          radioButton = findViewById(radioId);

          intent1= getIntent();

          int a= intent1.getIntExtra("cost of Products",0);

          if(name11.isEmpty() || email11.isEmpty() || city11.isEmpty() || add11.isEmpty() || !radioButton.isChecked() )
          {
              Toast.makeText(BasicDetail.this, "Filled All Details", Toast.LENGTH_LONG).show();
          }
          else
              {
                  intent2= new Intent(BasicDetail.this,FinalConfirmation.class);
                  intent2.putExtra("Final_user_name",name11);
                  intent2.putExtra("Final_user_email",email11);
                  intent2.putExtra("Final_user_city", city11);
                  intent2.putExtra("Final_user_add",add11);
                  intent2.putExtra("Final_user_phone", phone11);
                  intent2.putExtra("Final_user_total",a );
                  intent2.putExtra("Final_user_payment_type",radioButton.getText().toString() );
                  startActivity(intent2);

          }

        });


    }
}