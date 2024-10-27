package com.green.cloud.common;


import com.green.cloud.entity.UserAuth;

public class UserHolder {

    private static final ThreadLocal<UserAuth> tl = new ThreadLocal<>();


    public static UserAuth getUser(){
        return tl.get();
    }

    public static void saveUser(UserAuth userAuth){
        tl.set(userAuth);
    }

    public static void removeUser(){
        tl.remove();
    }

}


