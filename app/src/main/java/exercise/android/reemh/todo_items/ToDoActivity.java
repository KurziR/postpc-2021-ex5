package exercise.android.reemh.todo_items;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ToDoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        TextView result = findViewById(R.id.result);

        String originalNum = String.valueOf(intent.getLongExtra("original_number", 0));
        String root1 = String.valueOf(intent.getLongExtra("roo1", 0));
        String root2 = String.valueOf(intent.getLongExtra("root2", 0));
        String timeUntilGiveUp = String.valueOf(intent.getLongExtra("time_until_give_up_seconds", 0));


    }
}
