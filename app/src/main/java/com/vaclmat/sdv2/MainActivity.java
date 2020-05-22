package com.vaclmat.sdv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements CredentialsDialog.ICredentialsDialogListener, View.OnClickListener  {

    private String username;
    private String password;
    public static String client_id;
    public static String client_secret;
    private Button get_All_Students;
    private Button get_Student_By_ID;
    private Button add_Student;
    private EditText student_id;
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
        get_All_Students = findViewById(R.id.getAllStudents_button);
        maCoordinationLayout = findViewById(R.id.maCoordinatorLayout);
        get_Student_By_ID = findViewById(R.id.student_by_id_button);
        add_Student = findViewById(R.id.add_student);
        get_Student_By_ID.setOnClickListener(this);
        get_All_Students.setOnClickListener(this);
        add_Student.setOnClickListener(this);
        student_id = findViewById(R.id.student_id);
        student_id.setEnabled(false);

        //Enabled elements when return back to main activity from other activities
        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            get_All_Students.setEnabled(true);
            add_Student.setEnabled(true);
            student_id.setEnabled(true);
        }

        //Activate student_id EditText when filled
        student_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    get_Student_By_ID.setEnabled(false);
                } else {
                        get_Student_By_ID.setEnabled(true);
                }
            }
            public void afterTextChanged(Editable e) {
            }
        });
    }

    //Reaction on button click
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getAllStudents_button:
                Intent i = new Intent(MainActivity.this,GetAllStudents_Activity.class);
                this.startActivity(i);
                student_id.getText().clear();
                break;
            case R.id.student_by_id_button:
                String sid = student_id.getText().toString();
                Intent j = new Intent(MainActivity.this,GetStudentByID_Activity.class);
                j.putExtra("student_id", sid);
                this.startActivity(j);
                student_id.getText().clear();
                break;
            case R.id.add_student:
                Intent k = new Intent(MainActivity.this,AddStudent_Activity.class);
                this.startActivity(k);
                student_id.getText().clear();
                break;
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
        switch (item.getItemId()) {
            case R.id.menu_credentials:
                showCredentialsDialog();
                return true;
            case R.id.menu_logout:
                username = "";
                password = "";
                client_id = "";
                client_secret = "";
                credentials_mi.setVisible(true);
                logout_mi.setVisible(false);
                get_All_Students.setEnabled(false);
                get_Student_By_ID.setEnabled(false);
                add_Student.setEnabled(false);
                student_id.setEnabled(false);
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
        String accepted_password = "jit89ka";
        String accepted_username = "vaclmat@email.cz";
        if (this.username.equals(accepted_username) && this.password.equals(accepted_password)) {
            get_All_Students.setEnabled(true);
            add_Student.setEnabled(true);
            student_id.setEnabled(true);
            client_id = "7de33e9c-1c2c-426d-8824-57fa8e0a2ccc";
            client_secret = "J1mR4kY2aT1pO6nS4wE4qN1bB2qI4tX8xK6rT2sO5tG7gD3iT7";
            logout_mi.setVisible(true);
            credentials_mi.setVisible(false);
            SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
        } else {
            Snackbar.make(maCoordinationLayout, "Wrong username or password",
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}
