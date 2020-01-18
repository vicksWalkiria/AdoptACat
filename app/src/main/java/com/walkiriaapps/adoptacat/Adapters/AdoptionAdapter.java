package com.walkiriaapps.adoptacat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.walkiriaapps.adoptacat.Classes.AppData;
import com.walkiriaapps.adoptacat.Classes.Cat;
import com.walkiriaapps.adoptacat.R;

import java.util.List;

public class AdoptionAdapter  extends RecyclerView.Adapter<AdoptionAdapter.ViewHolder> {

    private final List<Cat> cats;
    private final Context ctx;
    private final RequestManager glide;
    private final int requestAdoption;



    public AdoptionAdapter(
            List<Cat> cats,
            RequestManager glide,
            Context ctx, int requestAdoption)
    {
        this.cats = cats;
        this.ctx = ctx;
        this.glide = glide;
        this.requestAdoption = requestAdoption;
    }
    @NonNull
    @Override
    public AdoptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v;

        if(requestAdoption == AppData.REQUEST_ADOPTION)
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_horizontal, parent, false);
        else
        {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_vertical, parent, false);
        }

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdoptionAdapter.ViewHolder holder, int position) {

        final Cat cat = cats.get(position);

        holder.catName.setText(cat.getName());
        glide
                .load(cat.getPictureUrl()).error(Glide.with(ctx).load(R.drawable.cat_placeholder))
                .into(holder.catPicture);

        if(requestAdoption == AppData.REQUEST_ADOPTION)
        {
            holder.adoptionState.setText(ctx.getString(R.string.permanent_adoption));
            holder.adoptionState.setTextColor(ctx.getResources().getColor(R.color.colorPrimary));

        }
        else
        {
            holder.adoptionState.setText(ctx.getString(R.string.temporary_adoption));
            holder.adoptionState.setTextColor(ctx.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        final ImageView catPicture;
        final TextView catName;
        final TextView adoptionState;

        ViewHolder (View v)
        {
            super (v);

            catPicture = v.findViewById(R.id.cat_picture);
            catName = v.findViewById(R.id.cat_name);
            adoptionState = v.findViewById(R.id.adoption_state);
        }
    }
}
