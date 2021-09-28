package com.project.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FlowerDetails extends AppCompatActivity {

    String cardflowerName = "";
    ImageView flowerimage;
    TextView name,price,cost;
    Button editbtn,deletebtn;

    DatabaseReference deleteflowerref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_details);

        editbtn = findViewById(R.id.flowerdetailsedit);
        deletebtn = findViewById(R.id.flowerdetailsdelete);

        name = findViewById(R.id.flowerdetailsName);
        price = findViewById(R.id.flowerdetailsprice);
        cost = findViewById(R.id.flowerdetailscost);
        flowerimage = findViewById(R.id.flowerdetailsimage);



        cardflowerName = getIntent().getExtras().get("flowername").toString();
        Query flowerref = FirebaseDatabase.getInstance().getReference().child("Flowers").orderByChild("name").equalTo(cardflowerName);


        flowerref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namedb = snapshot.child(cardflowerName).child("name").getValue(String.class);
                String pricedb = snapshot.child(cardflowerName).child("price").getValue(String.class);
                String costdb = snapshot.child(cardflowerName).child("cost").getValue(String.class);
                String imagedb = snapshot.child(cardflowerName).child("imageurl").getValue(String.class);
                name.setText(namedb);
                price.setText(pricedb);
                cost.setText(costdb);
                Picasso.get().load(imagedb).into(flowerimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flowerref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String namedb = snapshot.child(cardflowerName).child("name").getValue(String.class);
                        String pricedb = snapshot.child(cardflowerName).child("price").getValue(String.class);
                        String costdb = snapshot.child(cardflowerName).child("cost").getValue(String.class);
                        String imagedb = snapshot.child(cardflowerName).child("imageurl").getValue(String.class);
                        name.setText(namedb);
                        price.setText(pricedb);
                        cost.setText(costdb);
                        Picasso.get().load(imagedb).into(flowerimage);

                        Intent editIntent = new Intent(FlowerDetails.this,flowerDetailsEdit.class);
                        editIntent.putExtra("flowername",namedb);
                        editIntent.putExtra("flowerprice",pricedb);
                        editIntent.putExtra("flowercost",costdb);
                        editIntent.putExtra("flowerimageurl",imagedb);
                        startActivity(editIntent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteflowerref = FirebaseDatabase.getInstance().getReference().child("Flowers").child(cardflowerName);
                deleteflowerref.removeValue();
                Toast.makeText(FlowerDetails.this,"Deleted!",Toast.LENGTH_SHORT).show();
                Intent deleteintent = new Intent(FlowerDetails.this,FlowersAdmin.class);
                startActivity(deleteintent);

            }
        });



    }
}