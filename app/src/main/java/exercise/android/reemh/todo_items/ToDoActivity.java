package exercise.android.reemh.todo_items;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ToDoActivity extends AppCompatActivity {

    public TodoItemsHolderImpl holder = null;
    private TodoItem editToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_todo_activity);

        if (holder == null) {
            holder = DataApp.getInstance().getDataApp();
        }

        Intent intent = getIntent();
        if (!intent.hasExtra("itemToEdit")) {
            finish();
            return;
        }
        editToDo = holder.getToDoItem(intent.getStringExtra("itemToEdit"));
        if (editToDo == null) {
            finish();
            return;
        }

        EditText description = findViewById(R.id.editDescription);
        CheckBox myCheckBox = findViewById(R.id.checkBoxActvity);
        TextView creationTimeTitle = findViewById(R.id.creationDate);
        TextView modifiedTimeTitle = findViewById(R.id.lastModifiedDate);
        TextView creationTime = findViewById(R.id.creation);
        TextView modifiedTime = findViewById(R.id.lastModified);

        creationTime.setVisibility(View.VISIBLE);
        creationTimeTitle.setVisibility(View.VISIBLE);
        modifiedTimeTitle.setVisibility(View.VISIBLE);
        modifiedTime.setVisibility(View.VISIBLE);

        myCheckBox.setChecked(editToDo.isDone());
        description.setText(editToDo.getTaskName());
        creationTime.setText(editToDo.getCreationTime().getTime().toString());
        TodoItem.calculateModifiedTime(editToDo.getLastModified(), modifiedTime);

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(description.getText().toString().isEmpty()) {
                    description.setError("Please edit description");
                } else{
                    description.setText(editToDo.getTaskName());
                }
                TodoItem.calculateModifiedTime(editToDo.getLastModified(), modifiedTime);
            }
        });

        myCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.markItemDone(editToDo);
            } else {
                holder.markItemInProgress(editToDo);
            }
            Calendar modifiedDate = Calendar.getInstance();
            TodoItem.calculateModifiedTime(modifiedDate, modifiedTime);
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}

