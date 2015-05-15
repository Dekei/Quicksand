package me.DaniOurGamer.quicksand.Helper;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

public class Metadata
{
  private static Plugin plugin;
  
  public static Boolean asBoolean(Metadatable entity, String key)
  {
    MetadataValue value = getValue(entity, key);
    return value == null ? null : Boolean.valueOf(value.asBoolean());
  }
  
  public static Double asDouble(Metadatable entity, String key)
  {
    MetadataValue value = getValue(entity, key);
    return value == null ? null : Double.valueOf(value.asDouble());
  }
  
  public static Integer asInt(Metadatable entity, String key)
  {
    MetadataValue value = getValue(entity, key);
    return value == null ? null : Integer.valueOf(value.asInt());
  }
  
  public static Long asLong(Metadatable entity, String key)
  {
    MetadataValue value = getValue(entity, key);
    return value == null ? null : Long.valueOf(value.asLong());
  }
  
  public static String asString(Metadatable entity, String key)
  {
    MetadataValue value = getValue(entity, key);
    return value == null ? null : value.asString();
  }
  
  public static List<String> asStringList(Metadatable entity, String key)
  {
    MetadataValue value = getValue(entity, key);
    return value == null ? new ArrayList() : (List)value;
  }
  
  public static Object get(Metadatable entity, String key)
  {
    MetadataValue value = getValue(entity, key);
    return value == null ? null : value.value();
  }
  
  public static boolean isset(Metadatable entity, String key)
  {
    return entity.hasMetadata(key);
  }
  
  public static void remove(Metadatable entity, String key)
  {
    entity.removeMetadata(key, plugin);
  }
  
  public static void set(Metadatable entity, String key, Object value)
  {
    entity.setMetadata(key, new FixedMetadataValue(plugin, value));
  }
  
  public static void setPlugin(Plugin plugin)
  {
    plugin = plugin;
  }
  
  private static MetadataValue getValue(Metadatable entity, String key)
  {
    if (!entity.hasMetadata(key)) {
      return null;
    }
    String pluginName = plugin.getName();
    for (MetadataValue value : entity.getMetadata(key)) {
      if (value.getOwningPlugin().getName().equals(pluginName)) {
        return value;
      }
    }
    return null;
  }
}
