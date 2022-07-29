package com.vaclmat.MBv1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Navody extends AppCompatActivity {

    private View alsCoordinationLayout;
    List<VideoRec> vndata;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Layout and layout elements activations
        setContentView(R.layout.navody);
        alsCoordinationLayout = findViewById(R.id.alsCoordinatorLayout);
        Toolbar toolbar = findViewById(R.id.alls_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        //Toolbar navigation to parent activity
        toolbar.setNavigationOnClickListener(view -> finish());

        //Setup http header parameters
        String client_token = MainActivity.client_token;
        String un = getIntent().getStringExtra("username");
        assert  un != null;

        String aj = "application/json";
        String aju = "application/json; charset=utf-8";
        String auth = "Bearer " + client_token;
        Log.i("Authorization: ", auth);

        List<VideoRec> arlene = new ArrayList<>();

        //Call REST-API getAllStudents
        GetData vidservice = IISDClient.getRetrofitInstance().create(GetData.class);
        Call<List<VidRec>> callvid = vidservice.getVid(auth, aj, aju, un);

        //Execute the request asynchronously//
        callvid.enqueue(new Callback<List<VidRec>>() {
            @Override
            //Handle a successful response//
            public void onResponse(@NonNull Call<List<VidRec>> callvid, @NonNull Response<List<VidRec>> vidresponse) {
                Log.i("getVid", String.valueOf(vidresponse.code()));
                assert vidresponse.body() != null;
                List<VidRec> viddata = vidresponse.body();

                for(VidRec name : viddata)
                {
                    Log.i("Vid", name.getVid());//Call REST-API getAllStudents
                    GetData vnservice = IISDClient.getRetrofitInstance().create(GetData.class);
                    Call<List<VideoRec>> callvn = vnservice.getVn(auth, aj, aju, name.getVid());

                    //Execute the request asynchronously//
                    callvn.enqueue(new Callback<List<VideoRec>>() {
                        @Override
                        //Handle a successful response//
                        public void onResponse(@NonNull Call<List<VideoRec>> callvn, @NonNull Response<List<VideoRec>> vnresponse) {
                            Log.i("getVn", String.valueOf(vnresponse.code()));
                            assert vnresponse.body() != null;
                            vndata = vnresponse.body();
    //                        VideoRec arlene1 = null;
                            for (VideoRec vnname: vndata) {
                                VideoRec arlene1 = new VideoRec();

                                arlene1.setId(vnname.getId());
                                arlene1.setVideoname(vnname.getVideoname());
                                arlene1.setLinktv(vnname.getLinktv());
                                arlene.add(arlene1);
                            }
                            loadDataList(arlene);
                        }

                        @Override
                        //Handle execution failures//
                        public void onFailure(@NonNull Call<List<VideoRec>> callvn, @NonNull Throwable vnthrowable) {
                            Log.i("error: ", vnthrowable.toString());
                            //If the request fails, then display the following Snackbar//
                            Snackbar.make(alsCoordinationLayout, "Unable to load Video records",
                                            Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }

            @Override
            //Handle execution failures//
            public void onFailure(@NonNull Call<List<VidRec>> callvid, @NonNull Throwable vidthrowable) {
                Log.i("error: ", vidthrowable.toString());
                //If the request fails, then display the following Snackbar//
                Snackbar.make(alsCoordinationLayout, "Unable to load Vid records",
                                Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void loadDataList(List<VideoRec> Navody) {
        //Get a reference to the RecyclerView//
        RecyclerView myRecyclerView = findViewById(R.id.myRecyclerView);
        MyAdapter myAdapter = new MyAdapter(Navody);
        //Use a LinearLayoutManager with default vertical orientation//
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Navody.this);
        myRecyclerView.setLayoutManager(layoutManager);
        //Set the Adapter to the RecyclerView//
        myRecyclerView.setAdapter(myAdapter);
    }
}
