package com.example.andrew.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;


/**
 * Created by andrew on 10/25/2017.
 */

public class InventoryAdapter extends CursorAdapter {
    private static final String TAG = "InventoryAdapter";
    MainActivity activity;
    public static int idsale;
    public static int salevalue;

    public InventoryAdapter(MainActivity context, Cursor c) {
        super( context, c, 0 );
        this.activity = context;
    }

    public InventoryAdapter(Context context, Cursor c, MainActivity activity) {
        super( context, c );
        this.activity = activity;
    }

    public InventoryAdapter(Context context, Cursor c, boolean autoRequery, MainActivity activity) {
        super( context, c, autoRequery );
        this.activity = activity;
    }

    public InventoryAdapter(Context context, Cursor c, int flags, MainActivity activity) {
        super( context, c, flags );
        this.activity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from( context ).inflate( R.layout.list, parent, false );
    }


    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView NameTEXT = (TextView) view.findViewById( R.id.productname );
        TextView PriceTEXT = (TextView) view.findViewById( R.id.textprice );
        TextView QuantityTEXT = (TextView) view.findViewById( R.id.quantity );
        ImageView ImageVIEW = (ImageView) view.findViewById( R.id.image );
        String name = cursor.getString( cursor.getColumnIndex( DataContract.DataEntry.COLUMN_NAME ) );
        NameTEXT.setText( name );
        String blob = cursor.getString( cursor.getColumnIndex( DataContract.DataEntry.COLUMN_IMAGE ) );
        ImageVIEW.setImageBitmap( getUri( Uri.parse( blob ) ) );
        String price = cursor.getString( cursor.getColumnIndex( DataContract.DataEntry.COLUMN_PRICE ) );
        PriceTEXT.setText( price );
        salevalue = Integer.parseInt( PriceTEXT.getText().toString() );
        String quantity = cursor.getString( cursor.getColumnIndex( DataContract.DataEntry.COLUMN_QUANTITY ) );
        QuantityTEXT.setText( String.valueOf( quantity ) );
        final int test = (int) cursor.getLong( cursor.getColumnIndex( DataContract.DataEntry._ID ) );
        Button sales = (Button) view.findViewById( R.id.saleopen );
        sales.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.clicksale( test );
                idsale = test;
            }
        } );

    }

    private Bitmap getUri(Uri uri) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = activity.getContentResolver().openFileDescriptor( uri, "r" );
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
