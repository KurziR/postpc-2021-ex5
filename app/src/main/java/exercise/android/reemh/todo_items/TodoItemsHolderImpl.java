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

  List<TodoItem> toDoesList = null;

  public TodoItemsHolderImpl() {
    toDoesList = new ArrayList<TodoItem>();
  }

  @Override
  public List<TodoItem> getCurrentItems() {
    return toDoesList;
  }

  @Override
  public void addNewInProgressItem(String description) {
    TodoItem new_item = new TodoItem(description);
    toDoesList.add(0, new_item); // add the item in the beginning of the list
  }

  @Override
  public void markItemDone(TodoItem item) {
    item.curr_status = TodoItem.status.DONE;
  }

  @Override
  public void markItemInProgress(TodoItem item) {
    item.curr_status = TodoItem.status.IN_PROGRESS;
  }

  @Override
  public void deleteItem(TodoItem item) {
    try {
      for (int i = 0; i < toDoesList.size(); i++) {
        if (item.equals(toDoesList.get(i))) {
          toDoesList.remove(toDoesList.get(i));
        }
      }
    } catch (NullPointerException e) {
      return;
    }
  }
}
