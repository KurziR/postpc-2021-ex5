package exercise.android.reemh.todo_items;


import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoHolder> {

    private TodoItemsHolder toDoesImpl = null;
    private ItemClickListener checkBokListener;
    private ItemClickListener removeListener;
    private ItemClickListener editTextListener;
    private final ArrayList<TodoItem> toDoesAllList = new ArrayList<>();
    private final ArrayList<TodoItem> toDoesDoneList = new ArrayList<>();
    private final ArrayList<TodoItem> toDoesInProgressList = new ArrayList<>();

    public ToDoAdapter(TodoItemsHolder holder) {
        toDoesImpl = holder;
    }

    public interface ItemClickListener{
        void onItemClick(int position, ToDoHolder holder);
    }

    public void setCheckBoxListener(ItemClickListener checkBokListener){
        this.checkBokListener = checkBokListener;
    }

    public void setRemoveListener(ItemClickListener removeListener){
        this.removeListener = removeListener;
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
        Log.e("TAG", "onBindViewHolder: " );

        // recycle the given holder with todoitem at position
        TodoItem todoItem = toDoesImpl.getToDo(position);
        String toDoTask = todoItem.task_name;

        holder.checkBox.setChecked(todoItem.isDone());

        holder.editTextTask.setText(toDoTask); // set edit-text as disabled (user cant input text)

        if (todoItem.isDone()) {
            holder.editTextTask.setPaintFlags(holder.editTextTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.editTextTask.setPaintFlags(holder.editTextTask.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

//        holder.checkBox.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                checkBokListener.onItemClick(position, holder);
//                notifyDataSetChanged();}

        holder.checkBox.setOnClickListener(v -> {
            if (todoItem.isDone()){
                toDoesImpl.markItemInProgress(todoItem);
            }
            else{
                toDoesImpl.markItemDone(todoItem);
            }
            notifyDataSetChanged();
        });

        holder.editTextTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                editTextListener.onItemClick(position, holder);
                notifyDataSetChanged();}
        });

        holder.removeToDo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                removeListener.onItemClick(position, holder);
                notifyDataSetChanged();}
        });


//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // the task in "DONE" status
//                if (!isChecked) {
//                    toDoesImpl.markItemInProgress(todoItem);
//                }
//                // the task in "IN-PROGRESS" status
//                else {
//                    toDoesImpl.markItemDone(todoItem);
//                }
//                //notifyDataSetChanged();
//            }
//        });

//        holder.editTextTask.setOnClickListener(v -> {
//            holder.editTextTask.setEnabled(true);
//        });
//
//        holder.removeToDo.setOnClickListener(v -> {
//            toDoesImpl.deleteItem(todoItem);
//            notifyDataSetChanged();
//        });
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
