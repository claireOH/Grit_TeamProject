package Singleton;

import Model.DB_raspi;

/* A Singleton class that creates class objects that connect to the raspberry pi itself DB */
/* Use the same DB object, no matter which file in the View file you access */
public class SingleClass_raspi {
    private static  SingleClass_raspi            single_rasp = null;
    public          DB_raspi                     model_raspi;
    
    private SingleClass_raspi () {
        model_raspi      = new DB_raspi();
    }
    
    public static SingleClass_raspi getInstance () {
        if (single_rasp == null) {
            single_rasp = new SingleClass_raspi();
        }
        
        return single_rasp;
    }
}
