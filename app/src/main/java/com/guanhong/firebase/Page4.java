package com.guanhong.firebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Page4 extends AppCompatActivity {

    private Spinner mSpinner;
    private EditText mEditText;
    private TextView mTextViewSearch;
    private ArrayAdapter<String> fileDBAdapter;
    private ListView mListView;

    private String mStringName;
    private String mStringTag;
    private String mStringResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page4);

        mSpinner = findViewById(R.id.page4_spinner);
        mEditText = findViewById(R.id.page4_edittext);
        mTextViewSearch = findViewById(R.id.page4_search);
        mListView = findViewById(R.id.page4_listView);
        mSpinner = findViewById(R.id.page4_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tag_array, android.R.layout.simple_spinner_item);
        mSpinner.setAdapter(adapter);

        fileDBAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        mListView.setAdapter(fileDBAdapter);

        mTextViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileDBAdapter.clear();

                mStringName = mEditText.getText().toString();
                mStringTag = mSpinner.getSelectedItem().toString();
                Log.d("hellooo", mStringName + "+" + mStringTag);

                final FirebaseDatabase fireDb = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = fireDb.getReference("article");

                Query query = myRef.orderByChild("author").equalTo(mStringName);

//                Query query = myRef.orderByChild("author").equalTo(mStringTag).equalTo(mStringName);
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        mStringResult = String.valueOf(dataSnapshot.child("").getValue());
                        Log.d("hellooo", "mStringResult" + mStringResult);
                        if(mStringResult.contains(mStringTag)){
                            fileDBAdapter.add(mStringResult);
                        }

//                        fileDBAdapter.add(String.valueOf(dataSnapshot.child("").getValue()));
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