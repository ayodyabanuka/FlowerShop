package com.project.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.delivery.Model.Order;
import com.squareup.picasso.Picasso;

import java.lang.ref.Reference;

public class FlowerDetailsUser extends AppCompatActivity {
    String cardflowerNameuser = "",user="";
    ImageView flowerimageuser;
    TextView nameuser,priceuser;
    Button buybutton;
    DatabaseReference orderref;
    EditText qtyflower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_details_user);



        nameuser = findViewById(R.id.flowerdetailsNameuser);
        priceuser = findViewById(R.id.flowerdetailspriceuser);
        flowerimageuser = findViewById(R.id.flowerdetailsimageuser);
        buybutton = findViewById(R.id.flowerbuy);
        qtyflower = findViewById(R.id.qty);

        cardflowerNameuser = getIntent().getExtras().get("flowername").toString();
        user = getIntent().getExtras().get("username").toString();
        Query flowerref = FirebaseDatabase.getInstance().getReference().child("Flowers").orderByChild("name").equalTo(cardflowerNameuser);

        flowerref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namedb = snapshot.child(cardflowerNameuser).child("name").getValue(String.class);
                String pricedb = snapshot.child(cardflowerNameuser).child("price").getValue(String.class);
                String costdb = snapshot.child(cardflowerNameuser).child("cost").getValue(String.class);
                String imagedb = snapshot.child(cardflowerNameuser).child("imageurl").getValue(String.class);
                nameuser.setText(namedb);
                priceuser.setText(pricedb);
                Picasso.get().load(imagedb).into(flowerimageuser);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flowerref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String namedb = snapshot.child(cardflowerNameuser).child("name").getValue(String.class);
                        String pricedb = snapshot.child(cardflowerNameuser).child("price").getValue(String.class);
                        String imagedb = snapshot.child(cardflowerNameuser).child("imageurl").getValue(String.class);

                        String quantity = qtyflower.getText().toString().trim();

                        int priceforone = Integer.parseInt(pricedb);
                        int qty = Integer.parseInt(quantity);

                        int total = priceforone * qty;

                        String Total = String.valueOf(total);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("Order");

                        Order order = new Order(namedb,Total,imagedb,quantity);
                        ref.child(user).child(namedb).setValue(order);


                        Toast.makeText(FlowerDetailsUser.this,"Added to Orders",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}