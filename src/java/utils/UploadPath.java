/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ResourceBundle;

/**
 *
 * @author pupil
 */
public class UploadPath {
   private final static ResourceBundle rb = ResourceBundle.getBundle("resources.pathToUploadFolder");
   public  static String getPath(String key){
       return rb.getString(key);
   }
}
