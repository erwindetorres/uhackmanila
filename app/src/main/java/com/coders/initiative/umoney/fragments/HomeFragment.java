package com.coders.initiative.umoney.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coders.initiative.umoney.AppController;
import com.coders.initiative.umoney.MainActivity;
import com.coders.initiative.umoney.R;
import com.coders.initiative.umoney.helpers.ConfigHelper;
import com.coders.initiative.umoney.model.AccountModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kira on 8/27/2016.
 */
public class HomeFragment extends Fragment {

    public static String TAG = ConfigHelper.PACKAGE_NAME + ".ContactsFragment";

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        new AsyncTask<Void,Void, Response>(){

            @Override
            protected Response doInBackground(Void... voids) {
                try {
                    OkHttpClient okHttpClient = ((AppController) MainActivity.getInstance().getApplication()).getMyHttpClient();

                    Request request = new Request.Builder()
                            .url(ConfigHelper.URL_ACCOUNT + "000000014901")
                            .get()
                            .addHeader(ConfigHelper.BORED_ID, ConfigHelper.BORED_CODER)
                            .addHeader(ConfigHelper.BORED_CLIENT, ConfigHelper.VOLDEMORT)
                            .addHeader("content-type", "application/json")
                            .addHeader("accept", "application/json")
                            .build();

                    Response response = okHttpClient.newCall(request).execute();
                    return response;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Response response) {
                if(response!=null){
                    try {
                        String responseData = response.body().string();
                        Log.d(TAG, "RESPONSE: " + responseData);

                        JSONObject json = new JSONObject(responseData);
                        AccountModel account = new AccountModel();
                        account.setAccountNo(json.getString(AccountModel.JSON_ACCOUNT_NO));
                        account.setAccoutnName(json.getString(AccountModel.JSON_ACCOUNT_NAME));
                        account.setAvailableBalance(json.getString(AccountModel.JSON_AVAIL_BALANCE));
                        account.setCurrentBalance(json.getString(AccountModel.JSON_CURRENT_BALANCE));
                        account.setCurrency(json.getString(AccountModel.JSON_CURRENCY));
                        account.setStatus(json.getString(AccountModel.JSON_STATUS));


                    } catch (JSONException e) {

                    }catch (IOException e){

                    }
                }
                super.onPostExecute(response);
            }
        }.execute();

        return view;

    }



}
