package exercise.android.reemh.todo_items;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


// TODO: feel free to add/change/remove methods as you want
public interface TodoItemsHolder extends Serializable {

  /**
   * Get a copy of the current item
   */
  TodoItem getToDo(int position);

  /**
   * Get a copy of the current items list
   */
  List<TodoItem> getCurrentItems();

  /**
   * Creates a new TodoItem and adds it to the list, with the @param description and status=IN-PROGRESS
   * Subsequent calls to [getCurrentItems()] should have this new TodoItem in the list
   */
  void addNewInProgressItem(String description);

  /**
   * mark the @param item as DONE
   */
  void markItemDone(TodoItem item);

  /**
   * mark the @param item as IN-PROGRESS
   */
  void markItemInProgress(TodoItem item);

  /**
   * delete the @param item
   */
  void deleteItem(TodoItem item);

  List<TodoItem> getAllList();

  void setDescription(TodoItem oldItem, String description);

  void setModifiedTime(TodoItem oldItem, Date newDate);

}
