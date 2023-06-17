package com.example.fastfood.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfood.Buy_Activity;
import com.example.fastfood.Models.CartModel;
import com.example.fastfood.Models.SimpleVerticalModel;
import com.example.fastfood.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.PlaceViewHolder> {

    private List<CartModel> cartModelList;
    private Context context;
    String check;
    private DatabaseReference cart;
    boolean chekk =false;
    String myUid;

    public CartAdapter(List<CartModel> cartModelList, Context context) {
        this.cartModelList = cartModelList;
        this.context = context;
        cart = FirebaseDatabase.getInstance().getReference("Carts");
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_cart,viewGroup,false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        CartModel cartModel = cartModelList.get(position);
        String title = cartModelList.get(position).getTitle_cart();
        int price = cartModelList.get(position).getTv_price();
        String img_cart = cartModelList.get(position).getImg_cart();
        int number_cart = cartModelList.get(position).getNumber_cart();
        String id = cartModelList.get(position).getId_cart();


        holder.title_cart.setText(title);
        holder.tv_price.setText("$"+ price);
        holder.number_cart.setText("$"+ number_cart);
        setchek(holder, id);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        chekk =true;
//                        if (chekk){
                            if (holder.checkBox.isChecked()){
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("check", "true");
                                cart.child(myUid).child(id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "check success", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "check fail", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                holder.checkBox.setChecked(true);
//                                chekk=false;
                             }else {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("check", "false");
                                cart.child(myUid).child(id).updateChildren(hashMap);
                                holder.checkBox.setChecked(false);
//                                chekk=false;
//                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



        try {
            Picasso.get().load(img_cart).placeholder(R.drawable.ic_cart).into(holder.img_cart);
        }catch (Exception e){

        }
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Query fquery = FirebaseDatabase.getInstance().getReference("Carts").child(myUid).orderByChild("id_cart").equalTo(id);
                cart.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(myUid).hasChild(id)){
                            cart.child(myUid).child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void setchek(PlaceViewHolder holder, String id) {
        cart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(myUid).hasChild(id) && snapshot.child(myUid).child(id).child("check").getValue().equals("true")){
                    holder.checkBox.setChecked(true);
                }else if (snapshot.child(myUid).hasChild(id) && snapshot.child(myUid).child(id).child("check").getValue().equals("false")){
                    holder.checkBox.setChecked(false);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });;

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_cart, img_delete;
        private TextView number_cart,title_cart,tv_price;
        CheckBox checkBox;
        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            img_cart =  itemView.findViewById(R.id.img_cart);
            number_cart = itemView.findViewById(R.id.number_cart);
            title_cart = itemView.findViewById(R.id.title_cart);
            tv_price = itemView.findViewById(R.id.tv_price);
            img_delete = itemView.findViewById(R.id.img_delete);
            checkBox = itemView.findViewById(R.id.check);
        }
    }
}


