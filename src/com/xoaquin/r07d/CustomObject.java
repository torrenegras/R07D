package com.xoaquin.r07d;

//OBJETO CUSTOM CON 4 STRINGS QUE VAN A INFLAR LOS 4 TEXTVIEWS DE CADA ITEM EN LISTVIEW, ESTOS FORMAN LA LISTA DEL MISMO TIPO.

public class CustomObject {

    private String prop1,prop2,prop3,prop4; 
    

    public CustomObject(String prop1, String prop2,String prop3,String prop4) {
        this.prop1 = prop1;
        this.prop2 = prop2;
        this.prop3 = prop3;
        this.prop4 = prop4;
    }

    public String getProp1() {
        return prop1;
    }

    public String getProp2() {
       return prop2;
    }

    public String getProp3() {
        return prop3;
     }
    
    public String getProp4() {
        return prop4;
     }



}
