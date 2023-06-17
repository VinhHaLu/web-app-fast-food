package com.example.fastfood.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfood.Buy_Activity;
import com.example.fastfood.Models.PlateModel;
import com.example.fastfood.Models.SimpleVerticalModel;
import com.example.fastfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimpleVerticalAdapter extends RecyclerView.Adapter<SimpleVerticalAdapter.PlaceViewHolder> {

    private List<SimpleVerticalModel> simpleVerticalModelList;
    private Context context;

    public SimpleVerticalAdapter(List<SimpleVerticalModel> simpleVerticalModelList, Context context) {
        this.simpleVerticalModelList = simpleVerticalModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_vertical_slider,viewGroup,false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        SimpleVerticalModel simpleVerticalModel = simpleVerticalModelList.get(position);

        String name = simpleVerticalModelList.get(position).getName_product();
        String time = simpleVerticalModelList.get(position).getPrice_product();
        String title = simpleVerticalModelList.get(position).getImage_product();
        String cate = simpleVerticalModelList.get(position).getCategory_product();
        String desc = simpleVerticalModelList.get(position).getDesc_product();
        String id = simpleVerticalModelList.get(position).getId_product();


        holder.name_product.setText("Tên món :" + " "+ name);
        holder.price_product.setText("Giá: "+" "+ time +"VNĐ");
//        holder.category_product.setText(cate);
        holder.desc_product.setText("Mô tả :"+" "+ desc);
        try {
            Picasso.get().load(title).placeholder(R.drawable.ic_cart).into(holder.image_product);
        }catch (Exception e){

        }
        holder.buy_product.setOnClickListener(new View.OnClickListener() {
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
        return simpleVerticalModelList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        private ImageView image_product;
        private TextView name_product,desc_product,price_product,category_product  ;
        private Button buy_product;
        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            image_product = (ImageView) itemView.findViewById(R.id.pro_img);
            name_product = itemView.findViewById(R.id.simple_title);
            desc_product = itemView.findViewById(R.id.simple_description);
            price_product = itemView.findViewById(R.id.simple_saleoff);
            category_product = itemView.findViewById(R.id.tv_ratting);
            buy_product = itemView.findViewById(R.id.buy_product);

        }
    }
}

