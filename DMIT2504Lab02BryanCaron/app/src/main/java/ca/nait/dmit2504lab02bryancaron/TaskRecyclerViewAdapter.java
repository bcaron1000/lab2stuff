package ca.nait.dmit2504lab02bryancaron;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TaskRecyclerViewAdapter extends  RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder>{

    private Context context;
    private static List<Task> tasks;

    // Step 4: Create parameterized constructor
    public TaskRecyclerViewAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    public TaskRecyclerViewAdapter(Context context) {
        this.context = context;
    }
    public void addTask(Task newTask){
        tasks.add(newTask);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View taskView = inflater.inflate(R.layout.simple_spinner_item, parent, false);
        TaskViewHolder viewHolder = new TaskViewHolder(taskView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = tasks.get(position);
        holder.taskIdTextView.setText("" + currentTask.getTaskId());
        holder.taskNameTextView.setText(currentTask.getTaskName());
        holder.taskDateTextView.setText(currentTask.getTaskDate());
        if (currentTask.getTaskComplete() == false){
            holder.taskCompletedSwitch.setChecked(false);
        }else{
            holder.taskCompletedSwitch.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    // Step 1: Create a view holder class that defines the views for a single item
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        //For each item in your list item, define a view for them
        public TextView taskIdTextView;
        public TextView taskNameTextView;
        public Switch taskCompletedSwitch;
        public TextView taskDateTextView;
        public Button deleteButton;
        public Button editButton;
        public Button archiveButton;



        public TaskViewHolder(@NonNull View taskView) {
            super(taskView);

            taskIdTextView = taskView.findViewById(R.id.task_id);
            taskNameTextView = taskView.findViewById(R.id.task_name);
            taskCompletedSwitch = taskView.findViewById(R.id.task_completed);
            taskDateTextView = taskView.findViewById(R.id.task_date);
            editButton = taskView.findViewById(R.id.task_edit_button);
            deleteButton = taskView.findViewById(R.id.task_delete_button);
            archiveButton = taskView.findViewById(R.id.task_archive_button);


            deleteButton.setOnClickListener(view -> {
                int position = getAdapterPosition();

                Task currentTask = tasks.get(position);
                Intent deleteTaskIntent = new Intent();
                deleteTaskIntent.setAction(MenuItemTodoItem.INTENT_ACTION_TASK_DELETE);
                deleteTaskIntent.putExtra(MenuItemTodoItem.EXTRA_TASK_TASK_ID, currentTask.getTaskId());
                itemView.getContext().sendBroadcast(deleteTaskIntent);

            });

            editButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                Task currentTask = tasks.get(position);
                Intent editTaskIntent = new Intent();
                editTaskIntent.setAction(MenuItemTodoItem.INTENT_ACTION_TASK_EDIT);
                editTaskIntent.putExtra(MenuItemTodoItem.EXTRA_TASK_TASK_ID, currentTask.getTaskId());
                itemView.getContext().sendBroadcast(editTaskIntent);
            });
        }
    }
}

