package ca.nait.dmit2504lab02bryancaron;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract() {

    }
    public static class ItemEntry implements BaseColumns{
        public static final String TABLE_NAME = "item_table";
        public static final String COLUMN_NAME_ITEM_NAME = "item_name";
    }
    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task_table";
        public static final String COLUMN_NAME_TASK_COMPLETED = "task_complete";
        public static final String COLUMN_NAME_TASK_NAME = "task_name";
        public static final String COLUMN_NAME_TASK_DATE = "task_date";
        public static final String COLUMN_NAME_TASK_ITEM_ID = "item_id";

    }





}
