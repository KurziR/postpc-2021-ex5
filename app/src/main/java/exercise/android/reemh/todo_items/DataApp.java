package exercise.android.reemh.todo_items;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DataApp extends Application {

    private TodoItemsHolderImpl info;

    @Override
    public void onCreate() {
        super.onCreate();
        info = new TodoItemsHolderImpl(this);
    }

    public TodoItemsHolderImpl getDataApp() {
        return info;
    }


}
