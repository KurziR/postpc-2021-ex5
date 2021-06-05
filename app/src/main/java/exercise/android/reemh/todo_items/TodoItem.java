package exercise.android.reemh.todo_items;

import android.widget.TextView;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Calendar;

public class TodoItem implements Serializable {

    private static final long NUMOFMININDAY = 1440;
    private static final long NUMOFMININHOUR = 60;

    enum status {
        IN_PROGRESS,
        DONE
    }

    String id;
    String task_name;
    status curr_status;
    Calendar creationTime;
    Calendar lastModified;

    TodoItem(String id, String description){
        this.id = id;
        this.curr_status = status.IN_PROGRESS;
        this.task_name = description;
        this.creationTime = Calendar.getInstance();
        this.lastModified = creationTime;
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

    public Calendar getLastModified() {
        return this.lastModified;
    }

    public Calendar getCreationTime() {
        return this.creationTime;
    }

    public String getTaskName() {
        return this.task_name;
    }

    public boolean isDone(){
        return curr_status == status.DONE;
    }

    public String toDoToString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static TodoItem stringToDoTo(String todo) {
        Gson gson = new Gson();
        return gson.fromJson(todo, TodoItem.class);
    }

    public static void calculateModifiedTime(Calendar last, TextView modifiedTime) {
        long diffminutes = (Calendar.getInstance().getTimeInMillis() - last.getTimeInMillis()) / 60000;
        if (diffminutes > NUMOFMININDAY) {
            modifiedTime.setText(last.getTime().toString());
        }
        else if (diffminutes > NUMOFMININHOUR) {
            modifiedTime.setText(("Today at " + (diffminutes / NUMOFMININHOUR)));
        }
        modifiedTime.setText((diffminutes + " minutes ago"));
    }

}
