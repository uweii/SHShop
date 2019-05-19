package com.up.uwei.shshop.service;

import com.up.uwei.shshop.pojo.Goods;
import com.up.uwei.shshop.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitService {
    @GET("register/getCode")
    Observable<ResponseBody> getCode(@Query("email") String mail);

    @GET("register")
    Observable<ResponseBody> register(@Query("email") String mail, @Query("pwd") String pwd);

    @GET("loginWithEmail")
    Observable<ResponseBody> loginWithEmail(@Query("email") String mail, @Query("pwd") String pwd);

    @GET("getGoods")
    Observable<ArrayList<Goods>> getGoods(@Query("offset") int offset, @Query("length") int length,
         @Query("key") String key, @Query("place") String place, @Query("secondhand") int secondhand);

}
