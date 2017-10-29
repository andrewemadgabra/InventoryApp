package com.example.andrew.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by andrew on 10/25/2017.
 */

public class Add extends AppCompatActivity {
    private ImageView imageviews;
    DataBase dp = new DataBase( this );
    Item testimage = new Item();
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.additem );
        final EditText inputProduct = (EditText) findViewById( R.id.editname );
        final EditText inputquantity = (EditText) findViewById( R.id.quantityname );
        final EditText inputprice = (EditText) findViewById( R.id.priceitem );
        final EditText inputEmail = (EditText) findViewById( R.id.emails );
        imageviews = (ImageView) findViewById( R.id.image_view );
        final Button image = (Button) findViewById( R.id.addimage );
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};
        image.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder( Add.this );
                builder.setTitle( "Add Photo!" );
                builder.setItems( options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which].equals( "Choose from Gallery" )) {
                            Intent intent;

                            if (Build.VERSION.SDK_INT < 19) {
                                intent = new Intent( Intent.ACTION_GET_CONTENT );
                            } else {
                                intent = new Intent( Intent.ACTION_OPEN_DOCUMENT );
                                intent.addCategory( Intent.CATEGORY_OPENABLE );
                            }
                            check();
                            intent.setType( "image/*" );
                            startActivityForResult( Intent.createChooser( intent, "Select Picture" ), 1 );
                        } else if (options[which].equals( "Cancel" )) {
                            dialog.dismiss();
                        }
                    }
                } );
                builder.show();
            }
        } );
        Button SAVE = (Button) findViewById( R.id.saveinput );
        SAVE.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputProduct.getText().toString().trim();
                String price = inputprice.getText().toString().trim();
                String quantity = inputquantity.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String test;
                if (uri == null) {
                    Toast.makeText( getApplicationContext(), "Not Found Image", Toast.LENGTH_LONG ).show();
                } else {
                    test = testimage.setImage( uri.toString() );
                    if (!name.isEmpty() && !price.isEmpty() && !quantity.isEmpty() && !email.isEmpty()) {
                        if (dp.insert( name, price, quantity, email, test )) {
                            Toast.makeText( getApplicationContext(), "Done", Toast.LENGTH_LONG ).show();
                            Intent intent = new Intent( Add.this, MainActivity.class );
                            startActivity( intent );
                        } else {
                            Toast.makeText( getApplicationContext(), "Found Error in insert data", Toast.LENGTH_LONG ).show();
                        }
                    } else {
                        if (name.isEmpty()) {
                            Toast.makeText( getApplicationContext(), "Please input name", Toast.LENGTH_LONG ).show();
                        } else if (quantity.isEmpty()) {
                            Toast.makeText( getApplicationContext(), "Please input number quantity ", Toast.LENGTH_LONG ).show();
                        } else if (price.isEmpty()) {
                            Toast.makeText( getApplicationContext(), "Please input number price", Toast.LENGTH_LONG ).show();
                        } else if (email.isEmpty()) {
                            Toast.makeText( getApplicationContext(), "Please input email", Toast.LENGTH_LONG ).show();
                        }
                    }
                }
            }
        } );
    }

    private void check() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE )
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission( this, android.Manifest.permission.READ_EXTERNAL_STORAGE )
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions( this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        1 );
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data.getData() != null) {
            uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query( uri, projection, null, null, null );
            assert cursor != null;
            cursor.moveToFirst();
            cursor.close();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), uri );
                imageviews.setImageBitmap( bitmap );
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}