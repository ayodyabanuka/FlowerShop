package com.project.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.delivery.Model.flower;

public class flowerDetailsEdit extends AppCompatActivity {

    TextView name;
    EditText price,cost;
    Button update;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference flowerref = database.getReference("Flowers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_details_edit);

        String detailsflowerName = getIntent().getExtras().get("flowername").toString();
        String detailsflowerprice = getIntent().getExtras().get("flowerprice").toString();
        String detailsflowercost = getIntent().getExtras().get("flowercost").toString();
        String detailsflowerimage = getIntent().getExtras().get("flowerimageurl").toString();

        name = findViewById(R.id.updateflowerName);
        price = findViewById(R.id.updateflowerPrice);
        cost = findViewById(R.id.updateflowerCost);
        update = findViewById(R.id.updateflowerdata);

        name.setText(detailsflowerName);
        price.setText(detailsflowerprice);
        cost.setText(detailsflowercost);




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameupdateedittext = detailsflowerName;
                String imageupdateedittext = detailsflowerimage;
                String priceupdatededittext = price.getText().toString().trim();
                String costupdatededittext = cost.getText().toString().trim();
                flower flowers = new flower(nameupdateedittext,priceupdatededittext,costupdatededittext,imageupdateedittext);
                flowerref.child(detailsflowerName).setValue(flowers);
                Toast.makeText(flowerDetailsEdit.this,"updated!",Toast.LENGTH_SHORT).show();

            }
        });




    }
}