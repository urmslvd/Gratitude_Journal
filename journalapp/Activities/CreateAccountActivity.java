package edu.fdu.journalapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.fdu.journalapp.R;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private Button createAccountBtn;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("MUsers");
        mAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);

        firstName = (EditText) findViewById(R.id.firstNameAct);
        lastName = (EditText) findViewById(R.id.lastNameAct);
        email = (EditText) findViewById(R.id.emailAct);
        password = (EditText) findViewById(R.id.passwordAct);

        createAccountBtn = (Button) findViewById(R.id.createAccoutAct);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {

        final String name = firstName.getText().toString().trim();
        final String lname = lastName.getText().toString().trim();
        String em = email.getText().toString().trim();
        String pwd = password.getText().toString().trim();

        if ((name != null) && (lname != null)
                && (em != null) && (pwd != null)) {

            mProgressDialog.setMessage("Creating Account...");
            mProgressDialog.show();

            mAuth.createUserWithEmailAndPassword(em, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                @Override
                public void onSuccess(AuthResult authResult) {

                    if (authResult != null) {
                        String userid = mAuth.getCurrentUser().getUid();

                        DatabaseReference currentUserDb = mDatabaseReference.child(userid);
                        currentUserDb.child("firstname").setValue(name);
                        currentUserDb.child("lastname").setValue(lname);
                        mProgressDialog.dismiss();

                        //send users to postList
                        Intent intent = new Intent(CreateAccountActivity.this, PostListActivity.class );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }
            });
        }
    }
}