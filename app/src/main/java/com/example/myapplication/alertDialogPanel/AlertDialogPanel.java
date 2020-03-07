package com.example.myapplication.alertDialogPanel;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.R;
import com.example.myapplication.Retrofit.BaseUrl;
import com.example.myapplication.Test.JsonImageConvert;
import com.example.myapplication.admin.UserProbPojo.OurretrofitForUserPb;
import com.example.myapplication.admin.UserProbPojo.PbPojo;
import com.example.myapplication.showUserProblemstoServiceProviders.ShareProblemToServiceProviders;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlertDialogPanel {

    public void sampleAlertDialog(final Context context, final String userProblems, final Bitmap photo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are You Sure To Submit Your Problem ?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendProblems(context, userProblems, photo);//send data to service providers over after save into My Roadside Api.
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void sendProblems(final Context context, String userProblems, Bitmap photo) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
//                pbPojo.setProblemImage(byteArray);
//                pbPojo.setProblemDescription(userProblems);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        OurretrofitForUserPb ourretrofitForUserPb = retrofit.create(OurretrofitForUserPb.class);
        PbPojo pbPojo = new PbPojo(byteArray, userProblems);
        Call<PbPojo> modelCall = ourretrofitForUserPb.postData(pbPojo);
        modelCall.enqueue(new Callback<PbPojo>() {
            @Override
            public void onResponse(Call<PbPojo> call, Response<PbPojo> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "wow response", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "failed to send problems to your database", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PbPojo> call, Throwable t) {

            }
        });
    }


    public void showAlertDialog(int position, List<ShareProblemToServiceProviders> body, final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_samples, null);
        CircleImageView image = view1.findViewById(R.id.alertImageSample);
        JsonImageConvert jsonImageConvert = new JsonImageConvert();
        Bitmap bitmap = jsonImageConvert.jsonimageConvertTOBitmap(body.get(position).getProblemImage());
        image.setImageBitmap(bitmap);
        final Button send = view1.findViewById(R.id.ok);
        final Button cancel = view1.findViewById(R.id.cancel);

        builder.setView(view1);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              Intent intent = new Intent(getContext().this, Main2Activity.class);
////             startActivity(intent);
                Toast.makeText(context, "Send click", Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
