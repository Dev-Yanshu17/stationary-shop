package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.model.UserOrder;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "UserOrder";
    private static final String TABLE_UserOrder = "UserOrder";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_MOBILE = "phoneNumber";
    private static final String COLUMN_ZIPCODE = "zipCode";
    private static final String COLUMN_ADDRESS = "address";
    //private static final String COLUMN_ADDRESSLINE2 = "addressLine2";
    private static final String COLUMN_LANDMARK = "landmark";
    private static final String COLUMN_PRODUCTIMAGE = "productImage";
    private static final String COLUMN_PRODUCTNAME = "productName";
    private static final String COLUMN_PRODUCTPRICE = "productPrice";
    private static final String COLUMN_PRODUCTQUANTITY = "productQuantity";
    private static final String COLUMN_PRODUCTTOTALPRICE = "productTotalPrice";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_UserOrder_TABLE = "CREATE  TABLE "
                + TABLE_UserOrder + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY,"
                + COLUMN_FIRSTNAME + " TEXT,"
                + COLUMN_LASTNAME + " TEXT,"
                + COLUMN_MOBILE + " TEXT,"
                + COLUMN_ZIPCODE + " TEXT,"
                + COLUMN_ADDRESS + " TEXT,"
               // + COLUMN_ADDRESSLINE2 + " TEXT,"
                + COLUMN_LANDMARK + " TEXT,"
                + COLUMN_PRODUCTIMAGE + " TEXT,"
                + COLUMN_PRODUCTNAME + " TEXT,"
                + COLUMN_PRODUCTPRICE + " TEXT,"
                + COLUMN_PRODUCTQUANTITY + " TEXT,"
                + COLUMN_PRODUCTTOTALPRICE + " TEXT" + ")";
        db.execSQL(CREATE_UserOrder_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UserOrder);
        onCreate(db);
    }

    public ArrayList<UserOrder> listUserOrder() {
        String sql = "select * from " + TABLE_UserOrder;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<UserOrder> storeUserOrder = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String firstname = cursor.getString(1);
                String lastname = cursor.getString(2);
                String mobile = cursor.getString(3);
                String zipCode = cursor.getString(4);
                String address = cursor.getString(5);
                //String addressLine2 = cursor.getString(6);
                String landmark = cursor.getString(6);
                String productImage = cursor.getString(7);
                String productName = cursor.getString(8);
                String productPrice = cursor.getString(9);
                String productQuantity = cursor.getString(10);
                String productTotalPrice = cursor.getString(11);
                storeUserOrder.add(new UserOrder(id, firstname, lastname, mobile, zipCode, address, landmark, productImage, productName, productPrice, productQuantity, productTotalPrice));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeUserOrder;
    }

    public void addUserOrder(UserOrder UserOrder) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, UserOrder.getFirstname());
        values.put(COLUMN_LASTNAME, UserOrder.getLastname());
        values.put(COLUMN_MOBILE, UserOrder.getMobile());
        values.put(COLUMN_ZIPCODE, UserOrder.getZipCode());
        values.put(COLUMN_ADDRESS, UserOrder.getAddress());
        //values.put(COLUMN_ADDRESSLINE2, UserOrder.getAddressLine2());
        values.put(COLUMN_LANDMARK, UserOrder.getLandmark());
        values.put(COLUMN_PRODUCTIMAGE, UserOrder.getProductImage());
        values.put(COLUMN_PRODUCTNAME, UserOrder.getProductName());
        values.put(COLUMN_PRODUCTPRICE, UserOrder.getProductPrice());
        values.put(COLUMN_PRODUCTQUANTITY, UserOrder.getProductQuantity());
        values.put(COLUMN_PRODUCTTOTALPRICE, UserOrder.getProductTotalPrice());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_UserOrder, null, values);
    }

    public void updateUserOrder(UserOrder UserOrder) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, UserOrder.getFirstname());
        values.put(COLUMN_LASTNAME, UserOrder.getLastname());
        values.put(COLUMN_MOBILE, UserOrder.getMobile());
        values.put(COLUMN_ZIPCODE, UserOrder.getZipCode());
        values.put(COLUMN_ADDRESS, UserOrder.getAddress());
        //values.put(COLUMN_ADDRESSLINE2, UserOrder.getAddressLine2());
        values.put(COLUMN_LANDMARK, UserOrder.getLandmark());
        values.put(COLUMN_PRODUCTIMAGE, UserOrder.getProductImage());
        values.put(COLUMN_PRODUCTNAME, UserOrder.getProductName());
        values.put(COLUMN_PRODUCTPRICE, UserOrder.getProductPrice());
        values.put(COLUMN_PRODUCTQUANTITY, UserOrder.getProductQuantity());
        values.put(COLUMN_PRODUCTTOTALPRICE, UserOrder.getProductTotalPrice());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_UserOrder, values, COLUMN_ID + " = ?", new String[]{String.valueOf(UserOrder.getId())});
    }

    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_UserOrder, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
