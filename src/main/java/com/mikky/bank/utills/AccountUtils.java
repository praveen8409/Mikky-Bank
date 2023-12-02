package com.mikky.bank.utills;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This User has already account created";

    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been created successfully";

    public static final String ACCOUNT_NOT_EXISTS_MESSAGE = "Account is not exists";
    public static final String ACCOUNT_NOT_EXISTS_CODE = "003";

    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_MESSAGE = "Account found successfully";
    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Amount credited successfully";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";
    public static final String ACCOUNT_DEBITED_SUCCESS = "007";
    public static final String ACCOUNT_DEBITED_MESSAGE = "Account has been successfully debited";

    public static String generatedAccountName(){
        /**
         *  2023 + randomSixDigits
         */

        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        // Generate a random number between min and max
        int randomNumber = (int) Math.floor(Math.random()*(max-min+1)+min);

        // Convert the randomNumber and currentYear into String, Then concatenate them

        String year = String.valueOf(currentYear);
        String number = String.valueOf(randomNumber);

        StringBuilder accountNumber = new StringBuilder();
        return accountNumber.append(year).append(number).toString();
    }

}
