package com.example.notalone_covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.notalone_covid19.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FirebaseDatabase.getInstance().getReference().child("RiskGroupPersonDB")
//                .child("3294").setValue(new RiskGroupPerson("3294","Stam Mishu",
//                "Tel Aviv",34.755499,32.109333,0,"0502834393",""));
//        FirebaseDatabase.getInstance().getReference().child("RiskGroupPersonDB")
//                .child("3214").setValue(new RiskGroupPerson("3214","Ploni Ehad",
//                "Tel Aviv",34.805499,32.109333,0,"0502956393",""));
//        FirebaseDatabase.getInstance().getReference().child("RiskGroupPersonDB")
//                .child("5294").setValue(new RiskGroupPerson("5294","Eled Levi",
//                "Tel Aviv",34.755499,32.159333,0,"0502807393",""));
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder( R.id.navigation_home, R.id.navigation_people).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}
