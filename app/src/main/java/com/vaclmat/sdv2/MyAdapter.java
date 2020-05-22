package com.vaclmat.sdv2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomViewHolder>  {

    private IIStudents dataList;

    MyAdapter(IIStudents dataList){
        this.dataList = dataList;
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        //Get a reference to the Views in our layout//
        final View myView;
        TextView textlastName;
        TextView textFirstName;
        TextView textGender;

        CustomViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
            textlastName = myView.findViewById(R.id.lastName);
            textFirstName = myView.findViewById(R.id.firstName);
            textGender = myView.findViewById(R.id.Gender);
        }
    }

    @Override
    //Construct a RecyclerView.ViewHolder//
    public @NonNull CustomViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    //Set the data//
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.textlastName.setText(dataList.getStudentsGETALLR().get(position).getLastName());
        holder.textFirstName.setText(dataList.getStudentsGETALLR().get(position).getFirstName());
        holder.textGender.setText(dataList.getStudentsGETALLR().get(position).getGenderType());
        holder.myView.setOnClickListener(view -> {
            Intent intent=new Intent(view.getContext(), StudentDetailsActivity.class);
            intent.putExtra("last_name",dataList.getStudentsGETALLR().get(position).getLastName());
            intent.putExtra("first_name",dataList.getStudentsGETALLR().get(position).getFirstName());
            intent.putExtra("gender",dataList.getStudentsGETALLR().get(position).getGenderType());
            intent.putExtra("studentID",dataList.getStudentsGETALLR().get(position).getStudentID());
            view.getContext().startActivity(intent);
        });
    }

    //Calculate the item count for the RecylerView//
    @Override
    public int getItemCount() {
        return dataList.getStudentsGETALLR().size();
    }
}