package exercise.android.reemh.todo_items;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DataApp extends Application {

    private SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp == null) {
            RecyclerView recyclerTodoItemsList= findViewById(R.id.recyclerTodoItemsList);
            ToDoAdapter adapter = new ToDoAdapter(holder);
            recyclerTodoItemsList.setAdapter(adapter);
            recyclerTodoItemsList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            );

            FloatingActionButton buttonCreateTodoItem = findViewById(R.id.buttonCreateTodoItem);
            EditText editTextInsertTask = findViewById(R.id.editTextInsertTask);

            editTextInsertTask.setText(""); // cleanup text in edit-text
            editTextInsertTask.setEnabled(true); // set edit-text as enabled (user can input text)
            buttonCreateTodoItem.setEnabled(false); // set button as disabled (user can't click)

            editTextInsertTask.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
                public void afterTextChanged(Editable s) {
                    // text did change
                    String newText = editTextInsertTask.getText().toString();
                    buttonCreateTodoItem.setEnabled(true);
                }
            });

            buttonCreateTodoItem.setOnClickListener(v -> {
                String userInputString = editTextInsertTask.getText().toString();
                if(userInputString.equals(""))
                    return;
                holder.addNewInProgressItem(userInputString);
                editTextInsertTask.setText(""); // cleanup text in edit-text
                recyclerTodoItemsList.getAdapter().notifyDataSetChanged();
            });
        }
    }

    public DataApp getDataApp() {
        return
    }
}
