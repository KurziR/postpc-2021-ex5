package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TodoItemsHolderImpl implements TodoItemsHolder{

  List<TodoItem> toDoesDoneList = null;
  List<TodoItem> toDoesInProgressList = null;
  List<TodoItem> toDoesAllList = null;
  SharedPreferences sp;
  Context context;

  public TodoItemsHolderImpl(Context context) {
    toDoesDoneList = new ArrayList<TodoItem>();
    toDoesInProgressList = new ArrayList<TodoItem>();
    toDoesAllList = new ArrayList<TodoItem>();
    this.context = context;
    this.sp = context.getSharedPreferences("local_db_todo", Context.MODE_PRIVATE);
    initializeFromSp();
  }

  private void initializeFromSp() {
    Set<String> keys = sp.getAll().keySet();
    for (String key:keys) {
      String toDoToString = sp.getString(key, null);
      TodoItem todo = TodoItem.stringToDoTo(toDoToString);
      if (todo.curr_status == TodoItem.status.DONE) {
        toDoesDoneList.add(0, todo);
      }
      else {
        toDoesInProgressList.add(0, todo);
      }
      toDoesAllList.add(0, todo);
    }
  }

  @Override
  public TodoItem getToDo(int position) {
    return toDoesAllList.get(position);
  }

  @Override
  public List<TodoItem> getCurrentItems() {
    List<TodoItem> toDoesList = new ArrayList<TodoItem>();
    toDoesList.addAll(toDoesInProgressList);
    toDoesList.addAll(toDoesDoneList);
    return toDoesList;
  }

  @Override
  public void addNewInProgressItem(String description) {
    String newId = UUID.randomUUID().toString();
    TodoItem new_item = new TodoItem(newId, description);
    toDoesInProgressList.add(0, new_item); // add the item in the beginning of the list
    combineLists();
    String toDoToString = TodoItem.toDoToString(new_item);
    SharedPreferences.Editor editor = sp.edit();
    editor.putString(newId, toDoToString);
    editor.apply();
  }

  @Override
  public void markItemDone(TodoItem item) {
    for(int i=0; i<toDoesInProgressList.size(); i++) {
      TodoItem curr = toDoesInProgressList.get(i);
      if(item.equals(curr)) {
        curr.setStatus(TodoItem.status.DONE);
        toDoesDoneList.add(0, curr);
        toDoesInProgressList.remove(curr);
        combineLists();
        updateList(curr);
        return;
      }
    }
  }

  @Override
  public void markItemInProgress(TodoItem item) {
    for(int i=0; i<toDoesDoneList.size(); i++) {
      TodoItem curr = toDoesDoneList.get(i);
      if(item.equals(curr)) {
        curr.setStatus(TodoItem.status.IN_PROGRESS);
        toDoesInProgressList.add(0, curr);
        toDoesDoneList.remove(curr);
        combineLists();
        updateList(curr);
        return;
      }
    }
  }

  @Override
  public void deleteItem(TodoItem item) {
    try {
      for (int i = 0; i < toDoesDoneList.size(); i++) {
        TodoItem curr = toDoesDoneList.get(i);
        if (item.equals(curr)) {
          for (int j = 0; j < toDoesAllList.size(); j++) {
            TodoItem currInAll = toDoesAllList.get(j);
            if (item.equals(currInAll)) {
              toDoesDoneList.remove(curr);
              toDoesAllList.remove(currInAll);
            }
          }
        }
      }
      for (int i = 0; i < toDoesInProgressList.size(); i++) {
        TodoItem curr = toDoesInProgressList.get(i);
        if (item.equals(curr)) {
          for (int j = 0; j < toDoesAllList.size(); j++) {
            TodoItem currInAll = toDoesAllList.get(j);
            if (item.equals(currInAll)) {
              toDoesInProgressList.remove(curr);
              toDoesAllList.remove(currInAll);
            }
          }
          }
        }
      SharedPreferences.Editor editor = sp.edit();
      editor.remove(item.id);
      editor.apply();
    } catch (NullPointerException e) {
      return;
    }
  }

  public void combineLists() {
    toDoesAllList.clear();
    toDoesAllList.addAll(toDoesInProgressList);
    toDoesAllList.addAll(toDoesDoneList);
  }

  public void updateList(TodoItem curr) {
    String toDoToString = TodoItem.toDoToString(curr);
    SharedPreferences.Editor editor = sp.edit();
    editor.putString(curr.id, toDoToString);
    editor.apply();
  }
}
