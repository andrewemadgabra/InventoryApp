package com.example.andrew.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by andrew on 10/25/2017.
 */

public class Detailsitem extends AppCompatActivity {
    private static final String TAG = "Detailsitem";
    public int quantity;
    DataBase db = new DataBase( this );
    public static int idopen;
    TextView QuantityTEXT;
    TextView updatequantity;
    TextView PriceTEXT;
    public int negative, sum;
    String name, email, blob, price, message;
    String PhoneEdit = "0120845";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.details );
        final CharSequence[] options = {"YES", "NO"};
        TextView NameTEXT = (TextView) findViewById( R.id.productnamedetails );
        PriceTEXT = (TextView) findViewById( R.id.textpricedetails );
        QuantityTEXT = (TextView) findViewById( R.id.quantitydetails );
        updatequantity = (TextView) findViewById( R.id.updateqantity );
        ImageView ImageVIEW = (ImageView) findViewById( R.id.imagesdetails );
        idopen = MainActivity.positions;
        db.readData();
        Cursor read = db.select( idopen );
        read.moveToFirst();
        name = read.getString( read.getColumnIndex( DataContract.DataEntry.COLUMN_NAME ) );
        NameTEXT.setText( name );
        quantity = read.getInt( read.getColumnIndex( DataContract.DataEntry.COLUMN_QUANTITY ) );
        QuantityTEXT.setText( String.valueOf( quantity ) );
        blob = read.getString( read.getColumnIndex( DataContract.DataEntry.COLUMN_IMAGE ) );
        ImageVIEW.setImageBitmap( getUri( Uri.parse( blob ) ) );
        price = read.getString( read.getColumnIndex( DataContract.DataEntry.COLUMN_PRICE ) );
        email = read.getString( read.getColumnIndex( DataContract.DataEntry.COLUMN_EMAIL ) );
        PriceTEXT.setText( price );
        Button increase = (Button) findViewById( R.id.increase );
        increase.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                sum = Integer.parseInt( QuantityTEXT.getText().toString() );
                sum = sum + 1;
                QuantityTEXT.setText( Integer.toString( sum ) );
                db.update( sum, idopen );
            }
        } );
        Button decrease = (Button) findViewById( R.id.decrease );
        decrease.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                negative = Integer.parseInt( QuantityTEXT.getText().toString() );
                if (negative > 0) {
                    negative = negative - 1;
                    QuantityTEXT.setText( Integer.toString( negative ) );
                    db.update( negative, idopen );
                } else {
                    Toast.makeText( getApplicationContext(), "The quantity equal zero", Toast.LENGTH_LONG ).show();
                }
            }
        } );
        Button orders = (Button) findViewById( R.id.order );
        orders.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( Intent.ACTION_DIAL );
                intent.setData( Uri.parse( "tel:" + PhoneEdit.toString().trim() ) );
                startActivity( intent );
            }
        } );
        message = "name product  : " + name + " quantity : " + quantity + "price : " + price;
        Button emailsend = (Button) findViewById( R.id.emailsend );
        emailsend.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent emails = new Intent( android.content.Intent.ACTION_SEND );
                emails.setType( "vnd.android.cursor.item/email" );
                emails.putExtra( Intent.EXTRA_EMAIL, email );
                emails.putExtra( Intent.EXTRA_SUBJECT, "Order Item" );
                emails.putExtra( Intent.EXTRA_TEXT, message );
                startActivity( Intent.createChooser( emails, "Send mail" ) );
            }
        } );
        Button deletes = (Button) findViewById( R.id.delete );
        deletes.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder( Detailsitem.this );
                builder.setTitle( "Delete Item" );
                builder.setItems( options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which].equals( "YES" )) {
                            db.deleteitem( idopen );
                            db.readData();
                            Intent intent = new Intent( Detailsitem.this, MainActivity.class );
                            startActivity( intent );
                        } else if (options[which].equals( "NO" )) {
                            dialog.dismiss();
                        }
                    }
                } );
                builder.show();
            }
        } );
    }

    public void update(View view) {
        Intent intent = new Intent( Detailsitem.this, MainActivity.class );
        startActivity( intent );
    }

    private Bitmap getUri(Uri uri) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = this.getContentResolver().openFileDescriptor( uri, "r" );
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor( fileDescriptor );
            parcelFileDescriptor.close();
            return image;
        } catch (Exception e) {
            Log.e( TAG, "Failed to load image.", e );
            return null;
        } finally {
            try {
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e( TAG, "Error closing ParcelFile Descriptor" );
            }
        }
    }
}

