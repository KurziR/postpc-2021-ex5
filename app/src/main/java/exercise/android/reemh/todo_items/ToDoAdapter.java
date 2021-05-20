package exercise.android.reemh.todo_items;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import exercise.android.reemh.todo_items.TodoItem;
import exercise.android.reemh.todo_items.TodoItemsHolder;
import exercise.android.reemh.todo_items.TodoItemsHolderImpl;

import static androidx.recyclerview.widget.RecyclerView.*;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoHolder> {

    private TodoItemsHolder toDoesImpl = null;

    public ToDoAdapter(TodoItemsHolder holder) {
        toDoesImpl = holder;
    }

    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one_todo, parent, false);
        return new ToDoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHolder holder, int position) {

        // recycle the given holder with todoitem at position
        TodoItem todoItem = toDoesImpl.getToDo(position);
        String toDoTask = todoItem.task_name;
        holder.editTextTask.setText(toDoTask); // set edit-text as disabled (user cant input text)
        holder.checkBox.isChecked();

        holder.checkBox.setOnClickListener(v -> {
            if(holder.checkBox.isChecked()) {
                toDoesImpl.markItemInProgress(todoItem);
            }
            else {
                toDoesImpl.markItemDone(todoItem);
            }
            notifyDataSetChanged();
        });

        holder.editTextTask.setOnClickListener(v -> {
            // todo ?????
            holder.editTextTask.setEnabled(true);
        });

        holder.removeToDo.setOnClickListener(v -> {
            toDoesImpl.deleteItem(todoItem);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return this.toDoesImpl.getCurrentItems().size();
    }
}
