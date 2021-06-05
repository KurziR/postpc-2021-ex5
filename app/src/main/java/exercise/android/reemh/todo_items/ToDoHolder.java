package exercise.android.reemh.todo_items;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import exercise.android.reemh.todo_items.R;

public class ToDoHolder extends RecyclerView.ViewHolder{

    TextView editTextTask;
    CheckBox checkBox;
    Button removeToDo;
    Button updateToDo;

    public ToDoHolder(@NonNull View itemView) {
        super(itemView);
        editTextTask = itemView.findViewById(R.id.todo_text);
        checkBox = itemView.findViewById(R.id.checkBox);
        removeToDo = itemView.findViewById(R.id.remove_button);
        updateToDo = itemView.findViewById(R.id.update_button);
    }
}
