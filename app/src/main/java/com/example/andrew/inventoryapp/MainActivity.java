package com.example.andrew.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBase db = new DataBase(this);
        ListView listView = (ListView) findViewById(R.id.listview);
        final Button add = (Button) findViewById(R.id.buttomitem);
        TextView emptylist = (TextView) findViewById(R.id.empty_view);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add.class);
                startActivity(intent);
            }
        });
        Cursor cursor = db.readData();
        InventoryAdapter adapter = new InventoryAdapter(this, cursor);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptylist);
    }

    public void clickonitem(int id) {
        Intent intent = new Intent(MainActivity.this, Detailsitem.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}