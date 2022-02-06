package com.ufcquixada.meutroco.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.ufcquixada.meutroco.R;
import com.ufcquixada.meutroco.models.LoggedUser;

public class ReceiveQrCode extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private double valueQrCode;
    LoggedUser loggedUser;

    public ReceiveQrCode() {
        // Required empty public constructor
    }

    public static ReceiveQrCode newInstance(double valueQrCode) {
        ReceiveQrCode fragment = new ReceiveQrCode();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.valueOf(valueQrCode));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            valueQrCode = Double.valueOf(getArguments().getString(ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive_qr_code, container, false);

        String id = loggedUser.getInstance().getUser().get_id();

        String url =  "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl={%22id_recipient%22:%22"+id+"%22,%22value%22:"+valueQrCode+"}";
        Log.d("URL", url);

        WebView mCharView = (WebView) view.findViewById(R.id.char_view);
        mCharView.loadUrl(url);

        Toast.makeText(getActivity(), "Carregando... ", Toast.LENGTH_LONG).show();
        return view;
    }
}