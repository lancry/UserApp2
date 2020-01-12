package com.example.userapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity {

    List<String> l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);


        String name = getIntent().getStringExtra("FRIEND_NAME");
        TextView name_txt= (TextView) findViewById(R.id.name_textview);
        name_txt.setText(name);


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://senderapp-85057.firebaseio.com/");


        DatabaseReference myRef = database.getReference("Members/" + name);

        l = new ArrayList<String>();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                // HashMap temp= dataSnapshot.getValue(HashMap.class);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    l.add(String.valueOf(snapshot.getValue()));


                }
                //l.add("AAAAA");
                //l.add("BBBB");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(FriendActivity.this,
                        android.R.layout.simple_list_item_1, l);


                final ListView l = (ListView) findViewById(R.id.friend_list);
                l.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {


                    }

                });


                l.setAdapter(adapter);

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        myRef.addValueEventListener(postListener);
    }
}
