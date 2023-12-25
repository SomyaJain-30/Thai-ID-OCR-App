package com.example.thaiidocr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IDAdapter extends RecyclerView.Adapter<IDAdapter.ViewHolder> {
    Context context;
    List<IDModel> IDList;

    public IDAdapter(Context context, List<IDModel> IDList){
        this.context = context;
        this.IDList = IDList;
    }
    @NonNull
    @Override
    public IDAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull IDAdapter.ViewHolder holder, int position) {
        holder.firstName.setText(IDList.get(position).getFirstName().toString());
        holder.lastName.setText(IDList.get(position).getLastName().toString());
        holder.dob.setText(IDList.get(position).getDob().toString());
        holder.doi.setText(IDList.get(position).getDoi().toString());
        holder.doe.setText(IDList.get(position).getDoe().toString());
        holder.identification.setText(IDList.get(position).getIdentification());
    }

    @Override
    public int getItemCount() {
        return IDList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, lastName, dob, doi, doe, identification;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.name);
            lastName = itemView.findViewById(R.id.lastname);
            dob = itemView.findViewById(R.id.dob);
            doi = itemView.findViewById(R.id.doi);
            doe = itemView.findViewById(R.id.doe);
            identification = itemView.findViewById(R.id.identification);
        }
    }
}
