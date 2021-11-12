package ca.nait.dmit2504lab02bryancaron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.List;

import ca.nait.dmit2504lab02bryancaron.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences prefs;
    View mainView;
    private ActivityMainBinding binding;
    private final String KEY_RESPONSE_CODE = "ca.nait.dmit2504lab02bryancaron.RESPONSE_CODE";

    private Handler postDataHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
                //super.handleMessage(msg);

                Bundle bundle = msg.getData();
                int responseCode = bundle.getInt(KEY_RESPONSE_CODE);
                    if (responseCode == HttpURLConnection.HTTP_OK){
                        Toast.makeText(MainActivity.this, "Send Message was successful", Toast.LENGTH_SHORT).show();
                        binding.activityMainEditText.setText("");
                    }else{
                        String message = String.format("Error sending with code %d", responseCode);
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create the view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        rebindRecyclerView();

    }
    private void rebindRecyclerView() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Item> items = dbHelper.getItemList();
        ItemRecyclerViewAdapter recyclerViewAdapter = new ItemRecyclerViewAdapter(this, items);
        binding.activityMainItemRecyclerview.setAdapter(recyclerViewAdapter);
        binding.activityMainItemRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
     public void onAddButtonClick(View view){
            String itemName = binding.activityMainEditText.getText().toString();
            if (itemName.isEmpty()){
                Toast.makeText(this, "Item Name is Required", Toast.LENGTH_SHORT).show();
            }else{
                Item newItem = new Item();
                newItem.setItemName(itemName);
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                long itemId = dbHelper.addItem(newItem);
                String message = String.format("Save Successful With ID %s", itemId);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                binding.activityMainEditText.setText("");
                rebindRecyclerView();
            }
     }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if( key.equals("bg_color")){
            String bgColor = prefs.getString(key, "#cecece");
            mainView.setBackgroundColor(Color.parseColor(bgColor));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

//    public void onArchiveClick(View view) {
//            Map<String, String> requestFormDataMap = new HashMap<>();
//            requestFormDataMap.put("LIST_TITLE",binding.activityMainEditText.getText().toString());
//            requestFormDataMap.put("CONTENT","");
//            requestFormDataMap.put("COMPLETED_FLAG","");
//            requestFormDataMap.put("ALIAS","");
//            requestFormDataMap.put("PASSWORD","");
//            requestFormDataMap.put("CREATED_DATE","");
//
//            NetworkAPI networkAPI = new NetworkAPI();
//            final String urlSting = "https://capstone1.app.dmitcapstone.ca/api/Lab02Servlet?ALIAS=username&PASSWORD=password";
//
//        CompletableFuture<Void> postDataFuture = CompletableFuture.runAsync(() -> {
//            int responseCode = networkAPI.postFormData(urlSting,requestFormDataMap);
//
//            Message msg = postDataHandler.obtainMessage();
//            Bundle bundle = new Bundle();
//            bundle.putInt(KEY_RESPONSE_CODE, responseCode);
//            msg.setData(bundle);
//            postDataHandler.sendMessage(msg);
//        });
//
//
//
//    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){


            case R.id.menu_item_todo_items: {
                Intent intent = new Intent(this, MenuItemTodoItem.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_item_user_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;  }
        }

          return true;
    }
}