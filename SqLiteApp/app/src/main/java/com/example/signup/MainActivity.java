 package com.example.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {
    EditText username,password,repassword;
    Button signup,signin;
    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_register);


        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        repassword=(EditText) findViewById(R.id.repassword);
        signup=(Button) findViewById(R.id.btnsignup);
        signin=(Button) findViewById(R.id.btnsignin);
        DB = new DbHelper(this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                if(user.equals("") || pass.equals("") || repass.equals("")){
                    Toast.makeText(MainActivity.this,"Please enter all fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkUsername(user);
                        if(checkuser==false){
                            Boolean insert = DB.insertData(user,pass);
                            if(insert==true){
                                Toast.makeText(MainActivity.this,"Registration successfull",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Homepage.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();

                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this,"User already exists plese login",Toast.LENGTH_SHORT);
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this,"password didnt match",Toast.LENGTH_SHORT);
                    }
                }

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Loginpage.class);
                startActivity(intent);

            }
        });

    }

}