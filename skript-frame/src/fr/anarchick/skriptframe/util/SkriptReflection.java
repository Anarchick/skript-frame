package fr.anarchick.skriptframe.util;

import ch.njol.skript.Skript;
import ch.njol.skript.variables.Variables;
import org.bukkit.event.Event;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class SkriptReflection {

  private static Field LOCAL_VARIABLES;
  private static Field VARIABLES_MAP_HASHMAP;
  private static Field VARIABLES_MAP_TREEMAP;
  @SuppressWarnings("rawtypes")
private static Constructor VARIABLES_MAP;

  static {
    Field _FIELD;
    @SuppressWarnings("rawtypes")
	Constructor _CONSTRUCTOR;

    try {
      _FIELD = Variables.class.getDeclaredField("localVariables");
      _FIELD.setAccessible(true);
      LOCAL_VARIABLES = _FIELD;
    } catch (NoSuchFieldException e) {
      Skript.warning("Skript's local variables field could not be resolved.");
    }

    try {
      Class<?> variablesMap = Class.forName("ch.njol.skript.variables.VariablesMap");

      try {
        _FIELD = variablesMap.getDeclaredField("hashMap");
        _FIELD.setAccessible(true);
        VARIABLES_MAP_HASHMAP = _FIELD;
      } catch (NoSuchFieldException e) {
        Skript.warning("Skript's hash map field could not be resolved.");
      }

      try {
        _FIELD = variablesMap.getDeclaredField("treeMap");
        _FIELD.setAccessible(true);
        VARIABLES_MAP_TREEMAP = _FIELD;
      } catch (NoSuchFieldException e) {
        Skript.warning("Skript's tree map field could not be resolved.");
      }

      try {
        _CONSTRUCTOR = variablesMap.getDeclaredConstructor();
        _CONSTRUCTOR.setAccessible(true);
        VARIABLES_MAP = _CONSTRUCTOR;
      } catch (NoSuchMethodException e) {
        Skript.warning("Skript's variables map constructors could not be resolved.");
      }
    } catch (ClassNotFoundException e) {
      Skript.warning("Skript's variables map class could not be resolved.");
    }

  }

  /**
   * Sets the local variables of an {@link Event} to the given local variables.
   */
  @SuppressWarnings("unchecked")
  public static void putLocals(Object originalVariablesMap, Event to) {
    if (originalVariablesMap == null)
      removeLocals(to);

    try {
      Map<Event, Object> localVariables = (Map<Event, Object>) LOCAL_VARIABLES.get(null);

      localVariables.put(to, originalVariablesMap);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * Removes and returns the local variables from the given {@link Event}.
   */
  @SuppressWarnings("unchecked")
  public static Object removeLocals(Event event) {
    try {
      Map<Event, Object> localVariables = (Map<Event, Object>) LOCAL_VARIABLES.get(null);
      return localVariables.remove(event);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Retrieves the local variables from an {@link Event}.
   * @param event The {@link Event} to get the local variables from.
   * @return The local variables of the given {@link Event}.
   */
  @SuppressWarnings("unchecked")
  public static Object getLocals(Event event) {
    try {
      Map<Event, Object> localVariables = (Map<Event, Object>) LOCAL_VARIABLES.get(null);
      return localVariables.get(event);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Copies the VariablesMap contained in the given {@link Object}.
   * @param locals The local variables to copy.
   * @return The copied local variables.
   */
  @SuppressWarnings("unchecked")
  public static Object copyLocals(Object locals) {
    if (locals == null)
      return null;

    try {
      Object copiedLocals = VARIABLES_MAP.newInstance();

      ((Map<String, Object>) VARIABLES_MAP_HASHMAP.get(copiedLocals))
        .putAll((Map<String, Object>) VARIABLES_MAP_HASHMAP.get(locals));
      ((Map<String, Object>) VARIABLES_MAP_TREEMAP.get(copiedLocals))
        .putAll((Map<String, Object>) VARIABLES_MAP_TREEMAP.get(locals));
      return copiedLocals;
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

}