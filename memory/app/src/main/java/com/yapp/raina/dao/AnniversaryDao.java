package com.yapp.raina.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yapp.raina.dto.AnniversaryDto;

import java.util.ArrayList;

public class AnniversaryDao {

    private SQLiteDatabase db;

    private static final String TABLE_NAME = "anniversary_tb";
    private static final String KEY_ID_PK = "ID_PK";
    private static final String KEY_DATE_YMD = "DATE_YMD";
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_ABSTRACT = "ABSTRACT";
    private static final String KEY_CONTENT = "CONTENT";
    private static final String KEY_REFERENCE = "REFERENCE";
    private static final String KEY_CATEGORY = "CATEGORY";
    private static final String KEY_ALARM_ST = "ALARM_ST";
    private static final String KEY_SOLAR_ST = "SOLAR_ST";
    private static final String KEY_BOOKMARK_ST = "BOOKMARK_ST";

    public AnniversaryDao(SQLiteDatabase db) {
        this.db = db;
    }

    public void insert(AnniversaryDto dto) {
//        ContentValues values = new ContentValues();
//        values.put(KEY_DATE_YMD, dto.getDate_ymd());
//        values.put(KEY_TITLE, dto.getTitle());
//        db.insert(TABLE_NAME, null, values);
    }

    public void delete(AnniversaryDto dto) {
        db.delete(TABLE_NAME, KEY_ID_PK + "=" + dto.getId_pk(), null);
    }

    public void update(AnniversaryDto dto) {
//        ContentValues values = new ContentValues();
//        values.put(KEY_BOOKMARK_ST, false);
//        String whereClause = KEY_ID_PK + "=?";
//        String[] whereArgs = new String[] {dto.getId_pk()};
    }

    public void updateBookmark(AnniversaryDto dto) {
//        ContentValues values = new ContentValues();
//        values.put(KEY_BOOKMARK_ST, false);
//        String whereClause = KEY_ID_PK + "=?";
//        String[] whereArgs = new String[] {dto.getId_pk()};

        Log.d("before dto", dto.toString());
        if (dto.getBookmark_st())
            db.execSQL("update anniversary_tb set BOOKMARK_ST = 'FALSE' where ID_PK = " + dto.getId_pk() + ";");
        else
            db.execSQL("update anniversary_tb set BOOKMARK_ST = 'TRUE' where ID_PK = " + dto.getId_pk() + ";");
        AnniversaryDto test = selectByKey(dto.getId_pk());
                Log.d("update dto", test.toString());

    }

    public ArrayList<AnniversaryDto> selectAll() {
        ArrayList<AnniversaryDto> listDto = new ArrayList<AnniversaryDto>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        int i = 0;
        while (c.moveToNext()) {
            AnniversaryDto dto = new AnniversaryDto();
            dto.setId_pk(c.getInt(c.getColumnIndex(KEY_ID_PK)));
            dto.setDate_ymd(c.getString(c.getColumnIndex(KEY_DATE_YMD)));
            dto.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            dto.setAbstract_(c.getString(c.getColumnIndex(KEY_ABSTRACT)));
            dto.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));

            dto.setReference(c.getString(c.getColumnIndex(KEY_REFERENCE)));
            dto.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
            dto.setAlarm_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_ALARM_ST))));
            dto.setSolar_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_SOLAR_ST))));
            dto.setBookmark_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BOOKMARK_ST))));
            listDto.add(dto);
        }
        c.close();
        return listDto;
    }

    public ArrayList<AnniversaryDto> selectFavoriteList() {
        ArrayList<AnniversaryDto> listDto = new ArrayList<AnniversaryDto>();
        Cursor c = db.rawQuery("SELECT * FROM anniversary_tb WHERE BOOKMARK_ST = 'TRUE'", null);
        Log.d("number::::", c.getCount() + "");

        while (c.moveToNext()) {
            AnniversaryDto dto = new AnniversaryDto();
            dto.setId_pk(c.getInt(c.getColumnIndex(KEY_ID_PK)));
            dto.setDate_ymd(c.getString(c.getColumnIndex(KEY_DATE_YMD)));
            dto.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            dto.setAbstract_(c.getString(c.getColumnIndex(KEY_ABSTRACT)));
            dto.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));

            dto.setReference(c.getString(c.getColumnIndex(KEY_REFERENCE)));
            dto.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
            dto.setAlarm_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_ALARM_ST))));
            dto.setSolar_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_SOLAR_ST))));
            dto.setBookmark_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BOOKMARK_ST))));
            listDto.add(dto);
        }
        c.close();
        return listDto;
    }

    public ArrayList<AnniversaryDto> selectByMonth(int month) {
        ArrayList<AnniversaryDto> listDto = new ArrayList<AnniversaryDto>();
        Cursor c = db.rawQuery("SELECT * FROM anniversary_tb WHERE DATE_YMD LIKE '%.2.%'", null);

        while (c.moveToNext()) {
            AnniversaryDto dto = new AnniversaryDto();
            dto.setId_pk(c.getInt(c.getColumnIndex(KEY_ID_PK)));
            dto.setDate_ymd(c.getString(c.getColumnIndex(KEY_DATE_YMD)));
            dto.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            dto.setAbstract_(c.getString(c.getColumnIndex(KEY_ABSTRACT)));
            dto.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));

            dto.setReference(c.getString(c.getColumnIndex(KEY_REFERENCE)));
            dto.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
            dto.setAlarm_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_ALARM_ST))));
            dto.setSolar_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_SOLAR_ST))));
            dto.setBookmark_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BOOKMARK_ST))));
            listDto.add(dto);
        }
        c.close();
        return listDto;
    }

    public AnniversaryDto selectByKey(int key_id) {
        AnniversaryDto dto = new AnniversaryDto();
        Cursor c = db.rawQuery("SELECT * FROM anniversary_tb where ID_PK = " + dto.getId_pk() + ";", null);
        while (c.moveToNext()) {
            dto.setId_pk(c.getInt(c.getColumnIndex(KEY_ID_PK)));
            dto.setDate_ymd(c.getString(c.getColumnIndex(KEY_DATE_YMD)));
            dto.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            dto.setAbstract_(c.getString(c.getColumnIndex(KEY_ABSTRACT)));
            dto.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));

            dto.setReference(c.getString(c.getColumnIndex(KEY_REFERENCE)));
            dto.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
            dto.setAlarm_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_ALARM_ST))));
            dto.setSolar_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_SOLAR_ST))));
            dto.setBookmark_st(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_BOOKMARK_ST))));
        }

        return dto;
    }
}
