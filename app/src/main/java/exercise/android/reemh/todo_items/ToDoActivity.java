package exercise.android.reemh.todo_items;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ToDoActivity extends AppCompatActivity {

    public  TodoItemsHolderImpl holder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_todo_activity);


        TextView result = findViewById(R.id.result);

        String originalNum = String.valueOf(intent.getLongExtra("original_number", 0));
        String root1 = String.valueOf(intent.getLongExtra("roo1", 0));
        String root2 = String.valueOf(intent.getLongExtra("root2", 0));
        String timeUntilGiveUp = String.valueOf(intent.getLongExtra("time_until_give_up_seconds", 0));


    }
}
