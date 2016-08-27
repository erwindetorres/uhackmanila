package com.coders.initiative.umoney.model;

/**
 * Created by Kira on 8/27/2016.
 */
public class AccountModel {
    /**
     * [
     {
     "account_no": "000000014901",
     "currency": "PHP",
     "account_name": "UBP 000000014901",
     "status": "ACTIVE",
     "avaiable_balance": "100000.0000",
     "current_balance": "100000.0000"
     }
     ]
     */

    /**
     * JSON FIELDS
     */
    public static final String JSON_ACCOUNT_NO = "account_no";
    public static final String JSON_CURRENCY = "currency";
    public static final String JSON_ACCOUNT_NAME = "account_name";
    public static final String JSON_STATUS = "status";
    public static final String JSON_AVAIL_BALANCE = "avaiable_balance";
    public static final String JSON_CURRENT_BALANCE = "current_balance";

    private String accountNo;
    private String currency;
    private String accoutnName;
    private String status;
    private String availableBalance;
    private String currentBalance;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccoutnName() {
        return accoutnName;
    }

    public void setAccoutnName(String accoutnName) {
        this.accoutnName = accoutnName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }
}
