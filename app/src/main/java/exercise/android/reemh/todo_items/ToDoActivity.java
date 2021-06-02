package exercise.android.reemh.todo_items;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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



}
}
