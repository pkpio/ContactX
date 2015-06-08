package xyz.praveen.contactx;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by praveen on 08.06.15.
 */
public class JsonFactory {

    public static String ContactToJson(String name, String number) {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("name", name);
            jsonObj.put("number", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObj.toString();
    }

    public static String JsonToContact(String json) {
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(json);
            String response = "";
            response += "Name : " + jsonObj.getString("name") + "\n";
            response += "Number : " + jsonObj.getString("number") + "\n";
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
