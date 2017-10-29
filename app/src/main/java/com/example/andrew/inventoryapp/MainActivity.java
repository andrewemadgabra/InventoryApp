package com.example.andrew.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static int positions;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ListView listView = (ListView) findViewById( R.id.listview );
        final Button add = (Button) findViewById( R.id.buttomitem );
        TextView emptylist = (TextView) findViewById( R.id.empty_view );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Add.class );
                startActivity( intent );
            }
        } );
        final DataBase db = new DataBase( this );
        final Cursor cursor = db.readData();
        InventoryAdapter adapter = new InventoryAdapter( this, cursor );
        listView.setAdapter( adapter );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                positions = (int) id;
                db.readData();
                Intent intent = new Intent( MainActivity.this, Detailsitem.class );
                intent.putExtra("positions", positions );
                finish();
                startActivity( intent );
            }
        } );
        listView.setEmptyView( emptylist );
    }

    public void clicksale(int id) {

    }
}