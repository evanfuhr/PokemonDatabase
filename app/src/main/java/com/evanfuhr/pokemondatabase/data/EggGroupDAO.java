package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.models.EggGroup;

public class EggGroupDAO extends DataBaseHelper {

    public EggGroupDAO(Context context) {
        super(context);
    }

    public EggGroup getEggGroupByID(EggGroup eggGroup) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + TABLE_EGG_GROUP_PROSE + "." + KEY_EGG_GROUP_ID +
                ", " + TABLE_EGG_GROUP_PROSE + "." + KEY_NAME +
            " FROM " + TABLE_EGG_GROUP_PROSE +
            " WHERE " + TABLE_EGG_GROUP_PROSE + "." + KEY_EGG_GROUP_ID + " =  " + eggGroup.getID() +
                " AND " + TABLE_EGG_GROUP_PROSE + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'"
            ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                eggGroup.setID(Integer.parseInt(cursor.getString(0)));
                eggGroup.setName(cursor.getString(1));
            }
            cursor.close();
        }

        return eggGroup;
    }
}
