package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
  private final SharedPreferences sp;
  private final Context context;

  private final MutableLiveData<List<TodoItem>> inProgressLiveDataMutable;
  public final LiveData<List<TodoItem>> inProgressLiveDataPublic;

  private final MutableLiveData<List<TodoItem>> doneLiveDataMutable;
  public final LiveData<List<TodoItem>> doneLiveDataPublic;

  private final MutableLiveData<List<TodoItem>> allLiveDataMutable;
  public final LiveData<List<TodoItem>> allLiveDataPublic;

  public TodoItemsHolderImpl(Context context) {
    inProgressLiveDataMutable = new MutableLiveData<>();
    inProgressLiveDataPublic = inProgressLiveDataMutable;

    doneLiveDataMutable = new MutableLiveData<>();
    doneLiveDataPublic = doneLiveDataMutable;

    allLiveDataMutable = new MutableLiveData<>();
    allLiveDataPublic = allLiveDataMutable;

    toDoesDoneList = new ArrayList<TodoItem>();
    toDoesInProgressList = new ArrayList<TodoItem>();
    toDoesAllList = new ArrayList<TodoItem>();
    this.context = context;
    this.sp = context.getSharedPreferences("local_db_todo", Context.MODE_PRIVATE);
    initializeFromSp();
  }


  public List<TodoItem> getAllList() {
    return toDoesAllList;
  }

  private void initializeFromSp() {
    Set<String> keys = sp.getAll().keySet();
    for (String key:keys) {
      String toDoToString = sp.getString(key, null);
      TodoItem todo = TodoItem.stringToDoTo(toDoToString);
      if (todo.curr_status == TodoItem.status.DONE) {
        toDoesDoneList.add(0, todo);
        doneLiveDataMutable.setValue(new ArrayList(toDoesDoneList));
      }
      else {
        toDoesInProgressList.add(0, todo);
        inProgressLiveDataMutable.setValue(new ArrayList(toDoesInProgressList));
      }
      toDoesAllList.add(0, todo);
      allLiveDataMutable.setValue(new ArrayList(toDoesAllList));
    }
  }

  @Override
  public TodoItem getToDo(int id) {
    return toDoesAllList.get(id);
  }

  @Override
  public List<TodoItem> getCurrentItems() {
    List<TodoItem> toDoesList = new ArrayList<TodoItem>();
    toDoesList.addAll(toDoesInProgressList);
    toDoesList.addAll(toDoesDoneList);
    return toDoesList;
  }

  public List<TodoItem> getDoneItems() {
    List<TodoItem> toDoesList = new ArrayList<TodoItem>();
    toDoesList.addAll(toDoesDoneList);
    return toDoesList;
  }

  public List<TodoItem> getInProgressItems() {
    List<TodoItem> toDoesList = new ArrayList<TodoItem>();
    toDoesList.addAll(toDoesInProgressList);
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

    allLiveDataMutable.setValue(new ArrayList(toDoesAllList));
    inProgressLiveDataMutable.setValue(new ArrayList(toDoesInProgressList));
  }

  @Override
  public void markItemDone(TodoItem item) {
    item.setStatus(TodoItem.status.DONE);
    toDoesInProgressList.remove(item);
    toDoesDoneList.add(item);
    combineLists();
    updateList(item);
    }


  @Override
  public void markItemInProgress(TodoItem item) {
    item.setStatus(TodoItem.status.IN_PROGRESS);
    toDoesDoneList.remove(item);
    toDoesInProgressList.add(item);
    combineLists();
    updateList(item);
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
              SharedPreferences.Editor editor = sp.edit();
              editor.remove(item.id);
              editor.apply();
              allLiveDataMutable.setValue(new ArrayList(toDoesAllList));
              doneLiveDataMutable.setValue(new ArrayList(toDoesDoneList));
              return;
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
              SharedPreferences.Editor editor = sp.edit();
              editor.remove(item.id);
              editor.apply();
              allLiveDataMutable.setValue(new ArrayList(toDoesAllList));
              inProgressLiveDataMutable.setValue(new ArrayList(toDoesInProgressList));
              return;
            }
          }
          }
        }

    } catch (NullPointerException e) {
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
    allLiveDataMutable.setValue(new ArrayList(toDoesAllList));
    doneLiveDataMutable.setValue(new ArrayList(toDoesDoneList));
    inProgressLiveDataMutable.setValue(new ArrayList(toDoesInProgressList));

  }

}
