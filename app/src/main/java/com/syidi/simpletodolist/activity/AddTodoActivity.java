package com.syidi.simpletodolist.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.syidi.simpletodolist.R;
import com.syidi.simpletodolist.database.Todo;

public class AddTodoActivity extends AppCompatActivity {

    EditText etxtTitle, etxtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponents();
    }

    // Init all components in XML

    private void initComponents() {
        etxtTitle = (EditText) findViewById(R.id.etxtTitle);
        etxtDescription = (EditText) findViewById(R.id.etxtDescription);
    }

    // Button clicked

    public void saveButtonClicked(View view) {
        if (validateInput()) {
            Todo todo = new Todo(etxtTitle.getText().toString().trim(),
                    etxtDescription.getText().toString().trim());
            todo.save();

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    // Validate user input

    public boolean validateInput() {
        if (etxtTitle.getText().toString().equals("") || etxtDescription.getText().toString().equals("")) {
            showAlert("Error", "Please complete the form");
            return false;
        }
        return true;
    }

    // Display alert

    public void showAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

}
