package ca.nait.dmit2504lab02bryancaron;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String TAG = "DatabaseHelper";
    public  static final  int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "SQLiteItem.db";

    private static final String SQL_CREATE_ITEM_ENTRIES = "CREATE TABLE " +
            DatabaseContract.ItemEntry.TABLE_NAME + "("
             + DatabaseContract.ItemEntry._ID + " INTEGER PRIMARY KEY, "
            + DatabaseContract.ItemEntry.COLUMN_NAME_ITEM_NAME + " TEXT " +")";

    private static final String SQL_CREATE_TASK_ENTRIES =
            "CREATE TABLE " + DatabaseContract.TaskEntry.TABLE_NAME + "("
                    + DatabaseContract.TaskEntry._ID + " INTEGER PRIMARY KEY, "
                    + DatabaseContract.TaskEntry.COLUMN_NAME_TASK_NAME+ " TEXT, "
                    + DatabaseContract.TaskEntry.COLUMN_NAME_TASK_DATE + " TEXT,"
                    + DatabaseContract.TaskEntry.COLUMN_NAME_TASK_COMPLETED + " BOOLEAN, "
                    + DatabaseContract.TaskEntry.COLUMN_NAME_TASK_ITEM_ID + " INTEGER,"
                    + " FOREIGN KEY (" + DatabaseContract.TaskEntry.COLUMN_NAME_TASK_ITEM_ID + ")"
                    + " REFERENCES " + DatabaseContract.ItemEntry.TABLE_NAME + "(" + DatabaseContract.ItemEntry._ID + ")" + ")";

    private static final String SQL_DELETE_ITEM_ENTRIES = "DROP TABLE IF EXISTS "+
            DatabaseContract.ItemEntry.TABLE_NAME;

    private static final String SQL_DELETE_TASK_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.TaskEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ITEM_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_TASK_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(SQL_DELETE_ITEM_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_TASK_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public long addItem(Item newItem){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ItemEntry.COLUMN_NAME_ITEM_NAME, newItem.getItemName());
        return db.insert(DatabaseContract.ItemEntry.TABLE_NAME, null, values);
    }
    public long addTask(Task newTask){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TaskEntry.COLUMN_NAME_TASK_NAME, newTask.getTaskName());
        values.put(DatabaseContract.TaskEntry.COLUMN_NAME_TASK_DATE, newTask.getTaskDate());
        values.put(DatabaseContract.TaskEntry.COLUMN_NAME_TASK_COMPLETED, newTask.getTaskComplete());
        values.put(DatabaseContract.TaskEntry.COLUMN_NAME_TASK_ITEM_ID, newTask.getTaskItemId());
        return db.insert(DatabaseContract.TaskEntry.TABLE_NAME, null, values);
    }
    public Cursor getItemCursor() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                DatabaseContract.ItemEntry._ID,
                DatabaseContract.ItemEntry.COLUMN_NAME_ITEM_NAME
        };
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = DatabaseContract.ItemEntry.COLUMN_NAME_ITEM_NAME + " ASC";

        return db.query(
                DatabaseContract.ItemEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );
    }
    public Cursor getTaskCursor(int itemID) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                DatabaseContract.TaskEntry._ID,
                DatabaseContract.TaskEntry.COLUMN_NAME_TASK_NAME,
                DatabaseContract.TaskEntry.COLUMN_NAME_TASK_DATE,
                DatabaseContract.TaskEntry.COLUMN_NAME_TASK_COMPLETED,
                DatabaseContract.TaskEntry.COLUMN_NAME_TASK_ITEM_ID

        };
        String selection = "item_id = ?";
        String[] selectionArgs = {toString().valueOf(itemID)};
        String groupBy = null;
        String having = null;
        String orderBy = DatabaseContract.TaskEntry.COLUMN_NAME_TASK_NAME + " ASC";

        return db.query(
                DatabaseContract.TaskEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );
    }
    public List<Item> getItemList(){
        Cursor queryResultCursor = getItemCursor();
        List<Item> items = new ArrayList<>();
        while(queryResultCursor.moveToNext()){
            Item currentItem = new Item();
            currentItem.setItemID(queryResultCursor.getInt(0));
            currentItem.setItemName(queryResultCursor.getString(1));

//            Item currentItem = mapCursorToItem(queryResultCursor);
            items.add(currentItem);

        }
        return items;
    };
    public List<Task> getTaskList(int itemID){
        Cursor queryResultCursor = getTaskCursor(itemID);
        List<Task> tasks = new ArrayList<>();
        while(queryResultCursor.moveToNext()){
            Task currentTask = new Task();
            currentTask.setTaskId(queryResultCursor.getInt(0));
            currentTask.setTaskName(queryResultCursor.getString(1));
            currentTask.setTaskDate(queryResultCursor.getString(2));
            currentTask.setTaskComplete(queryResultCursor.getInt(3) == 1);
            currentTask.setTaskItemId(queryResultCursor.getInt(4));

            tasks.add(currentTask);

        }
        return tasks;
    };
    private Item mapCursorToItem(Cursor queryResultCursor) {
        Item currentItem = new Item();
        int columnIndexItemId = queryResultCursor.getColumnIndexOrThrow(DatabaseContract.ItemEntry._ID);
        currentItem.setItemID(queryResultCursor.getInt(columnIndexItemId));
        int columnIndexItemName = queryResultCursor.getColumnIndexOrThrow(DatabaseContract.ItemEntry.COLUMN_NAME_ITEM_NAME);
        currentItem.setItemName(queryResultCursor.getString(columnIndexItemName));

        return currentItem;
    }
    public Cursor getTasksByItemId(int itemId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                DatabaseContract.TaskEntry._ID,
                DatabaseContract.TaskEntry.COLUMN_NAME_TASK_COMPLETED,
                DatabaseContract.TaskEntry.COLUMN_NAME_TASK_NAME,
                DatabaseContract.TaskEntry.COLUMN_NAME_TASK_DATE,
                DatabaseContract.TaskEntry.COLUMN_NAME_TASK_ITEM_ID
        };
        String selection = DatabaseContract.TaskEntry.COLUMN_NAME_TASK_ITEM_ID + " = ?";
        String[] selectionArgs = {String.valueOf(itemId)};
        String groupBy = null;
        String having = null;
        String orderBy = DatabaseContract.TaskEntry.COLUMN_NAME_TASK_NAME + " ASC";


        return db.query(
                DatabaseContract.TaskEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );
    }
    public Task findOneTaskById(int taskId) {
        Task existingTask = null;

        Cursor queryResultCursor = findOneTaskCursorById(taskId);
        if (queryResultCursor.moveToNext()) {
            existingTask = mapCursorToTask(queryResultCursor);
        }

        return existingTask;
    }
    private Task mapCursorToTask(Cursor queryResultCursor) {
        Task currentTask = new Task();

//        currentCategory.setCategoryId(queryResultCursor.getInt(0));
//        currentCategory.setCategoryName(queryResultCursor.getString(1));

        int columnIndexCategoryId = queryResultCursor.getColumnIndexOrThrow(DatabaseContract.TaskEntry._ID);
        currentTask.setTaskId(queryResultCursor.getInt(columnIndexCategoryId));
        int columnIndexTaskName = queryResultCursor.getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_NAME_TASK_NAME);
        currentTask.setTaskName(queryResultCursor.getString(columnIndexTaskName));

        return currentTask;
    }
    public Cursor findOneTaskCursorById(int itemId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                DatabaseContract.ItemEntry._ID,
                DatabaseContract.ItemEntry.COLUMN_NAME_ITEM_NAME
        };
        String selection = DatabaseContract.ItemEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(itemId)};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        return db.query(
                DatabaseContract.ItemEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );
    }
}
