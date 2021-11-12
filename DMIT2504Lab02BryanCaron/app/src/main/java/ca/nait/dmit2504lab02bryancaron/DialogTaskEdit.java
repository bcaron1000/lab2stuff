package ca.nait.dmit2504lab02bryancaron;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DialogTaskEdit extends DialogFragment {

    private Task editTask;
    private MenuItemTodoItem menuItemTodoItem;

    public DialogTaskEdit(final Task editTask, final MenuItemTodoItem menuItemTodoItem) {
        this.editTask = editTask;
        this.menuItemTodoItem = menuItemTodoItem;
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
////        return super.onCreateDialog(savedInstanceState);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
//        View dialogView = layoutInflater.inflate(R.layout.dialog_category_edit, null);
//
//        EditText categoryNameEditText = dialogView.findViewById(R.id.dialog_category_edit_categoryname_edittext);
//        categoryNameEditText.setText(editCategory.getCategoryName());
//        categoryNameEditText.requestFocus();
//
//        Button cancelButton = dialogView.findViewById(R.id.dialog_category_edit_cancel_button);
//        Button saveButton = dialogView.findViewById(R.id.dialog_category_edit_save_button);
//
//        cancelButton.setOnClickListener(view -> {
//            Toast.makeText(getActivity(),"Cancel edit", Toast.LENGTH_SHORT).show();
//            dismiss();
//        });
//    }

}