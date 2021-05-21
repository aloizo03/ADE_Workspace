package com.example.motionet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


// Get openCV camera functions to can access the camera
public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{
    private Button btnCap;
    private Button btnStop;
    private Button btn20frames;

    private static  String TAG = "MainActivity";
    JavaCameraView javaCameraView;
    ArrayList<Mat> frames;

    boolean capture = false;
    private boolean cap20Frames = false;
    long lastTime=0;
    double totalFPS = 0;

    private Context context;
    private motioNet motioNet;
    long start = 0;
    Mat mRGBA, mRGBAT;

    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(MainActivity.this) {
        @Override
        public void onManagerConnected(int status) {
           super.onManagerConnected(status);
            switch (status){
                case BaseLoaderCallback.SUCCESS: {
                    javaCameraView.enableView();
                    break;
                }
                default:{
                    super.onManagerConnected(status);
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        btnCap = (Button) findViewById(R.id.btnCapture);
        btn20frames = (Button) findViewById(R.id.Button20Frames);
        btnStop = (Button) findViewById(R.id.StopBtn);
        javaCameraView = (JavaCameraView) findViewById(R.id.cameraFrames);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(MainActivity.this);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                javaCameraView.setVisibility(SurfaceView.GONE);
                btnStop.setVisibility(Button.INVISIBLE);
                capture = false;
                long duration = System.nanoTime() - start;
                long seconds = TimeUnit.NANOSECONDS.toSeconds(duration);

                totalFPS = frames.size()/seconds;
                if(!cap20Frames){
                    System.out.println("Video length : " + seconds);
                    System.out.println("Total frames per second : " + totalFPS);
                    System.out.println("Array List Size : "+frames.size());
//                openLoadActivity(totalFPS, frames, seconds);
                    motionProcess motion = new motionProcess(frames, context);
                    motion.convertToBitMap();
                    List<posePart> poseParts = motion.findKeyPoints();
                    motioNet = new motioNet(seconds, totalFPS, context);
                    motioNet.algorithm(poseParts);
                    Toast.makeText(MainActivity.this, "BVH File save into : "+motioNet.file, Toast.LENGTH_LONG).show();
                }

                btnCap.setVisibility(Button.VISIBLE);
                btn20frames.setVisibility(Button.VISIBLE);
                javaCameraView.setVisibility(SurfaceView.VISIBLE);
                cap20Frames = false;
                frames = new ArrayList<>();
                totalFPS = 0;
                start = 0;
            }
        });

        btnCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStop.setVisibility(Button.VISIBLE);
                btnCap.setVisibility(Button.INVISIBLE);
                btn20frames.setVisibility(Button.INVISIBLE);
                start = System.nanoTime();
                capture = true;
                frames= new ArrayList<>();

                frames = new ArrayList<>();
                totalFPS = 0;
                start = 0;
            }
        });

        //Add 20 frames
        btn20frames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStop.setVisibility(Button.VISIBLE);
                btnCap.setVisibility(Button.INVISIBLE);
                btn20frames.setVisibility(Button.INVISIBLE);
                start = System.nanoTime();
                cap20Frames = true;
                capture = true;

                frames = new ArrayList<>();
                totalFPS = 0;
                start = 0;
            }
        });
    }



    @Override
    public void onCameraViewStarted(int width, int height) {
        mRGBA = new Mat(height, width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRGBA.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRGBA = inputFrame.rgba();
        mRGBAT = mRGBA.t();
        // Flip the image to get the normal view
        Core.flip(mRGBA.t(), mRGBAT, 1);
//        System.out.println(mRGBA);
        // Resize the image to 720x720p analysis
        Imgproc.resize(mRGBAT, mRGBAT, mRGBA.size());

        // If the capture button has pressed start save frames
        if(capture){
            double fps = 1000000.0 / (lastTime - (lastTime = System.nanoTime()));
            totalFPS += fps;

            System.out.println("Size Image : "+mRGBAT.size());
            System.out.println("Total channels : "+mRGBAT.channels());
            frames.add(mRGBAT);
            if(frames.size() == 20 && cap20Frames){
                findMotion();
            }
        }
        return mRGBAT;
    }

    private void findMotion() {
        long duration = System.nanoTime() - start;
        long seconds = TimeUnit.NANOSECONDS.toSeconds(duration);
        totalFPS = frames.size()/seconds;

        motionProcess motion = new motionProcess(frames, context);
        motion.convertToBitMap();
        List<posePart> poseParts = motion.findKeyPoints();
        motioNet = new motioNet(seconds, totalFPS, context);
        motioNet.algorithm(poseParts);
        frames.clear();
        frames = new ArrayList<>();
        totalFPS = 0;
        start = 0;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(javaCameraView != null){
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(javaCameraView != null){
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCV IS CONFIG OR CONNECTED SUCCESSFUL");
            baseLoaderCallback.onManagerConnected(baseLoaderCallback.SUCCESS);
        }else{
            Log.d(TAG, "OpenCV not working");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, baseLoaderCallback);
        }
    }



}