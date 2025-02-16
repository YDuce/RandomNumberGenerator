package com.example.randomnumbergenerator.activities;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.randomnumbergenerator.R;
import com.example.randomnumbergenerator.model.RandomNumber;
import com.example.randomnumbergenerator.lib.Utils;
import com.example.randomnumbergenerator.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String NUMBER_HISTORY = "number_history_key";

    private ActivityMainBinding binding;
    private RandomNumber mRandomNumber;
    private ArrayList<Integer> mNumberHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarMain.toolbar);

        binding.fab.setOnClickListener(view -> handleFabClick());
        mRandomNumber = new RandomNumber();
        initializeHistoryList(savedInstanceState, NUMBER_HISTORY);
    }

    private void handleFabClick() {
        String fromText = binding.contentMain.etFrom.getText().toString();
        String toText = binding.contentMain.etTo.getText().toString();

        if (fromText.isEmpty() || toText.isEmpty()) {
            if (fromText.isEmpty())
                binding.contentMain.etFrom.setError("Please enter a number");
            if (toText.isEmpty())
                binding.contentMain.etTo.setError("Please enter a number");
            Toast.makeText(this, "Both fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            try {
                int from = Integer.parseInt(fromText);
                int to = Integer.parseInt(toText);
                mRandomNumber.setFromTo(from, to);
                int randomNumber = mRandomNumber.getCurrentRandomNumber();
                binding.contentMain.textViewNumber.setText(String.valueOf(randomNumber));
                mNumberHistory.add(randomNumber);
            } catch (NumberFormatException e) {
                if (Long.parseLong(fromText) > Integer.MAX_VALUE || Long.parseLong(fromText) < Integer.MIN_VALUE)
                    binding.contentMain.etFrom.setError("Number must be valid int");
                if (Long.parseLong(toText) > Integer.MAX_VALUE || Long.parseLong(toText) < Integer.MIN_VALUE)
                    binding.contentMain.etTo.setError("Number must be a valid int");
                Toast.makeText(this, "Invalid range: Number must be a valid int", Toast.LENGTH_SHORT).show();
            } catch (RuntimeException e) {
                binding.contentMain.etFrom.setError("From must be less than To");
                binding.contentMain.etTo.setError("To must be greater than From");
                Toast.makeText(this, "Invalid range: To must be greater than From", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initializeHistoryList(Bundle savedInstanceState, String NUMBER_HISTORY) {
        if (savedInstanceState != null) {
            mNumberHistory = savedInstanceState.getIntegerArrayList(NUMBER_HISTORY);
        } else {
            String history = getDefaultSharedPreferences(this).getString(NUMBER_HISTORY, null);
            mNumberHistory = history == null ? new ArrayList<>() : Utils.getNumberListFromJSONString(history);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(NUMBER_HISTORY, mNumberHistory);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        String historyJson = Utils.getJSONStringFromNumberList(mNumberHistory);
        editor.putString(NUMBER_HISTORY, historyJson);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_show_history) {
            Utils.showInfoDialog(MainActivity.this, "History", "Numbers Generated: " + mNumberHistory.toString());
            return true;
        } else if (id == R.id.action_clear_history) {
            mNumberHistory.clear();
            Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_about) {
            Toast.makeText(this, getString(R.string.about_text), Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}