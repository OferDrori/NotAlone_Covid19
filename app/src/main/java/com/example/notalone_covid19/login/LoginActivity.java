package com.example.notalone_covid19.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notalone_covid19.MainActivity;
import com.example.notalone_covid19.MyApp;
import com.example.notalone_covid19.MySharedPreferences;
import com.example.notalone_covid19.R;
import com.example.notalone_covid19.RiskGroupPerson;
import com.example.notalone_covid19.VolunteerUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private TextView registerTextView;
    private TextView timeCountTextView;
    private EditText passwordEditText;
    private EditText userNameEditText;
    private Button loginButton;
    private MySharedPreferences msp;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private final String RISK_GROUP_PERSONS_DB_NAME = "RiskGroupPersonDB";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        findViews();
        mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(goToHomeScreen);
        registerTextView.setOnClickListener(goToRegisterActivity);
        addHelperUser();
    }

    private void addHelperUser() {
        RiskGroupPerson riskGroupPerson=new RiskGroupPerson("313344","yosi choen","Tel Aviv",
                32.2,33,13333,"xxxx","need help");
        RiskGroupPerson riskGroupPerson2=new RiskGroupPerson("3134444","Aviv choen","Tel Aviv",
                32.2,33,13333,"xxxx","need help");
        RiskGroupPerson riskGroupPerson3=new RiskGroupPerson("313444","Guy choen","Tel Aviv",
                32.2,33,13333,"xxxx","need help");
        RiskGroupPerson riskGroupPerson4=new RiskGroupPerson("313644","Guy choen","Tel Aviv",
                35.217018,31.771959,13333,"xxxx","need help");
        myRef.child(RISK_GROUP_PERSONS_DB_NAME).child(riskGroupPerson.getId()).setValue(riskGroupPerson);
        myRef.child(RISK_GROUP_PERSONS_DB_NAME).child(riskGroupPerson2.getId()).setValue(riskGroupPerson2);
        myRef.child(RISK_GROUP_PERSONS_DB_NAME).child(riskGroupPerson3.getId()).setValue(riskGroupPerson3);
        myRef.child(RISK_GROUP_PERSONS_DB_NAME).child(riskGroupPerson4.getId()).setValue(riskGroupPerson4);
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

                            checkIfGetPremition();
                            // Sign in success, update UI with the signed-in user's information
                       //     goToNextActivity(MainActivity.class);
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

    private void checkIfGetPremition() {

        final FirebaseUser userUID = FirebaseAuth.getInstance().getCurrentUser();
        MyApp.setMyUid(userUID.getUid());
// Read from the database
        myRef.child("User").child("Israel").child(userUID.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                VolunteerUser volunteerUser = dataSnapshot.getValue(VolunteerUser.class);
                Log.d("ptttt", "Value is: " + volunteerUser.getFullName());
                FirebaseDatabase.getInstance().getReference().child(userUID.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<RiskGroupPerson> people = new ArrayList<>();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren())
                            people.add(snapshot.getValue(RiskGroupPerson.class));
                        MyApp.setPeopleHelp(people);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if(volunteerUser.isPermissionAccess()){
                    goToNextActivity(MainActivity.class);
                }
                else{
                    goToNextActivity(WaitingActivity.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
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
