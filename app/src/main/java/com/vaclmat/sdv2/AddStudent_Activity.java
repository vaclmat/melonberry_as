package com.vaclmat.sdv2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudent_Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText stid;
    private EditText fn;
    private EditText ln;
    private EditText gen;
    private Button sas;
    private Boolean stidb = false;
    private Boolean fnb = false;
    private Boolean lnb = false;
    private Boolean genb = false;
    private View astCoordinationLayout;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Layout and layout elements activation
        setContentView(R.layout.addstudent);
        astCoordinationLayout = findViewById(R.id.astCoordinatorLayout);
        stid = findViewById(R.id.studentidet);
        fn = findViewById(R.id.firstnameet);
        ln = findViewById(R.id.lastnameet);
        gen = findViewById(R.id.genderet);
        sas = findViewById(R.id.button_submit);
        sas.setOnClickListener(this);
        sas.setEnabled(false);
        Toolbar toolbar = findViewById(R.id.as_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        //Toolbar navigation to parent activity
        toolbar.setNavigationOnClickListener(view -> finish());

        //Check to fill every data element
        stid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    stidb = false;
                } else {
                        stidb = true;
                        if (fnb && lnb && genb)
                            sas.setEnabled(true);
                }
            }
            public void afterTextChanged(Editable e) {
            }
        });
        fn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    fnb = false;
                } else {
                    fnb = true;
                    if (stidb && lnb && genb)
                        sas.setEnabled(true);
                }
            }
            public void afterTextChanged(Editable e) {
            }
        });
        ln.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    lnb = false;
                } else {
                    lnb = true;
                    if (fnb && stidb && genb)
                        sas.setEnabled(true);
                }
            }
            public void afterTextChanged(Editable e) {
            }
        });
        gen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    genb = false;
                } else {
                    genb = true;
                    if (fnb && lnb && stidb)
                        sas.setEnabled(true);
                }
            }
            public void afterTextChanged(Editable e) {
            }
        });


    }

    public void onClick(View view) {
        if (view.getId() == R.id.button_submit) {
            String sids = stid.getText().toString();
            String  fns = fn.getText().toString();
            String lns = ln.getText().toString();
            String gens = gen.getText().toString();

            //Setup http header parameters
            String client_id = MainActivity.client_id;
            String client_secret = MainActivity.client_secret;
            String aj = "application/json";
            String aju = "application/json; charset=utf-8";

            //Call REST-API addStudent
            GetData service = IISDClient.getRetrofitInstance().create(GetData.class);

            //Fill data in json format
            JsonObject jsonParams = new JsonObject();
            jsonParams.addProperty("studentID", sids);
            jsonParams.addProperty("firstName", fns );
            jsonParams.addProperty("lastName", lns );
            jsonParams.addProperty("gender", gens );

            Call<Void> call;
            call = service.addStudent(client_id, client_secret, aj, aju, jsonParams);

            //Execute the request asynchronously//
            call.enqueue(new Callback<Void>() {
                @Override
                //Handle a successful response//
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> bodyResponse) {
                    loadDataList(String.valueOf(bodyResponse.code()));
                }

                @Override
                //Handle execution failures//
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
                    //If the request fails, then display the following snackbar//
                    Snackbar.make(astCoordinationLayout, "Unable to load students",
                            Snackbar.LENGTH_LONG)
                            .show();
                }
            });

            //Clear data
            stid.getText().clear();
            fn.getText().clear();
            ln.getText().clear();
            gen.getText().clear();
            //Close keyboard
            ln.onEditorAction(EditorInfo.IME_ACTION_DONE);
            fn.onEditorAction(EditorInfo.IME_ACTION_DONE);
            gen.onEditorAction(EditorInfo.IME_ACTION_DONE);
            stid.onEditorAction(EditorInfo.IME_ACTION_DONE);
        }
    }
    private void loadDataList(String mes) {
        String msc;
        if (mes.equals("201")) {
            msc = "Student was added successfully";
            //Snackbar manually modified
            Snackbar mySnack = Snackbar.make(astCoordinationLayout, msc, 50000);
            mySnack.setActionTextColor(ContextCompat.getColor(getBaseContext(),R.color.actionTextColor));
            mySnack.setAction("Back", new AddStudent_Activity.MyUndoListener());
            View sandbarView = mySnack.getView();
            TextView textView = sandbarView.findViewById(R.id.snackbar_text);
            textView.setMaxLines(2);
            textView.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.actionTextColor));
            //set text size
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            //Set Snackbar background color
            sandbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorBg));
            mySnack.show();
        } else {
            msc = "Return http code: " + mes;
            Snackbar mySnack = Snackbar.make(astCoordinationLayout, msc, 50000);
            mySnack.setAction("Back", new AddStudent_Activity.MyUndoListener());
            mySnack.show();
        }
    }
    private class MyUndoListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Code to undo the user's last action
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
}
