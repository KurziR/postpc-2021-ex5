package exercise.android.reemh.todo_items;

import java.util.List;

// TODO: implement!
public class TodoItemsHolderImpl implements TodoItemsHolder {
  @Override
  public List<TodoItem> getCurrentItems() {
    return toDoesList;
  }

  @Override
  public void addNewInProgressItem(String description) {
    TodoItem new_item = new TodoItem();
    new_item.task_name = description;
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
