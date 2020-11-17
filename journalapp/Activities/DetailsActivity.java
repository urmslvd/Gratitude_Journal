package edu.fdu.journalapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

import edu.fdu.journalapp.R;

public class DetailsActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private TextView grateful1;
    private TextView grateful2;
    private TextView grateful3;
    private TextView affirmations;
    private TextView date;
    private Button backButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("Journal");
        mDatabaseReference.keepSynced(true);

        grateful1 = findViewById(R.id.detailsgrateful1ID);
        grateful2 = findViewById(R.id.detailsgrateful2ID);
        grateful3 = findViewById(R.id.detailsgrateful3ID);
        affirmations = findViewById(R.id.detailsaffirmationsID);
        date = findViewById(R.id.detailsdateID);
        backButton = findViewById(R.id.detailsbackButtonID);
        deleteButton = findViewById(R.id.detailsdeleteButtonID);
        final String affirmationsFromBundle;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d("qqq", String.valueOf(bundle));
            grateful1.setText(bundle.getString("grateful1"));
            grateful2.setText(bundle.getString("grateful2"));
            grateful3.setText(bundle.getString("grateful3"));
            affirmations.setText(bundle.getString("affirmations"));
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(Long.valueOf(bundle.getString("date"))).getTime());
            date.setText(formattedDate);

        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsActivity.this, PostListActivity.class));
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                String affirmations = bundle.getString("affirmations");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Journal").orderByChild("affirmations").equalTo(bundle.getString("affirmations"));

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                startActivity(new Intent(DetailsActivity.this, PostListActivity.class));
                finish();

            }
        });
    }
}