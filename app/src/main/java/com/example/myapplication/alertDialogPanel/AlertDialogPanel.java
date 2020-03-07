package com.example.myapplication.alertDialogPanel;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.R;
import com.example.myapplication.Test.JsonImageConvert;
import com.example.myapplication.showUserProblemstoServiceProviders.ShareProblemToServiceProviders;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlertDialogPanel {

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
