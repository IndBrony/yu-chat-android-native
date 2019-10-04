package com.yu.chat.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.yu.chat.R;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;

    private FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if (firebaseAuth.getCurrentUser() != null) finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setIndeterminate(true);
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginButton.setEnabled(false);
                final String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                auth.signInWithEmailAndPassword(email, password);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        auth.removeAuthStateListener(authStateListener);
    }
}