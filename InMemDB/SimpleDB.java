import java.util.HashMap;
import java.util.Map;


public class SimpleDB {

    Map<String, Integer> keyValueMap = new HashMap<>();

    Map<Integer, Integer> valueCountMap = new HashMap<>();

    public Integer get(String key) {
        if (keyValueMap.containsKey(key)) {
            return keyValueMap.get(key);
        } else {
            return null;
        }
    }

    public void set(String key, int value) {
        if (keyValueMap.containsKey(key)) {

            //remove old value
            int oldKeyValue = keyValueMap.get(key);
            decreaseValue(oldKeyValue);

        }

        // put new value
        increaseValue(value);

        keyValueMap.put(key, value);

    }

    public void unset(String key) {
        if (keyValueMap.containsKey(key)) {
            int value = keyValueMap.get(key);

            keyValueMap.remove(key);
            decreaseValue(value);
        }
    }

    private void decreaseValue(int value) {
        if (!valueCountMap.containsKey(value)) {
            return;
        }

        int count = valueCountMap.get(value);
        if (count == 1) {
            valueCountMap.remove(value);
        } else {
            valueCountMap.put(value, count - 1);
        }
    }


    private void increaseValue(int value) {
        if (!valueCountMap.containsKey(value)) {
            valueCountMap.put(value, 1);
        } else {
            int count = valueCountMap.get(value);
            valueCountMap.put(value, count + 1);
        }
    }

    public int numEqualTo(int value) {
        if (valueCountMap.get(value) == null) {
            return 0;
        } else {
            return valueCountMap.get(value);
        }
    }

    public String findInvertOf(String cmd) {
        String[] splited = cmd.split("\\s+");
        switch (splited[0]) {
            case "set":
                if (!keyValueMap.containsKey(splited[1])) {
                    return "unset " + splited[1];
                } else {
                    int oldValue = keyValueMap.get(splited[1]);
                    return "set " + splited[1] + " " + oldValue;
                }

            case "unset":
                if (keyValueMap.containsKey(splited[1])) {
                    int oldValue = keyValueMap.get(splited[1]);
                    return "set " + splited[1] + " " + oldValue;
                } else {
                    return null;
                }

        }
        return null;
    }

}
