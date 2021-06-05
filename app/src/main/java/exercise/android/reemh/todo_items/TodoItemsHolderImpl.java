package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TodoItemsHolderImpl implements TodoItemsHolder{

  public List<TodoItem> toDoesDoneList;
  public List<TodoItem> toDoesInProgressList;
  public List<TodoItem> toDoesAllList;
  private final SharedPreferences sp;

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

    toDoesDoneList = new ArrayList<>();
    toDoesInProgressList = new ArrayList<>();
    toDoesAllList = new ArrayList<>();
    this.sp = context.getSharedPreferences("local_db_todo", Context.MODE_PRIVATE);
    initializeFromSp();
  }


  public List<TodoItem> getAllList() {
    return toDoesAllList;
  }

  private void initializeFromSp() {
    Set<String> keys = sp.getAll().keySet();
    for (String key:keys) {
      if (sp.getString(key, null) == null){
        return;
      }
      TodoItem todo = TodoItem.stringToDoTo(sp.getString(key, null));
      if (todo.getStatus() == TodoItem.status.DONE) {
        toDoesDoneList.add(0, todo);
        doneLiveDataMutable.setValue(this.toDoesDoneList);
      }
      else {
        toDoesInProgressList.add(0, todo);
        inProgressLiveDataMutable.setValue(this.toDoesInProgressList);
      }
      toDoesAllList.add(0, todo);
      allLiveDataMutable.setValue(this.toDoesAllList);
    }
  }

  @Override
  public TodoItem getToDo(int id) {
    return toDoesAllList.get(id);
  }

  @Override
  public List<TodoItem> getCurrentItems() {
    List<TodoItem> toDoesList = new ArrayList<>();
    toDoesList.addAll(toDoesInProgressList);
    toDoesList.addAll(toDoesDoneList);
    return toDoesList;
  }

  public List<TodoItem> getDoneItems() {
    return new ArrayList<>(toDoesDoneList);
  }

  public List<TodoItem> getInProgressItems() {
    return new ArrayList<>(toDoesInProgressList);
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  public void addNewInProgressItem(String description) {
    String newId = UUID.randomUUID().toString();
    TodoItem new_item = new TodoItem(newId, description);
    toDoesInProgressList.add(0, new_item); // add the item in the beginning of the list
    combineLists();
    String toDoToString = new_item.toDoToString();
    SharedPreferences.Editor editor = sp.edit();
    editor.putString(newId, toDoToString);
    editor.apply();
    allLiveDataMutable.setValue(this.toDoesAllList);
    inProgressLiveDataMutable.setValue(this.toDoesInProgressList);
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
              editor.remove(item.getId());
              editor.apply();
              allLiveDataMutable.setValue(this.toDoesAllList);
              doneLiveDataMutable.setValue(this.toDoesDoneList);
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
              editor.remove(item.getId());
              editor.apply();
              allLiveDataMutable.setValue(this.toDoesAllList);
              inProgressLiveDataMutable.setValue(this.toDoesInProgressList);
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
    String toDoToString = curr.toDoToString();
    SharedPreferences.Editor editor = sp.edit();
    editor.putString(curr.getId(), toDoToString);
    editor.apply();
    allLiveDataMutable.setValue(this.toDoesAllList);
    doneLiveDataMutable.setValue(this.toDoesDoneList);
    inProgressLiveDataMutable.setValue(this.toDoesInProgressList);
  }

  @Nullable
  public TodoItem getToDoItem(String itemId) {
    for (TodoItem item: toDoesAllList) {
      if (item.getId().equals(itemId)) {
        return item;
      }
    }
    return null;
  }

  @Override
  public void setDescription(TodoItem oldItem, String description) {
    for (TodoItem todoItem: this.toDoesAllList){
      if (todoItem == oldItem){
        todoItem.setTaskName(description);
      }
    }
    combineLists();
    updateList(oldItem);
  }

  @Override
  public void setModifiedTime(TodoItem oldItem, Date newDate) {
    for (TodoItem todoItem: this.toDoesAllList){
      if (todoItem == oldItem){
        todoItem.setFinishTime(newDate);
      }
    }
    combineLists();
    updateList(oldItem);
  }
}
