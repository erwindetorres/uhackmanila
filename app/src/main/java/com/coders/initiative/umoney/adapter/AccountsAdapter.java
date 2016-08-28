package com.coders.initiative.umoney.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coders.initiative.umoney.MainActivity;
import com.coders.initiative.umoney.R;
import com.coders.initiative.umoney.helpers.AccountOpenListener;
import com.coders.initiative.umoney.model.AccountModel;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Kira on 8/28/2016.
 */
public class AccountsAdapter  extends RecyclerView.Adapter<AccountsAdapter.AccountViewHolder> {

    private List<AccountModel> accountModelList;
    private LayoutInflater inflater;
    private AccountOpenListener listener;

    public AccountsAdapter(Context context, List<AccountModel> accountItem, AccountOpenListener listener) {
        inflater = LayoutInflater.from(context);
        this.accountModelList = accountItem;
        this.listener = listener;
    }

    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccountViewHolder(inflater.inflate(R.layout.card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        //TODO: AMOUNT FORMATTING

        if (position % 2 == 1) {
            holder.cardType.setImageDrawable(MainActivity.getInstance().getResources().getDrawable(R.mipmap.visa));
            holder.walletType.setText(String.valueOf("Savings"));
            holder.cardBackground.setBackgroundDrawable(MainActivity.getInstance().getResources().getDrawable(R.drawable.cardbackground_sky));
        } else {
            holder.tvOverlay.setVisibility(View.GONE);
            holder.cardBackground.setBackgroundDrawable(MainActivity.getInstance().getResources().getDrawable(R.drawable.cardbackground_world));
        }

        double amount = Double.parseDouble(accountModelList.get(position).getAvailableBalance());
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        holder.accountNo.setText(String.valueOf(accountModelList.get(position).getAccountNo()));
        holder.accountName.setText(String.valueOf(accountModelList.get(position).getAccountName()));
        holder.availBalance.setText(String.valueOf(accountModelList.get(position).getCurrency()) + " " + String.valueOf(formatter.format(amount)));
    }

    @Override
    public int getItemCount() {
        return accountModelList.size();
    }

    class AccountViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout cardBackground;
        private TextView walletType;
        private TextView accountName;
        private TextView accountNo;
        private TextView availBalance;
        private ImageView cardType;
        private TextView tvOverlay;
        //TextView currentBalance;
        //TextView status;

        AccountViewHolder(View itemView) {
            super(itemView);
            cardBackground = (LinearLayout) itemView.findViewById(R.id.cardBackground);
            cardType = (ImageView) itemView.findViewById(R.id.tvTypeOfCard);
            walletType = (TextView) itemView.findViewById(R.id.tvWalletType);
            accountName = (TextView) itemView.findViewById(R.id.tvAccountName);
            accountNo = (TextView) itemView.findViewById(R.id.tvMobileNumber);
            availBalance = (TextView) itemView.findViewById(R.id.tvCurrentBalance);
            tvOverlay = (TextView) itemView.findViewById(R.id.overlay);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onAccountOpen(getAdapterPosition());
                }
            });
        }


    }
}