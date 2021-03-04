package com.example.customadapterdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    UserListAdapter adapter;
    ListView listView;
    Button sortByNameButton, sortByPhoneButton, sortBySexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        sortByNameButton = findViewById(R.id.sortByName);
        sortByPhoneButton = findViewById(R.id.sortByPhone);
        sortBySexButton = findViewById(R.id.sortBySex);

        final ArrayList<User> users;

        InputStream stream = null;
        try {
            stream = getAssets().open("Users");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        User[] text = gson.fromJson(new InputStreamReader(stream), User[].class);

        users = new ArrayList<>(Arrays.asList(text));

        adapter = new UserListAdapter(this, users);

        listView.setAdapter(adapter);

        sortByNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(users, compareByName);
                adapter.notifyDataSetChanged();
            }
        });

        sortByPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(users, compareByPhone);
                adapter.notifyDataSetChanged();
            }
        });

        sortBySexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(users, compareBySex);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public Comparator<User> compareByName = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.name.compareTo(o2.name);
        }
    };

    public Comparator<User> compareByPhone = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.phoneNumber.compareTo(o2.phoneNumber);
        }
    };

    public Comparator<User> compareBySex = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.sex.compareTo(o2.sex);
        }
    };
}
