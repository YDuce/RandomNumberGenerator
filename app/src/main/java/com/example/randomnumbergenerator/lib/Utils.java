package com.example.randomnumbergenerator.lib;

import android.content.Context;
import android.content.DialogInterface;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.randomnumbergenerator.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

public class Utils {
    /**
     * Shows an Android (nicer) equivalent to JOptionPane
     *
     * @param strTitle Title of the Dialog box
     * @param strMsg   Message (body) of the Dialog box
     */
    public static void showInfoDialog(Context context, String strTitle, String strMsg) {
        // create the listener for the dialog
        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        // Create the AlertDialog.Builder object
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // Use the AlertDialog's Builder Class methods to set the title, icon, message, et al.
        // These could all be chained as one long statement, if desired
        alertDialogBuilder.setTitle(strTitle);
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
        alertDialogBuilder.setMessage(strMsg);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setNeutralButton(context.getString(android.R.string.ok), listener);

        // Create and Show the Dialog
        alertDialogBuilder.show();
    }

    public static ArrayList<Integer> getNumberListFromJSONString(String gson) {
        Type ArrayListIntegerType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        return new Gson().fromJson(gson, ArrayListIntegerType);
    }

    public static String getJSONStringFromNumberList(ArrayList<Integer> numberList) {
        return new Gson().toJson(numberList);
    }
}
