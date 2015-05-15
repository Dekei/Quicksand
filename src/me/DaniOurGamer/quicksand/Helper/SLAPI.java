package me.DaniOurGamer.quicksand.Helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SLAPI
{
  public static <T> T load(String path)
    throws Exception
  {
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
    T result = ois.readObject();
    ois.close();
    return result;
  }
  
  public static <T> void save(T obj, String path)
    throws Exception
  {
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
    oos.writeObject(obj);
    oos.flush();
    oos.close();
  }
}
