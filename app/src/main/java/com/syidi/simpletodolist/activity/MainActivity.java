package com.syidi.simpletodolist.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.syidi.simpletodolist.R;
import com.syidi.simpletodolist.adapter.TodoAdapter;
import com.syidi.simpletodolist.database.Todo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final int EXTRA_ADD_TODO = 100;

    TodoAdapter todoAdapter;
    ListView listView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponents();
    }

    // Init all components in XML

    private void initComponents() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(this);
        }

        listView = (ListView) findViewById(R.id.listView);
        todoAdapter = new TodoAdapter(this);
        listView.setAdapter(todoAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    // Button clicked

    @Override
    public void onClick(View v) {
        if (v == fab) {
            Intent intent = new Intent(this, AddTodoActivity.class);
            // if AddTodoActivity add new Todo, notify me.
            startActivityForResult(intent, EXTRA_ADD_TODO);
        }
    }

    // Other class notify this class to do something when its finish

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if AddTodoActivity notify me
        if (requestCode == EXTRA_ADD_TODO) {
            if (resultCode == RESULT_OK) {
                todoAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Todo todo = (Todo) todoAdapter.getItem(position);
        if (todo.isDone()) {
            todo.setDone(false);
        } else {
            todo.setDone(true);
        }
        todo.save();
        todoAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Todo todo = (Todo) todoAdapter.getItem(position);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(todo.getTitle())
                .setMessage("What you want to do?")
                .setPositiveButton("Update", null)
                .setNegativeButton("Delete", null)
                .setNeutralButton("Cancel", null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        updateTodo(todo);
                        alertDialog.dismiss();
                    }
                });

                Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        todo.delete();
                        todoAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();

        return true;
    }

    public void updateTodo(final Todo todo) {
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_update_todo, null);
        final EditText etxtTitle = (EditText) dialogView.findViewById(R.id.etxtTitle);
        final EditText etxtDescription = (EditText) dialogView.findViewById(R.id.etxtDescription);
        etxtTitle.setText(todo.getTitle());
        etxtDescription.setText(todo.getDescription());

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Update Todo")
                .setPositiveButton("Save", null)
                .setNeutralButton("Cancel", null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (etxtTitle.getText().toString().trim().equals("") || etxtDescription.getText().toString().trim().equals("")) {
                            showAlert("Error", "Please enter title");
                        } else {
                            todo.setTitle(etxtTitle.getText().toString().trim());
                            todo.setDescription(etxtDescription.getText().toString().trim());
                            todo.save();
                            todoAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        alertDialog.show();
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
