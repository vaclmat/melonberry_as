package com.vaclmat.MBv1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UserInfo extends AppCompatActivity  {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Layout and layout elements activation
        setContentView(R.layout.userinfo);
        EditText nn = findViewById(R.id.nickname);
        EditText fn = findViewById(R.id.firstnameet);
        EditText ln = findViewById(R.id.lastnameet);
        EditText dn = findViewById(R.id.display_name);
        EditText emailet = findViewById(R.id.emailet);
        Toolbar toolbar = findViewById(R.id.as_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        //Toolbar navigation to parent activity
        toolbar.setNavigationOnClickListener(view -> finish());

        String nicename = getIntent().getStringExtra("nicename");
        assert  nicename != null;
        String fnu = getIntent().getStringExtra("fn");
        assert  fnu != null;
        String lnu = getIntent().getStringExtra("ln");
        assert  lnu != null;
        String dnu = getIntent().getStringExtra("dn");
        assert  dnu != null;
        String emu = getIntent().getStringExtra("em");
        assert  emu != null;

        nn.setText(nicename);
        fn.setText(fnu);
        ln.setText(lnu);
        dn.setText(dnu);
        emailet.setText(emu);
    }

}
