package exercise.android.reemh.todo_items;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class TodoItem implements Serializable {

    enum status {
        IN_PROGRESS,
        DONE
    }

    status curr_status;
    String task_name;
    String id;
    Date creationTime;
    Date lastModified;

    TodoItem(String id, String description){
        this.id = id;
        this.curr_status = status.IN_PROGRESS;
        this.task_name = description;
        this.creationTime = new Date();
        this.lastModified = new Date();
    }

    public String getId() {
        return this.id;
    }

    public status getStatus() {
        return this.curr_status;
    }

    public void setStatus(status status) {
        this.curr_status = status;
    }

    public boolean isDone(){
        return curr_status == status.DONE;
    }

    public static String toDoToString(TodoItem todo) {
        Gson gson = new Gson();
        String toDoAsString = gson.toJson(todo);
        return toDoAsString;
    }

    public static TodoItem stringToDoTo(String todo) {
        Gson gson = new Gson();
        TodoItem todoObj = gson.fromJson(todo, TodoItem.class);
        return todoObj;
    }

}
