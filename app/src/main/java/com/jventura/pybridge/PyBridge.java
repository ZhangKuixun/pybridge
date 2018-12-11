package com.jventura.pybridge;

import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;


public class PyBridge {

    /**
     * Initializes the Python interpreter.
     *
     * @param datapath the location of the extracted python files
     * @return error code
     */
    public static native int start(String datapath);

    /**
     * Stops the Python interpreter.
     *
     * @return error code
     */
    public static native int stop();

    /**
     * Sends a string payload to the Python interpreter.
     *
     * @param payload the payload string
     * @return a string with the result
     */
    public static native String call(String payload);

    /**
     * Sends a JSON payload to the Python interpreter.
     *
     * @param payload JSON payload
     * @return JSON response
     */
    public static JSONObject call(JSONObject payload) {
        try {
            String[] abis = Build.SUPPORTED_ABIS;
            payload.put("architecture", abis[0]);
            String result = call(payload.toString());
            return new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Load library
    static {
        System.loadLibrary("pybridge");
//        System.loadLibrary("python3.5m");
//        System.loadLibrary("crystax");
    }
}
