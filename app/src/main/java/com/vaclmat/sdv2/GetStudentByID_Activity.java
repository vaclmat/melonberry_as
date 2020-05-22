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

public class GetStudentByID_Activity extends AppCompatActivity {

    private View myCoordinatorLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //layout and layout elements activation
        setContentView(R.layout.getstudentbyid);
        myCoordinatorLayout = findViewById(R.id.myCoordinatorLayout);
        Toolbar toolbar = findViewById(R.id.bid_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        //toolbar navigation to parent activity (main activity)
        toolbar.setNavigationOnClickListener(view -> finish());

        //Transfer parameter student_id from parent activity
        String student_id = getIntent().getStringExtra("student_id");
        assert student_id != null;

        //Setup http header parameters
        String client_id = MainActivity.client_id;
        String client_secret = MainActivity.client_secret;
        String aj = "application/json";

        //Call REST-API getStudentByID
        GetData service = IISDClient.getRetrofitInstance().create(GetData.class);
        Call<IIStudent> call = service.getStudentByID(client_id, client_secret,aj, student_id);
        //Execute the request asynchronously//
        call.enqueue(new Callback<IIStudent>() {
            @Override
            //Handle a successful response//
            public void onResponse(@NonNull Call<IIStudent> call, @NonNull Response<IIStudent> response) {
                if (String.valueOf(response.code()).equals("200") ) {
                    if (response.body() != null) {
                        loadDataList(response.body());
                    } else {
                        Snackbar.make(myCoordinatorLayout, "Record not found",
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                } else {
                   if (String.valueOf(response.code()).equals("400")) {
                       Snackbar.make(myCoordinatorLayout, "Bad request. Student ID should have 9 characters.",
                               Snackbar.LENGTH_SHORT)
                               .show();
                   } else {
                       Snackbar.make(myCoordinatorLayout, "Record not found.",
                               Snackbar.LENGTH_SHORT)
                               .show();
                   }
                }
            }

            @Override
            //Handle execution failures//
            public void onFailure(@NonNull Call<IIStudent> call, @NonNull Throwable throwable) {
                Snackbar.make(myCoordinatorLayout, "Unable to load students",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void loadDataList(IIStudent getStudentByID) {
        //Get a reference to the RecyclerView//
        RecyclerView byIDRecyclerView;
        byIDRecyclerView = findViewById(R.id.bidRecyclerView);
        com.vaclmat.sdv2.ByIDAdapter byIDAdapter = new ByIDAdapter(getStudentByID, myCoordinatorLayout);

        //Use a LinearLayoutManager with default vertical orientation//
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(GetStudentByID_Activity.this);
        byIDRecyclerView.setLayoutManager(layoutManager);

        //Set the Adapter to the RecyclerView//
        byIDRecyclerView.setAdapter(byIDAdapter);
    }
}