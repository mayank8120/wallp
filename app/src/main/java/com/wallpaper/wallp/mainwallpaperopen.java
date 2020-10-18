package com.wallpaper.wallp;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class mainwallpaperopen extends AppCompatActivity {

    public static final int PERMISSION_WRITE = 0;
    String url;
    ProgressDialog progressDialog;
    Button hidebutt;
    View myView;
    RelativeLayout relativeLayout;
    ImageView wallimage;
    ImageButton setwallpaper, download, like, share;
    boolean isUp;
    private Context mCtx;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainwallpaperopen);


        Intent intent = getIntent();
        url = "" + intent.getStringExtra("wallpaperid");


        myView = findViewById(R.id.my_view);

        hidebutt = findViewById(R.id.show);
        relativeLayout = findViewById(R.id.mainrelativelayout);

        download = findViewById(R.id.downloadbutton);


        progressDialog = new ProgressDialog(this);

        checkPermission();
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    new Downloading().execute(url);
                }
            }
        });


        // initialize as invisible (could also do in xml)
        myView.setVisibility(View.VISIBLE);
        hidebutt.setVisibility(View.VISIBLE);


        wallimage = findViewById(R.id.wallimagelink);


        Picasso.get()
                .load(url)
                .fit().centerCrop()
                .into(wallimage);


        isUp = false;

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isUp == false) {
                    slideDown(myView);
                    slideDownBUTT(hidebutt);

                } else {
                    slideUp(myView);
                    slideUpBUTT(hidebutt);

                }
                isUp = !isUp;
                return false;
            }
        });

        /*set as wallpaper*/
        setwallpaper = findViewById(R.id.setwallpaperbutton);
        setwallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(mainwallpaperopen.this)
                        .setMessage("Do you want to set this as wallpaper ? ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Bitmap bitmapImg = ((BitmapDrawable) wallimage.getDrawable()).getBitmap();

                                WallpaperManager myWallpaperManager = WallpaperManager
                                        .getInstance(getApplicationContext());
                                try {
                                    myWallpaperManager
                                            .setBitmap(bitmapImg);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });

        share = findViewById(R.id.sharebutton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.get().load(url).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("image/*");
                        i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                        startActivity(Intent.createChooser(i, "Share Image"));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

            }
        });


        /*relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUp) {
                    slideDown(myView);
                    slideDownBUTT(hidebutt);
                    //myButton.setText("Slide up");
                } else {
                    slideUp(myView);
                    slideUpBUTT(hidebutt);
                    //myButton.setText("Slide down");
                }
                isUp = !isUp;
            }
        });*/

    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    private void setWallpaper(String url) throws IOException {

        URL url1 = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
        try {
            manager.setBitmap(bitmap);
            Toast.makeText(this, "Wallpaper set!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }

        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do somethings
        }
    }

    // slide the view from below itself to the current position
    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(600);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.bringToFront();
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        view.setVisibility(View.INVISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(600);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.bringToFront();
    }

    public void slideUpBUTT(View view) {
        view.setVisibility(View.VISIBLE);
        view.bringToFront();

    }

    // slide the view from its current position to below itself
    public void slideDownBUTT(View view) {
        view.setVisibility(View.INVISIBLE);
        view.bringToFront();

    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            slideDown(myView);
            slideDownBUTT(hidebutt);

        } else {
            slideUp(myView);
            slideUpBUTT(hidebutt);

        }
        isUp = !isUp;
    }

    public class Downloading extends AsyncTask<String, Integer, String> {

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Download starting...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... url) {
            File mydir = new File(Environment.getExternalStorageDirectory() + "/11zon");
            if (!mydir.exists()) {
                mydir.mkdirs();
            }

            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(url[0]);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

            /*SimpleDateFormat dateFormat = new SimpleDateFormat("mmddyyyyhhmmss");
            String date = dateFormat.format(new Date());*/

            String named = url.toString().substring(20);

            /*File file = new File(Environment
                    .getExternalStorageDirectory().getPath() + named);
            if (file.exists())
                file.delete();*/

            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle("Downloading")
                    .setDestinationInExternalPublicDir("/11zon", named + ".jpg");

            manager.enqueue(request);
            return mydir.getAbsolutePath() + File.separator + named + ".jpg";
        }

        @Override
        public void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Image Saved", Toast.LENGTH_SHORT).show();
        }
    }


}