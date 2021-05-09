package com.karayadar.app;

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

import com.karayadar.app.activities.Apartment;
import com.karayadar.app.models.Apartments;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyApartmentListAdapter extends RecyclerView.Adapter<MyApartmentListAdapter.UsersViewHolder> {

    private Context mCtx;
    private List<Apartments> userList;
    public static int currentPosition = 0;


    public MyApartmentListAdapter(Context mCtx, List<Apartments> userList) {
        this.mCtx = mCtx;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_items, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder holder, final int position) {
        Apartments itm = userList.get(position);

        holder.textViewName.setText(itm.getDescription());
        holder.textViewRent.setText(itm.getrent());
        holder.textViewType.setText(itm.getStatus());
        holder.textViewDuration.setText(itm.getfloors());

        Picasso.with(mCtx).load(RetrofitClient.Image_url + itm.getImage()).into(holder.image);




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

                //Toast.makeText(mCtx,itm.getAddress(), Toast.LENGTH_SHORT).show();
                currentPosition=1;
                Intent intent = new Intent(mCtx, ApartmentItemActivity.class);
                intent.putExtra("Id",itm.getId());
                intent.putExtra("Status",itm.getStatus());
                intent.putExtra("Bedrooms",itm.getbedrooms());
                intent.putExtra("Floors",itm.getfloors());
                intent.putExtra("Elevator",itm.getelevator());
                intent.putExtra("Rent",itm.getrent());
                intent.putExtra("Address",itm.getAddress());
                intent.putExtra("Descrr",itm.getDescription());
                intent.putExtra("image",itm.getImage());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

