package com.university.hafsa.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.university.hafsa.R;

public class ActivityMother extends AppCompatActivity {

    public static void exitTheApp(Activity activity, Context context) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
        dialog.setTitle(R.string.exit_dialog_title);
        dialog.setIcon(R.drawable.ic_exit);
        dialog.setMessage(R.string.exit_dialog_msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.yes, (dialogInterface, i) -> activity.finish());
        dialog.setNegativeButton(R.string.no, (dialogInterface, i) -> Toast.makeText(context, activity.getString(R.string.cancel_exit), Toast.LENGTH_LONG).show());
        dialog.show();

    }
    public void sharTheApp() {
        String url = getString(R.string.app_link);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plane");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_dialog_msg));
        intent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(intent, getString(R.string.share_dialog_title)));
    }
}
