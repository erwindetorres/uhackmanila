package com.coders.initiative.umoney.model;

import com.orm.SugarRecord;

/**
 * Created by Kira on 8/28/2016.
 */
public class PaymentResponseModel extends SugarRecord {

    public static final String CHANNEL_ID = "channel_id";
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String STATUS = "status";
    public static final String CONFIRMATION_NO = "confirmation_no";
    public static final String ERROR_MESSAGE = "error_message";

    String channelid;
    String transactionid;
    String status;
    String confirmationno;
    String errormessage;
    String message;

    public PaymentResponseModel(){
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaymentResponseModel(String channelid, String transactionid, String status, String confirmationno, String errormessage, String message){
        this.channelid = channelid;
        this.transactionid = transactionid;
        this.status = status;
        this.confirmationno = confirmationno;
        this.errormessage = errormessage;
        this.message = message;

    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirmationno() {
        return confirmationno;
    }

    public void setConfirmationno(String confirmationno) {
        this.confirmationno = confirmationno;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }
}
