package net.simplyrin.config.test;

import net.simplyrin.json.Json;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TestJson {

    public static void main(String[] args) {
        Json json = new Json();

        json.set("Key.String", "String");
        json.set("Key.Int", Integer.MAX_VALUE);
        json.set("Key.Long", Long.MAX_VALUE);
        json.set("Key.Float", Float.MAX_VALUE);
        json.set("Key.Double", Double.MAX_VALUE);
        json.set("Key.Boolean.True", true);
        json.set("Key.Boolean.False", false);

        json.setStringList("List.String", Arrays.asList("abc", "xyz"));
        json.setIntList("List.Int", Arrays.asList(1234, 5678));

        System.out.println("String: " + json.getString("Key.String"));
        System.out.println("Int: " + json.getString("Key.Int"));
        System.out.println("Long: " + json.getString("Key.Long"));
        System.out.println("Float: " + json.getString("Key.Float"));
        System.out.println("Double: " + json.getString("Key.Double"));
        System.out.println("Boolean.True: " + json.getString("Key.Boolean.True"));
        System.out.println("Boolean.False: " + json.getString("Key.Boolean.False"));

        List<String> stringList = json.getStringList("List.String");
        for (String value : stringList) {
            System.out.println("String: " + value);
        }

        List<Integer> intList = json.getIntList("List.Int");
        for (Integer value : intList) {
            System.out.println("Int: " + value);
        }

        json.saveJson(new File("test.json"));
    }

}
