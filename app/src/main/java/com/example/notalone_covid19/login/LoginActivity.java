package com.example.notalone_covid19.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notalone_covid19.MainActivity;
import com.example.notalone_covid19.MySharedPreferences;
import com.example.notalone_covid19.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView registerTextView;
    private TextView timeCountTextView;
    private EditText passwordEditText;
    private EditText userNameEditText;
    private Button loginButton;
    private MySharedPreferences msp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        findViews();
        loginButton.setOnClickListener(goToHomeScreen);
        registerTextView.setOnClickListener(goToRegisterActivity);
    }

    View.OnClickListener goToRegisterActivity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goToRegisterActivity();
        }
    };
    private void goToRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);

    }
    View.OnClickListener goToHomeScreen = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            loginFunc(view);

        }
    };
    public void loginFunc(View view)
    {
        mAuth.signInWithEmailAndPassword(userNameEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            goToNextActivity(MainActivity.class);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "login failed.", Toast.LENGTH_SHORT).show();
                            Context context = getApplicationContext();
                            CharSequence text = "try again!";

                        }
                    }
                    // ...

                });
    }

    private void goToNextActivity(Class activity)
    {
        Intent intent = new Intent(this , activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
    private void findViews () {
        passwordEditText = findViewById(R.id.pass_login_editText);
        userNameEditText = findViewById(R.id.name_login_editText);
        registerTextView = findViewById(R.id.joinActivity_login_activity_text_view);
        loginButton = findViewById(R.id.login_btn_login);
        timeCountTextView=findViewById(R.id.login_textView_timeCount);
    }

}
