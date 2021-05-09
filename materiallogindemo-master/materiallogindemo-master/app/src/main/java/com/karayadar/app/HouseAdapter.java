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
import com.karayadar.app.models.HouseModel;
import com.karayadar.app.models.Shops;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.UsersViewHolder> {

    private Context mCtx;
    private List<HouseModel> userList;
    private static int currentPosition = 0;


    public HouseAdapter(Context mCtx, List<HouseModel> userList) {
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
        HouseModel itm = userList.get(position);

        holder.textViewName.setText(itm.getDescription());
        holder.textViewRent.setText(itm.getaddress());
        holder.textViewType.setText(itm.getsize());
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
                HouseModel itm = userList.get(position);
               // Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

            //    Toast.makeText(mCtx,itm.getsimage(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mCtx, HouseItemDisplayActivity.class);

                intent.putExtra("ShopStatus",itm.gethousestatus());
                intent.putExtra("id",itm.getId());
                intent.putExtra("Size",itm.getsize());
                intent.putExtra("Floors",itm.getfloors());
                intent.putExtra("Address",itm.getaddress());
                intent.putExtra("Rent",itm.getrent());
                intent.putExtra("Descrr",itm.getDescription());
                intent.putExtra("userid",itm.getuserid());
                intent.putExtra("Furnished",itm.getfurnished());
                intent.putExtra("Bedroom",itm.getBedroom());
                intent.putExtra("Garage",itm.getgarage());
                intent.putExtra("image",itm.getImage());
                intent.putExtra("image2",itm.getsimage());
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
