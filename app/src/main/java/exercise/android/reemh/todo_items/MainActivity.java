package exercise.android.reemh.todo_items;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  public TodoItemsHolderImpl holder = null;
  private static final String BUNDLE_HOLDER = "holder";
  private ToDoAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (holder == null) {
      holder = (TodoItemsHolderImpl) DataApp.getInstance().getDataApp();
    }

    holder.allLiveDataPublic.observe(this, todoItems -> adapter.toDoesList(holder.getCurrentItems()));
    RecyclerView recyclerTodoItemsList= findViewById(R.id.recyclerTodoItemsList);
    this.adapter = new ToDoAdapter(this.holder);
    recyclerTodoItemsList.setAdapter(this.adapter);
    recyclerTodoItemsList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    adapter.setCheckBoxListener(((position, holder) -> {
      if (holder.checkBox.isChecked()){
        this.holder.markItemDone(this.holder.getAllList().get(position));
      }
      else this.holder.markItemInProgress(this.holder.getAllList().get(position));
    }));

    adapter.setRemoveListener(((position, holder) -> {
        this.holder.deleteItem(this.holder.getAllList().get(position));
    }));


//    holder.getInProgressLiveDataPublic().observe(this, new Observer<List<TodoItem>>() {
//      @Override
//      public void onChanged(List<TodoItem> todo){
////        adapter.notifyDataSetChanged();
//      }
//    });
//
//    holder.getDoneLiveDataPublic().observe(this, new Observer<List<TodoItem>>() {
//      @Override
//      public void onChanged(List<TodoItem> todo){
////        adapter.notifyDataSetChanged();
//      }
//    });

    holder.getAllLiveDataPublic().observe(this, new Observer<List<TodoItem>>() {
      @Override
      public void onChanged(List<TodoItem> todo){
        Log.e("stom", "onChanged: I got here" );
        adapter.notifyDataSetChanged();
      }
    });

    FloatingActionButton buttonCreateTodoItem = findViewById(R.id.buttonCreateTodoItem);
    EditText editTextInsertTask = findViewById(R.id.editTextInsertTask);

    editTextInsertTask.setText(""); // cleanup text in edit-text
    editTextInsertTask.setEnabled(true); // set edit-text as enabled (user can input text)
    buttonCreateTodoItem.setEnabled(false); // set button as disabled (user can't click)

    editTextInsertTask.addTextChangedListener(new TextWatcher() {
      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
      public void onTextChanged(CharSequence s, int start, int before, int count) { }
      public void afterTextChanged(Editable s) {
        // text did change
        String newText = editTextInsertTask.getText().toString();
        buttonCreateTodoItem.setEnabled(true);
      }
    });

    buttonCreateTodoItem.setOnClickListener(v -> {
      String userInputString = editTextInsertTask.getText().toString();
      if(userInputString.equals(""))
        return;
      holder.addNewInProgressItem(userInputString);
      editTextInsertTask.setText(""); // cleanup text in edit-text
      recyclerTodoItemsList.getAdapter().notifyDataSetChanged();
      adapter.notifyDataSetChanged();
    });

  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    EditText editTextInsertTask = findViewById(R.id.editTextInsertTask);
    String userInputString = editTextInsertTask.getText().toString();
    outState.putSerializable(BUNDLE_HOLDER, holder);
    outState.putString("userInput", userInputString);
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    holder = (TodoItemsHolderImpl) savedInstanceState.getSerializable(BUNDLE_HOLDER);
    String userInputString = savedInstanceState.getString("userInput");
    EditText editTextInsertTask = findViewById(R.id.editTextInsertTask);
    editTextInsertTask.setText(userInputString);
  }

}


/*

SPECS:

- the screen starts out empty (no items shown, edit-text input should be empty)
- every time the user taps the "add TODO item" button:
    * if the edit-text is empty (no input), nothing happens
    * if there is input:
        - a new TodoItem (checkbox not checked) will be created and added to the items list
        - the new TodoItem will be shown as the first item in the Recycler view
        - the edit-text input will be erased
- the "TodoItems" list is shown in the screen
  * every operation that creates/edits/deletes a TodoItem should immediately be shown in the UI
  * the order of the TodoItems in the UI is:
    - all IN-PROGRESS items are shown first. items are sorted by creation time,
      where the last-created item is the first item in the list
    - all DONE items are shown afterwards, no particular sort is needed (but try to think about what's the best UX for the user)
  * every item shows a checkbox and a description. you can decide to show other data as well (creation time, etc)
  * DONE items should show the checkbox as checked, and the description with a strike-through text
  * IN-PROGRESS items should show the checkbox as not checked, and the description text normal
  * upon click on the checkbox, flip the TodoItem's state (if was DONE will be IN-PROGRESS, and vice versa)
  * add a functionality to remove a TodoItem. either by a button, long-click or any other UX as you want
- when a screen rotation happens (user flips the screen):
  * the UI should still show the same list of TodoItems
  * the edit-text should store the same user-input (don't erase input upon screen change)

Remarks:
- you should use the `holder` field of the activity
- you will need to create a class extending from RecyclerView.Adapter and use it in this activity
- notice that you have the "row_todo_item.xml" file and you can use it in the adapter
- you should add tests to make sure your activity works as expected. take a look at file `MainActivityTest.java`



(optional, for advanced students:
- save the TodoItems list to file, so the list will still be in the same state even when app is killed and re-launched
)

*/
