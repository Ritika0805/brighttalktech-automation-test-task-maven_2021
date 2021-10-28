package AutomationTest.BrightTalkTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class JSONParser {

    HashMap<String, List<String>> map = new HashMap<String, List<String>>();

    public void jsonParsing(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            final JsonNode rootNode = mapper.readTree(jsonString);
            parseJson(rootNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getValue(Integer index, String key) {
        return map.get(key).get(index);
    }

    public List<String> getValue(String key) {
        return map.get(key);
    }

    /**
     * parse json recursively  add all the field as a key in hashmap and values stored as list
     */
    private void parseJson(JsonNode rootNode) {

        try {
            if (rootNode.isArray() && rootNode.isContainerNode()) {
                for (JsonNode objNode : rootNode) {
                    parseJson(objNode);
                }

            } else if (rootNode.isContainerNode()) {

                Iterator<String> childNode = rootNode.fieldNames();

                while (childNode.hasNext()) {
                    String key = childNode.next();

                    if (!rootNode.path(key).isValueNode()) {
                        parseJson(rootNode.path(key));

                    } else {
                        if (map.containsKey(key)) {
                            map.get(key).add(rootNode.path(key).asText());
                        } else {
                            List<String> value = new ArrayList<String>();
                            value.add(rootNode.path(key).asText());
                            map.put(key, value);
                        }

                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Invalid field or index");

        }

    }

}