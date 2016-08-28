package com.coders.initiative.umoney.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.coders.initiative.umoney.AppController;
import com.coders.initiative.umoney.MainActivity;
import com.coders.initiative.umoney.R;
import com.coders.initiative.umoney.helpers.ConfigHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Kira on 8/27/2016.
 */
public class MerchantFragment extends Fragment implements View.OnClickListener{

    private Spinner merchantSpinner;
    private Button btnPayBill;
    private TextView mercAmount, mercReference, uMobilePin, uConfirmPin;

    String merchantAccount ="";
    String packageAmount  ="";
    String packageReference  ="";

    public MerchantFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_merchant, container, false);
        merchantSpinner = (Spinner) view.findViewById(R.id.spinnerBill);
        merchantSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        mercAmount = (TextView) view.findViewById(R.id.tvMercAmount);
        mercReference = (TextView) view.findViewById(R.id.tvMercReference);
        uMobilePin = (TextView) view.findViewById(R.id.tvMobilePin);
        uConfirmPin = (TextView) view.findViewById(R.id.tvConfirmMobilePin);
        btnPayBill = (Button) view.findViewById(R.id.btnPay);
        btnPayBill.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        switch(v.getId ()) {
            case R.id.btnPay :
                String packageAmount  = String.valueOf(mercAmount.getText().toString());
                String packageReference  = String.valueOf(mercReference.getText().toString());
                initPayment(merchantAccount, packageAmount, packageReference);
                Toast.makeText(MainActivity.getInstance(), "Send Payment!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    ProgressDialog progressDialog;
    private void initPayment(final String targetAccount, final String targetAmount, final String targetReference){
        new AsyncTask<Void, Void, Response>(){

            @Override
            protected Response doInBackground(Void... voids) {
                try {
                    OkHttpClient okHttpClient = ((AppController) MainActivity.getInstance().getApplication()).getMyHttpClient();

                    //TODO: CHANGE PARAM
                    //TODO: IF UNBANKED, MOBILE NUMBER ELSE BANK ACCOUNT
                    MediaType mediaType = MediaType.parse("application/json");

                    int min = 11111111;
                    int max = 888888888;

                    Random r = new Random();
                    int transID = r.nextInt(max - min + 1) + min;

                    RequestBody body = RequestBody.create(mediaType, "{\"channel_id\":\""+ ConfigHelper.URL_CHANNEL_ID+"\",\"transaction_id\":\""+transID+"\",\"source_account\":\"000000014909\",\"source_currency\":\"php\",\"biller_id\":\""+targetAccount+"\",\"reference1\":\""+targetReference+"\",\"reference2\":\"000000000B\",\"reference3\":\"000000000C\",\"amount\":"+targetAmount+"}");
                    Request request = new Request.Builder()
                            .url(ConfigHelper.URL_PAYMENT)
                            .post(body)
                            .addHeader(ConfigHelper.BORED_ID, ConfigHelper.BORED_CODER)
                            .addHeader(ConfigHelper.BORED_CLIENT, ConfigHelper.VOLDEMORT)
                            .addHeader("content-type", "application/json")
                            .addHeader("accept", "application/json")
                            .build();

                    Response response = okHttpClient.newCall(request).execute();

                    if(response!=null){
                        return  response;
                    }

                    return null;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                progressDialog = new ProgressDialog(MainActivity.getInstance());
                progressDialog.setMessage("Processing Payment...Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Response response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if(response!=null){
                    try {
                        String responseData = response.body().string();

                        String transactionID = "";
                        String confirmation = "";
                        String status = "";

                        JSONObject jsonObject = new JSONObject(responseData);
                        transactionID = jsonObject.getString("transaction_id");
                        confirmation = jsonObject.getString("confirmation_no");
                        status = jsonObject.getString("status");

                        String message ="";
                        if(status.equals("S")){
                            message ="TransID: "+transactionID+" Utility Payment successfully completed. \nConfirmation No:" + confirmation;
                        }else{
                            message ="TransID: "+transactionID+" Failed to process Utility Payment. Please try again later.";
                        }
                        final String finalMessage = message;
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(MainActivity.getInstance(), R.style.AppCompatAlertDialogStyle);
                                builder.setTitle("Utility Payment");
                                builder.setMessage(finalMessage);
                                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mercAmount.setText("");
                                        mercReference.setText("");
                                        uConfirmPin.setText("");
                                        uMobilePin.setText("");
                                       dialog.dismiss();
                                    }
                                });
                                //builder.setPositiveButton(android.R.string.ok, null);
                                builder.show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch (IOException io){
                        io.printStackTrace();
                    }
                }
                super.onPostExecute(response);
            }
        }.execute();
    }



    private class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
           /* Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();*/
            merchantAccount = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
}
