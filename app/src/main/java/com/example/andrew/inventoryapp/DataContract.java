package com.example.andrew.inventoryapp;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by andrew on 10/25/2017.
 */

public class DataContract {
    public DataContract() {
    }

    public final class DataEntry implements BaseColumns {
        public static final String Table_name = "Item";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_EMAIL = "email";
    }
}
