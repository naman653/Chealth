package com.btp.chealth.utils;

public class Constants {
    private Constants(){};

    public static final String NEWS_BASE_URL = "https://newsapi.org/";
    public static final String CHEALTH_BASE_URL = "https://nutrition-btp.herokuapp.com";

    // Request Codes
    public static final int RC_SIGN_IN = 1;
    public static final int EDIT_CODE = 2;

    // Firestore Collection Names
    public static final String FEEDBACK = "Feedback";
    public static final String USER = "User";

    // Intent Extras
    public static final String IS_EDIT = "is_edit";

    // Strings for use
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String OTHER = "Other";

    // PrefService keys
    public static final String AGE = "age";
    public static final String GENDER = "gender";
    public static final String WEIGHT = "weight";
    public static final String HEIGHT = "height";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String BMI = "bmi";
}
