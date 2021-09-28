package com.project.delivery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.delivery.Model.Order;
import com.project.delivery.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class OrderUserAdapter extends RecyclerView.Adapter<OrderUserAdapter.OrderViewHolder> {
    Context context;


    ArrayList<Order> orderList;

    public OrderUserAdapter(Context context, ArrayList<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderUserAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_card_user, parent,false);
        return new OrderUserAdapter.OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderUserAdapter.OrderViewHolder holder, int position) {

        Order order = orderList.get(position);


        holder.name.setText(order.getName());
        holder.price.setText(order.getPrice());
        holder.qty.setText(order.getQty());
        Picasso.get().load(order.getImageurl()).into(holder.Image);


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    public static class OrderViewHolder extends RecyclerView.ViewHolder{


        TextView name,price,qty;
        ImageView Image;
        CardView cardView;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.userOrdername);
            price = itemView.findViewById(R.id.userOrderprice);
            Image = itemView.findViewById(R.id.userOrderimage);
            qty = itemView.findViewById(R.id.userOrderqty);
            cardView = itemView.findViewById(R.id.orderusercard);
        }
    }

}
