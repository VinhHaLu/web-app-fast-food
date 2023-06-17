package com.example.fastfood.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfood.Models.CategoryModel;
import com.example.fastfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.PlaceViewHolder> {

    private List<CategoryModel> categoryModelList;
    private Context context;

    public CatAdapter(List<CategoryModel> categoryModelList, Context context) {
        this.categoryModelList = categoryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category,viewGroup,false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
//        CategoryModel categoryModel = categoryModelList.get(position);

        String name = categoryModelList.get(position).getCate_title();
        String time = categoryModelList.get(position).getCate_img();
             holder.cat_title.setText(name);
        try {
            Picasso.get().load(time).placeholder(R.drawable.ic_cart).into(holder.cat_img);
        }catch (Exception e){

        }
//        holder.cat_title.setText(categoryModel.getCat_title());
//        Glide.with(context).load(categoryModel.getCat_img()).placeholder(R.drawable.ic_loadfood).into(holder.cat_img);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        private ImageView cat_img;
        private TextView cat_title;
        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            cat_img =  itemView.findViewById(R.id.cat_img);
            cat_title = itemView.findViewById(R.id.textView2);

        }
    }
}

