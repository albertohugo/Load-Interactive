package hugo.alberto.clustermap.Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hugo.alberto.clustermap.Model.EstablishmentModel;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "establishments_db";
    private final static String TABLE_NAME = "establishments";
    private final static int DATABASE_VERSION = 1;
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String DESCRIPTION = "description";
    private static final String LOCALNAME = "localname";
    private static final String LAT = "lat";
    private static final String LONGI = "longi";


    private List<EstablishmentModel> lista;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + NAME + " TEXT NOT NULL," + PHONE + " TEXT," + DESCRIPTION + " TEXT ,"
                + LOCALNAME + " TEXT," + LAT + " TEXT," + LONGI + " TEXT" + ");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS" + TABLE_NAME);
        onCreate(db);

    }

    //TODO
    public void addEstablishments(EstablishmentModel establishmentsData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, establishmentsData.getName());
        values.put(PHONE, establishmentsData.getPhone());
        values.put(DESCRIPTION, establishmentsData.getDescription());
        values.put(LOCALNAME, establishmentsData.getLocalname());
        values.put(LAT, establishmentsData.getLat());
        values.put(LONGI, establishmentsData.getLon());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

   public List<EstablishmentModel> getAllEstablishment() {
        List<EstablishmentModel> EstablishmentList = new ArrayList<EstablishmentModel>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " GROUP BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                EstablishmentModel data = new EstablishmentModel();
                data.setName(cursor.getString(0));
                data.setPhone(cursor.getString(1));
                data.setDescription(cursor.getString(2));
                data.setLocalname(cursor.getString(3));
                data.setLat(cursor.getString(4));
                data.setLon(cursor.getString(5));

                EstablishmentList.add(data);

            } while (cursor.moveToNext());
        }

        return EstablishmentList;
    }

    /* public List<ListDataModel> getUsersBooks(String user_id) {
        List<ListDataModel> userList = new ArrayList<ListDataModel>();
        String[] args = {user_id};

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, args);

        if (cursor.moveToFirst()) {
            do {

                ListDataModel data = new ListDataModel();
                data.setId(cursor.getString(0));
                data.setUserId(cursor.getString(1));
                data.setTitle(cursor.getString(2));
                data.setBody(cursor.getString(3));

                userList.add(data);

            } while (cursor.moveToNext());
        }

        return userList;
    }

    public List<ListDataModel> getDetail(String id) {
        List<ListDataModel> userList = new ArrayList<ListDataModel>();
        String[] args = {id};

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE title = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, args);

        if (cursor.moveToFirst()) {
            do {

                ListDataModel data = new ListDataModel();
                data.setId(cursor.getString(0));
                data.setUserId(cursor.getString(1));
                data.setTitle(cursor.getString(2));
                data.setBody(cursor.getString(3));

                userList.add(data);

            } while (cursor.moveToNext());
        }

        return userList;
    }
*/

}
