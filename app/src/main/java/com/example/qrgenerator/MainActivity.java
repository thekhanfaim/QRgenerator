package com.example.qrgenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import static com.google.zxing.BarcodeFormat.*;

public class MainActivity extends AppCompatActivity {

    EditText txt_Name;
    ImageView img_QR;
    Button btn_Generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_Name = findViewById(R.id.txtName);
        img_QR = findViewById(R.id.imgQR);
        btn_Generate = findViewById(R.id.btnGenerate);

        btn_Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String value = txt_Name.getText().toString();

                if(value.isEmpty()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("You have not Entered any text")
                            .setMessage("Please Enter the text and then Try Again!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                } else {
                    MultiFormatWriter writer = new MultiFormatWriter();

                    try {
                        BitMatrix matrix = writer.encode(value, QR_CODE,400,400);

                        BarcodeEncoder encoder = new BarcodeEncoder();

                        Bitmap bitmap = encoder.createBitmap(matrix);

                        img_QR.setImageBitmap(bitmap);

                        InputMethodManager manager = (InputMethodManager)getSystemService(
                                Context.INPUT_METHOD_SERVICE
                        );

                        manager.hideSoftInputFromWindow(txt_Name.getApplicationWindowToken()
                                ,0);

                    } catch (WriterException e) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("It Seems Some Error is encountered")
                                .setMessage("Please resolve the error and then Try Again!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();
                    }
                }
            }
        });
    }
}