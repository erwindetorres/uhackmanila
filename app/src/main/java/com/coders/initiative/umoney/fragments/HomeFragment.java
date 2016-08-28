package com.coders.initiative.umoney.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coders.initiative.umoney.AppController;
import com.coders.initiative.umoney.MainActivity;
import com.coders.initiative.umoney.QRActivity;
import com.coders.initiative.umoney.R;
import com.coders.initiative.umoney.adapter.AccountsAdapter;
import com.coders.initiative.umoney.helpers.AccountOpenListener;
import com.coders.initiative.umoney.helpers.ConfigHelper;
import com.coders.initiative.umoney.model.AccountModel;
import com.coders.initiative.umoney.model.PaymentResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Kira on 8/27/2016.
 */
public class HomeFragment extends Fragment implements AccountOpenListener{

    public static String TAG = ConfigHelper.PACKAGE_NAME + ".ContactsFragment";
    ProgressDialog progressDialog;


    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerViewAccounts;
    private AccountsAdapter accountsAdapter;

    private List<AccountModel> myAccounts;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        recyclerViewAccounts = (RecyclerView) view.findViewById(R.id.rv_Accounts);
        recyclerViewAccounts.setLayoutManager(new LinearLayoutManager(MainActivity.getInstance()));
        recyclerViewAccounts.setItemAnimator(new DefaultItemAnimator());

        myAccounts = new ArrayList<>();
        myAccounts = initDummy();
        setList(myAccounts);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initBalance();
            }
        });

        initBalance();


        return view;

    }

    private void setList(List<AccountModel> list) {

        accountsAdapter = new AccountsAdapter(MainActivity.getInstance(), list, HomeFragment.this);
        recyclerViewAccounts.setAdapter(accountsAdapter);
        accountsAdapter.notifyDataSetChanged();

    }

    private List<AccountModel> initDummy(){
        List<AccountModel> list = new ArrayList<>();
        AccountModel am = new AccountModel();
        //TODO: IF NOT REGISTERED, PUT MOBILE NUMBER FROM REGISTRATION
        am.setAccountNo("0912 3456 789");
        am.setAccountName("KOPIKO BLACK");
        am.setAvailableBalance("0.00");
        am.setCurrency("PHP ");
        am.setStatus("ACTIVE");
        am.setAccountType("TEMP");
        list.add(am);

        //SAVINGS DEACTIVATED UNLESS UPGRADE ACCOUNT
        am = new AccountModel();
        am.setAccountNo("N/A");
        am.setAccountName("N/A");
        am.setAvailableBalance("0.00");
        am.setCurrency("PHP ");
        am.setStatus("DEACTIVE");
        am.setAccountType("uSavings");
        list.add(am);

        return list;
    }

    public void initBalance(){
        new AsyncTask<Void,Void, List<AccountModel>>(){

            List<AccountModel> accounts;

            @Override
            protected List<AccountModel> doInBackground(Void... voids) {

                if (accounts == null) {
                    accounts = new ArrayList<AccountModel>();
                }

                try {
                    OkHttpClient okHttpClient = ((AppController) MainActivity.getInstance().getApplication()).getMyHttpClient();

                    //TODO: CHANGE PARAM
                    //TODO: IF UNBANKED, MOBILE NUMBER ELSE BANK ACCOUNT
                    Request request = new Request.Builder()
                            .url(ConfigHelper.URL_ACCOUNT + "000000014909")
                            .get()
                            .addHeader(ConfigHelper.BORED_ID, ConfigHelper.BORED_CODER)
                            .addHeader(ConfigHelper.BORED_CLIENT, ConfigHelper.VOLDEMORT)
                            .addHeader("content-type", "application/json")
                            .addHeader("accept", "application/json")
                            .build();

                    Response response = okHttpClient.newCall(request).execute();

                    if(response!=null){
                        try {
                            String responseData = response.body().string();
                            Log.d(TAG, "RESPONSE: " + responseData);

                            JSONArray jsonArray = new JSONArray(responseData);

                            for(int x=0;x<jsonArray.length();x++){


                                JSONObject jsonObject = jsonArray.getJSONObject(x);
                                Log.d(TAG, "ACCOUNT NO:" + jsonObject.get(AccountModel.JSON_ACCOUNT_NO));
                                Log.d(TAG, "ACCOUNT NAME:" + jsonObject.get(AccountModel.JSON_ACCOUNT_NAME));
                                Log.d(TAG, "ACCOUNT BALANCE:" + jsonObject.get(AccountModel.JSON_AVAIL_BALANCE));

                                AccountModel retAccount = new AccountModel();
                                retAccount.setAccountNo(jsonObject.getString(AccountModel.JSON_ACCOUNT_NO));
                                retAccount.setAccountName(jsonObject.getString(AccountModel.JSON_ACCOUNT_NAME));
                                retAccount.setAvailableBalance(jsonObject.getString(AccountModel.JSON_AVAIL_BALANCE));
                                retAccount.setCurrency(jsonObject.getString(AccountModel.JSON_CURRENCY));
                                retAccount.setCurrentBalance(jsonObject.getString(AccountModel.JSON_CURRENT_BALANCE));
                                retAccount.setStatus(jsonObject.getString(AccountModel.JSON_STATUS));

                                accounts.add(retAccount);
                            }

                            AccountModel am = new AccountModel();
                            am.setAccountNo("N/A");
                            am.setAccountName("N/A");
                            am.setAvailableBalance("0.00");
                            am.setCurrency("PHP ");
                            am.setStatus("DEACTIVE");
                            am.setAccountType("uSavings");
                            accounts.add(am);

                            return accounts;

                        } catch (JSONException je) {
                            je.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                    return null;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<AccountModel> response) {
                if (response != null) {
                    if (response.size() > 0) {
                        recyclerViewAccounts.setLayoutManager(new LinearLayoutManager(MainActivity.getInstance()));
                        recyclerViewAccounts.setItemAnimator(new DefaultItemAnimator());
                        accountsAdapter = new AccountsAdapter(MainActivity.getInstance(), response, HomeFragment.this);

                        recyclerViewAccounts.setAdapter(accountsAdapter);
                        accountsAdapter.notifyDataSetChanged();

                        Toast.makeText(MainActivity.getInstance(), "Balance refreshed.", Toast.LENGTH_LONG).show();
                    }

                }
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                super.onPostExecute(response);
            }
        }.execute();
    }

    @Override
    public void onAccountOpen(int position) {
       switch(position){
           case 1:
               Toast.makeText(MainActivity.getInstance(), "Upgrade your account to enable this feature", Toast.LENGTH_SHORT).show();
               break;
           default:
               showDialog();
               break;
       }

    }

    private Dialog dialog;
    private TextView btnCancel;
    public void showDialog() {
        if (dialog == null) {
            dialog = new Dialog(MainActivity.getInstance(), R.style.CustomDialogTheme);
        }
        LayoutInflater inflater = MainActivity.getInstance().getLayoutInflater();
        View dLayout = inflater.inflate(R.layout.dialog_purchase_options,null);
        dialog.setContentView(dLayout);

        btnCancel = (TextView) dLayout.findViewById(R.id.dialog_purchase_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        String[] values = new String[] { "Purchase Goods", "Purchase Materials", "Pay Merchant" };
        ListView listView = (ListView) dLayout.findViewById(R.id.lv_puchase_options);
        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.getInstance(), android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position,
                                    long id) {

                switch (position){
                    case 0:
                        //PURCHASE GOODS QRSCANNING
                        Intent i = new Intent(MainActivity.getInstance(), QRActivity.class);
                        startActivity(i);
                        break;
                    case 1:
                        initPayment();
                        //PURCHASE MATERIALS FUND TRANSFER KEY: MERC
                        break;
                    case 2:
                        //PAY MERCHANT, FUND TRANSFER: BILL
                        break;
                    default:
                        //DO NOTHING
                        break;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },2000);

                //Toast.makeText(MainActivity.getInstance(), "" + adapter.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

        });
        dialog.show();

    }

    ProgressDialog progressDialog;
    private void initPayment(){

        LayoutInflater inflater = MainActivity.getInstance().getLayoutInflater();
        View dLayout = inflater.inflate(R.layout.dialog_pay_merchant,null);
        dialog.setContentView(dLayout);


        Button btnSendPayment = (Button) dLayout.findViewById(R.id.dialog_send_payment);

        final TextView ref = (TextView) dLayout.findViewById(R.id.tvPayMercRef);
        final TextView amnt = (TextView) dLayout.findViewById(R.id.tvPayMercAmount);
        final TextView pin = (TextView) dLayout.findViewById(R.id.tvPayMobilePin);
        final TextView merc = (TextView) dLayout.findViewById(R.id.tvMercNumber);

        final String targetAccount = String.valueOf(merc.getText().toString());
        final String targetReference = String.valueOf(ref.getText().toString());
        //String pin = String.valueOf(merc.getText().toString());
        final String targetAmount = String.valueOf(amnt.getText().toString());


        btnSendPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                String channelID = "";
                                String errorMessage = "";

                                JSONObject jsonObject = new JSONObject(responseData);
                                channelID = jsonObject.getString("channel_id");
                                transactionID = jsonObject.getString("transaction_id");
                                confirmation = jsonObject.getString("confirmation_no");
                                status = jsonObject.getString("status");
                                errorMessage = jsonObject.getString("error_message");

                                String message ="";
                                if(status.equals("S")){
                                    message ="TransID: "+transactionID+" Utility Payment successfully completed. \nConfirmation No:" + confirmation;
                                }else{
                                    message ="TransID: "+transactionID+" Failed to process Utility Payment. Please try again later.";
                                }
                                PaymentResponseModel prm = new PaymentResponseModel(channelID, transactionID, status, confirmation, errorMessage,message);
                                prm.save();

                                final String finalMessage = message;
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder builder =
                                                new AlertDialog.Builder(MainActivity.getInstance(), R.style.AppCompatAlertDialogStyle);
                                        builder.setTitle("Merchant Payment");
                                        builder.setMessage(finalMessage);
                                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                merc.setText("");
                                                ref.setText("");
                                                amnt.setText("");
                                                pin.setText("");
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
        });


    }

}
