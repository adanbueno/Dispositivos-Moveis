package com.ufcquixada.meutroco.component;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.models.Store;
import com.ufcquixada.meutroco.models.Transfer;

public class TransferAdpter extends ArrayAdapter<Transfer> {
    TextView textName;
    TextView textvalue;

    public TransferAdpter(Context context, Transfer[] transfer) {
        super(context, 0, transfer);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Transfer transfer = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_transfer, parent, false);
        }

        textName = (TextView) convertView.findViewById(R.id.textViewNameUserTransfer);
        textName.setText(transfer.getName());

        textvalue = (TextView) convertView.findViewById(R.id.textViewValueUserTransfer);
        textvalue.setText("R$ " + transfer.getValue());

        if(transfer.getValue() >= 0) {
            textvalue.setTextColor(Color.GREEN);
        } else {
            textvalue.setTextColor(Color.RED);
        }

        return convertView;
    }
}
