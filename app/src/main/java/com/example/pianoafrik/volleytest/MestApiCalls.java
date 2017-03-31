package com.example.pianoafrik.volleytest;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by pianoafrik on 3/31/17.
 */

public class MestApiCalls {

    String response;


    public  MestApiCalls(String response) {
        this.response = response;
    }

    public JSONArray convertResponseToArray (){

        JSONArray jsonArray ;

        try {
            jsonArray = new JSONArray(this.response);
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }

      return null;

    }


}
