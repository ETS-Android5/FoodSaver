package com.sp.myapplication.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sp.myapplication.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private EditText User, Email, Password, CfmPassword;
    private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        User = findViewById(R.id.search);
        Email = findViewById(R.id.emailin);
        Password = findViewById(R.id.password1);
        CfmPassword = findViewById(R.id.passwordcfm);
        Register = findViewById(R.id.register);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userstr = User.getText().toString();
                String emailstr = Email.getText().toString();
                String passwordstr = Password.getText().toString();
                String passwordcfmstr = CfmPassword.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();
                editor.putString("username", userstr);
                editor.apply();
                validateUser(userstr);
                validateEmail(emailstr);
                validatePassword(passwordstr);
                validateCfmPassword(passwordstr, passwordcfmstr);
                if(validateUser(userstr) &&validateEmail(emailstr) && validatePassword(passwordstr) && validateCfmPassword(passwordstr, passwordcfmstr))
                    createAccount(emailstr, passwordstr);
            }
        });
    }

    private boolean validateUser(String userstr) {

        if(TextUtils.isEmpty(userstr.trim())) {
            User.setError("Field can't be empty");
            return false;
        }
        else {
            User.setError(null);
            return true;
        }
    }
        private boolean validateEmail(String emailstr) {

            if(TextUtils.isEmpty(emailstr.trim())) {
                Email.setError("Field can't be empty");
                return false;
            }
            else if(!isEmailValid(emailstr.trim())){
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
        private boolean validatePassword(String passwordstr) {

            if (TextUtils.isEmpty(passwordstr.trim())) {
                Password.setError("Field can't be empty");
                return false;
            }
            else if (passwordstr.trim().length() < 6) {
                Password.setError("Password must be at least 6 characters");
                return false;}
                else {
                Password.setError(null);
                return true;
            }
        }
        private boolean validateCfmPassword(String passwordstr, String passwordcfmstr) {

            if(TextUtils.isEmpty(passwordcfmstr.trim())) {
                CfmPassword.setError("Field can't be empty");
                return false;
            }
            else if(!passwordstr.trim().equals(passwordcfmstr.trim())){

                CfmPassword.setError("Passwords are not matching!");
                return false;
            }
            else {
                CfmPassword.setError(null);
                return true;
            }
        }

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Success",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "This email address is linked to a current account.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void updateUI(FirebaseUser user) {
        finish();
    }
}