package com.poly.moneylover.ui.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.constants.Constants;
import com.poly.moneylover.models.Response.Export;
import com.poly.moneylover.network.BudgetApi;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Response;

public class ExportDataCsvActivity extends AppCompatActivity {

    private static final int PERMISSION_REQ_CODE = 100;
    private static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private ImageButton imbBack;
    private Button btnExport;
    public String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_data);
        initView();
        back();
        export();
        download();
    }

    private void download() {
        btnExport.setOnClickListener(v -> {
            requestRuntimePermission();
        });
    }

    private void back() {
        imbBack.setOnClickListener(v -> finish());
    }

    private void requestRuntimePermission() {
        if (ActivityCompat.checkSelfPermission(this, PERMISSION_WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            startDownload();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_WRITE_EXTERNAL_STORAGE)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This app requires WRITE_EXTERNAL_STORAGE permission ")
                    .setTitle("Permission Required")
                    .setCancelable(false)
                    .setPositiveButton("Ok", (dialog, which) -> {
                        ActivityCompat.requestPermissions(ExportDataCsvActivity.this, new String[]{PERMISSION_WRITE_EXTERNAL_STORAGE},
                                PERMISSION_REQ_CODE);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQ_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                startDownload();
            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Setting Please")
                        .setTitle("Permission Required")
                        .setCancelable(false)
                        .setNegativeButton("Cancel", ((dialog, which) -> dialog.dismiss()))
                        .setPositiveButton("Setting", ((dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);

                            dialog.dismiss();
                        }));
                builder.show();
            }
        }
    }

    private void startDownload() {
        String url = Constants.API_DOWNLOAD_URL + link;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(link);
        request.setDescription("Downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, link);
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    private void export() {

        Thread thread = new Thread(() -> {
            try {
                Response<Export> call = BudgetApi.api.getLinkCsv().execute();
                if (call.isSuccessful() && call.code() == 200) {
                    link = call.body().getLink();
                } else {
                    Toast.makeText(this, "Xuất dữ liệu xịt", Toast.LENGTH_SHORT).show();
                }
            } catch (HttpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void initView() {
        imbBack = findViewById(R.id.imb_back);
        btnExport = findViewById(R.id.btn_export);
    }
}