package com.example.fastfood.Adapters;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.fastfood.Buy_Activity;
import com.example.fastfood.Models.GreatOffersModel;

import com.example.fastfood.Models.SimpleVerticalModel;
import com.example.fastfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GreatOffersAdapter extends RecyclerView.Adapter<GreatOffersAdapter.PlaceViewHolder> {

    private List<SimpleVerticalModel> simpleVerticalModels;
    private Context context;

    public GreatOffersAdapter(List<SimpleVerticalModel> simpleVerticalModels, Context context) {
        this.simpleVerticalModels = simpleVerticalModels;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_horizontal_great_offers,viewGroup,false);
        return new PlaceViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {


        String name = simpleVerticalModels.get(position).getName_product();
        String time = simpleVerticalModels.get(position).getPrice_product();
        String title = simpleVerticalModels.get(position).getImage_product();
        String cate = simpleVerticalModels.get(position).getCategory_product();
        String desc = simpleVerticalModels.get(position).getDesc_product();
        String id = simpleVerticalModels.get(position).getId_product();


        holder.text_title.setText(name);
        holder.text_description.setText(time);
        holder.text_saleoff.setText(cate);
        holder.text_ratting.setText(desc);
        try {
            Picasso.get().load(title).placeholder(R.drawable.ic_cart).into(holder.offer_img);
        }catch (Exception e){

        }
        holder.offer_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Buy_Activity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return simpleVerticalModels.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        private ImageView offer_img;
        private TextView text_title,text_description,text_saleoff,text_ratting;
        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            offer_img =  itemView.findViewById(R.id.offer_img);
            text_title = itemView.findViewById(R.id.text_title);
            text_description = itemView.findViewById(R.id.text_description);
            text_saleoff = itemView.findViewById(R.id.text_saleoff);
            text_ratting = itemView.findViewById(R.id.text_ratting);
        }
    }
}


