package com.vaclmat.sdv2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;

public class ByIDAdapter extends RecyclerView.Adapter<ByIDAdapter.CustomViewHolder> {

    private IIStudent dataList;
    private View myCoordinatorLayout;

    ByIDAdapter(IIStudent dataList, View myCoordinatorLayout){
        this.dataList = dataList;
        this.myCoordinatorLayout = myCoordinatorLayout;
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
    public @NonNull CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    //Set the data//
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String erring;
        try {
            holder.textlastName.setText(dataList.getStudentsGETBYIDR().getLastName());
            holder.textFirstName.setText(dataList.getStudentsGETBYIDR().getFirstName());
            holder.textGender.setText(dataList.getStudentsGETBYIDR().getGender());
            erring = "Record founded";
            holder.myView.setOnClickListener(view -> {
                Intent intent=new Intent(view.getContext(), StudentDetailsActivity.class);
                intent.putExtra("last_name",dataList.getStudentsGETBYIDR().getLastName());
                intent.putExtra("first_name",dataList.getStudentsGETBYIDR().getFirstName());
                intent.putExtra("gender",dataList.getStudentsGETBYIDR().getGender());
                intent.putExtra("studentID",dataList.getStudentsGETBYIDR().getStudentID());
                view.getContext().startActivity(intent);
            });
        } catch(Exception e) {
            erring = "Record with such student ID was not found";
        }
        Snackbar.make(myCoordinatorLayout, erring, 3000).show();
    }

    //Calculate the item count for the RecylerView//
    @Override
    public int getItemCount() {
        //        return dataList.getStudentsGETBYIDR().size();
        return 1;
    }
}