package ca.nait.dmit2504lab02bryancaron;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.nait.dmit2504lab02bryancaron.databinding.ActivityMenuTodoItemsBinding;

public class MenuItemTodoItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ActivityMenuTodoItemsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_todo_items);

        binding = ActivityMenuTodoItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor categoryQueryResultCursor = dbHelper.getItemCursor();
        String[] fromColumnNames = {DatabaseContract.ItemEntry.COLUMN_NAME_ITEM_NAME};
        int[] toViewIds = {android.R.id.text1};
        int flags = 0;
        SimpleCursorAdapter taskAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                categoryQueryResultCursor,
                fromColumnNames,
                toViewIds,
                flags);
        binding.activityManageTodoSpinner.setAdapter(taskAdapter);

        rebindRecyclerView();
        binding.activityManageTodoSpinner.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        rebindRecyclerView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onTaskAddButtonClick(View view) {
        String taskName = binding.activityTodoEditText.getText().toString();
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String taskDate = df.format(c);
        int taskItemId = binding.activityManageTodoSpinner.getSelectedItemPosition() + 1;
        if (taskName.isEmpty()){
            Toast.makeText(this, "Task Name is Required", Toast.LENGTH_SHORT).show();
        }else {
            Task newTask = new Task();
            newTask.setTaskName(taskName);
            newTask.setTaskDate(taskDate);
            newTask.setTaskComplete(false);
            newTask.setTaskItemId(taskItemId);
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            long taskId = dbHelper.addTask(newTask);
            String message = String.format("Save Successful With ID %s", taskId);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            binding.activityTodoEditText.setText("");
            rebindRecyclerView();
        }
    }
    private void rebindRecyclerView() {
        DatabaseHelper dbHelper = new DatabaseHelper(MenuItemTodoItem.this);
        int taskItemId = binding.activityManageTodoSpinner.getSelectedItemPosition() + 1;
        List<Task> tasks = dbHelper.getTaskList(taskItemId);
        TaskRecyclerViewAdapter recyclerViewAdapter = new TaskRecyclerViewAdapter(MenuItemTodoItem.this, tasks);
        binding.activityTodoRecyclerview.setAdapter(recyclerViewAdapter);
        binding.activityTodoRecyclerview.setLayoutManager(new LinearLayoutManager(MenuItemTodoItem.this));
    }
    public static final String INTENT_ACTION_TASK_DELETE = "ca.nait.dmit2504lab02bryancaron.TASK_DELETE";
    public static final String INTENT_ACTION_TASK_EDIT = "ca.nait.dmit2504lab02bryancaron.TASK_EDIT";
    public static final String EXTRA_TASK_TASK_ID = "ca.nait.dmit2504lab02bryancaron.TASK_ID";

    class EditTaskBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (intent.getAction().equals(INTENT_ACTION_TASK_EDIT)) {
                int taskId = intent.getIntExtra(EXTRA_TASK_TASK_ID, 0);
                if (taskId > 0) {

                    //Toast.makeText(context, "Edit category received", Toast.LENGTH_SHORT).show();
                    DatabaseHelper dbHelper = new DatabaseHelper(MenuItemTodoItem.this);
                    Task editTask = dbHelper.findOneTaskById(taskId);
                    DialogTaskEdit editDialog = new DialogTaskEdit(editTask, MenuItemTodoItem.this);
                    editDialog.show(getSupportFragmentManager(), "MenuItemTodoItem");
                }
            }
        }
    }






}