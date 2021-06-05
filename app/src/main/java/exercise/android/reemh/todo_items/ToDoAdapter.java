package exercise.android.reemh.todo_items;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class ToDoAdapter extends RecyclerView.Adapter<ToDoHolder> {

    private TodoItemsHolder toDoesImpl = null;
    ItemClickListener editTextListener;
    ItemClickListener removeListener;
    private final ArrayList<TodoItem> toDoesAllList = new ArrayList<>();
    private final ArrayList<TodoItem> toDoesDoneList = new ArrayList<>();
    private final ArrayList<TodoItem> toDoesInProgressList = new ArrayList<>();

    public ToDoAdapter(TodoItemsHolder holder) {
        toDoesImpl = holder;
    }

    public interface ItemClickListener{
        void onItemClick(int position, ToDoHolder holder);
    }

    public void toDoesList(List<TodoItem> newItems){
        toDoesAllList.clear();
        toDoesAllList.addAll(newItems);
        notifyDataSetChanged();
    }

    public void doneList(List<TodoItem> newItems){
        toDoesDoneList.clear();
        toDoesDoneList.addAll(newItems);
        notifyDataSetChanged();
    }

    public void inProgressList(List<TodoItem> newItems){
        toDoesInProgressList.clear();
        toDoesInProgressList.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_todo_item, parent, false);
        return new ToDoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHolder holder, int position) {
        // recycle the given holder with todoitem at position
        TodoItem todoItem = toDoesImpl.getToDo(position);
        String toDoTask = todoItem.getTaskName();

        holder.checkBox.setChecked(todoItem.isDone());

        holder.editTextTask.setText(toDoTask); // set edit-text as disabled (user cant input text)

        if (todoItem.isDone()) {
            holder.editTextTask.setPaintFlags(holder.editTextTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.editTextTask.setPaintFlags(holder.editTextTask.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.checkBox.setOnClickListener(v -> {
            if (todoItem.isDone()){
                toDoesImpl.markItemInProgress(todoItem);
            }
            else{
                toDoesImpl.markItemDone(todoItem);
            }
            notifyDataSetChanged();
        });

        holder.updateToDo.setOnClickListener(v -> {
            if (this.editTextListener != null) {
                editTextListener.onItemClick(position, holder);
                Intent intent = new Intent(v.getContext(), ToDoActivity.class);
                intent.putExtra("toDoToEdit", toDoesImpl.getToDo(position).getId());
                v.getContext().startActivity(intent);
                notifyDataSetChanged();
            }
        });

        holder.removeToDo.setOnClickListener(v -> {
            toDoesImpl.deleteItem(todoItem);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        try {
            return this.toDoesImpl.getCurrentItems().size();
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
