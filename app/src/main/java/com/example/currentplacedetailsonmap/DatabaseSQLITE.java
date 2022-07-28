package com.example.currentplacedetailsonmap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSQLITE extends SQLiteOpenHelper {


    public DatabaseSQLITE(@Nullable Context context) {

        super(context, "LicentaDBFinalv3.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creation Strings;
        String creationSQL1 = "CREATE TABLE cities (idcity INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, city_name STRING)";
        String creationSQL2 = "CREATE TABLE gc_acc (idgc_acc INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, gc_name STRING, gc_password STRING UNIQUE);";
        String creationSQL3 = "CREATE TABLE last_month_stats (idlast_month_stats INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, pointid INTEGER REFERENCES points (idPoints) UNIQUE, lm_paper INTEGER, lm_plastic INTEGER, lm_glass INTEGER, lm_total INTEGER);";
        String creationSQL4 = "CREATE TABLE last_year_stats (\n" +
                "                idlast_month_stats INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                UNIQUE,\n" +
                "                pointid            INTEGER REFERENCES points (idPoints)\n" +
                "                UNIQUE,\n" +
                "                ly_paper           INTEGER,\n" +
                "                ly_plastic         INTEGER,\n" +
                "                ly_glass           INTEGER,\n" +
                "                ly_total           INTEGER\n" +
                "        )";
        String creationSQL5 = "CREATE TABLE points (idPoints INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, LAT DOUBLE, LNG DOUBLE, PAPER BOOLEAN DEFAULT (0), PLASTIC BOOLEAN DEFAULT (0), GLASS BOOLEAN DEFAULT (0), Paper_Current INTEGER, Plastic_Current INTEGER, Glass_Current INTEGER, Paper_Capacity INTEGER, Plastic_Capacity INTEGER, Glass_Capacity INTEGER, Total INTEGER, city INTEGER REFERENCES cities (idcity), point_name STRING)";
        String creationSQL6 = "CREATE TABLE current_month_stats (current_month_statsid INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, pointid INTEGER REFERENCES points (idPoints) UNIQUE, cm_paper INTEGER, cm_plastic INTEGER, cm_glass INTEGER, cm_total INTEGER);\n";
        String creationSQL7 = "CREATE TABLE current_year_stats (current_year_statsid INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, pointid INTEGER REFERENCES points (idPoints) UNIQUE, ym_paper INTEGER, ym_plastic INTEGER, ym_glass INTEGER, ym_total INTEGER);\n";
        db.execSQL(creationSQL5);
        db.execSQL(creationSQL2);
        db.execSQL(creationSQL3);
        db.execSQL(creationSQL4);
        db.execSQL(creationSQL1);
        db.execSQL(creationSQL6);
        db.execSQL(creationSQL7);

        // Inserts Garbage Collection Company Accounts
        String insert = "INSERT INTO gc_acc (idgc_acc, gc_name, gc_password) VALUES (1, 'Polaris', 'test123');";
        db.execSQL(insert);
        insert = "INSERT INTO gc_acc (idgc_acc, gc_name, gc_password) VALUES (2, 'Romprest', '123test');";
        db.execSQL(insert);

        // Insert Collection Points
        insert = "INSERT INTO points (idPoints, LAT, LNG, PAPER, PLASTIC, GLASS, Paper_Current, Plastic_Current, Glass_Current, Paper_Capacity, Plastic_Capacity, Glass_Capacity, Total, city, point_name) VALUES (1, 44.317, 28.606, 1, 1, 1, 11, 23, 50, 100, 100, 100, 84, 1, 'Point A')";
        db.execSQL(insert);
        insert = "INSERT INTO points (idPoints, LAT, LNG, PAPER, PLASTIC, GLASS, Paper_Current, Plastic_Current, Glass_Current, Paper_Capacity, Plastic_Capacity, Glass_Capacity, Total, city, point_name) VALUES (2, 44.323, 28.604, 0, 1, 1, 16, 7, 0, 0, 100, 100, 23, 1, 'Point B')";
        db.execSQL(insert);
        insert = "INSERT INTO points (idPoints, LAT, LNG, PAPER, PLASTIC, GLASS, Paper_Current, Plastic_Current, Glass_Current, Paper_Capacity, Plastic_Capacity, Glass_Capacity, Total, city, point_name) VALUES (3, 44.315, 28.612, 1, 1, 0, 36, 29, 0, 100, 100, 0, 65, 1, 'Point C')";
        db.execSQL(insert);
        insert = "INSERT INTO points (idPoints, LAT, LNG, PAPER, PLASTIC, GLASS, Paper_Current, Plastic_Current, Glass_Current, Paper_Capacity, Plastic_Capacity, Glass_Capacity, Total, city, point_name) VALUES (4, 44.217190 , 28.627904, 1, 1, 1, 169, 87, 108, 200, 200, 200, 364, 2, 'Point Alpha')";
        db.execSQL(insert);
        insert = "INSERT INTO points (idPoints, LAT, LNG, PAPER, PLASTIC, GLASS, Paper_Current, Plastic_Current, Glass_Current, Paper_Capacity, Plastic_Capacity, Glass_Capacity, Total, city, point_name) VALUES (5, 44.205551 , 28.643859, 1, 1, 1, 186, 18, 192, 200, 200, 200, 396, 2, 'Point Beta')";
        db.execSQL(insert);
        insert = "INSERT INTO points (idPoints, LAT, LNG, PAPER, PLASTIC, GLASS, Paper_Current, Plastic_Current, Glass_Current, Paper_Capacity, Plastic_Capacity, Glass_Capacity, Total, city, point_name) VALUES (6, 44.202721 , 28.630069, 1, 1, 0, 148, 18, 0, 200, 200, 0, 166, 2, 'Point Gamma')";
        db.execSQL(insert);
        insert = "INSERT INTO points (idPoints, LAT, LNG, PAPER, PLASTIC, GLASS, Paper_Current, Plastic_Current, Glass_Current, Paper_Capacity, Plastic_Capacity, Glass_Capacity, Total, city, point_name) VALUES (7, 44.202525 , 28.611224, 1, 1, 0, 121, 47, 0, 200, 200, 0, 168, 2, 'Point Delta')";
        db.execSQL(insert);
        insert = "INSERT INTO points (idPoints, LAT, LNG, PAPER, PLASTIC, GLASS, Paper_Current, Plastic_Current, Glass_Current, Paper_Capacity, Plastic_Capacity, Glass_Capacity, Total, city, point_name) VALUES (8, 44.171712 , 28.609973, 1, 0, 1, 58, 0, 24, 200, 0, 200, 82, 2, 'Point Epsilon')";
        db.execSQL(insert);
        insert = "INSERT INTO points (idPoints, LAT, LNG, PAPER, PLASTIC, GLASS, Paper_Current, Plastic_Current, Glass_Current, Paper_Capacity, Plastic_Capacity, Glass_Capacity, Total, city, point_name) VALUES (9, 44.150587 , 28.623206, 1, 0, 0, 123, 0, 0, 200, 0, 0, 123, 2, 'Point Zeta')";
        db.execSQL(insert);
        insert = "INSERT INTO points (idPoints, LAT, LNG, PAPER, PLASTIC, GLASS, Paper_Current, Plastic_Current, Glass_Current, Paper_Capacity, Plastic_Capacity, Glass_Capacity, Total, city, point_name) VALUES (10, 44.178962 , 28.649955, 0, 1, 0, 0, 172, 0, 0, 200, 0, 172, 2, 'Point Eta')";
        db.execSQL(insert);
        insert = "INSERT INTO points (idPoints, LAT, LNG, PAPER, PLASTIC, GLASS, Paper_Current, Plastic_Current, Glass_Current, Paper_Capacity, Plastic_Capacity, Glass_Capacity, Total, city, point_name) VALUES (11, 44.190592 , 28.650433, 0, 0, 1, 0, 0, 76, 0, 0, 200, 76, 2, 'Point Theta')";
        db.execSQL(insert);

        // Insert Last Month Stats
        insert = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (1, 1, 265, 1069, 2951, 4285);";
        db.execSQL(insert);
        insert = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (2, 2, 0, 1521, 1463, 2984);";
        db.execSQL(insert);
        insert = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (3, 3, 822, 425, 0, 1247);";
        db.execSQL(insert);
        insert = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (4, 4, 1870, 3578, 3052, 8500)";
        db.execSQL(insert);
        insert = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (5, 5, 1934, 2992, 1207, 6133)";
        db.execSQL(insert);
        insert = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (6, 6, 2333, 4141, 0, 6474)";
        db.execSQL(insert);
        insert = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (7, 7, 4203, 2671, 0, 6874)";
        db.execSQL(insert);
        insert = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (8, 8, 1036, 0, 3478, 4514)";
        db.execSQL(insert);
        insert = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (9, 9, 4491, 0, 0, 4491)";
        db.execSQL(insert);
        insert = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (10, 10, 0, 3209, 0, 3209)";
        db.execSQL(insert);
        insert = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (11, 11, 0, 0, 1341, 1341)";

        // Insert Last Year Stats
        insert = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (1, 1, 5287, 4731, 7119, 17137);";
        db.execSQL(insert);
        insert = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (2, 2, 0, 3647, 3815, 7462);";
        db.execSQL(insert);
        insert = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (3, 3, 6860, 6588, 0, 13448);";
        db.execSQL(insert);
        insert = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (4, 4, 6555, 7224, 6979, 14203)";
        db.execSQL(insert);
        insert = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (5, 5, 4711, 4328, 6214, 15253)";
        db.execSQL(insert);
        insert = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (6, 6, 8866, 5131, 0, 13997)";
        db.execSQL(insert);
        insert = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (7, 7, 6769, 3168, 0, 9937)";
        db.execSQL(insert);
        insert = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (8, 8, 4780, 0, 7733, 12513)";
        db.execSQL(insert);
        insert = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (9, 9, 7379, 0, 0, 7379)";
        db.execSQL(insert);
        insert = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (10, 10, 0, 8383, 0, 8383)";
        db.execSQL(insert);
        insert = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (11, 11, 0, 0, 6409, 6409)";
        db.execSQL(insert);

        // Insert Cities
        insert = "INSERT INTO cities (idcity, city_name) VALUES (1, 'Navodari')";
        db.execSQL(insert);
        insert = "INSERT INTO cities (idcity, city_name) VALUES (2, 'Constanta')";
        db.execSQL(insert);
        insert = "INSERT INTO cities (idcity, city_name) VALUES (3, 'Bucharest')";
        db.execSQL(insert);

        // Insert CRMS
        insert = "INSERT INTO current_month_stats (current_month_statsid, pointid, cm_paper, cm_plastic, cm_glass, cm_total) VALUES (1, 1, 1450, 566, 887, 2903)";
        db.execSQL(insert);
        insert = "INSERT INTO current_month_stats (current_month_statsid, pointid, cm_paper, cm_plastic, cm_glass, cm_total) VALUES (2, 2, 0, 1199, 780, 1979)";
        db.execSQL(insert);
        insert = "INSERT INTO current_month_stats (current_month_statsid, pointid, cm_paper, cm_plastic, cm_glass, cm_total) VALUES (3, 3, 808, 956, 0, 1764)";
        db.execSQL(insert);
        insert = "INSERT INTO current_month_stats (current_month_statsid, pointid, cm_paper, cm_plastic, cm_glass, cm_total) VALUES (4, 4, 2878, 2754, 1147, 6779)";
        db.execSQL(insert);
        insert = "INSERT INTO current_month_stats (current_month_statsid, pointid, cm_paper, cm_plastic, cm_glass, cm_total) VALUES (5, 5, 1977, 1374, 2150, 5501)";
        db.execSQL(insert);
        insert = "INSERT INTO current_month_stats (current_month_statsid, pointid, cm_paper, cm_plastic, cm_glass, cm_total) VALUES (6, 6, 1352, 2893, 0, 4245)";
        db.execSQL(insert);
        insert = "INSERT INTO current_month_stats (current_month_statsid, pointid, cm_paper, cm_plastic, cm_glass, cm_total) VALUES (7, 7, 1949, 2575, 0, 4524)";
        db.execSQL(insert);
        insert = "INSERT INTO current_month_stats (current_month_statsid, pointid, cm_paper, cm_plastic, cm_glass, cm_total) VALUES (8, 8, 2339, 0, 2409, 4748)";
        db.execSQL(insert);
        insert = "INSERT INTO current_month_stats (current_month_statsid, pointid, cm_paper, cm_plastic, cm_glass, cm_total) VALUES (9, 9, 824, 0, 0, 824)";
        db.execSQL(insert);
        insert = "INSERT INTO current_month_stats (current_month_statsid, pointid, cm_paper, cm_plastic, cm_glass, cm_total) VALUES (10, 10, 0, 1201, 0, 1201)";
        db.execSQL(insert);
        insert = "INSERT INTO current_month_stats (current_month_statsid, pointid, cm_paper, cm_plastic, cm_glass, cm_total) VALUES (11, 11, 0, 0, 851, 851)";

        // Insert CRYS
        insert = "INSERT INTO current_year_stats (current_year_statsid, pointid, ym_paper, ym_plastic, ym_glass, ym_total) VALUES (1, 3, 4173, 3193, 0, 7366)";
        db.execSQL(insert);
        insert = "INSERT INTO current_year_stats (current_year_statsid, pointid, ym_paper, ym_plastic, ym_glass, ym_total) VALUES (2, 2, 0, 5585, 4836, 10421)";
        db.execSQL(insert);
        insert = "INSERT INTO current_year_stats (current_year_statsid, pointid, ym_paper, ym_plastic, ym_glass, ym_total) VALUES (3, 1, 2347, 4694, 3247, 10288)";
        db.execSQL(insert);
        insert = "INSERT INTO current_year_stats (current_year_statsid, pointid, ym_paper, ym_plastic, ym_glass, ym_total) VALUES (4, 4, 4521, 5544, 5347, 15412)";
        db.execSQL(insert);
        insert = "INSERT INTO current_year_stats (current_year_statsid, pointid, ym_paper, ym_plastic, ym_glass, ym_total) VALUES (5, 5, 2527, 2349, 2790, 7666)";
        db.execSQL(insert);
        insert = "INSERT INTO current_year_stats (current_year_statsid, pointid, ym_paper, ym_plastic, ym_glass, ym_total) VALUES (6, 6, 1412, 1908, 0, 3320)";
        db.execSQL(insert);
        insert = "INSERT INTO current_year_stats (current_year_statsid, pointid, ym_paper, ym_plastic, ym_glass, ym_total) VALUES (7, 7, 5516, 1661, 0, 7177)";
        db.execSQL(insert);
        insert = "INSERT INTO current_year_stats (current_year_statsid, pointid, ym_paper, ym_plastic, ym_glass, ym_total) VALUES (8, 8, 5649, 0, 1170, 6819)";
        db.execSQL(insert);
        insert = "INSERT INTO current_year_stats (current_year_statsid, pointid, ym_paper, ym_plastic, ym_glass, ym_total) VALUES (9, 9, 3592, 0, 0, 3592)";
        db.execSQL(insert);
        insert = "INSERT INTO current_year_stats (current_year_statsid, pointid, ym_paper, ym_plastic, ym_glass, ym_total) VALUES (10, 10, 0, 4976, 0, 4976)";
        db.execSQL(insert);
        insert = "INSERT INTO current_year_stats (current_year_statsid, pointid, ym_paper, ym_plastic, ym_glass, ym_total) VALUES (11, 11, 0, 0, 1148, 1148)";
        db.execSQL(insert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Points");
        onCreate(db);
    }

    public List<Integer> get_lms(int id){
        List<Integer> list = new ArrayList<>();
        String get = "SELECT * FROM last_month_stats WHERE pointid = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(get,null);
        if (cursor.moveToFirst()){
            do{
                list.add(cursor.getInt(2));
                list.add(cursor.getInt(3));
                list.add(cursor.getInt(4));
                list.add(cursor.getInt(5));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Integer> get_lys(int id){
        List<Integer> list = new ArrayList<>();
        String get = "SELECT * FROM last_year_stats WHERE pointid = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(get,null);
        if (cursor.moveToFirst()){
            do{
                list.add(cursor.getInt(2));
                list.add(cursor.getInt(3));
                list.add(cursor.getInt(4));
                list.add(cursor.getInt(5));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Markers> getMarkers(){
        List<Markers> list = new ArrayList<>();
        String getall = "SELECT * FROM points";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getall,null);
        if (cursor.moveToFirst()){
            do{
                int _id = cursor.getInt(0);
                double _lat = cursor.getDouble(1);
                double _long = cursor.getDouble(2);
                boolean _paper = cursor.getInt(3) > 0;
                boolean _plastic = cursor.getInt(4) > 0;
                boolean _glass = cursor.getInt(5) > 0;
                int _cp = cursor.getInt(6);
                int _cpl = cursor.getInt(7);
                int _cg = cursor.getInt(8);
                int _pc = cursor.getInt(9);
                int _pcl = cursor.getInt(10);
                int _pg = cursor.getInt(11);
                int _total = cursor.getInt(12);
                int _city = cursor.getInt(13);
                String _pointname = cursor.getString(14);
                Markers newMarker = new Markers(_id, _lat, _long, _paper, _plastic, _glass, _cp, _cpl, _cg, _pc, _pcl, _pg, _total , _city , _pointname);
                list.add(newMarker);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;

    }

    public Markers get_Marker(int id){
        Markers aux = new Markers();
        String getall = "SELECT * FROM points WHERE idPoints = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getall,null);
        if (cursor.moveToFirst()){
                int _id = cursor.getInt(0);
                double _lat = cursor.getDouble(1);
                double _long = cursor.getDouble(2);
                boolean _paper = cursor.getInt(3) > 0;
                boolean _plastic = cursor.getInt(4) > 0;
                boolean _glass = cursor.getInt(5) > 0;
                int _cp = cursor.getInt(6);
                int _cpl = cursor.getInt(7);
                int _cg = cursor.getInt(8);
                int _pc = cursor.getInt(9);
                int _pcl = cursor.getInt(10);
                int _pg = cursor.getInt(11);
                int _total = cursor.getInt(12);
                int _city = cursor.getInt(13);
                String _point_name = cursor.getString(14);
                aux = new Markers(_id, _lat, _long, _paper, _plastic, _glass, _cp, _cpl, _cg, _pc, _pcl, _pg, _total , _city , _point_name);
        }
        cursor.close();
        db.close();
        return aux;
    }

    public void update_paper(Markers aux , int n){
        int updated_current = aux.getCp() + n;
        int total_current = aux.getTotal() + n;
        String update = "UPDATE points SET Paper_Current = " + updated_current + " WHERE idPoints = " + aux.getId();
        String update2 = "UPDATE points SET Total = " + total_current + " WHERE idPoints = " + aux.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        db.execSQL(update2);
        db.close();
    }

    public void update_plastic(Markers aux , int n){
        int updated_current = aux.getCpl() + n;
        int total_current = aux.getTotal() + n;
        String update = "UPDATE points SET Plastic_Current = " + updated_current + " WHERE idPoints = " + aux.getId();
        String update2 = "UPDATE points SET Total = " + total_current + " WHERE idPoints = " + aux.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        db.execSQL(update2);
        db.close();
    }

    public void update_glass(Markers aux , int n){
        int updated_current = aux.getCg() + n;
        int total_current = aux.getTotal() + n;
        String update = "UPDATE points SET Glass_Current = " + updated_current + " WHERE idPoints = " + aux.getId();
        String update2 = "UPDATE points SET Total = " + total_current + " WHERE idPoints = " + aux.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        db.execSQL(update2);
        db.close();
    }

    public String get_account_passwords(String password){
        String getall = "SELECT * FROM gc_acc";
        ArrayList<String> accounts = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getall,null);
        if (cursor.moveToFirst()){
            do{
                accounts.add(cursor.getString(1));
                passwords.add(cursor.getString(2));
            }while(cursor.moveToNext());
        }
        if (passwords.contains(password))
            return accounts.get(passwords.indexOf(password));

        cursor.close();
        db.close();
        return "";
    }

    public void empty_point(int id){
        String update = "UPDATE points SET Paper_Current = 0 , Plastic_Current = 0 , Glass_Current = 0 , Total = 0 WHERE idPoints = " + id;
        String gets = "SELECT * from points WHERE idPoints = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(gets,null);
        if (cursor.moveToFirst()){
                int paper = cursor.getInt(6);
                int plastic = cursor.getInt(7);
                int glass = cursor.getInt(8);
                int total = cursor.getInt(12);
                String move_to_month = "UPDATE current_month_stats SET cm_paper = cm_paper + " + paper + " , cm_plastic = cm_plastic + " + plastic + " , cm_glass = cm_glass + " + glass + " , cm_total = cm_total + " + total + " WHERE pointid = " + id;
                db.execSQL(move_to_month);
        }
        cursor.close();
        db.execSQL(update);
        db.close();
    }

    public List<Markers> get_marker_from_city(int city_id){
        List<Markers> list = new ArrayList<>();
        String getall = "SELECT * FROM points WHERE city = " + city_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getall,null);
        if (cursor.moveToFirst()){
            do{
                int _id = cursor.getInt(0);
                double _lat = cursor.getDouble(1);
                double _long = cursor.getDouble(2);
                boolean _paper = cursor.getInt(3) > 0;
                boolean _plastic = cursor.getInt(4) > 0;
                boolean _glass = cursor.getInt(5) > 0;
                int _cp = cursor.getInt(6);
                int _cpl = cursor.getInt(7);
                int _cg = cursor.getInt(8);
                int _pc = cursor.getInt(9);
                int _pcl = cursor.getInt(10);
                int _pg = cursor.getInt(11);
                int _total = cursor.getInt(12);
                int _city = cursor.getInt(13);
                String _pointname = cursor.getString(14);
                Markers newMarker = new Markers(_id, _lat, _long, _paper, _plastic, _glass, _cp, _cpl, _cg, _pc, _pcl, _pg, _total , _city , _pointname);
                list.add(newMarker);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<String> get_city_names(){
        String getall = "SELECT * FROM cities";
        List<String> cities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getall,null);
        if (cursor.moveToFirst()){
            do{
                cities.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cities;
    }

    public void roll_over_year(){
        String empty = "UPDATE current_year_stats SET ym_paper = 0 , ym_plastic = 0 , ym_glass = 0 , ym_total = 0";
        List<Integer> paper = new ArrayList<>();
        List<Integer> plastic = new ArrayList<>();
        List<Integer> glass = new ArrayList<>();
        List<Integer> total = new ArrayList<>();
        String roll_over = "SELECT * FROM current_year_stats";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(roll_over,null);
        if (cursor.moveToFirst()){                                  /// Save Stats
            do{
                paper.add(cursor.getInt(3));
                plastic.add(cursor.getInt(4));
                glass.add(cursor.getInt(5));
                total.add(cursor.getInt(6));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.execSQL(empty);                                         /// Empty Table
        for (int i=0;i<paper.size();i++){                          /// Move Over
            String movetoLast = "UPDATE last_year_stats SET ly_paper = " + paper.get(i) + " , ly_plastic = " + plastic.get(i) + " , ly_glass = " + glass.get(i) + " , ly_total = " + total.get(i) + " WHERE idPoints = " + i+1;
            db.execSQL(movetoLast);
        }
        cursor.close();
        db.close();
    }

    public void roll_over_month(){
        String empty = "UPDATE current_month_stats SET cm_paper = 0 , cm_plastic = 0 , cm_glass = 0 , cm_total = 0";
        List<Integer> paper = new ArrayList<>();
        List<Integer> plastic = new ArrayList<>();
        List<Integer> glass = new ArrayList<>();
        List<Integer> total = new ArrayList<>();
        String roll_over = "SELECT * FROM current_month_stats";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(roll_over,null);
        if (cursor.moveToFirst()){                                  /// Save Stats
            do{
                paper.add(cursor.getInt(3));
                plastic.add(cursor.getInt(4));
                glass.add(cursor.getInt(5));
                total.add(cursor.getInt(6));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.execSQL(empty);
        for (int i=0;i<paper.size();i++){                          /// Move Over
            String movetoLast = "UPDATE last_month_stats SET lm_paper = " + paper.get(i) + " , lm_plastic = " + plastic.get(i) + " , lm_glass = " + glass.get(i) + " , lm_total = " + total.get(i) + " WHERE idPoints = " + i+1;
            String add_to_cy = "UPDATE current_year_stats SET ym_paper = ym_paper + " + paper.get(i) + " , ym_plastic = ym_plastic + " + plastic.get(i) + ", ym_glass = ym_glass + " + glass.get(i) + ", ym_total = ym_total + " + total.get(i) + " WHERE idPoints = " + i+1;
            db.execSQL(movetoLast);
            db.execSQL(add_to_cy);
        }
        cursor.close();
        db.close();

    }

    public void insert_debug(){                                                                     // Debug method used to modify the database.
        /*String insert1 = "CREATE TABLE last_month_stats (\n" +
                "    idlast_month_stats INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                               UNIQUE,\n" +
                "    pointid            INTEGER REFERENCES points (idPoints) \n" +
                "                               UNIQUE,\n" +
                "    lm_paper           INTEGER,\n" +
                "    lm_plastic         INTEGER,\n" +
                "    lm_glass           INTEGER,\n" +
                "    lm_total           INTEGER\n" +
                ")";

        String insert2 = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (1, 1, 265, 1069, 2951, 4285)";
        String insert3 = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (2, 2, 0, 1521, 1463, 2984)";
        String insert4 = "INSERT INTO last_month_stats (idlast_month_stats, pointid, lm_paper, lm_plastic, lm_glass, lm_total) VALUES (3, 3, 822, 425, 0, 1247)";
        */
        String insert1 = "CREATE TABLE last_year_stats (\n" +
                "    idlast_month_stats INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                               UNIQUE,\n" +
                "    pointid            INTEGER REFERENCES points (idPoints) \n" +
                "                               UNIQUE,\n" +
                "    ly_paper           INTEGER,\n" +
                "    ly_plastic         INTEGER,\n" +
                "    ly_glass           INTEGER,\n" +
                "    ly_total           INTEGER\n" +
                ")";
        String insert2 = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (1, 1, 5287, 4731, 7119, 17137)";
        String insert3 = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (2, 2, 0, 3647, 3815, 7462)";
        String insert4 = "INSERT INTO last_year_stats (idlast_month_stats, pointid, ly_paper, ly_plastic, ly_glass, ly_total) VALUES (3, 3, 6860, 6588, 0, 13448)";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(insert1);
        db.execSQL(insert2);
        db.execSQL(insert3);
        db.execSQL(insert4);
        db.close();
    }
}
