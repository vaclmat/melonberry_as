package com.vaclmat.MBv1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CredentialsDialog extends DialogFragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private ICredentialsDialogListener listener;

    public interface ICredentialsDialogListener {
        void onDialogPositiveClick(String username, String password);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() instanceof ICredentialsDialogListener) {
            listener = (ICredentialsDialogListener) getActivity();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_credentials, null);
        usernameEditText = view.findViewById(R.id.username_edittext);
        passwordEditText = view.findViewById(R.id.password_edittext);
        assert getArguments() != null;
        usernameEditText.setText(getArguments().getString("username"));
        passwordEditText.setText(getArguments().getString("password"));
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setView(view)
                .setTitle("Přihlášení")
                .setNegativeButton("Zrušit", null)
                .setPositiveButton("Přihlásit", (dialog, which) -> {
                    if (listener != null) {
                        listener.onDialogPositiveClick(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    }
                });
        return builder.create();
    }
}
