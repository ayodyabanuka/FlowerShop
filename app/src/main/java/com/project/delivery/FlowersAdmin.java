package com.project.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.delivery.Adapters.FlowerAdminAdapter;
import com.project.delivery.Model.flower;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FlowersAdmin extends AppCompatActivity {

    RecyclerView flowerrecyclerView;
    DatabaseReference flowerdatabase;
    FlowerAdminAdapter floweradapter;
    ArrayList<flower> flowerList;
    FloatingActionButton addflowerbutton;
    EditText searchET;
    String str="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowers_admin);

        flowerrecyclerView = findViewById(R.id.flowerlistrecycler);
        flowerdatabase = FirebaseDatabase.getInstance().getReference("Flowers");
        flowerrecyclerView.setHasFixedSize(true);
        flowerrecyclerView.setLayoutManager(new LinearLayoutManager(FlowersAdmin.this));

        flowerList = new ArrayList<>();
        floweradapter = new FlowerAdminAdapter(FlowersAdmin.this,flowerList);
        flowerrecyclerView.setAdapter(floweradapter);

        searchET = findViewById(R.id.search_flower_text);
        addflowerbutton = findViewById(R.id.addflower);



        addflowerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addflowerIntent = new Intent(FlowersAdmin.this,CreateFlower.class);
                startActivity(addflowerIntent);
            }
        });

        flowerrecyclerView = findViewById(R.id.flowerlistrecycler);
        flowerdatabase = FirebaseDatabase.getInstance().getReference("Flowers");

        flowerrecyclerView.setHasFixedSize(true);
        flowerrecyclerView.setLayoutManager(new LinearLayoutManager(FlowersAdmin.this));

        flowerList = new ArrayList<>();
        floweradapter = new FlowerAdminAdapter(FlowersAdmin.this,flowerList);
        flowerrecyclerView.setAdapter(floweradapter);

        flowerdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    flower flowers = datasnapshot.getValue(flower.class);
                    flowerList.add(flowers);
                }
                floweradapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(searchET.getText().toString().equals("")){
                    flowerrecyclerView = findViewById(R.id.flowerlistrecycler);
                    flowerdatabase = FirebaseDatabase.getInstance().getReference("Flowers");

                    flowerrecyclerView.setHasFixedSize(true);
                    flowerrecyclerView.setLayoutManager(new LinearLayoutManager(FlowersAdmin.this));

                    flowerList = new ArrayList<>();
                    floweradapter = new FlowerAdminAdapter(FlowersAdmin.this,flowerList);
                    flowerrecyclerView.setAdapter(floweradapter);

                    flowerdatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot datasnapshot : snapshot.getChildren()){

                                flower flowers = datasnapshot.getValue(flower.class);
                                flowerList.add(flowers);
                            }
                            floweradapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else {
                    str = s.toString();
                    onStart();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<flower> options = null;
        if (str.equals("")){
            options = new FirebaseRecyclerOptions.Builder<flower>().setQuery(flowerdatabase,flower.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<flower>().setQuery(flowerdatabase.orderByChild("name").startAt(str).endAt(str + "\uf8ff"),flower.class).build();
        }
        FirebaseRecyclerAdapter<flower,FlowerAdminViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<flower, FlowerAdminViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull FlowerAdminViewHolder flowerAdminViewHolder, int i, @NonNull flower flowers) {
                flowerAdminViewHolder.name.setText(flowers.getName());
                flowerAdminViewHolder.cost.setText(flowers.getCost());
                flowerAdminViewHolder.price.setText(flowers.getPrice());

                Picasso.get().load(flowers.getImageurl()).placeholder(R.mipmap.ic_launcher).into(flowerAdminViewHolder.flowerImage);

                flowerAdminViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FlowersAdmin.this, FlowerDetails.class);
                        intent.putExtra("flowername",flowers.getName());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public FlowerAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flower_card_admin, parent, false);
                FlowerAdminViewHolder viewHolder = new FlowerAdminViewHolder(view);
                return viewHolder;
            }
        };
        flowerrecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class FlowerAdminViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView name,price,cost;
        ImageView flowerImage;


        public FlowerAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.adminflower_name);
            price = itemView.findViewById(R.id.adminflower_price);
            cost = itemView.findViewById(R.id.adminflower_cost);
            flowerImage = itemView.findViewById(R.id.adminflowerimage);
            cardView = itemView.findViewById(R.id.floweradmincard);
        }
    }



}