package com.example.ol.assignment2;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ol.assignment2.model.Book;
import com.example.ol.assignment2.model.Review;
import com.example.ol.assignment2.notifications.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    private TextView txtNames,txtCompanyName1,txtCompanyName2;
    private ImageView ivCloseDiscount;
    private TextView tvDiscount;
    private Button btnSubmitDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("newBook");
        FirebaseMessaging.getInstance().subscribeToTopic("discount");
        FirebaseMessaging.getInstance().subscribeToTopic("visit");
        AnalyticsManager.getInstance().init(getApplicationContext());

        txtNames = findViewById(R.id.txtNames);
        txtCompanyName1 = findViewById(R.id.txtCompanyName1);
        txtCompanyName2 = findViewById(R.id.txtCompanyName2);
        Typeface myFont = Typeface.createFromAsset(this.getAssets(), "fonts/Champagne & Limousines Bold.ttf");
        Typeface myFontLogo = Typeface.createFromAsset(this.getAssets(), "fonts/Prototype.ttf");
        txtNames.setTypeface(myFont);
        txtCompanyName1.setTypeface(myFontLogo);
        txtCompanyName2.setTypeface(myFontLogo);

        if(getIntent().getStringExtra("type") == null) {
          noNotifOrNoVisit();
        }
        else
        if(getIntent().getStringExtra("type").equals("discount"))
            { showDialogDiscount();
            }
            else
        if(getIntent().getStringExtra("type").equals("newBook"))
        {
           notifNewBook(getIntent().getStringExtra("book_id"));
        }
        else
        if(getIntent().getStringExtra("type").equals("visit"))
        {
            noNotifOrNoVisit();
        }
    }

    private void showDialogDiscount()
    {
        final Dialog dialog = new Dialog(SplashScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.all_books_discount);

        ivCloseDiscount = (ImageView) dialog.findViewById(R.id.ivCloseDiscount);
        tvDiscount= (TextView) dialog.findViewById(R.id.txtDiscount);
        btnSubmitDiscount = (Button) dialog.findViewById(R.id.btnSubmitDiscount);


        btnSubmitDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                nextIntent();
            }
        });
        ivCloseDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                nextIntent();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void nextIntent()
    {
        Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    private void notifNewBook(String bookId)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Books").child(bookId);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Book getBook = dataSnapshot.getValue(Book.class);
                Intent intent = new Intent(SplashScreenActivity.this, BookActivity.class);
                intent.putExtra("choseBook", getBook);
                intent.putExtra("activityFrom", "splash");
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void noNotifOrNoVisit()
    {
        Timer timer;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }


}
