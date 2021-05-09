package com.karayadar.app;

import android.content.ClipData;
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
import com.karayadar.app.models.Car;
import com.karayadar.app.models.HouseModel;
import com.karayadar.app.models.Items;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCarListAdapter extends RecyclerView.Adapter<MyCarListAdapter.UsersViewHolder> {

    private Context mCtx;
    private List<Items> userList;
    public static int currentPosition = 0;


    public MyCarListAdapter(Context mCtx, List<Items> userList) {
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
        Items itm = userList.get(position);

        holder.textViewName.setText(itm.getDescription());
        holder.textViewRent.setText(itm.getRent());
        holder.textViewType.setText(itm.getStatus());
        holder.textViewDuration.setText(itm.getColor());

        Picasso.with(mCtx).load(RetrofitClient.Image_url + itm.getImage()).into(holder.image);




        Glide.with(mCtx)
                .asBitmap()
                .load(userList.get(position))
        ;

        // holder.textViewName.set(userList.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Items itm = userList.get(position);
                // Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

                //Toast.makeText(mCtx,itm.getId(), Toast.LENGTH_SHORT).show();
                currentPosition=1;
                Intent intent = new Intent(mCtx, ItemDisplayActivity.class);

                intent.putExtra("Status",itm.getStatus());
                intent.putExtra("Rent",itm.getRent());
                intent.putExtra("Model",itm.getModel());
                intent.putExtra("Maker",itm.getMaker());
                intent.putExtra("Color",itm.getColor());
                intent.putExtra("userid",itm.getUserid());
                intent.putExtra("Desc",itm.getDescription());
                intent.putExtra("image",itm.getImage());
                intent.putExtra("id",itm.getId());
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

