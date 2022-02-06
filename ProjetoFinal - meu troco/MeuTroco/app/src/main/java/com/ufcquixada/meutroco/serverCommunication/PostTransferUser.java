package com.ufcquixada.meutroco.serverCommunication;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ufcquixada.meutroco.Views.CameraTestActivity;
import com.ufcquixada.meutroco.Views.Payment;
import com.ufcquixada.meutroco.constantes.MEUTROCO;
import com.ufcquixada.meutroco.models.LoggedUser;
import com.ufcquixada.meutroco.models.Transfer;
import com.ufcquixada.meutroco.models.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class TranformQrCode {
    String id_recipient;
    double value;
}


public class PostTransferUser extends Thread {
    Gson gson;
    LoggedUser loggedUser;
    Payment activity;
    String id_sender;
    String id_recipient;
    double value;

    public PostTransferUser(Payment activity, String qrCode) {
        gson = new Gson();
        TranformQrCode tranformQrCode = gson.fromJson( qrCode, TranformQrCode.class );

        this.activity = activity;
        this.id_sender = loggedUser.getInstance().getUser().get_id();
        this.id_recipient = tranformQrCode.id_recipient;
        this.value = tranformQrCode.value;
    }

    public void run() {
        String router = "/transfers";
        HttpURLConnection urlConnection;
        String result = null;


        try {
            URL url = new URL(MEUTROCO.URLSTRING + router);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("authorization", "Bearer " + loggedUser.getInstance().getToken());
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_sender", this.id_sender);
            jsonParam.put("id_recipient", this.id_recipient);
            jsonParam.put("value", this.value);

            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.close();
            outputStream.close();

            final int responseCode = urlConnection.getResponseCode();

            if(responseCode >= 400) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Error Transfer",  Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

            final Transfer transfer = gson.fromJson( result, Transfer.class );

            Log.d("Result", result);
            Log.d("User id sender", transfer.get_id());

            if(transfer.get_id() == null || transfer.get_id().trim().isEmpty()) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Not transfer", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Transferência realizada com sucesso", Toast.LENGTH_LONG).show();
                    activity.finishTransfer(transfer);
                }
            });


        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

}
