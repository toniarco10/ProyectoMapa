package com.izv.dam.newquip.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by toniarcogarcia on 1/12/16.
 */

@DatabaseTable(tableName = "localizador")
public class Localizador implements Parcelable{

    public static final String ID_NOTA = "idNota";
    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";

    @DatabaseField(columnName = ID_NOTA)
    private long idNota;
    @DatabaseField(columnName = LATITUD)
    private double latitud;
    @DatabaseField(columnName = LONGITUD)
    private double longitud;

    public Localizador(long idNota, double latitud, double longitud) {
        this.idNota = idNota;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Localizador() {
    }

    protected Localizador(Parcel in) {
        idNota = in.readLong();
        latitud = in.readDouble();
        longitud = in.readDouble();
    }

    public static final Creator<Localizador> CREATOR = new Creator<Localizador>() {
        @Override
        public Localizador createFromParcel(Parcel in) {
            return new Localizador(in);
        }

        @Override
        public Localizador[] newArray(int size) {
            return new Localizador[size];
        }
    };

    public long getIdNota() {
        return idNota;
    }

    public void setIdNota(long idNota) {
        this.idNota = idNota;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Localizador{" +
                ", idNota=" + idNota +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idNota);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
    }
}
