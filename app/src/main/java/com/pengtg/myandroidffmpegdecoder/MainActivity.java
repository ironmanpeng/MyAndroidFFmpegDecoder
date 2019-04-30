package com.pengtg.myandroidffmpegdecoder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private static String TAG = "myandroidffmpegdecoder";

    /** 原始的文件路径 **/
    private static String mp3FilePath = "/mnt/sdcard/Music/131.mp3";
    /** 解码后的PCM文件路径 **/
    private static String pcmFilePath = "/mnt/sdcard/Music/131.pcm";

    private Button mp3_encoder_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp3_encoder_btn = (Button) findViewById(R.id.mp3_encoder_btn);
        mp3_encoder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long startTimeMills = System.currentTimeMillis();
                Mp3Decoder decoder = new Mp3Decoder();
                int ret = decoder.init(mp3FilePath, pcmFilePath);
                if(ret >= 0) {
                    Log.i(TAG, "Decoder Initialized Success...");
                    decoder.decode();
                    decoder.destroy();
                } else {
                    Log.i(TAG, "Decoder Initialized Failed...");
                }
                int wasteTimeMills = (int)(System.currentTimeMillis() - startTimeMills);
                Log.i(TAG, "Decode Mp3 Waste TimeMills : " + wasteTimeMills + "ms");
            }
        });
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
