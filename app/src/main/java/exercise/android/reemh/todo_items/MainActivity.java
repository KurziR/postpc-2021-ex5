package exercise.android.reemh.todo_items;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

  public TodoItemsHolder holder = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    holder = new TodoItemsHolderImpl();

    RecyclerView recyclerTodoItemsList= findViewById(R.id.recyclerTodoItemsList);
    ToDoAdapter adapter = new ToDoAdapter(holder);
    recyclerTodoItemsList.setAdapter(adapter);
    recyclerTodoItemsList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    );
    if (holder == null) {
      holder = new TodoItemsHolderImpl();
    }
    // TODO: implement the specs as defined below
    //    (find all UI components, hook them up, connect everything you need)

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
      });
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
