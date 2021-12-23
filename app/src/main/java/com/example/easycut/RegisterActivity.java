package com.example.easycut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easycut.objects.Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth; //authentication for DB
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Button submit;
    private EditText _firstName;
    private EditText _lastName;
    private EditText _email;
    private EditText _pass;
    private EditText _phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);
        _firstName=(EditText) findViewById(R.id.first_name_fill);
        _lastName=(EditText)findViewById(R.id.last_name_fill);
        _email=(EditText)findViewById(R.id.email_fill);
        _pass=(EditText)findViewById(R.id.pass_fill);
        _phone=(EditText)findViewById(R.id.phone_fill);
        submit = (Button) findViewById(R.id.submit);
        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
                createClient();
            }
        });
    }

    /**
     *  Check if user is already signed in (non-null), if so continue to ScreenUser.
     */
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(getApplicationContext(), "You are already signed in!" ,Toast.LENGTH_SHORT).show();
            currentUser.reload();
            String s=currentUser.getUid();
            System.out.println(s);
            // check if current user is the hairstyle for next screen
            if(!s.equals("hsp5DBs6EZQ3JbDrWk0WiMXTzSM2")){
                Intent intent = new Intent(RegisterActivity.this, ScreenUserActivity.class);
                startActivity(intent);
            }
            else{
                currentUser.reload();
                Intent intent = new Intent(RegisterActivity.this, ScreenHairStylistActivity.class);
                startActivity(intent);
            }
        }
    }

    public void createClient() {
        String firstName = _firstName.getText().toString();
        String lastName = _lastName.getText().toString();
        String phone = _phone.getText().toString();
        String email = _email.getText().toString();
        String pass = _pass.getText().toString();

        if (TextUtils.isEmpty(firstName)) {
            _firstName.setError("First name cannot by empty ");
            _firstName.requestFocus();
        } else if (TextUtils.isEmpty(lastName)) {
            _lastName.setError("Last name cannot by empty ");
            _lastName.requestFocus();
        } else if (TextUtils.isEmpty(phone)) {
            _phone.setError("Phone cannot by empty ");
            _phone.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            _email.setError("Email cannot by empty ");
            _email.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            _pass.setError("Password cannot by empty ");
            _pass.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String id=mAuth.getCurrentUser().getUid();
                        database = FirebaseDatabase.getInstance();
                       // reference = database.getReference("Users");
                        Client client=new Client(firstName,lastName,email,pass,phone,id);
                        reference = database.getReference("Client");
                        reference.child(client.getUserID()).setValue(client);
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, ScreenUserActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}