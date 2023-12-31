package com.example.fastfood.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfood.Models.PlateModel;
import com.example.fastfood.R;

import java.util.List;

public class PlateAdapter extends RecyclerView.Adapter<PlateAdapter.PlaceViewHolder> {

    private List<PlateModel>plateModelList;
    private Context context;

    public PlateAdapter(List<PlateModel> plateModelList, Context context) {
        this.plateModelList = plateModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_plates,viewGroup,false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        PlateModel plateModel = plateModelList.get(position);
        Glide.with(context).load(plateModel.getPlate_img()).into(holder.plateImg);

    }

    @Override
    public int getItemCount() {
        return plateModelList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        private ImageView plateImg;
        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            plateImg = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }
}
