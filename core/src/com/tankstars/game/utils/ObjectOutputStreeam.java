package com.tankstars.game.utils;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class ObjectOutputStreeam {
    public ObjectOutputStreeam(Object o){

    }
    public void writeObject(Object o1)throws Exception{
        ObjectOutputStream oOut = new ObjectOutputStream(new FileOutputStream("out.txt"));
        try{
            oOut.writeObject(o1);
        }catch(Exception e){

        }
    }
}
