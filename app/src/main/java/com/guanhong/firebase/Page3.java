package com.guanhong.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Page3 extends AppCompatActivity {

    private EditText mEditText;
    private TextView mTextViewSearch;
    private ArrayAdapter<String> fileDBAdapter;
    private ListView mListView;
    private TextView mTextViewPage4;

    private String mStringName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);

        mEditText = findViewById(R.id.page3_edittext);
        mTextViewSearch = findViewById(R.id.page3_search);
        mListView = findViewById(R.id.page3_listView);
        mTextViewPage4 = findViewById(R.id.textView_page3);


        fileDBAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        mListView.setAdapter(fileDBAdapter);


        mTextViewPage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page3.this, Page4.class);
                startActivity(intent);
                finish();
            }
        });

        mTextViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileDBAdapter.clear();

                final FirebaseDatabase fireDb = FirebaseDatabase.getInstance();
                DatabaseReference myRef = fireDb.getReference("article");

                mStringName = mEditText.getText().toString();

                Query query = myRef.orderByChild("author").equalTo(mStringName);
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


    }
}
