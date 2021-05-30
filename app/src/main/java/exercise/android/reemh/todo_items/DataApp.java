package exercise.android.reemh.todo_items;

import android.app.Application;

public class DataApp extends Application {

    private TodoItemsHolderImpl info;
    private static DataApp instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        info = new TodoItemsHolderImpl(this);
    }

    public static DataApp getInstance() {
        return instance;
    }

    public TodoItemsHolderImpl getDataApp() {
        return info;
    }
}
