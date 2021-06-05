package exercise.android.reemh.todo_items;

import android.os.Build;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class TodoItem implements Serializable {

    private static final long NUMOFMININDAY = 1440;
    private static final long NUMOFMININHOUR = 60;

    enum status {
        IN_PROGRESS,
        DONE
    }

    private final String id;
    private String task_name;
    private status curr_status;
    private final Date creationTime;
    private Date lastModified;

    @RequiresApi(api = Build.VERSION_CODES.N)
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

    public Date getLastModified() {
        return this.lastModified;
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    public String getTaskName() {
        return this.task_name;
    }

    public void setTaskName(String description) {
        this.task_name = description;
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
    public void setFinishTime(Date finishTime){
        this.lastModified = finishTime;
    }
}
