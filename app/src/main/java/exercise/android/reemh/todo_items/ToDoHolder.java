package exercise.android.reemh.todo_items;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import exercise.android.reemh.todo_items.R;

public class ToDoHolder extends RecyclerView.ViewHolder{
    public ToDoHolder(@NonNull View itemView) {
        super(itemView);
        TextView editTextTask = itemView.findViewById(R.id.todo_text);
    }
}
