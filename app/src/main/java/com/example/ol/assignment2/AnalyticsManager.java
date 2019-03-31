package com.example.ol.assignment2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ol.assignment2.model.Book;
import com.example.ol.assignment2.model.Review;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.HashMap;
import java.util.Map;


public class AnalyticsManager {
    private static String TAG = "AnalyticsManager";
    private static AnalyticsManager mInstance = null;
    private FirebaseAnalytics mFirebaseAnalytics;
    private  MixpanelAPI mMixpanel;
    public static final String MIXPANEL_TOKEN = "4d8eae21184ec135ec1bc6d53e92a4c7";


    private AnalyticsManager() {

    }

    public static AnalyticsManager getInstance() {

        if (mInstance == null) {
            mInstance = new AnalyticsManager();
        }
        return (mInstance);
    }

    public void init(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        // We chose Mixpanel Analytics.
        mMixpanel = MixpanelAPI.getInstance(context, MIXPANEL_TOKEN);

    }

    public void trackSearchEvent(String i_SearchedBook) {
        // Analytics for check which books, the users are searched.
        //Firebase
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.SEARCH_TERM, i_SearchedBook);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH,params);

        // MixPanel
        Map<String, Object> eventParams = new HashMap<String, Object>();
        eventParams.put("searched_book", i_SearchedBook);
        mMixpanel.trackMap("search",eventParams);
    }

    public void trackSignupTypeEvent(String i_SignupMethod) {

        // Analytics signup between Regular (With User\Pass) , Facebook  or Google.
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.SIGN_UP_METHOD, i_SignupMethod);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, params);

        // MixPanel
        Map<String, Object> eventParams = new HashMap<String, Object>();
        eventParams.put("method", i_SignupMethod);
        mMixpanel.trackMap("sign_up_method",eventParams);
    }

    public void trackRevenuesSellings(String i_BookName, double i_BookPrice) {

        // Track only after the money that entered.
        Bundle params = new Bundle();
        params.putString("book_name", i_BookName);
        // I'm do PRICE and VALUE (is same content, but I want to flow in 2 options)
        params.putString(FirebaseAnalytics.Param.CURRENCY, "USD"); // I'm do PRICE and VALUE (is same content, but I want to flow in 2 options in firebase)
        params.putDouble(FirebaseAnalytics.Param.VALUE, i_BookPrice); // I'm do PRICE and VALUE (is same content, but I want to flow in 2 options in firebase)
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, params);

        // MixPanel
        Map<String, Object> eventParams = new HashMap<String, Object>();
        eventParams.put("book_name", i_BookName);
        eventParams.put("price", i_BookPrice);
        mMixpanel.trackMap("revenues_sellings",eventParams);
    }

    public void trackReviewsScores(String i_BookName, double i_ReviewScore) {

        String eventName = "reviews_total";
        String paramsName = "book_name";

        Bundle params = new Bundle();
        params.putString(paramsName, i_BookName);
        params.putDouble(FirebaseAnalytics.Param.SCORE, i_ReviewScore);
        mFirebaseAnalytics.logEvent(eventName, params);

        // MixPanel
        Map<String, Object> eventParams = new HashMap<String, Object>();
        eventParams.put(paramsName, i_BookName);
        eventParams.put("review_score", i_ReviewScore);
        mMixpanel.trackMap(eventName,eventParams);
    }

    public void trackViewBooks(String i_BookName) {

        String eventName = "books_views";
        String paramsName = "book_name";
        Bundle params = new Bundle();
        params.putString(paramsName, i_BookName);
        mFirebaseAnalytics.logEvent(eventName, params);


        // MixPanel
        Map<String, Object> eventParams = new HashMap<String, Object>();
        eventParams.put(paramsName, i_BookName);
        mMixpanel.trackMap(eventName,eventParams);
    }

    public void trackUsersForgotPassword(String i_Username)
    {
        String eventName = "users_forgot_password";
        String paramsName = "user_name";
        Bundle params = new Bundle();
        params.putString(paramsName, i_Username);
        mFirebaseAnalytics.logEvent(eventName, params);

        // MixPanel
        Map<String, Object> eventParams = new HashMap<String, Object>();
        eventParams.put(paramsName, i_Username);
        mMixpanel.trackMap(eventName,eventParams);
    }

    public void trackHowManyContinueAsAnonymous()
    {
        // I want only count the number of anonymous users login and because it the params is null \ empty.
        String eventName = "anonymous_users_count";
        Bundle params = new Bundle();
        mFirebaseAnalytics.logEvent(eventName, Bundle.EMPTY);

        mMixpanel.trackMap(eventName,null);

    }

    public void setUserID(String id) {
        mFirebaseAnalytics.setUserId(id);

        mMixpanel.alias(id, null);
        mMixpanel.identify(id);
        mMixpanel.getPeople().identify(mMixpanel.getDistinctId());
    }

    public void setUserProperty(String i_Name , String i_Value) {
        mFirebaseAnalytics.setUserProperty(i_Name,i_Value);

        mMixpanel.getPeople().set(i_Name,i_Value);
    }

}