package ca.nait.dmit2504lab02bryancaron;

public class Task {
    private int taskId;
    private String taskName;
    private String taskDate;
    private boolean taskComplete;
    private int itemId;

    public Task() {
    }

    public Task(int taskId, String taskName, boolean taskComplete, String taskDate, int itemId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskComplete = false;
        this.taskDate = taskDate;
        this.itemId = itemId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public boolean getTaskComplete() {
        return taskComplete;
    }

    public void setTaskComplete(boolean taskComplete) {
        this.taskComplete = taskComplete;
    }

    public int getTaskItemId() {
        return itemId;
    }

    public void setTaskItemId(int itemId) {
        this.itemId = itemId;
    }
}