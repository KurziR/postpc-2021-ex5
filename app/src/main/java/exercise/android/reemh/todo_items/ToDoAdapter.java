package exercise.android.reemh.todo_items;

import android.content.Intent;
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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHolder holder, int position) {
        holder.checkBox.setOnClickListener(v -> {

        });

        holder.editTextTask.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return this.toDoesImpl.getCurrentItems().size();
    }
}
