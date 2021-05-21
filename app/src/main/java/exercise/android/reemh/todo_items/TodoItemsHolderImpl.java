package exercise.android.reemh.todo_items;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// TODO: implement!
public class TodoItemsHolderImpl implements TodoItemsHolder{

  List<TodoItem> toDoesDoneList = null;
  List<TodoItem> toDoesInProgressList = null;
  List<TodoItem> toDoesAllList = null;

  public TodoItemsHolderImpl() {
    toDoesDoneList = new ArrayList<TodoItem>();
    toDoesInProgressList = new ArrayList<TodoItem>();
    toDoesAllList = new ArrayList<TodoItem>();
  }

  @Override
  public TodoItem getToDo(int position) {
    return toDoesAllList.get(position);
  }

  @Override
  public List<TodoItem> getCurrentItems() {
    return toDoesAllList;
  }

  @Override
  public void addNewInProgressItem(String description) {
    TodoItem new_item = new TodoItem(description);
    toDoesInProgressList.add(0, new_item); // add the item in the beginning of the list
   combineLists();
  }

  @Override
  public void markItemDone(TodoItem item) {
    for(int i=0; i<toDoesInProgressList.size(); i++) {
      TodoItem curr = toDoesInProgressList.get(i);
      if(item.equals(curr)) {
        curr.curr_status = TodoItem.status.DONE;
        toDoesDoneList.add(0, curr);
        toDoesInProgressList.remove(curr);
        combineLists();
        return;
      }
    }
  }

  @Override
  public void markItemInProgress(TodoItem item) {
    for(int i=0; i<toDoesDoneList.size(); i++) {
      TodoItem curr = toDoesDoneList.get(i);
      if(item.equals(curr)) {
        curr.curr_status = TodoItem.status.IN_PROGRESS;
        toDoesInProgressList.add(0, curr);
        toDoesDoneList.remove(curr);
        combineLists();
        return;
      }
    }
  }

  @Override
  public void deleteItem(TodoItem item) {
    try {
      for (int i = 0; i < toDoesAllList.size(); i++) {
        TodoItem curr = toDoesDoneList.get(i);
        if (item.equals(curr)) {
          if (curr.curr_status == TodoItem.status.DONE) {
            toDoesDoneList.remove(curr);
          }
          else {
            toDoesInProgressList.remove(curr);
          }
          toDoesAllList.remove(toDoesAllList.get(i));
        }
      }
    } catch (NullPointerException e) {
      return;
    }
  }

  public void combineLists() {
    toDoesAllList.clear();
    toDoesAllList.addAll(toDoesInProgressList);
    toDoesAllList.addAll(toDoesDoneList);
  }
}
