package com.karayadar.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.karayadar.app.models.Apartments;
import com.karayadar.app.models.Items;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ApartmentAdapter extends RecyclerView.Adapter<ApartmentAdapter.UsersViewHolder> {

    private Context mCtx;
    private List<Apartments> userList;
    public static int currentPosition = 0;


    public ApartmentAdapter(Context mCtx, List<Apartments> userList) {
        this.mCtx = mCtx;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_items, parent, false);
        return new UsersViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder holder, final int position) {
        Apartments itm = userList.get(position);

        holder.textViewName.setText(itm.getDescription());
        holder.textViewRent.setText(itm.getelevator());
        holder.textViewType.setText(itm.getfloors());
        holder.textViewDuration.setText(itm.getrent());


        Picasso.with(mCtx).load(RetrofitClient.Image_url + itm.getImage()).into(holder.image);

        //Toast.makeText(mCtx, ""+RetrofitClient.Image_url, Toast.LENGTH_SHORT).show();




        Glide.with(mCtx)
                .asBitmap()
                .load(userList.get(position))
                ;

       // holder.textViewName.set(userList.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Apartments itm = userList.get(position);
               // Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

                //Toast.makeText(mCtx,itm.getItemName(), Toast.LENGTH_SHORT).show();
                currentPosition =0;
                Intent intent = new Intent(mCtx, ApartmentItemActivity.class);

                intent.putExtra("Status",itm.getStatus());
                intent.putExtra("Id",itm.getId());
                intent.putExtra("Bedrooms",itm.getbedrooms());
                intent.putExtra("Floors",itm.getfloors());
                intent.putExtra("Elevator",itm.getelevator());
                intent.putExtra("Rent",itm.getrent());
                intent.putExtra("Address",itm.getAddress());
                intent.putExtra("Descrr",itm.getDescription());
                intent.putExtra("userid",itm.getUserid());
                intent.putExtra("image",itm.getImage());
                intent.putExtra("image2",itm.getImage2());
                mCtx.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewRent, textViewType
                ,textViewDuration;
        ImageView image;
        LinearLayout parentLayout;


        public UsersViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewRent = itemView.findViewById(R.id.textViewRent);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewDuration = itemView.findViewById(R.id.textViewduration);
            image = itemView.findViewById(R.id.imageViewPic);

            parentLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
