package com.izv.dam.newquip.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.izv.dam.newquip.pojo.Localizador;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by toniarcogarcia on 1/12/16.
 */

public class AyudanteLocalizador extends OrmLiteSqliteOpenHelper {

    private static final int VERSION = 1;
    private Dao<Localizador, Integer> simpleDao = null;
    private RuntimeExceptionDao<Localizador, Integer> simpleRuntimeDao = null;

    public AyudanteLocalizador(Context context) {
        super(context, "ormlite", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Localizador.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(database, connectionSource);
    }

    public Dao<Localizador, Integer> getDao() throws SQLException{
        if (simpleDao == null){
            simpleDao = getDao(Localizador.class);
        }

        return simpleDao;
    }

    public RuntimeExceptionDao<Localizador, Integer> getDataDao(){
        if(simpleRuntimeDao == null){
            simpleRuntimeDao = getRuntimeExceptionDao(Localizador.class);
        }

        return simpleRuntimeDao;
    }

    @Override
    public void close(){
        super.close();
        simpleDao = null;
        simpleRuntimeDao = null;
    }
}
