package com.coders.initiative.umoney.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coders.initiative.umoney.AppController;
import com.coders.initiative.umoney.MainActivity;
import com.coders.initiative.umoney.R;
import com.coders.initiative.umoney.helpers.ConfigHelper;
import com.coders.initiative.umoney.model.AccountModel;
import com.coders.initiative.umoney.model.PaymentResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kira on 8/27/2016.
 */
public class LoansFragment extends Fragment implements View.OnClickListener{

    Button btnRequestQuote;
    TextView tvLoanAmount, tvLoanInterest, tvYearsToPay;
    public LoansFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_loans, container, false);
        tvLoanAmount = (TextView) view.findViewById(R.id.tvPrincipalAmount);
        tvLoanInterest = (TextView) view.findViewById(R.id.tvRate);
        tvYearsToPay = (TextView) view.findViewById(R.id.tvYearsToPay);
        btnRequestQuote  = (Button) view.findViewById(R.id.btnRequestQuote);
        btnRequestQuote.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRequestQuote:
                String amount = String.valueOf(tvLoanAmount.getText().toString());
                String rate = "5.5";
                String years = String.valueOf(tvYearsToPay.getText().toString());
                initRequestQuote(amount, rate, years);
                break;
            default:
                break;
        }
    }
    ProgressDialog progressDialog;
    private void initRequestQuote(final String amount, final String rate, final String years){
        new AsyncTask<Void, Void, Response>() {
            @Override
            protected Response doInBackground(Void... voids) {
                try {
                    OkHttpClient okHttpClient = ((AppController) MainActivity.getInstance().getApplication()).getMyHttpClient();

                    //TODO: CHANGE PARAM
                    //TODO: IF UNBANKED, MOBILE NUMBER ELSE BANK ACCOUNT
                    String fUrl = String.format(ConfigHelper.URL_LOANS,amount,rate,years);
                    Request request = new Request.Builder()
                            .url(fUrl)
                            .get()
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
                progressDialog.setMessage("Requesting quote...");
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
                        System.out.println("RESPONSE: " + responseData);


                        try {
                            JSONObject jsonObject = new JSONObject(responseData);
                            String json = jsonObject.getString("loan");
                            System.out.println("JSON:" + json);


                            JSONObject jobj = new JSONObject(json);
                            /*System.out.println("NOY:" + jobj.getString("noy"));
                            System.out.println("rate:" + jobj.getString("interest"));
                            System.out.println("principal:" + jobj.getString("principal"));
                            System.out.println("income:" + jobj.getString("income"));*/

                            double total = Double.parseDouble(jobj.getString("total"));
                            double income = Double.parseDouble(jobj.getString("income"));

                            DecimalFormat formatter = new DecimalFormat("#,###.00");

                            final String finalMessage = "Principal Loan: " + jobj.getString("principal") + "\n"
                                                        +"Monthly Amortization for "+jobj.getString("noy")+" year(s): " + formatter.format(total) + "\n"
                                                        +"Fixed Rate: " + jobj.getString("interest") + "%\n\n"
                                                        +"To avail this loan you need to have a monthly income of: " + formatter.format(income);
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder =
                                            new AlertDialog.Builder(MainActivity.getInstance(), R.style.AppCompatAlertDialogStyle);
                                    builder.setTitle("Loan Offer");
                                    builder.setMessage(finalMessage);
                                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    //builder.setPositiveButton(android.R.string.ok, null);
                                    builder.show();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }catch (IOException io){
                        io.printStackTrace();
                    }
                }
                super.onPostExecute(response);
            }
        }.execute();

    }


}
