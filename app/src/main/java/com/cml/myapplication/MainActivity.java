package com.cml.myapplication;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cml.myapplication.andfix.AndfixPatchManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

//storage/emulated/0/Android/data/com.cml.myapplication/cache/apatch
public class MainActivity extends AppCompatActivity {

    private static final String FILE_END = ".apatch";
    private String mPatchDir ;


    private TextView mTvSpeed;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    mTvSpeed.setText("network: "+msg.obj.toString());
                    break;
            }
        }
    };
    private Runtime runtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        andfix();

//        mTvSpeed = findViewById(R.id.tv_speed);
//        new NetWorkUtils(this,handler).startShowNetSpeed();

        runtime = Runtime.getRuntime();
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                ping();
//            }
//        },0,10000);
    }

    private void andfix() {
        mPatchDir = getExternalCacheDir().getAbsolutePath()+"/apatch/";
        File file = new File(mPatchDir);
        if(file == null || !file.exists()){
            file.mkdir();
        }
    }

    public void createBug(View view){
        String printMsg = "bug fix";
        Toast.makeText(this, printMsg, Toast.LENGTH_SHORT).show();

    }

    public void fixBug(View view){
        AndfixPatchManager.getInstance().addPatch(mPatchDir.concat("cml").concat(FILE_END));
    }


    private void ping(){
        try {
            Process p = runtime.exec("ping -c 5 www.baidu.com");
            int ret = p.waitFor();

            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = new String();
            StringBuilder stringBuilder = new StringBuilder();
            // 读出全部信息并显示
            while ((str = buf.readLine()) != null) {
                str = str + "\r\n";
                stringBuilder.append(str);
            }


            Log.e("Cml", "Process:"+ret+stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class NetWorkUtils{

        private Context context;
        private Handler handler;
        private long lastTotalRxBytes;
        private long lastTimeStamp;

        public NetWorkUtils(Context context, Handler handler) {
            this.context = context;
            this.handler = handler;
        }

        public void startShowNetSpeed(){
            lastTotalRxBytes = getTotalRxBytes();
            lastTimeStamp = System.currentTimeMillis();
            new Timer().schedule(timerTask,1000,1000);
        }

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                showNetSpeed();
            }
        };

        private void showNetSpeed() {
            long nowTotalRxBytes = getTotalRxBytes();
            long nowTimeStamp = System.currentTimeMillis();
            long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
            long speed2 = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp));//毫秒转换

            lastTimeStamp = nowTimeStamp;
            lastTotalRxBytes = nowTotalRxBytes;

            Message msg = handler.obtainMessage();
            msg.what = 100;
            msg.obj = String.valueOf(speed) + "." + String.valueOf(speed2) + " kb/s";
            handler.sendMessage(msg);
        }

        private long getTotalRxBytes(){
            return TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 :
                    TrafficStats.getTotalRxBytes() / 1024;
        }
    }
}
