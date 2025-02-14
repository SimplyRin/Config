package net.simplyrin.json;

import com.google.gson.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.simplyrin.config.Configuration;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  Copyright 2025 SimplyRin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Json {

    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    public static Json getJson(File file) {
        try (FileReader reader = new FileReader(file)) {
            return new Json(JsonParser.parseReader(reader).getAsJsonObject());
        } catch (IllegalStateException e) {
            return new Json(new JsonObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean saveJson(JsonObject jsonObject, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(jsonObject, writer);
            return true;
        } catch (Exception e) {
        }

        return false;
    }

    private final JsonObject jsonObject;

    public Json() {
        this.jsonObject = new JsonObject();
    }

    public Json(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JsonObject getAsJsonObject() {
        return this.jsonObject;
    }

    public boolean saveJson(File file) {
        return Json.saveJson(this.jsonObject, file);
    }

    public String getString(String key) {
        return this.getString(key, "");
    }

    public String getString(String key, String def) {
        JsonElement element = this.getElement(key);
        return (element != null && element.isJsonPrimitive()) ? element.getAsString() : def;
    }

    public List<String> getStringList(String key) {
        return this.getStringList(key, Collections.emptyList());
    }

    public List<String> getStringList(String key, List<String> def) {
        JsonElement element = this.getElement(key);
        if (element != null && element.isJsonArray()) {
            JsonArray jsonArray = element.getAsJsonArray();
            List<String> list = new ArrayList<>();
            for (JsonElement e : jsonArray) {
                if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isString()) {
                    list.add(e.getAsString());
                }
            }
            return list;
        }
        return def;
    }

    public List<Integer> getIntList(String key) {
        return this.getIntList(key, Collections.emptyList());
    }

    public List<Integer> getIntList(String key, List<Integer> def) {
        JsonElement element = this.getElement(key);
        if (element != null && element.isJsonArray()) {
            JsonArray jsonArray = element.getAsJsonArray();
            List<Integer> list = new ArrayList<>();
            for (JsonElement e : jsonArray) {
                if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isNumber()) {
                    list.add(e.getAsInt());
                }
            }
            return list;
        }
        return def;
    }

    public int getInt(String key) {
        return this.getInt(key, 0);
    }

    public int getInt(String key, int def) {
        JsonElement element = this.getElement(key);
        return (element != null && element.isJsonPrimitive()) ? element.getAsInt() : def;
    }

    public long getLong(String key) {
        return this.getLong(key, 0L);
    }

    public long getLong(String key, long def) {
        JsonElement element = this.getElement(key);
        return (element != null && element.isJsonPrimitive()) ? element.getAsLong() : def;
    }

    public double getDouble(String key) {
        return this.getDouble(key, 0D);
    }

    public double getDouble(String key, double def) {
        JsonElement element = this.getElement(key);
        return (element != null && element.isJsonPrimitive()) ? element.getAsDouble() : def;
    }

    public float getFloat(String key) {
        return this.getFloat(key, 0F);
    }

    public float getFloat(String key, float def) {
        JsonElement element = this.getElement(key);
        return (element != null && element.isJsonPrimitive()) ? element.getAsFloat() : def;
    }

    public boolean getBoolean(String key) {
        return this.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        JsonElement element = this.getElement(key);
        return (element != null && element.isJsonPrimitive()) ? element.getAsBoolean() : def;
    }

    public JsonElement getElement(String key) {
        String[] keys = key.split("\\."); // ドットでキーを分割
        JsonElement element = this.jsonObject;

        for (String k : keys) {
            if (element != null && element.isJsonObject()) {
                element = element.getAsJsonObject().get(k);
            } else {
                return null; // パスが正しくない場合
            }
        }

        return element;
    }

    // String
    public void set(String key, String value) {
        this.addPropertyHelper(key, value);
    }

    // int
    public void set(String key, int value) {
        this.addPropertyHelper(key, value);
    }

    // long
    public void set(String key, long value) {
        this.addPropertyHelper(key, value);
    }

    // double
    public void set(String key, double value) {
        this.addPropertyHelper(key, value);
    }

    // float
    public void set(String key, float value) {
        this.addPropertyHelper(key, value);
    }

    // boolean
    public void set(String key, boolean value) {
        this.addPropertyHelper(key, value);
    }

    public void set(String key, JsonObject value) {
        this.addPropertyHelper(key, value);
    }

    public void set(String key, JsonArray value) {
        this.addPropertyHelper(key, value);
    }

    public void set(String key, JsonElement value) {
        this.addPropertyHelper(key, value);
    }

    public void setStringList(String key, List<String> value) {
        JsonArray jsonArray = new JsonArray();
        for (String item : value) {
            jsonArray.add(item);
        }
        this.addPropertyHelper(key, jsonArray);
    }

    public void setIntList(String key, List<Integer> value) {
        JsonArray jsonArray = new JsonArray();
        for (int item : value) {
            jsonArray.add(item);
        }
        this.addPropertyHelper(key, jsonArray);
    }

    // 実際にプロパティを追加するヘルパーメソッド
    private void addPropertyHelper(String key, Object value) {
        String[] keys = key.split("\\."); // ドットでキーを分割
        JsonObject currentObject = this.getJsonObject(keys);

        // 最後のキーでプロパティを追加
        if (value instanceof String) {
            currentObject.addProperty(keys[keys.length - 1], (String) value);
        } else if (value instanceof Integer) {
            currentObject.addProperty(keys[keys.length - 1], (Integer) value);
        } else if (value instanceof Long) {
            currentObject.addProperty(keys[keys.length - 1], (Long) value);
        } else if (value instanceof Double) {
            currentObject.addProperty(keys[keys.length - 1], (Double) value);
        } else if (value instanceof Float) {
            currentObject.addProperty(keys[keys.length - 1], (Float) value);
        } else if (value instanceof Boolean) {
            currentObject.addProperty(keys[keys.length - 1], (Boolean) value);
        } else if (value instanceof JsonObject) {
            currentObject.add(keys[keys.length - 1], (JsonObject) value);
        } else if (value instanceof JsonArray) {
            currentObject.add(keys[keys.length - 1], (JsonArray) value);
        } else if (value instanceof JsonElement) {
            currentObject.add(keys[keys.length - 1], (JsonElement) value);
        }
    }

    public void remove(String key) {
        String[] keys = key.split("\\."); // ドットでキーを分割
        JsonObject currentObject = this.jsonObject;

        for (int i = 0; i < keys.length - 1; i++) {
            String currentKey = keys[i];
            JsonElement element = currentObject.get(currentKey);

            if (element != null && element.isJsonObject()) {
                currentObject = element.getAsJsonObject(); // 次の JsonObject に進む
            } else {
                return; // キーが存在しない場合、何もしない
            }
        }

        // 最後のキーを削除
        currentObject.remove(keys[keys.length - 1]);
    }

    private JsonObject getJsonObject(String[] keys) {
        JsonObject currentObject = this.jsonObject;

        for (int i = 0; i < keys.length - 1; i++) {
            String currentKey = keys[i];
            JsonElement element = currentObject.get(currentKey);

            // 現在のキーが JsonObject でなければ新しく JsonObject を作成
            if (element == null || !element.isJsonObject()) {
                JsonObject newObject = new JsonObject();
                currentObject.add(currentKey, newObject);
                currentObject = newObject; // 新し いJsonObject に移動
            } else {
                currentObject = element.getAsJsonObject(); // 既存の JsonObject に移動
            }
        }
        return currentObject;
    }


}
