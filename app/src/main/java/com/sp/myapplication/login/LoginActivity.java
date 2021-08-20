package com.sp.myapplication.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sp.myapplication.home.HomeActivity;
import com.sp.myapplication.R;
import com.sp.myapplication.mains.WhyActivity;

public class LoginActivity extends AppCompatActivity {
    private MediaPlayer bgmusic;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private EditText Email;
    private EditText Password;
    private Button Register;
    private Button Login;
    private Switch Rembme;
    private ImageView BG, FS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.search);
        Password = findViewById(R.id.password1);
        Register = findViewById(R.id.register);
        Login = findViewById(R.id.login);
        Rembme = findViewById(R.id.active);
        RemPass();

        bgmusic = MediaPlayer.create(LoginActivity.this,R.raw.login_bgmusic);
        bgmusic.start();
        bgmusic.setVolume(10,10);
        bgmusic.setLooping(true);

        BG = findViewById(R.id.login_bg);
        FS = findViewById(R.id.fs_login);
        moveAnim1();
        moveAnim2();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailstr = Email.getText().toString();
                String passwordstr = Password.getText().toString();
                validateEmail();
                validatePassword();
                RemPass();
                if(Rembme.isChecked())
                {
                    SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();
                    editor.putString("password", Password.getText().toString());
                    editor.putString("email", Email.getText().toString());
                    editor.apply();
                } else
                {
                    SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();
                    editor.putString("password", null);
                    editor.putString("email", null);
                    editor.apply();
                };
                if(validateEmail() && validatePassword()) {
                    signIn(emailstr, passwordstr);
                    bgmusic.stop();
                }
            }

        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent2);
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
    // [END on_start_check_user]

    @Override
    public void onPause() {
        super.onPause();
        //Pause your player
        bgmusic.pause();
        //do more stuff
    }

    @Override
    public void onResume() {
        super.onResume();
        //Pause your player
        bgmusic.start();
        //do more stuff
    }


    private boolean validateEmail() {
        String emailstr = Email.getText().toString().trim();

        if(TextUtils.isEmpty(emailstr)) {
            Email.setError("Field can't be empty");
            return false;
        }
        else if(!isEmailValid(emailstr)){
            Email.setError("Invalid email");
            return false;
        }
        else {
            Email.setError(null);
            return true;
        }
    }
    boolean isEmailValid(String emailstr) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailstr).matches();
    }
    private boolean validatePassword() {
        String passwordstr = Password.getText().toString().trim();

        if(TextUtils.isEmpty(passwordstr)) {
            Password.setError("Field can't be empty");
            return false;
        }
        else {
            Email.setError(null);
            return true;
        }
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Password.setError("Wrong Email or Password");
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {
        Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent1);
    }

    private void RemPass() {

        SharedPreferences p = getSharedPreferences("USER", MODE_PRIVATE);
        String email = p.getString("email", null);
        String password = p.getString("password", null);
        if(email != null)Email.setText(email);
        else;
        if(password != null)Password.setText(password);
        else;
    }

    public void moveAnim1() {
        Animation img = new TranslateAnimation(Animation.ABSOLUTE,1200, Animation.ABSOLUTE, 0);
        img.setDuration(1500);
        img.setFillAfter(true);
        BG.startAnimation(img);
    }

    public void moveAnim2() {
        Animation img = new TranslateAnimation(Animation.ABSOLUTE,1200, Animation.ABSOLUTE, 0);
        img.setDuration(1000);
        img.setFillAfter(true);
        FS.startAnimation(img);
    }
}