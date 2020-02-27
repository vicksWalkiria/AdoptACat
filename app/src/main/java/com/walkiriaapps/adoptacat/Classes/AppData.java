package com.walkiriaapps.adoptacat.Classes;

import android.content.Intent;
import android.content.SharedPreferences;

import com.walkiriaapps.adoptacat.AdoptACatActivity;
import com.walkiriaapps.adoptacat.MainActivity;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppData {

    //VARIABLES//
    public static String token = "";
    public static String userId = "";

    //PREFERENCES//
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";

    //SCREEN NAMES//
    public static final String SPLASH_SCREEN = "SplashScreen";
    public static final String LOGIN_SCREEN = "LoginScreen";
    public static final String SIGNUP_SCREEN = "SignUpScreen";
    public static final String MAIN_SCREEN = "MainScreen";
    public static final String CAT_SCREEN = "CatScreen";


    //PARAMS
    public static final String CAT_ID = "cat_id";


    //SERVICES//
    public static final String ROOT_URL = "http://10.0.2.2/adoptACat/";
    private static final String API_URL = ROOT_URL + "api/v1/";
    public static final String LOGIN_SERVICE_URL = API_URL + "login.php";
    public static final String SIGNUP_SERVICE_URL = API_URL + "signup.php";
    public static final String ADOPT_SERVICE_URL = API_URL + "get_cats.php";
    public static String GET_CAT_SERVICE_URL = API_URL + "get_cat.php";
    public static final String PICTURES_URL = ROOT_URL + "pictures";



    //SERVICES RESPONSES//
    public static final String JSON_DATA_FIELD = "data";
    public static final String JSON_OK = "OK";
    public static final String TOKEN_FIELD = "token";
    public static final String MESSAGE_FIELD = "message";
    public static final String USER_ID_FIELD = "user_id";
    public static final String CATS_FIELD = "cats";
    public static final String CAT_FIELD = "cat";
    public static final String JSON_200 = "200";
    public static final String CODE = "code";




    //REQUESTS_IDs
    public static final int REQUEST_ADOPTION = 1;
    public static final int REQUEST_TEMPORARY = 0;
    public static final int REQUEST_GET_CAT = 1;


    public static String encodePassword(String string) {
        MessageDigest md = null;
        String hex = "";
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(string.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            hex = String.format("%064x", new BigInteger(1, digest));


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

        return hex;

    }

    public static void loginOK(AdoptACatActivity activity, String tokenFromServer, SharedPreferences prefs, String userName, String password) {

        token = tokenFromServer;
        SharedPreferences.Editor edit = prefs.edit();

        edit.putString(AppData.USER_NAME, userName);
        edit.putString(AppData.PASSWORD, password);
        edit.commit();

        Intent mainIntent = new Intent(activity, MainActivity.class);
        activity.startActivity(mainIntent);
        activity.finish();
    }


}
