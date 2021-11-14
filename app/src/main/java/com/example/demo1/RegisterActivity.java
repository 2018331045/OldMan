package com.example.demo1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText register_email , register_password , register_confirm_password;
    private Button register_btn , register_to_login_btn ;
    private ProgressBar register_progress_bar;
    private CheckBox register_checkbox;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        register_email = findViewById(R.id.register_email_et);
        register_password = findViewById(R.id.register_password_et);
        register_confirm_password = findViewById(R.id.register_confirm_password_et);
        register_btn = findViewById(R.id.register_btn);
        register_to_login_btn = findViewById(R.id.register_to_login_button);
        register_progress_bar = findViewById(R.id.register_progress_bar);
        register_checkbox = findViewById(R.id.register_checkbox);

        register_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    register_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    register_confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    register_password.setSelection(register_password.getText().length());
                    register_confirm_password.setSelection(register_confirm_password.getText().length());
                } else {
                    register_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    register_confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    register_password.setSelection(register_password.getText().length());
                    register_confirm_password.setSelection(register_confirm_password.getText().length());
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = register_email.getText().toString().trim();
                String password = register_password.getText().toString().trim();
                String confirm_password = register_confirm_password.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";




                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(confirm_password)){
                    if(password.equals(confirm_password) && email.matches(emailPattern) && password.length()>=8){
                        register_progress_bar.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    register_progress_bar.setVisibility(View.GONE);
                                    SendtoMain(); // for at this stage of the project
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error : "+error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    if(!(password.equals(confirm_password))){
                       // Toast.makeText(RegisterActivity.this, "Password and Confirm Password Not Matched!", Toast.LENGTH_SHORT).show();
                        register_confirm_password.setError("Password and Confirm Password Not Matched!");
                        register_confirm_password.setSelection(register_confirm_password.getText().length());
                    }
                    if(password.length()<8) {
                        //Toast.makeText(RegisterActivity.this, "Password Must Be More Than 8 Character!", Toast.LENGTH_SHORT).show();
                        register_password.setError("Password Must Be More Than 8 Character!");
                        register_password.setSelection(register_password.getText().length());
                    }
                    if(!(email.matches(emailPattern))){
                        //Toast.makeText(RegisterActivity.this, "Enter A Valid Email Address!", Toast.LENGTH_SHORT).show();
                        register_email.setError("Enter A Valid Email Address!");
                    } else {
                        Toast.makeText(RegisterActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Please Fill All The Values Correctly!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register_to_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RegisterActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void SendtoMain() {

        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null){
            SendtoMain();
            finish();
        }

    }
}