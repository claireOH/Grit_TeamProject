package Singleton;

import Model.DB_web;

/* Singleton Class creates class objects that connect to the Web Server */
/* Use the same DB object, no matter which file in the View file you access */
public class SingleClass_web {
    private static  SingleClass_web             single      = null;
    public          DB_web                      model_web;

    private SingleClass_web () {
        model_web = new DB_web();
    }

    public static SingleClass_web getInstance () {
        if (single == null) {
            single = new SingleClass_web();
        }
        return single;
    }
}
