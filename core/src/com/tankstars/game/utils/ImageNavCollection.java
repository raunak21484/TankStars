package com.tankstars.game.utils;

import java.util.ArrayList;

public class ImageNavCollection {
    ArrayList<String> paths;
    MutableInt index;

    public ImageNavCollection(ArrayList<String> paths, MutableInt index) {
        this.paths = paths;
        this.index = index;
        if(index.val<0 || index.val>=paths.size()){
            index.val =0;
        }
    }
    public void setIndex(int index){
        if(index<0 || index>=paths.size()){
            return;
        }
        this.index.val = index;
    }
    public String getPath(){
        try{
            return paths.get(index.val);
        }catch(Exception e){
            return null;
        }
    }
    public ArrayList<String> getPaths() {
        return paths;
    }
    public MutableInt getIndex() {
        return index;
    }
}
