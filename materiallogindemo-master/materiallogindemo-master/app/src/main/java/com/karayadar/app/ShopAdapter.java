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
import com.karayadar.app.models.Items;
import com.karayadar.app.models.Shops;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.UsersViewHolder> {

    private Context mCtx;
    private List<Shops> userList;
    private static int currentPosition = 0;


    public ShopAdapter(Context mCtx, List<Shops> userList) {
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
        Shops itm = userList.get(position);

        holder.textViewName.setText(itm.getDescription());
        holder.textViewRent.setText(itm.getaddress());
        holder.textViewType.setText(itm.getsize());
        holder.textViewDuration.setText(itm.getrent());


        Picasso.with(mCtx).load(RetrofitClient.Image_url + itm.getshopimage()).into(holder.image);

        //Toast.makeText(mCtx, ""+RetrofitClient.Image_url, Toast.LENGTH_SHORT).show();




        Glide.with(mCtx)
                .asBitmap()
                .load(userList.get(position))
                ;

       // holder.textViewName.set(userList.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shops itm = userList.get(position);
               // Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

                //Toast.makeText(mCtx,itm.getId(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mCtx, ShopItemActivity.class);

                intent.putExtra("ShopStatus",itm.getshopstatus());
                intent.putExtra("Id",itm.getId());
                intent.putExtra("Size",itm.getsize());
                intent.putExtra("Floors",itm.getfloors());
                intent.putExtra("Address",itm.getaddress());
                intent.putExtra("Rent",itm.getrent());
                intent.putExtra("Descrr",itm.getDescription());
                intent.putExtra("userid",itm.getus_id());
                intent.putExtra("image",itm.getshopimage());
                intent.putExtra("image2",itm.getimage2());
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
