package com.vaclmat.sdv2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText fn;
    private EditText ln;
    private EditText gen;
    private EditText sid;
    private String student_id;
    private View sdCoordinationLayout;
    private Boolean fnb = false, lnb = false, genb = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentdetails);
        sdCoordinationLayout = findViewById(R.id.sdCoordinatorLayout);
        Toolbar toolbar = findViewById(R.id.sd_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> finish());

        fn = findViewById(R.id.firstnameetsd);
        ln = findViewById(R.id.lastnameetsd);
        gen = findViewById(R.id.genderetsd);
        sid = findViewById(R.id.studentidsd);
        Button sdm = findViewById(R.id.sdm_button_submit);
        Button sdd = findViewById(R.id.sdd_button_submit);
        sdd.setOnClickListener(this);
        sdm.setOnClickListener(this);
        sdm.setEnabled(false);
        sid.setEnabled(false);

        student_id = getIntent().getStringExtra("studentID");
        assert student_id != null;
        sid.setText(student_id);
        String lns = getIntent().getStringExtra("last_name");
        assert lns != null;
        ln.setText(lns);
        String fns = getIntent().getStringExtra("first_name");
        assert fns != null;
        fn.setText(fns);
        String gens = getIntent().getStringExtra("gender");
        assert gens != null;
        gen.setText(gens);

        fn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*
                if (count != 0) {
                    sdm.setEnabled(true);
                } else {
                    sdm.setEnabled(false);
                }

                 */
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void afterTextChanged(Editable e) {
                if ((e.toString().equals("") || e.toString().equals(fns)) && (!lnb && !genb)) {
                    sdm.setEnabled(false);
                } else {
                    sdm.setEnabled(true);
                    fnb = true;
                }
            }
        });
        ln.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*
                if (count != 0) {
                    sdm.setEnabled(true);
                } else {
                    sdm.setEnabled(false);
                }

                 */
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void afterTextChanged(Editable e) {
                if ((e.toString().equals("") || e.toString().equals(lns) && (!fnb && !genb))) {
                    sdm.setEnabled(false);
                } else {
                    sdm.setEnabled(true);
                    lnb = true;
                }
            }
        });
        gen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*
                if (count != 0) {
                    sdm.setEnabled(true);
                } else {
                    sdm.setEnabled(false);
                }

                 */
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void afterTextChanged(Editable e) {
                if ((e.toString().equals("") || e.toString().equals(gens) && (!fnb && !lnb))) {
                    sdm.setEnabled(false);
                } else {
                    sdm.setEnabled(true);
                    genb = true;
                }
            }
        });
    }

    public void onClick(View view) {
        String chinned = MainActivity.client_id;
        String client_secret = MainActivity.client_secret;
        String aj = "application/json";
        String aju = "application/json; charset=utf-8";
        if (view.getId() == R.id.sdm_button_submit) {
            String  fns = fn.getText().toString();
            String lns = ln.getText().toString();
            String gens = gen.getText().toString();
            /*
            Intent i = new Intent(this, ModifyStudentByID_result_Activity.class);
            i.putExtra("firstname", fns);
            i.putExtra("lastname",lns);
            i.putExtra("gender", gens);
            i.putExtra("studentID",student_id);
            this.startActivity(i);
             */

            GetData service = IISDClient.getRetrofitInstance().create(GetData.class);
            Log.i("GetData", "Created");

            JsonObject jsonParams = new JsonObject();
            jsonParams.addProperty("firstName", fns );
            jsonParams.addProperty("lastName", lns );
            jsonParams.addProperty("gender", gens );
            jsonParams.addProperty("studentID", student_id);
//            Log.i("body", String.valueOf(jsonParams));
            Call<Void> call;
            call = service.modifyStudent(chinned, client_secret, aj, aju, jsonParams);
//            Log.i("modifyStudent", "call started");
//Execute the request asynchronously//
            call.enqueue(new Callback<Void>() {
                @Override
//Handle a successful response//
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> bodyResponse) {
//                    Log.i("Response from mod_r", "OK");
//                    Log.i("Response body", String.valueOf(bodyResponse.body()));
//                    Log.i("Response code", String.valueOf(bodyResponse.code()));
                    loadDataList_m(String.valueOf(bodyResponse.code()));
                }
                @Override
//Handle execution failures//
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
//If the request fails, then display the following toast//
//                    Log.i("Failure", "Unable to add students");
//                    Log.i("Error response", throwable.toString());
//                    Toast.makeText(ModifyStudentByID_result_Activity.this, "Unable to modify students", Toast.LENGTH_SHORT).show();
                    Snackbar.make(sdCoordinationLayout, "Unable to modify students",
                            Snackbar.LENGTH_LONG)
                            .show();
                }
            });

            fn.getText().clear();
            ln.getText().clear();
            gen.getText().clear();
            sid.getText().clear();
//            Hide Keyboard
            ln.onEditorAction(EditorInfo.IME_ACTION_DONE);
            fn.onEditorAction(EditorInfo.IME_ACTION_DONE);
            gen.onEditorAction(EditorInfo.IME_ACTION_DONE);
        }
        if (view.getId() == R.id.sdd_button_submit) {
            /*
            Intent i = new Intent(this, DeleteStudentByID_Activity.class);
            i.putExtra("student_id",student_id);
            this.startActivity(i);
             */
            GetData service = IISDClient.getRetrofitInstance().create(GetData.class);
            Call<IIStudent> call = service.deleteStudentByID(chinned, client_secret, aj, student_id);
            call.enqueue(new Callback<IIStudent>() {
                @Override
//Handle a successful response//
                public void onResponse(@NonNull Call<IIStudent> call, @NonNull Response<IIStudent> response) {
//                        Log.i("Response from DelByID", "OK");
//                        Log.i("Response body", String.valueOf(response.body()));
//                        Log.i("Response Code", String.valueOf(response.code()));
                    loadDataList(String.valueOf(response.code()));
                }
                @Override
//Handle execution failures//
                public void onFailure(@NonNull Call<IIStudent> call, @NonNull Throwable throwable) {
//If the request fails, then display the following toast//
//                        Log.i("Failure", "Unable to delete students");
//                        Log.i("Error response", throwable.toString());
//                        Toast.makeText(DeleteStudentByID_Activity.this, "Unable to delete students", Toast.LENGTH_SHORT).show();
                    Snackbar.make(sdCoordinationLayout, "Unable to delete students",
                            Snackbar.LENGTH_LONG)
                            .show();
                }
            });

            fn.getText().clear();
            ln.getText().clear();
            gen.getText().clear();
            sid.getText().clear();
            /*
            try {
                wait(5000);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             */
        }
    }
    private void loadDataList(String message) {

        String mtc;
        if (message.equals("204")) {
            mtc = "Student was deleted successfully";
            Snackbar mySnack = Snackbar.make(sdCoordinationLayout, mtc, 50000);
            mySnack.setAction("Back", new MyUndoListener());
            mySnack.show();

        } else {
            mtc = "Return http code: " + message;
            Snackbar mySnack = Snackbar.make(sdCoordinationLayout, mtc, 50000);
            mySnack.setAction("Back", new MyUndoListener());
            mySnack.show();
        }
    }

    private void loadDataList_m(String message) {

        String mtc;
        if (message.equals("204")) {
            mtc = "Student was modified successfully";
            Snackbar mySnack = Snackbar.make(sdCoordinationLayout, mtc, 50000);
            mySnack.setAction("Back", new MyUndoListener());
            mySnack.show();

        } else {
            mtc = "Return http code: " + message;
            Snackbar mySnack = Snackbar.make(sdCoordinationLayout, mtc, 50000);
            mySnack.setAction("Back", new MyUndoListener());
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
