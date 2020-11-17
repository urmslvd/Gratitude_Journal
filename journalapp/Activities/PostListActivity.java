package edu.fdu.journalapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.fdu.journalapp.Data.JournalRecyclerAdapter;
import edu.fdu.journalapp.Model.Journal;
import edu.fdu.journalapp.R;

public class PostListActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private DatabaseReference uDatabaseReference;
    private FirebaseDatabase mDatabase;
    private RecyclerView recyclerView;
    private JournalRecyclerAdapter journalRecyclerAdapter;
    private List<Journal> journalList;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("Journal");
        uDatabaseReference = mDatabase.getReference().child("MUsers");
        mDatabaseReference.keepSynced(true);

        journalList = new ArrayList<>();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String userid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("Journal").orderByChild("userid").equalTo(userid);

        applesQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Journal journal = dataSnapshot.getValue(Journal.class);

                journalList.add(journal);

                Collections.reverse(journalList);

                journalRecyclerAdapter = new JournalRecyclerAdapter(PostListActivity.this,journalList);
                recyclerView.setAdapter(journalRecyclerAdapter);
                journalRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        You can see all posts here
//        mDatabaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                Journal journal = dataSnapshot.getValue(Journal.class);
//
//
//                journalList.add(journal);
//
//                Collections.reverse(journalList);
//
//                journalRecyclerAdapter = new JournalRecyclerAdapter(PostListActivity.this,journalList);
//                recyclerView.setAdapter(journalRecyclerAdapter);
//                journalRecyclerAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add:
                if (mUser != null && mAuth != null) {

                    startActivity(new Intent(PostListActivity.this, AddPostActivity.class));
                    finish();

                }
                break;
            case R.id.action_signout:

                if (mUser != null && mAuth != null) {
                    mAuth.signOut();

                    startActivity(new Intent(PostListActivity.this, MainActivity.class));
                    finish();

                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}