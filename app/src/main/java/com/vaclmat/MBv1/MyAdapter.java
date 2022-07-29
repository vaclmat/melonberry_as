package com.vaclmat.MBv1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomViewHolder>  {

    private List<VideoRec> dataList;

    MyAdapter(List<VideoRec> dataList){
        this.dataList = dataList;
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        //Get a reference to the Views in our layout//
        final View myView;
    //    TextView textId;
        TextView textVideoname;
    //    TextView textLinktv;

        CustomViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
        //    textId = myView.findViewById(R.id.lastName);
            textVideoname = myView.findViewById(R.id.firstName);
        //    textLinktv = myView.findViewById(R.id.Gender);
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
    //    holder.textId.setText(dataList.get(position).getId());
        holder.textVideoname.setText(dataList.get(position).getVideoname());
    //    holder.textLinktv.setText(dataList.get(position).getLinktv());
        holder.myView.setOnClickListener(view -> {
            Intent intent=new Intent(view.getContext(), Videa.class);
            intent.putExtra("linktv",dataList.get(position).getLinktv());
   //         intent.putExtra("first_name",dataList.getStudentsGETALLR().get(position).getFirstName());
   //         intent.putExtra("gender",dataList.getStudentsGETALLR().get(position).getGenderType());
   //         intent.putExtra("studentID",dataList.getStudentsGETALLR().get(position).getStudentID());
            view.getContext().startActivity(intent);
        });
    }

    //Calculate the item count for the RecylerView//
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}