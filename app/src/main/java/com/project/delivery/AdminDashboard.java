package com.project.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminDashboard extends AppCompatActivity {

    Button flower,cake,logout,profit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        flower = findViewById(R.id.floweradmindashboard);
        cake = findViewById(R.id.cakeadmindashboard);
        logout = findViewById(R.id.adminlogout);
        profit = findViewById(R.id.profitadmindashboard);

        flower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent floweradmindashboardIntent = new Intent(AdminDashboard.this,FlowersAdmin.class);
                startActivity(floweradmindashboardIntent);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutadmindashboardIntent = new Intent(AdminDashboard.this,Login.class);
                startActivity(logoutadmindashboardIntent);
            }
        });


        profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profitadmindashboardIntent = new Intent(AdminDashboard.this,ProfitCalculator.class);
                startActivity(profitadmindashboardIntent);
            }
        });
    }
}