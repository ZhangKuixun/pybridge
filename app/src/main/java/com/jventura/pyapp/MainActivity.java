package com.jventura.pyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jventura.pybridge.AssetExtractor;
import com.jventura.pybridge.PyBridge;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String python = "python";
        // 从 assets 中提取Python文件
        AssetExtractor assetExtractor = new AssetExtractor(this);
        assetExtractor.removeAssets(python);
        assetExtractor.copyAssets(python);

        // Get the extracted assets directory
        String pythonPath = assetExtractor.getAssetsDataDir() + python;


        // 启动Python解释器
        PyBridge.start(pythonPath);

        // 调用Python函数
        try {
            JSONObject json = new JSONObject();
            json.put("function", "greet");
            json.put("name", "Python 3.5");
            JSONObject result = PyBridge.call(json);

////            Log.d("kevin", "to json: " + "https://www.youtube.com/watch?v=TjaM0tdxtYA");
//            String call = PyBridge.call("https://www.youtube.com/watch?v=TjaM0tdxtYA");
//            JSONObject result;
//            try {
//                result = new JSONObject(call);
//            } catch (JSONException e) {
//                e.printStackTrace();
//                return;
//            }

            Log.d("kevin", "from json: " + result.toString());
            String answer = result.getString("result");

            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(answer);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Stop the interpreter
        PyBridge.stop();
    }
}
