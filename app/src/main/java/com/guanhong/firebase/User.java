package com.guanhong.firebase;

public class User {


    public String userId;
    public String name;
    public String email;
    public String invitation;
    public String friends;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userId, String username, String email, String invitation, String friends) {
        this.userId = userId;
        this.name = username;
        this.email = email;
        this.invitation = invitation;
        this.friends = friends;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }
}
