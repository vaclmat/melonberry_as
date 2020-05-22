package com.vaclmat.sdv2;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllStudents_Activity extends AppCompatActivity {

    private View alsCoordinationLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Layout and layout elements activations
        setContentView(R.layout.getallstudents);
        alsCoordinationLayout = findViewById(R.id.alsCoordinatorLayout);
        Toolbar toolbar = findViewById(R.id.alls_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        //Toolbar navigation to parent activity
        toolbar.setNavigationOnClickListener(view -> finish());

        //Setup http header parameters
        String chinned = MainActivity.client_id;
        String client_secret = MainActivity.client_secret;
        String aj = "application/json";

        //Call REST-API getAllStudents
        GetData service = IISDClient.getRetrofitInstance().create(GetData.class);
        Call<IIStudents> call = service.getAllStudents(chinned, client_secret,aj);

        //Execute the request asynchronously//
        call.enqueue(new Callback<IIStudents>() {
            @Override
            //Handle a successful response//
            public void onResponse(@NonNull Call<IIStudents> call, @NonNull Response<IIStudents> response) {
                loadDataList(response.body());
            }

            @Override
            //Handle execution failures//
            public void onFailure(@NonNull Call<IIStudents> call, @NonNull Throwable throwable) {
                //If the request fails, then display the following Snackbar//
                Snackbar.make(alsCoordinationLayout, "Unable to load students",
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void loadDataList(IIStudents getAllStudents) {
        //Get a reference to the RecyclerView//
        RecyclerView myRecyclerView = findViewById(R.id.myRecyclerView);
        MyAdapter myAdapter = new MyAdapter(getAllStudents);
        //Use a LinearLayoutManager with default vertical orientation//
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GetAllStudents_Activity.this);
            myRecyclerView.setLayoutManager(layoutManager);
            //Set the Adapter to the RecyclerView//
            myRecyclerView.setAdapter(myAdapter);
    }
}
