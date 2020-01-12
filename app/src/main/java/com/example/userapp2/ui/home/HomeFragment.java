package com.example.userapp2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.userapp2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    List<String> l;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

         */







        FirebaseDatabase database = FirebaseDatabase.getInstance("https://senderapp-85057.firebaseio.com/");


        DatabaseReference myRef = database.getReference("Members");

         l = new ArrayList<String>();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                // HashMap temp= dataSnapshot.getValue(HashMap.class);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    l.add(String.valueOf(snapshot.getKey()));


                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, l);

                final ListView l = root.findViewById(R.id.list);
                l.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        // ListView Clicked item index
                        int itemPosition     = position;

                        // ListView Clicked item value
                        String  itemValue    = (String) l.getItemAtPosition(position);

                        // Show Alert
                        Toast.makeText(getContext(),
                                "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                                .show();

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






        return root;
    }
}