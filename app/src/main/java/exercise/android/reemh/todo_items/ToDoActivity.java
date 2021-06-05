package exercise.android.reemh.todo_items;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ToDoActivity extends AppCompatActivity {

    public TodoItemsHolderImpl holder = null;
    private TodoItem editToDo;
    EditText editTextTask;
    CheckBox checkBox;
    TextView creationTimeTitle;
    TextView modifiedTimeTitle;
    TextView creationTime;
    TextView modifiedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_todo_activity);

        if (holder == null) {
            holder = DataApp.getInstance().getDataApp();
        }

        Intent intent = getIntent();
        if (!intent.hasExtra("toDoToEdit")) {
            finish();
            return;
        }
        editToDo = holder.getToDoItem(intent.getStringExtra("toDoToEdit"));
        if (editToDo == null) {
            finish();
            return;
        }

        editTextTask = findViewById(R.id.editDescription);
        checkBox = findViewById(R.id.checkBoxActvity);
        creationTimeTitle = findViewById(R.id.creationDate);
        modifiedTimeTitle = findViewById(R.id.lastModifiedDate);
        creationTime = findViewById(R.id.creation);
        modifiedTime = findViewById(R.id.lastModified);

        creationTime.setVisibility(View.VISIBLE);
        creationTimeTitle.setVisibility(View.VISIBLE);
        modifiedTimeTitle.setVisibility(View.VISIBLE);
        modifiedTime.setVisibility(View.VISIBLE);

        checkBox.setChecked(editToDo.isDone());
        editTextTask.setText(editToDo.getTaskName());
        updateCreationTime();
        creationTime.setText(editToDo.getCreationTime().toString());
        modifiedTime.setText(editToDo.getLastModified().toString());

        editTextTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!editTextTask.getText().toString().equals("")){
                    holder.setDescription(editToDo, editTextTask.getText().toString());
                    Date currDate = new Date();
                    holder.setModifiedTime(editToDo, currDate);
                    updateModifiedTime();
                }
            }
        });

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.markItemDone(editToDo);
            } else {
                holder.markItemInProgress(editToDo);
            }
            Date currentDate = new Date();
            holder.setModifiedTime(editToDo, currentDate);
            this.updateModifiedTime();
        });
    }

    @SuppressLint("SetTextI18n")
    public void updateModifiedTime(){
        Date currDate = new Date();
        Date prevDate = editToDo.getLastModified();
        long diff = currDate.getTime() - prevDate.getTime();
        long minutes = (int)diff / (1000 * 60);
        long hours = (int)minutes / 60;
        if(minutes < 60) {
            modifiedTime.setText(String.valueOf(minutes) + " minutes ago");
        }
        else {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(prevDate);
            String toDoMinutes = String.valueOf(calendar.get(Calendar.MINUTE));
            String toDoHours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
            String toDoDday = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String toDoMonth = String.valueOf(calendar.get(Calendar.MONTH));
            if(hours < 24) {
                modifiedTime.setText("Today at " + toDoHours + ":" + toDoMinutes);
            }
            else {
                modifiedTime.setText(toDoDday + "/" + toDoMonth + " at " + toDoHours + ":" + toDoMinutes);
            }
        }
    }

    public void updateCreationTime() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(editToDo.getCreationTime());
        String toDoDday = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String toDoMonth = String.valueOf(calendar.get(Calendar.MONTH));
        String toDoHours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String toDoMinutes = String.valueOf(calendar.get(Calendar.MINUTE));
        String day = "Created on " + toDoDday + "/" + toDoMonth + " on " + toDoHours + ":" + toDoMinutes;
        creationTime.setText(day);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textTask", editTextTask.getText().toString());
        outState.putBoolean("status", checkBox.isChecked());
        outState.putString("creationTime", creationTime.toString());
        outState.putString("modifiedTime", modifiedTime.toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editTextTask.setText(savedInstanceState.getString("textTask"));
        checkBox.setChecked(savedInstanceState.getBoolean("status"));
        creationTime.setText(savedInstanceState.getString("creationTime"));
        modifiedTime.setText(savedInstanceState.getString("modifiedTime"));
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}

