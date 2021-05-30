package exercise.android.reemh.todo_items;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.UUID;

public class TodoItem implements Serializable {

    enum status {
        IN_PROGRESS,
        DONE
    }

    status curr_status;
    String task_name;
    String id;

    TodoItem(String id, String description){
        this.id = id;
        this.curr_status = status.IN_PROGRESS;
        this.task_name = description;
    }

    public String getId() {
        return id;
    }

    public status getStatus() {
        return curr_status;
    }

    public void setStatus(status status) {
        this.curr_status = status;
    }

    public static String toDoToString(TodoItem todo) {
        String id = todo.id;
        TodoItem myTodo = new TodoItem(id, todo.task_name);
        Gson gson = new Gson();
        String toDoAsString = gson.toJson(myTodo);
        return toDoAsString;
    }

    public static TodoItem stringToDoTo(String todo) {
        Gson gson = new Gson();
        TodoItem todoObj = gson.fromJson(todo, TodoItem.class);
        return todoObj;
    }

}
