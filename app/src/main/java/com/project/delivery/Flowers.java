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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.delivery.Adapters.FlowerAdminAdapter;
import com.project.delivery.Adapters.FlowerUserAdapter;
import com.project.delivery.Model.flower;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Flowers extends AppCompatActivity {

    RecyclerView flowerrecyclerView;
    DatabaseReference flowerdatabaseuser;
    FlowerUserAdapter floweradapteruser;
    ArrayList<flower> flowerListuser;
    EditText searchET;
    String str="";

    String username = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowers);


        flowerrecyclerView = findViewById(R.id.flowerlistrecycleruser);
        flowerdatabaseuser = FirebaseDatabase.getInstance().getReference("Flowers");


        Intent flowerIntent = getIntent();
        String UserName1 = flowerIntent.getStringExtra("userName");


        username = UserName1;

        flowerrecyclerView.setHasFixedSize(true);
        flowerrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        flowerListuser = new ArrayList<>();
        floweradapteruser = new FlowerUserAdapter(this,flowerListuser);
        flowerrecyclerView.setAdapter(floweradapteruser);

        searchET = findViewById(R.id.search_flower_text_user);



        flowerdatabaseuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    flower flowers = datasnapshot.getValue(flower.class);
                    flowerListuser.add(flowers);
                }
                floweradapteruser.notifyDataSetChanged();

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
                    flowerrecyclerView = findViewById(R.id.flowerlistrecycleruser);
                    flowerdatabaseuser = FirebaseDatabase.getInstance().getReference("Flowers");

                    searchET = findViewById(R.id.search_flower_text_user);

                    flowerrecyclerView.setHasFixedSize(true);
                    flowerrecyclerView.setLayoutManager(new LinearLayoutManager(Flowers.this));

                    flowerListuser = new ArrayList<>();
                    floweradapteruser = new FlowerUserAdapter(Flowers.this,flowerListuser);
                    flowerrecyclerView.setAdapter(floweradapteruser);

                    flowerdatabaseuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot datasnapshot : snapshot.getChildren()){

                                flower flowers = datasnapshot.getValue(flower.class);
                                flowerListuser.add(flowers);
                            }
                            floweradapteruser.notifyDataSetChanged();

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
            options = new FirebaseRecyclerOptions.Builder<flower>().setQuery(flowerdatabaseuser,flower.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<flower>().setQuery(flowerdatabaseuser.orderByChild("name").startAt(str).endAt(str + "\uf8ff"),flower.class).build();
        }
        FirebaseRecyclerAdapter<flower, Flowers.FlowerUserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<flower, Flowers.FlowerUserViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull FlowerUserViewHolder flowerUserViewHolder, int i, @NonNull flower flowers) {
                flowerUserViewHolder.name.setText(flowers.getName());
                flowerUserViewHolder.price.setText(flowers.getPrice());

                Picasso.get().load(flowers.getImageurl()).placeholder(R.mipmap.ic_launcher).into(flowerUserViewHolder.flowerImage);

                flowerUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Flowers.this, FlowerDetailsUser.class);
                        intent.putExtra("flowername",flowers.getName());
                        intent.putExtra("username",username);

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public Flowers.FlowerUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flower_card_user, parent, false);
                Flowers.FlowerUserViewHolder viewHolder = new Flowers.FlowerUserViewHolder(view);
                return viewHolder;
            }
        };
        flowerrecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class FlowerUserViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView name,price;
        ImageView flowerImage;


        public FlowerUserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userflower_name);
            price = itemView.findViewById(R.id.userflower_price);
            flowerImage = itemView.findViewById(R.id.userflowerimage);
            cardView = itemView.findViewById(R.id.flowerusercard);
        }
    }
}