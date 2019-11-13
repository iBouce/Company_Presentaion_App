package com.pinfo.ibouce.galuchoalgeriespa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private LinearLayout mLinear_Layout;
    private BannerSlider mBannerSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Display Admob Ad Banner*/
        MobileAds.initialize(this, "ca-app-pub-1008729181712276~3457873403");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*Display Interstitial Ad Banner*/
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1008729181712276/9020620452");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mLinear_Layout = findViewById(R.id.linear_layout);
        mLinear_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });

        /*HashMap of the information about company*/
        final HashMap<String, String> infoListe = new HashMap<>();
        infoListe.put("Company Name", "Galucho Algérie Spa");
        infoListe.put("Company Type", getString(R.string.wholesale));
        infoListe.put("Executive Head", "");
        infoListe.put("Phone number", "+213 48 76 42 31");
        infoListe.put("Email Address", "galucho-algerie@hotmail.com");
        infoListe.put("Web Address", "www.galucho-algerie.com");
        infoListe.put("Address first line", "BP 38 Boulevard MESSALI EL HADJ");
        infoListe.put("Address second line", "Sidi Bel Abbes, Algérie.");

        /*HashMap of the opening times of the office*/
        LinkedHashMap<String, String> openingHours = new LinkedHashMap<>();
        openingHours.put(getString(R.string.sunday), "08:00 - 16:00");
        openingHours.put(getString(R.string.monday), "08:00 - 16:00");
        openingHours.put(getString(R.string.tuesday), "08:00 - 16:00");
        openingHours.put(getString(R.string.wednesday), "08:00 - 16:00");
        openingHours.put(getString(R.string.thursday), "08:00 - 16:00");
        openingHours.put(getString(R.string.friday), "Week-end");
        openingHours.put(getString(R.string.saturday), "Week-end");

        /*Defines and sets the photo sliderat the top of the app*/
        mBannerSlider = findViewById(R.id.banner_slider);
        List<Banner> banners=new ArrayList<>();
        //add banner using resource drawable
        banners.add(new DrawableBanner(R.drawable.logo));
        banners.add(new DrawableBanner(R.drawable.img));
        banners.add(new DrawableBanner(R.drawable.imgggg));

        for (int i=0; i < banners.size(); i++) {
            banners.get(i).setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        mBannerSlider.setBanners(banners);

        /*Retrieves the information about the company from the HashMap information list
        and passes the values to the relevant TextViews*/

        TextView companyNameTextView = findViewById(R.id.company_name);
        companyNameTextView.setText(infoListe.get("Company Name"));

        TextView companyDescription = findViewById(R.id.company_description);
        companyDescription.setText(infoListe.get("Company Type"));

        TextView executiveHead = findViewById(R.id.executive_head_name);
        executiveHead.setText(infoListe.get("Executive Head"));

        TextView phoneNumberTextView = findViewById(R.id.phone_number);
        phoneNumberTextView.setText(infoListe.get("Phone number"));

        TextView emailAddressTextView = findViewById(R.id.email_address);
        emailAddressTextView.setText(infoListe.get("Email Address"));

        TextView webAddressTextView = findViewById(R.id.web_address);
        webAddressTextView.setText(infoListe.get("Web Address"));

        TextView address = findViewById(R.id.address);
        address.setText(infoListe.get("Address first line") +
                "\n" + infoListe.get("Address second line"));

        /*Loops through openingHours HashMap and passes the days and opening times to the TextView*/
        TextView openingDaysTextView = findViewById(R.id.opening_days);
        TextView openingHoursTextView = findViewById(R.id.opening_hours);
        for (String day : openingHours.keySet()) {
            openingDaysTextView.append(day + ":\n");
            openingHoursTextView.append(openingHours.get(day) + "\n");
        }

        /*Sets what should happen after clicking on the particular layouts*/
        LinearLayout phoneNumberLayout = findViewById(R.id.phone_number_layout);
        phoneNumberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhoneNumber(infoListe.get("Phone number"));
            }
        });

        LinearLayout emailAddressLayout = findViewById(R.id.email_address_layout);
        emailAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(infoListe.get("Email Address"));
            }
        });

        LinearLayout webAddressLayout = findViewById(R.id.web_address_layout);
        webAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(infoListe.get("Web Address"));
            }
        });

        LinearLayout addressLinearLayout = findViewById(R.id.address_layout);
        addressLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMap((infoListe.get("Address first line")
                        + " " + infoListe.get("Address second line")));
            }
        });

    }

    /*Diplaying the Admob Interstitial ad unit after onCreate method*/
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_LONG).show();
    }

    /*After calling this function it takes the phone number of the company HashMap
    and passes it to the phone dialer*/

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /*Opens e-mail app. Passes the email address from company HashMap & subject from strings.xml*/
    public void sendEmail(String emailAddress) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress +
                "?subject=" + getString(R.string.email_subject)));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /*Opens the web browser and passes the web address from company HashMap*/
    public void openWebPage(String url) {
        Uri webpage = Uri.parse("http://" + url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /*Opens the navigation app and passes the address from company HashMap*/
    public void showMap(String address) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:0,0?q=" + address));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
