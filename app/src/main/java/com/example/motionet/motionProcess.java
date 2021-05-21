package com.example.motionet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;

import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

import org.tensorflow.lite.examples.posenet.lib.KeyPoint;
import org.tensorflow.lite.examples.posenet.lib.Person;


public class motionProcess extends Fragment {
    int modelWidth = 257, modelHeight = 257;

    private ArrayList<Mat> frames;
    private ArrayList<Bitmap> framesBitmap;
    private ArrayList<posePart> poses;
    private pose p;
    Context context;

    public motionProcess(ArrayList<Mat> framesOpenCV, Context pContext){
        this.frames = framesOpenCV;
        this.framesBitmap = new ArrayList<>();
        p = new pose();
        context = pContext;
        poses = new ArrayList<>();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void convertToBitMap() {
        for(int i =0; i < frames.size(); i++){
            Mat frame = frames.get(i);

            try {
                Bitmap bmp = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(frame, bmp);

                Bitmap bitmap = p.cropBitmap(bmp);
                bitmap = Bitmap.createScaledBitmap(bitmap, modelWidth, modelHeight, true);

                framesBitmap.add(bitmap);

            }catch (CvException e){
                Log.d("Exception",e.getMessage());
            }
        }

    }


    public ArrayList<Mat> getFrames() {
        return frames;
    }

    public ArrayList<Bitmap> getFramesBitmap() {
        return framesBitmap;
    }

    public List<posePart> findKeyPoints(){
        List<posePart> poseParts = new ArrayList<>();
//        double yMax = 0;
        int totalCount = 0;
        double score = 0;
        for(int i = 0; i < getFramesBitmap().size(); i++){
            Bitmap frame = framesBitmap.get(i);
            Person person =  p.calculateKeyPoint(frame, context);
            List<KeyPoint> keyPoints= person.getKeyPoints();
            posePart pp = new posePart();

            double totalScore = 0;
            for(int j=0; j < keyPoints.size(); j++){
               KeyPoint kp = keyPoints.get(j);
                // Set new key points
               if(kp.getBodyPart().toString() == "NOSE"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setNOSE(pos);
               }else if(kp.getBodyPart().toString() == "LEFT_EYE"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setLEFT_EYE(pos);
               }else if(kp.getBodyPart().toString() == "RIGHT_EYE"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setRIGHT_EYE(pos);
               }else if(kp.getBodyPart().toString() == "LEFT_EAR"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setLEFT_EAR(pos);
                }
               else if(kp.getBodyPart().toString() == "RIGHT_EAR"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setRIGHT_EAR(pos);
               }else if(kp.getBodyPart().toString() == "LEFT_SHOULDER"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setLEFT_SHOULDER(pos);
               }else if(kp.getBodyPart().toString() == "RIGHT_SHOULDER"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setRIGHT_SHOULDER(pos);
               }else if(kp.getBodyPart().toString() == "LEFT_ELBOW"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setLEFT_ELBOW(pos);
               }else if(kp.getBodyPart().toString() == "RIGHT_ELBOW"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setRIGHT_ELBOW(pos);
               }else if(kp.getBodyPart().toString() == "LEFT_WRIST"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setLEFT_WRIST(pos);
               }else if(kp.getBodyPart().toString() == "RIGHT_WRIST"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setRIGHT_WRIST(pos);
               }else if(kp.getBodyPart().toString() == "LEFT_HIP"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setLEFT_HIP(pos);
               }else if(kp.getBodyPart().toString() == "RIGHT_HIP"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setRIGHT_HIP(pos);
               }else if(kp.getBodyPart().toString() == "LEFT_KNEE"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setLEFT_KNEE(pos);
               }else if(kp.getBodyPart().toString() == "RIGHT_KNEE"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setRIGHT_KNEE(pos);
               }else if(kp.getBodyPart().toString() == "LEFT_ANKLE"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setLEFT_ANKLE(pos);
               }else if(kp.getBodyPart().toString() == "RIGHT_ANKLE"){
                   int x = kp.getPosition().getX();
                   int y = kp.getPosition().getY();

                   totalScore += kp.getScore();
                   position pos = new position();
                   pos.setX(x);
                   pos.setY(y);
                   pos.setAngle();
                   pp.setRIGHT_ANKLE(pos);
               }
            }
            double thresholdLLeg = Math.sqrt(Math.pow(pp.LEFT_KNEE.getX() - pp.LEFT_ANKLE.getX(), 2) + Math.pow(pp.LEFT_KNEE.getY() - pp.LEFT_ANKLE.getY(), 2));
            double thresholdRLeg = Math.sqrt(Math.pow(pp.RIGHT_KNEE.getX() - pp.RIGHT_ANKLE.getX(), 2) + Math.pow(pp.RIGHT_KNEE.getY() - pp.RIGHT_ANKLE.getY(), 2));

            thresholdLLeg = thresholdLLeg / 2;
            thresholdRLeg = thresholdRLeg / 2;
            // Check foot contact labels of each frame with the previous frames
            if(totalCount == 0){
                pp.setLeftFootContactLabel(true);
                pp.setRightFootContactLabel(true);
//                yMax = (pp.LEFT_ANKLE.getY() + pp.RIGHT_ANKLE.getY())/2;
            }else{
                posePart ppBefore = poseParts.get(totalCount - 1);
                double leftFoot = ppBefore.getLEFT_ANKLE().getY() - pp.getLEFT_ANKLE().getY();
                double rightFoot = ppBefore.getLEFT_ANKLE().getY() - pp.getLEFT_ANKLE().getY();
                if(!(leftFoot <= thresholdLLeg && leftFoot > -thresholdLLeg)) {
                    if (leftFoot > thresholdLLeg) {
                        pp.setLeftFootContactLabel(false);
                        if (ppBefore.isLeftFootContactLabel()) {
                            ppBefore.setLeftFootContactLabel(true);
                        } else {
                            ppBefore.setLeftFootContactLabel(false);
                        }
                    } else if (leftFoot <= -thresholdLLeg){
                        pp.setLeftFootContactLabel(true);
                        if (!ppBefore.isLeftFootContactLabel())
                            ppBefore.setLeftFootContactLabel(true);
                        else
                            ppBefore.setLeftFootContactLabel(false);
                    }else{
                        pp.setLeftFootContactLabel(true);
                        ppBefore.setLeftFootContactLabel(true);
                    }
                }else{
                    if(ppBefore.isLeftFootContactLabel())
                        pp.setLeftFootContactLabel(true);
                    else
                        pp.setLeftFootContactLabel(false);
                }
                if(!(rightFoot <= thresholdRLeg && rightFoot > -thresholdRLeg)) {
                    if (rightFoot > thresholdRLeg) {
                        pp.setRightFootContactLabel(false);
                        if (ppBefore.isLeftFootContactLabel()) {
                            ppBefore.setLeftFootContactLabel(true);
                        } else {
                            ppBefore.setLeftFootContactLabel(false);
                        }
                    } else if (rightFoot <= -thresholdRLeg) {
                        pp.setRightFootContactLabel(true);
                        if (!ppBefore.isLeftFootContactLabel())
                            ppBefore.setRightFootContactLabel(true);
                        else
                            ppBefore.setRightFootContactLabel(false);
                    } else {
                        pp.setRightFootContactLabel(true);
                        ppBefore.setRightFootContactLabel(true);
                    }
                }else{
                    if(ppBefore.isRightFootContactLabel())
                        pp.setRightFootContactLabel(true);
                    else
                        pp.setRightFootContactLabel(false);
                }
                poseParts.set(totalCount-1, ppBefore);
//                System.out.println("Left contact : "+ ppBefore.isLeftFootContactLabel());
//                System.out.println("RIGHT contact : "+ ppBefore.isRightFootContactLabel());
            }
            totalScore = totalScore/keyPoints.size();
//            System.out.println(totalScore);
            if(totalScore > 0.6){
                totalCount += 1;
                score+=totalScore;
                pp.setROOT_POSITION(pp.LEFT_HIP, pp.RIGHT_HIP);
                pp.ROOT_POSITION.setAngle();
                pp.setCHEST(pp.LEFT_SHOULDER, pp.RIGHT_SHOULDER);
                pp.CHEST.setAngle();
                poseParts.add(pp);
//                System.out.println(pp.toString());
            }


        }

        System.out.println("Average score : " + (score/totalCount));
        System.out.println("Confident value size : "+poseParts.size());
        return poseParts;
    }

}
