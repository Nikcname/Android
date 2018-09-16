package user.mobile.zeyneddin.zeyneddin.main.sqlite.databaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import user.mobile.zeyneddin.zeyneddin.main.Product;

/**
 *      Создан 2018,05,19 Nikcname
 */

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;          //версия базы
    private static final String DATABASE_NAME = "Baza001";  //имя базы
    private static final String TABLE_GOODS = "products";   //имя таблицы
    private static final String KEY_ID = "product_id";
    private static final String KEY_NAME = "product_name";
    private static final String KEY_AMOUNT = "product_amount";
    private static final String KEY_PRICE = "product_price";

    public DatabaseHandler(Context context){
        /*
            Переопределяем конструктор, чтоб передать имя и версию базы
         */
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
            Метод для создания новой таблицы в базе данных
            -! Имя таблицы !-
             ---------------------------------------------------------------------------
            |   product_id  |   product_name    |   product_amount |    product_price   |
            |---------------|-------------------|------------------|--------------------|
            |***************|*******************|******************|********************|
         */
        String CREATE_GOODS_TABLE = "CREATE TABLE " + TABLE_GOODS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_AMOUNT + " TEXT," + KEY_PRICE + " TEXT" +")";
        sqLiteDatabase.execSQL(CREATE_GOODS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /*
            метод для очистки конкретной таблицы из базы данных
         */
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GOODS);

        onCreate(sqLiteDatabase);
    }

    @Override
    public void addProduct(Product product) {
        /*
           Метод для добавления нового продукта в базу данных
         */
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getProductName());
        values.put(KEY_AMOUNT, product.getProductAmount());
        values.put(KEY_PRICE, product.getProductPrice());

        sqLiteDatabase.insert(TABLE_GOODS, null, values);
        sqLiteDatabase.close();
    }

    @Override
    public Product getProduct(int id) {
        /*
            Метод для получения данных о продукте по его ID
         */
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_GOODS, new String[] { KEY_ID,
                        KEY_NAME, KEY_AMOUNT, KEY_PRICE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Product product = new Product(Integer.parseInt(
                cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        return product;
    }

    @Override
    public List<Product> getAllProduct() {
        /*
            Метод для получения всех элементов из базы данных
         */
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_GOODS;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );

                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    @Override
    public int getProductCount() {
        String countQuery = "SELECT  * FROM " + TABLE_GOODS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    @Override
    public int updateProduct(Product product) {
        /*
            Метод для изменения продукта
         */
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getProductName());
        values.put(KEY_AMOUNT, product.getProductAmount());
        values.put(KEY_PRICE, product.getProductPrice());

        return sqLiteDatabase.update(TABLE_GOODS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(product.getProductId()) });
    }

    @Override
    public void deleteProduct(Product product) {
        /*
            Метод для удаления продукта из базы
         */
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_GOODS, KEY_ID + " = ?", new String[] { String.valueOf(product.getProductId()) });
        sqLiteDatabase.close();

    }

    @Override
    public void deleteAll() {
        /*
            Метод для удаления всех данных из таблицы
         */
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_GOODS, null, null);
        sqLiteDatabase.close();

    }

}


