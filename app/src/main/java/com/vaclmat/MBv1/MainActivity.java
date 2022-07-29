package com.vaclmat.MBv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CredentialsDialog.ICredentialsDialogListener, View.OnClickListener  {

    private String username;
    private String password;
    private String nicename;
    private String fn;
    private String ln;
    private String dn;
    private String em;
    public static String client_token;
    private Button navody;
    private Button userinfo;
    private MenuItem credentials_mi;
    private MenuItem logout_mi;
    private View maCoordinationLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //layout and layout elements activation
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        navody = findViewById(R.id.navody_button);
        maCoordinationLayout = findViewById(R.id.maCoordinatorLayout);
        userinfo = findViewById(R.id.UserInfo_button);
        navody.setOnClickListener(this);
        userinfo.setOnClickListener(this);


        //Enabled elements when return back to main activity from other activities
        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            navody.setEnabled(true);
            userinfo.setEnabled(true);
        }
    }

    //Reaction on button click
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.navody_button) {
            Intent i = new Intent(MainActivity.this, Navody.class);
            i.putExtra("username", username);
            this.startActivity(i);
        } else if (id == R.id.UserInfo_button) {
            Intent k = new Intent(MainActivity.this, UserInfo.class);
            k.putExtra("nicename", nicename);
            k.putExtra("fn", fn);
            k.putExtra("ln", ln);
            k.putExtra("dn", dn);
            k.putExtra("em", em);
            this.startActivity(k);
        }
    }

    //Menu activation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        logout_mi = menu.findItem(R.id.menu_logout);
        credentials_mi = menu.findItem(R.id.menu_credentials);
        //Rearrange menu elements when return back to main activity from other activities
        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            logout_mi.setVisible(true);
            credentials_mi.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_credentials) {
            showCredentialsDialog();
            return true;
        } else if (itemId == R.id.menu_logout) {
            username = "";
            password = "";
            client_token = "";
            credentials_mi.setVisible(true);
            logout_mi.setVisible(false);
            navody.setEnabled(false);
            userinfo.setEnabled(false);
            //Delete information about logged status
            SaveSharedPreference.setLoggedIn(getApplicationContext(), false);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCredentialsDialog() {
        CredentialsDialog dialog = new CredentialsDialog();
        Bundle arguments = new Bundle();
        arguments.putString("username", username);
        arguments.putString("password", password);
        dialog.setArguments(arguments);

        dialog.show(getSupportFragmentManager(), "credentialsDialog");
    }

    @Override
    public void onDialogPositiveClick(String username, String password) {
        this.username = username;
        this.password = password;

        String aju = "application/json; charset=utf-8";

        //Call REST-API getAllStudents
        GetData service = IISDClient.getRetrofitInstance().create(GetData.class);

        JsonObject jsonParams = new JsonObject();
        jsonParams.addProperty("username", username );
        jsonParams.addProperty("password", password );

        Call<Ud> call = service.getToken(aju, jsonParams);

        //Execute the request asynchronously//
        call.enqueue(new Callback<Ud>() {
            @Override
            //Handle a successful response//
            public void onResponse(@NonNull Call<Ud> call, @NonNull Response<Ud> response) {
                Log.i("Response token", String.valueOf(response.code()));
                assert response.body() != null;
                if (String.valueOf(response.body().getStatusCode()).equals("200") ) {
                    assert response.body() != null;
                    client_token = response.body().getData().getToken();
                    nicename = response.body().getData().getNicename();
                    fn = response.body().getData().getFirstName();
                    ln = response.body().getData().getLastName();
                    dn = response.body().getData().getDisplayName();
                    em = response.body().getData().getEmail();
        //            Log.i("Client token: ", client_token);
                    navody.setEnabled(true);
                    userinfo.setEnabled(true);
                    logout_mi.setVisible(true);
                    credentials_mi.setVisible(false);
                    SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                } else {
                    Snackbar.make(maCoordinationLayout, "Nesprávná přihlašovací data",
                            Snackbar.LENGTH_LONG)
                            .show();
                }


            }

            @Override
            //Handle execution failures//
            public void onFailure(@NonNull Call<Ud> call, @NonNull Throwable throwable) {
                //If the request fails, then display the following Snackbar//
                Log.i("error: ", throwable.toString());
                Snackbar.make(maCoordinationLayout, "Unable to get token",
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }
}
