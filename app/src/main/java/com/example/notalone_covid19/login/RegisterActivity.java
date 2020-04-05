package com.example.notalone_covid19.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notalone_covid19.MySharedPreferences;
import com.example.notalone_covid19.R;
import com.example.notalone_covid19.VolunteerUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {


    private Button nextPageBtn;
    private EditText fullNameEditText;
    private EditText emailEditText;
    private EditText locationEditText;
    private EditText passwordEditText;
    private EditText phoneNumberEditText;
    private EditText idNumberEditText;
    private EditText descriptionEditText;
    private Button btn_selectImage;
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    private ImageView showImage;
    FirebaseStorage storage;
    StorageReference storageReference;
    String urlPath = UUID.randomUUID().toString();
    private FirebaseAuth mAuth;
    DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00BCD4"));
        actionBar.setBackgroundDrawable(colorDrawable);
        findView();
        mAuth = FirebaseAuth.getInstance();
        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User");

        nextPageBtn.setOnClickListener(makeAuser);
        btn_selectImage.setOnClickListener(selectImage);



    }


    View.OnClickListener makeAuser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           // uploadImage();
            regFunc(v);
        }


    };

    public void regFunc(View view) {

        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            VolunteerUser volunteerUser = new VolunteerUser(fullNameEditText.getText().toString(),emailEditText.getText().toString(),
                                    idNumberEditText.getText().toString(),urlPath,descriptionEditText.getText().toString(),
                                    phoneNumberEditText.getText().toString(),locationEditText.getText().toString());
                            FirebaseUser userUID = FirebaseAuth.getInstance().getCurrentUser();
                            myRef.child("Israel").child((userUID.getUid())).setValue(volunteerUser);
                            Toast.makeText(RegisterActivity.this, "REG ok.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, WaitingActivity.class);
                            RegisterActivity.this.startActivity(intent);
                            finish();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "REG failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                showImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }




    // UploadImage method
    private void uploadImage() {
        if (filePath != null) {
            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/").child(urlPath);
            Log.i("123123132", " " + ref);
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Log.i("111111111", "succes");

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error, Image not uploaded
                            Log.i("111111111", "fail");

                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                    Log.i("111111111", "uploading");
                                }
                            });
        }
    }



    View.OnClickListener selectImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SelectImage();
        }
    };

    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }


    private void findView() {
        nextPageBtn = findViewById(R.id.Register_btn_Register);
        fullNameEditText = findViewById(R.id.Register_EditText_fullName);
        emailEditText = findViewById(R.id.Register_EditText_email);
        passwordEditText = findViewById(R.id.Register_EditText_Password);
        btn_selectImage = findViewById(R.id.Register_btn_uploadImg);
        showImage = findViewById(R.id.Register_ImageView_profileImg);
        phoneNumberEditText=findViewById(R.id.Register_EditText_phoneNumber);
        locationEditText=findViewById(R.id.Register_EditText_Location);
        idNumberEditText=findViewById(R.id.Register_EditText_idNumber);
        descriptionEditText=findViewById(R.id.Register_EditText_tellAboutYourSelf);

    }
}
