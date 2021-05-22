package exercise.android.reemh.todo_items;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_todo_item, parent, false);
        return new ToDoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHolder holder, int position) {

        holder.checkBox.setOnCheckedChangeListener(null);

        // recycle the given holder with todoitem at position
        TodoItem todoItem = toDoesImpl.getToDo(position);
        String toDoTask = todoItem.task_name;
        holder.editTextTask.setText(toDoTask); // set edit-text as disabled (user cant input text)
        if (todoItem.getStatus(todoItem) == TodoItem.status.DONE) {
            holder.checkBox.setChecked(true);
            holder.editTextTask.setPaintFlags(holder.editTextTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            holder.checkBox.setChecked(false);
            holder.editTextTask.setPaintFlags(holder.editTextTask.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // the task in "DONE" status
                if(!isChecked) {
                    toDoesImpl.markItemInProgress(todoItem);
                    todoItem.setStatus(TodoItem.status.IN_PROGRESS);
                    holder.checkBox.setChecked(false);
                }
                // the task in "IN-PROGRESS" status
                else {
                    toDoesImpl.markItemDone(todoItem);
                    todoItem.setStatus(TodoItem.status.DONE);
                    holder.checkBox.setChecked(true);
                }
                notifyDataSetChanged();
            }
        });

        //        holder.checkBox.setOnCheckedChangeListener(v -> {
//            // the task in "DONE" status
//            if(holder.checkBox.isChecked()) {
//                toDoesImpl.markItemInProgress(todoItem);
//                holder.checkBox.setChecked(false);
//                holder.editTextTask.setPaintFlags(holder.editTextTask.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
//            }
//            // the task in "IN-PROGRESS" status
//            else {
//                toDoesImpl.markItemDone(todoItem);
//                holder.checkBox.setChecked(true);
//                holder.editTextTask.setPaintFlags(holder.editTextTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            }
//            notifyDataSetChanged();
//        });

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
