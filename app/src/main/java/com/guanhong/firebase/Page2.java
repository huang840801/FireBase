package com.guanhong.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Page2 extends AppCompatActivity {

    private Spinner mSpinner;
    private TextView mTextViewSearch;
    private ArrayAdapter<String> fileDBAdapter;
    private ListView list;
    private TextView mTextViewPage3;

    private String mStringSpinner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);


        mSpinner = findViewById(R.id.page2_spinner);
        mTextViewSearch = findViewById(R.id.page2_search);
        list = (ListView) findViewById(R.id.page2_listView);
        mTextViewPage3 = findViewById(R.id.textView_page2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tag_array, android.R.layout.simple_spinner_item);
        mSpinner.setAdapter(adapter);

        fileDBAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        list.setAdapter(fileDBAdapter);

        mTextViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileDBAdapter.clear();


                final FirebaseDatabase fireDb = FirebaseDatabase.getInstance();
                DatabaseReference myRef = fireDb.getReference("article");

                mStringSpinner = mSpinner.getSelectedItem().toString();
                Log.d("search tag = ", mStringSpinner);

                Query query = myRef.orderByChild("tag").equalTo(mStringSpinner);
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        fileDBAdapter.add(String.valueOf(dataSnapshot.child("").getValue()));
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
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        mTextViewPage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page2.this, Page3.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
