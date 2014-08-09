package com.example.youhuipaipai.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.youhuipaipai.entity.User;

public final class UserDB extends BaseDbHelper {

    public UserDB(Context context) {
        super(context, DataBaseFiledParams.TABLE_USER);
    }

    @Override
    protected void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DataBaseFiledParams.TABLE_USER + "("
                + DataBaseFiledParams.TableUser.ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DataBaseFiledParams.TableUser.ROW_ID + " TEXT,"
                + DataBaseFiledParams.TableUser.ROW_NICKNAME + " TEXT,"
                + DataBaseFiledParams.TableUser.ROW_SEX + " TEXT,"
                + DataBaseFiledParams.TableUser.ROW_THECITY + " TEXT,"
                + DataBaseFiledParams.TableUser.ROW_HEADIMG + " TEXT,"
                + DataBaseFiledParams.TableUser.ROW_INTEGRANUM + " TEXT,"
                + DataBaseFiledParams.TableUser.ROW_MAIL + " TEXT,"
                + DataBaseFiledParams.TableUser.ROW_CREATEDATE + " TEXT);");
    }

    /**
     * 插入数据
     */
    public void insertValues(User user) {
        deleteUser();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseFiledParams.TableUser.ROW_ID, user.getUserid());
        values.put(DataBaseFiledParams.TableUser.ROW_NICKNAME,
                user.getNickname());
        values.put(DataBaseFiledParams.TableUser.ROW_SEX, user.getSex());
        values.put(DataBaseFiledParams.TableUser.ROW_THECITY, user.getThecity());
        values.put(DataBaseFiledParams.TableUser.ROW_HEADIMG, user.getHeadimg());
        values.put(DataBaseFiledParams.TableUser.ROW_INTEGRANUM,
                user.getIntegranum());
        values.put(DataBaseFiledParams.TableUser.ROW_CREATEDATE,
                user.getCreatedate());
        values.put(DataBaseFiledParams.TableUser.ROW_MAIL, user.getMail());
        long i = db.insert(DataBaseFiledParams.TABLE_USER, null, values);
        db.close();
    }

    public void deleteUser() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DataBaseFiledParams.TABLE_USER, null, null);
    }

    public void updateValue(User user, String id) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseFiledParams.TableUser.ROW_ID, user.getUserid());
        cv.put(DataBaseFiledParams.TableUser.ROW_NICKNAME, user.getNickname());
        cv.put(DataBaseFiledParams.TableUser.ROW_SEX, user.getSex());
        cv.put(DataBaseFiledParams.TableUser.ROW_THECITY, user.getThecity());
        cv.put(DataBaseFiledParams.TableUser.ROW_HEADIMG, user.getHeadimg());
        cv.put(DataBaseFiledParams.TableUser.ROW_INTEGRANUM,
                user.getIntegranum());
        cv.put(DataBaseFiledParams.TableUser.ROW_CREATEDATE,
                user.getCreatedate());
        cv.put(DataBaseFiledParams.TableUser.ROW_MAIL, user.getMail());
        SQLiteDatabase db = getWritableDatabase();
        db.update(DataBaseFiledParams.TABLE_USER, cv, "userid=?",
                new String[] { id });
        db.close();
    }
    public void updatePhone(User user, String id) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseFiledParams.TableUser.ROW_ID, user.getUserid());
        cv.put(DataBaseFiledParams.TableUser.ROW_NICKNAME, user.getNickname());
        cv.put(DataBaseFiledParams.TableUser.ROW_SEX, user.getSex());
        cv.put(DataBaseFiledParams.TableUser.ROW_THECITY, user.getThecity());
        cv.put(DataBaseFiledParams.TableUser.ROW_HEADIMG, user.getHeadimg());
        cv.put(DataBaseFiledParams.TableUser.ROW_INTEGRANUM,
                user.getIntegranum());
        cv.put(DataBaseFiledParams.TableUser.ROW_CREATEDATE,
                user.getCreatedate());
        cv.put(DataBaseFiledParams.TableUser.ROW_MAIL, user.getMail());
        SQLiteDatabase db = getWritableDatabase();
        db.update(DataBaseFiledParams.TABLE_USER, cv, "userid=?",
                new String[] { id });
        db.close();
    }

    /**
     * @param c
     * @return
     */
    private User getDataFromCursor(Cursor c) {
        if (c.moveToNext()) {
            User user = new User();
            user.setUserid(c.getString(c
                    .getColumnIndex(DataBaseFiledParams.TableUser.ROW_ID)));
            user.setNickname(c.getString(c
                    .getColumnIndex(DataBaseFiledParams.TableUser.ROW_NICKNAME)));
            user.setSex(c.getString(c
                    .getColumnIndex(DataBaseFiledParams.TableUser.ROW_SEX)));
            user.setThecity(c.getString(c
                    .getColumnIndex(DataBaseFiledParams.TableUser.ROW_THECITY)));
            user.setHeadimg(c.getString(c
                    .getColumnIndex(DataBaseFiledParams.TableUser.ROW_HEADIMG)));
            user.setIntegranum(c.getString(c
                    .getColumnIndex(DataBaseFiledParams.TableUser.ROW_INTEGRANUM)));
            user.setCreatedate(c.getString(c
                    .getColumnIndex(DataBaseFiledParams.TableUser.ROW_CREATEDATE)));
            user.setMail(c.getString(c
                    .getColumnIndex(DataBaseFiledParams.TableUser.ROW_MAIL)));
            c.close();
            return user;
        }
        return null;
    }

    public User getUser() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + DataBaseFiledParams.TABLE_USER;
        Cursor c = db.rawQuery(sql, null);
        User user = getDataFromCursor(c);
        return user;
    }

}
