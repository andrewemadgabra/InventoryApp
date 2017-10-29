package com.example.andrew.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by andrew on 10/28/2017.
 */

public class saleview extends AppCompatActivity {
    private static final String TAG = "saleview";
    public static int idopens ;
    TextView QuantityTEXTs;
    TextView PriceTEXTs;
    public int quantity;
    int salemain = InventoryAdapter.salevalue;
    int sales;
    String nameproduct, blobsproduct, pricesproduct;
    DataBase db = new DataBase( this );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.saleview );
        TextView Namesale = (TextView) findViewById( R.id.productnamesale );
        PriceTEXTs = (TextView) findViewById( R.id.textpricesale );
        QuantityTEXTs = (TextView) findViewById( R.id.quantitysale );
        ImageView ImageVIEW = (ImageView) findViewById( R.id.imagessale );
        db.readData();
        idopens = InventoryAdapter.idsale;
        Cursor read = db.select( idopens );
        read.moveToFirst();
        nameproduct = read.getString( read.getColumnIndex( DataContract.DataEntry.COLUMN_NAME ) );
        Namesale.setText( nameproduct );
        quantity = read.getInt( read.getColumnIndex( DataContract.DataEntry.COLUMN_QUANTITY ) );
        QuantityTEXTs.setText( String.valueOf( quantity ) );
        blobsproduct = read.getString( read.getColumnIndex( DataContract.DataEntry.COLUMN_IMAGE ) );
        ImageVIEW.setImageBitmap( getUri( Uri.parse( blobsproduct ) ) );
        pricesproduct = read.getString( read.getColumnIndex( DataContract.DataEntry.COLUMN_PRICE ) );
        sales = Integer.parseInt( pricesproduct.toString() );
        int price = udpates( sales );
        PriceTEXTs.setText( String.valueOf( price ) );
    }

    public int udpates(int sale) {
        if (sale != 0 && sale > 0 && sale != 1) {
            sale--;

            db.updatesale( sale, idopens );
            return sale;
        }
        return sale;
    }

    public void finish(View view) {
        Intent intent = new Intent( saleview.this, MainActivity.class );
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

