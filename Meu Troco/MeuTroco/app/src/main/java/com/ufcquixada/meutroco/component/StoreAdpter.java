package com.ufcquixada.meutroco.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.models.Store;

import java.util.ArrayList;

public class StoreAdpter extends ArrayAdapter<Store> {
    public StoreAdpter(Context context, Store[] stores) {
        super(context, 0, stores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Store store = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_store, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.textViewNameStore);

        tvName.setText(store.getName());

        return convertView;
    }
}
