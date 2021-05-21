package exercise.android.reemh.todo_items;

import java.io.Serializable;

public class TodoItem implements Serializable {
  // TODO: edit this class as you want

    enum status {
        IN_PROGRESS,
        DONE
    }

    status curr_status;
    String task_name;

    TodoItem(String description){
        this.curr_status = status.IN_PROGRESS;
        this.task_name = description;
    }

}
