package edu.fdu.journalapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import edu.fdu.journalapp.R;

public class AddPostActivity extends AppCompatActivity {

    private EditText mgrateful1;
    private EditText mgrateful2;
    private EditText mgrateful3;
    private EditText maffirmations;
    private Button mSubmitButton;
    private DatabaseReference mPostDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mPostDatabase = FirebaseDatabase.getInstance().getReference().child("Journal");
        mgrateful1 = findViewById(R.id.grateful1ID);
        mgrateful2 = findViewById(R.id.grateful2ID);
        mgrateful3 = findViewById(R.id.grateful3ID);
        maffirmations = findViewById(R.id.affirmationsID);
        mSubmitButton = findViewById(R.id.submitButtonID);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Posting to database;
                startPosting();
            }
        });
    }

    private void startPosting() {

        mProgress.setMessage("Posting journal...");
        mProgress.show();

        final String grateful1 = mgrateful1.getText().toString().trim();
        final String grateful2 = mgrateful2.getText().toString().trim();
        final String grateful3 = mgrateful3.getText().toString().trim();
        final String affirmations = maffirmations.getText().toString().trim();

        if((grateful1 != null) && (affirmations != null)) {

            DatabaseReference newPost = mPostDatabase.push();

            Map<String, String> dataToSave = new HashMap<>();
            dataToSave.put("grateful1", grateful1);
            dataToSave.put("grateful2", grateful2);
            dataToSave.put("grateful3", grateful3);
            dataToSave.put("affirmations", affirmations);
            dataToSave.put("timestamp", String.valueOf(java.lang.System.currentTimeMillis()));
            dataToSave.put("userid", mUser.getUid());
            dataToSave.put("username", mUser.getEmail());

            newPost.setValue(dataToSave);

            mProgress.dismiss();

            startActivity(new Intent(AddPostActivity.this, PostListActivity.class));
            finish();


        }
    }
}