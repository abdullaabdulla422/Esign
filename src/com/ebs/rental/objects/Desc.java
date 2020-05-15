package com.ebs.rental.objects;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.Vector;



@SuppressWarnings("InstantiatingObjectToGetClassObject")
public class Desc extends Vector<Parts> implements KvmSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1166006770093411055L;

    @Override
    public Object getProperty(int arg0) {
            return this.get(arg0);
    }

    @Override
    public int getPropertyCount() {
            return 1;
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
            arg2.name = "Parts";
            arg2.type = new Parts().getClass();
    }

    @Override
    public void setProperty(int arg0, Object arg1) {
            this.add((Parts)arg1);
    }

}

