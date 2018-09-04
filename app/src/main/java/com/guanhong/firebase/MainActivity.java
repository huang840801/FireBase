package com.guanhong.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "AppWork";
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextSearch;
    private EditText mEditTextTitle;
    private EditText mEditTextContent;

    private Spinner mSpinner;

    private TextView mTextViewRegister;
    private TextView mFriendStatus;
    private TextView mTextViewAddFriend;
    private TextView mTextViewPost;
    private TextView mTextViewSearch;

    private TextView mTextViewNextPage;

    private String mUserName;
    private String mUserEmail;

    private String mSearchEmail;

    private String mUserId;
    private String mArticleTitle;
    private String mArticleContent;
    private String mArticleTag;
    private String mArticleAuthor;
    private String mArticleCreatedTime;

    private Context mContext;

    private TextView mTextViewFriend;
    private TextView mTextViewFriendConfirm;
    private TextView mTextViewSearchMyFriend;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "hellooooo");

        mContext = this;

        mEditTextEmail = findViewById(R.id.editText_useremail);
        mEditTextName = findViewById(R.id.editText_username);
        mTextViewRegister = findViewById(R.id.textView_register);

        mEditTextSearch = findViewById(R.id.editText_search);
        mTextViewSearch = findViewById(R.id.textView_search);
        mFriendStatus = findViewById(R.id.textView_status);
        mTextViewAddFriend = findViewById(R.id.textView_addfriend);

        mEditTextTitle = findViewById(R.id.edittext_title);
        mEditTextContent = findViewById(R.id.edittext_content);
        mSpinner = findViewById(R.id.spinner_tag);
//        mEditTextTag = findViewById(R.id.spinner_tag);
//        mEditTextCreatedTime = findViewById(R.id.edittext_created_time);
        mTextViewPost = findViewById(R.id.textView_post);

        mTextViewNextPage = findViewById(R.id.textView_page2);

        mTextViewFriend = findViewById(R.id.textView_my_friend);
        mTextViewFriendConfirm = findViewById(R.id.textView_my_friend_confirm);
        mTextViewSearchMyFriend = findViewById(R.id.textView_search_invitation_list);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tag_array, android.R.layout.simple_spinner_item);
        mSpinner.setAdapter(adapter);


        mTextViewSearch.setVisibility(View.GONE);
        mTextViewPost.setVisibility(View.GONE);
        mTextViewAddFriend.setVisibility(View.GONE);
//        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference myUserDataBase = userDatabase.getReference("users");


        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mUserName = mEditTextName.getText().toString();
                mUserEmail = mEditTextEmail.getText().toString();


                writeNewUser(mUserName, mUserEmail);

                mTextViewSearch.setVisibility(view.getVisibility());
                mTextViewPost.setVisibility(view.getVisibility());

                FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myUserDataBase = userDatabase.getReference("users");
//                Query query = myUserDataBase.orderByChild("email").equalTo(otherEmail);


            }
        });
        mTextViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
                mUserId = userData.getString("authorId", "");

                final String otherEmail = mEditTextSearch.getText().toString();


                FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myUserDataBase = userDatabase.getReference("users");

                Query query = myUserDataBase.orderByChild("email").equalTo(otherEmail);

//                Log.d("mFriendStatus", "edittext = " + otherEmail);


                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        mFriendStatus.setText(dataSnapshot.getValue().toString());
                        Log.d("mFriendStatus", "othername = " + dataSnapshot.child("name").getValue());
                        Log.d("mFriendStatus", "otherId = " + dataSnapshot.child("userId").getValue());

                        SharedPreferences otherData = mContext.getSharedPreferences("otherData", Context.MODE_PRIVATE);
                        otherData.edit()
                                .putString("otherId", "" + dataSnapshot.child("userId").getValue())
                                .commit();

//                        if (dataSnapshot.child("invitation").equals(null) || dataSnapshot.child("invitation").equals(null)) {
//                            mFriendStatus.setText("你們不是好友");


                        Log.d(TAG, ""+dataSnapshot.getValue().toString());

                        if (!dataSnapshot.getValue().toString().contains("invitation")) {
                            mFriendStatus.setText("你們不是好友");

                            mTextViewAddFriend.setVisibility(view.getVisibility());
//                            if (!dataSnapshot.child("invitation").getValue().toString().contains(mUserId) || !dataSnapshot.child("friends").getValue().toString().contains(mUserId)) {
//                                mFriendStatus.setText("你們不是好友");
//                                mTextViewAddFriend.setVisibility(view.getVisibility());
//                            } else if (dataSnapshot.child("invitation").getValue().toString().contains(mUserId)) {
//                                mFriendStatus.setText("待確認");
//                            }
                        }
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

        mTextViewSearchMyFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
                mUserId = userData.getString("authorId", "");
                Log.d("gooooooooo ", mUserId);

                FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myUserDataBase = userDatabase.getReference("users");

                Query query = myUserDataBase.orderByChild(mUserId);
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        mTextViewFriend.setText(dataSnapshot.child("invitation").getValue() +"");
//                        if(!dataSnapshot.child("invitation").getValue().toString().equals("")){
//                            Log.d("gooooooooo ","null");
//                        }else {
//                            Log.d("gooooooooo ","no null = " + dataSnapshot.child("invitation").getValue().toString());
//
//                        }
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
//                mTextViewFriend.setText(query.orderByChild("").toString());
//                mTextViewFriendConfirm.setVisibility(view.getVisibility());
            }
        });

        mTextViewFriendConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
                mUserId = userData.getString("authorId", "");

                SharedPreferences otherData = mContext.getSharedPreferences("otherData", Context.MODE_PRIVATE);
                String mOtherId = otherData.getString("otherId", "");

                Log.d("mFriendStatus", "mUserId = " + mUserId);
                Log.d("mFriendStatus", "mOtherId = " + mOtherId);

                FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myUserDataBase = userDatabase.getReference("users");

                myUserDataBase.child(mUserId).child("friends").child(mOtherId).setValue("true");
                myUserDataBase.child(mUserId).child("invitation").setValue("");

                FirebaseDatabase otherDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myOtherDataBase = otherDatabase.getReference("users");

                myOtherDataBase.child(mOtherId).child("friends").child(mUserId).setValue("true");
                myOtherDataBase.child(mOtherId).child("invitation").setValue("");
            }
        });


        mTextViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
                mUserId = userData.getString("authorId", "");


                mArticleTitle = mEditTextTitle.getText().toString();
                mArticleContent = mEditTextContent.getText().toString();
//                mArticleAuthor = mEditTextAuthor.getText().toString();
                mArticleTag = mSpinner.getSelectedItem().toString();
                mArticleAuthor = mEditTextName.getText().toString();
//                mArticleCreatedTime = mEditTextCreatedTime.getText().toString();
                mArticleCreatedTime = "201809051012";

                Log.d("mUserId = ", "" + mUserId);


                Article article = new Article(mUserId, mArticleTitle, mArticleContent, mArticleTag, mArticleAuthor, mArticleCreatedTime);

                postArticle(article);
            }
        });

        mTextViewAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFriendStatus.setText("待確認");
                mTextViewAddFriend.setVisibility(View.GONE);


                SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
                mUserId = userData.getString("authorId", "");

                SharedPreferences otherData = mContext.getSharedPreferences("otherData", Context.MODE_PRIVATE);
                String mOtherId = otherData.getString("otherId", "");

                Log.d("mFriendStatus", "mUserId = " + mUserId);
                Log.d("mFriendStatus", "mOtherId = " + mOtherId);


                FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myUserDataBase = userDatabase.getReference("users");

                myUserDataBase.child(mUserId).child("invitation").child(mOtherId).setValue("待接受");


                FirebaseDatabase otherDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myOtherDataBase = otherDatabase.getReference("users");

                myOtherDataBase.child(mOtherId).child("invitation").child(mUserId).setValue("待確認");


            }
        });


        mTextViewNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Page2.class);
                startActivity(intent);
            }
        });


    }

    private void postArticle(Article article) {

        FirebaseDatabase emailDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myArticleDataBase = emailDatabase.getReference("article");

        myArticleDataBase.push().setValue(article);
    }


    private void writeNewUser(String name, String email) {

        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myUserDataBase = userDatabase.getReference("users");

        String key = myUserDataBase.push().getKey();

        SharedPreferences userdata = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
        userdata.edit()
                .putString("authorId", key)
                .putString("username", name)
                .putString("userEmail", email)
                .commit();

        User user = new User(key, name, email, "", "");

        myUserDataBase.child(key).setValue(user);

    }


}
