package com.example.motionet;

import android.content.Context;
import android.os.Environment;
import android.text.format.DateFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Math;
import org.jblas.*;


public class motioNet {

    List<Bones> bonesLength;
    List<posePart> T_Pose;
    List<posePart> poseEstimation;
    double duration;
    double FPS;
    Context context;
    String file;
    String output;

    public motioNet(long seconds, double totalFPS, Context cont){
        duration = seconds;
        FPS = totalFPS;
        context = cont;
        bonesLength = new ArrayList<>();
        output = "";
    }

    public void algorithm(List<posePart> poseParts){
        estimateEs(poseParts);
        poseEstimation = forwardKinematic(poseParts);
        createBVH();
    }

    private void createBVH() {

        Date d = new Date();
        CharSequence s = DateFormat.format("dd/MM/yyyy HH:mm:ss", d.getTime());
        int random = (int) (Math.random() * 100000);
        String filename = "dataMotion_" + random + ".bvh";
        file = filename;
        posePart pose = poseEstimation.get(0);
        double frameTime = duration * FPS;
        frameTime = duration / frameTime;
        output+="HIERARCHY\n";
        output+="ROOT Hips\n";
        output+="{\n";
        output+="\tOFFSET 0.000000 0.000000 0.000000\n" +
                "\tCHANNELS 6 Xposition Yposition Zposition Zrotation Yrotation Xrotation \n" +
                "\tJOINT RightUpLeg\n" +
                "\t{\n" +
                "\t\tOFFSET -2.565358 0.000000 0.000000\n" +
                "\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\tJOINT RightLeg\n" +
                "\t\t{\n" +
                "\t\t\tOFFSET 0.000000 -9.283620 0.000000\n" +
                "\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\tJOINT RightFoot\n" +
                "\t\t\t{\n" +
                "\t\t\t\tOFFSET 0.000000 -9.237254 0.000000\n" +
                "\t\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\t\tEnd Site\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\tOFFSET 0.000000 0.000000 0.000000\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\tJOINT LeftUpLeg\n" +
                "\t{\n" +
                "\t\tOFFSET 2.565358 0.000000 0.000000\n" +
                "\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\tJOINT LeftLeg\n" +
                "\t\t{\n" +
                "\t\t\tOFFSET 0.000000 -9.283620 0.000000\n" +
                "\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\tJOINT LeftFoot\n" +
                "\t\t\t{\n" +
                "\t\t\t\tOFFSET 0.000000 -9.237254 0.000000\n" +
                "\t\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\t\tEnd Site\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\tOFFSET 0.000000 0.000000 0.000000\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\tJOINT Spine\n" +
                "\t{\n" +
                "\t\tOFFSET 0.000000 5.069166 0.000000\n" +
                "\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\tJOINT Spine1\n" +
                "\t\t{\n" +
                "\t\t\tOFFSET 0.000000 4.968125 0.000000\n" +
                "\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\tJOINT Neck\n" +
                "\t\t\t{\n" +
                "\t\t\t\tOFFSET 0.000000 2.358147 0.000000\n" +
                "\t\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\t\tJOINT Head\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\tOFFSET 0.000000 2.282368 0.000000\n" +
                "\t\t\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\t\t\tEnd Site\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\tOFFSET 0.000000 0.000000 0.000000\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t\tJOINT LeftArm\n" +
                "\t\t\t{\n" +
                "\t\t\t\tOFFSET 3.535219 0.000000 0.000000\n" +
                "\t\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\t\tJOINT LeftForeArm\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\tOFFSET 5.759970 0.000000 0.000000\n" +
                "\t\t\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\t\t\tJOINT LeftHand\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\tOFFSET 4.940773 0.000000 0.000000\n" +
                "\t\t\t\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\t\t\t\tEnd Site\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\tOFFSET 0.000000 0.000000 0.000000\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t\tJOINT RightArm\n" +
                "\t\t\t{\n" +
                "\t\t\t\tOFFSET -3.535219 0.000000 0.000000\n" +
                "\t\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\t\tJOINT RightForeArm\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\tOFFSET -5.759970 0.000000 0.000000\n" +
                "\t\t\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\t\t\tJOINT RightHand\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\tOFFSET -4.940773 0.000000 0.000000\n" +
                "\t\t\t\t\t\tCHANNELS 3 Zrotation Yrotation Xrotation\n" +
                "\t\t\t\t\t\tEnd Site\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\tOFFSET 0.000000 0.000000 0.000000\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}\n";
        output+="MOTION\nFrames: "+(poseEstimation.size()+1)+"\n" +
                "Frame Time: "+frameTime+"\n";
        output+="0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 \n";
        for(int i = 0; i < poseEstimation.size(); i++){
            pose = poseEstimation.get(i);
            output+=pose.ROOT_POSITION.getX()/257+" "+pose.ROOT_POSITION.getY()/257+" "+pose.ROOT_POSITION.getZ()/257+" "+pose.ROOT_POSITION.getRotationZ()+" "+pose.ROOT_POSITION.getRotationY()+" "+pose.ROOT_POSITION.getRotationX()+" "+pose.RIGHT_HIP.getRotationZ()+" "+pose.RIGHT_HIP.getRotationY()+" "+pose.RIGHT_HIP.getRotationX()+" "+pose.RIGHT_KNEE.getRotationZ()+" "+pose.RIGHT_KNEE.getRotationY()+" "+pose.RIGHT_KNEE.getRotationX()+" "+pose.RIGHT_ANKLE.getRotationZ()+" "+pose.RIGHT_ANKLE.getRotationY()+" "+pose.RIGHT_ANKLE.getRotationX()+" "+
                    +pose.LEFT_HIP.getRotationZ()+" "+pose.LEFT_HIP.getRotationY()+" "+pose.LEFT_HIP.getRotationX()+" "+pose.LEFT_KNEE.getRotationZ()+" "+pose.LEFT_KNEE.getRotationY()+" "+pose.LEFT_KNEE.getRotationX()+" "+pose.LEFT_ANKLE.getRotationZ()+" "+pose.LEFT_ANKLE.getRotationY()+" "+pose.LEFT_ANKLE.getRotationX()+" "
                    +"0.000000 0.000000 0.000000 "+pose.CHEST.getRotationZ()+" "+pose.CHEST.getRotationY()+" "+pose.CHEST.getRotationX()+" 0.000000 0.000000 0.000000 "+pose.NOSE.getRotationZ()+" "+pose.NOSE.getRotationY()+" "+pose.NOSE.getRotationX()+" "
                    +pose.LEFT_SHOULDER.getRotationZ()+" "+pose.LEFT_SHOULDER.getRotationY()+" "+pose.LEFT_SHOULDER.getRotationX()+" "+pose.LEFT_ELBOW.getRotationZ()+" "+pose.LEFT_ELBOW.getRotationY()+" "+pose.LEFT_ELBOW.getRotationX()+" "+pose.LEFT_WRIST.getRotationZ()+" "+pose.LEFT_WRIST.getRotationY()+" "+pose.LEFT_WRIST.getRotationX()+" "
                    +pose.RIGHT_SHOULDER.getRotationZ()+" "+pose.RIGHT_SHOULDER.getRotationY()+" "+pose.RIGHT_SHOULDER.getRotationX()+" "+pose.RIGHT_ELBOW.getRotationZ()+" "+pose.RIGHT_ELBOW.getRotationY()+" "+pose.RIGHT_ELBOW.getRotationX()+" "+pose.RIGHT_WRIST.getRotationZ()+" "+pose.RIGHT_WRIST.getRotationY()+" "+pose.RIGHT_WRIST.getRotationX()+"\n";
        }
        writeFileOnInternalStorage(filename, s);
    }

    public void writeFileOnInternalStorage(String sFileName, CharSequence s){

        File myDir = new File(Environment.getExternalStorageDirectory() + "/saved_motion_file/"+s.toString());
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        try {
            File gpxfile = new File(myDir, sFileName);
            FileOutputStream fos = new FileOutputStream(gpxfile);
            fos.write(output.getBytes());
            fos.close();
            System.out.println(myDir);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void estimateEs(List<posePart> poseParts) {
        bonesLength = new ArrayList<>();
        T_Pose = new ArrayList<>();
        for(int i = 0; i < poseParts.size(); i++){
            posePart jointRot = poseParts.get(i);
            Bones bones = new Bones();
            double upLArm = Math.sqrt(Math.pow(jointRot.LEFT_ELBOW.getX() - jointRot.LEFT_SHOULDER.getX(), 2) + Math.pow(jointRot.LEFT_ELBOW.getY() - jointRot.LEFT_SHOULDER.getY(), 2));
            double downLArm = Math.sqrt(Math.pow(jointRot.LEFT_WRIST.getX() - jointRot.LEFT_ELBOW.getX(), 2) + Math.pow(jointRot.LEFT_WRIST.getY() - jointRot.LEFT_ELBOW.getY(), 2));

            double upRArm = Math.sqrt(Math.pow(jointRot.RIGHT_ELBOW.getX() - jointRot.RIGHT_SHOULDER.getX(), 2) + Math.pow(jointRot.RIGHT_ELBOW.getY() - jointRot.RIGHT_SHOULDER.getY(), 2));
            double downRArm = Math.sqrt(Math.pow(jointRot.RIGHT_WRIST.getX() - jointRot.RIGHT_ELBOW.getX(), 2) + Math.pow(jointRot.RIGHT_WRIST.getY() - jointRot.RIGHT_ELBOW.getY(), 2));

            double upLLeg = Math.sqrt(Math.pow(jointRot.LEFT_HIP.getX() - jointRot.LEFT_KNEE.getX(), 2) + Math.pow(jointRot.LEFT_HIP.getY() - jointRot.LEFT_KNEE.getY(), 2));
            double downLLeg = Math.sqrt(Math.pow(jointRot.LEFT_KNEE.getX() - jointRot.LEFT_ANKLE.getX(), 2) + Math.pow(jointRot.LEFT_KNEE.getY() - jointRot.LEFT_ANKLE.getY(), 2));

            double upRLeg = Math.sqrt(Math.pow(jointRot.RIGHT_HIP.getX() - jointRot.RIGHT_KNEE.getX(), 2) + Math.pow(jointRot.RIGHT_HIP.getY() - jointRot.RIGHT_KNEE.getY(), 2));
            double downRLeg = Math.sqrt(Math.pow(jointRot.RIGHT_KNEE.getX() - jointRot.RIGHT_ANKLE.getX(), 2) + Math.pow(jointRot.RIGHT_KNEE.getY() - jointRot.RIGHT_ANKLE.getY(), 2));

            double spine = Math.sqrt(Math.pow(jointRot.ROOT_POSITION.getX() - jointRot.getCHEST().getX(), 2) + Math.pow(jointRot.ROOT_POSITION.getY() - jointRot.getCHEST().getY(), 2));

            double neck = Math.sqrt(Math.pow(jointRot.getCHEST().getX() - jointRot.NOSE.getX(), 2) + Math.pow(jointRot.getCHEST().getY() - jointRot.NOSE.getY(), 2));

            bones.setLeftDownArmLength(downLArm);
            bones.setLeftUpperArmLength(upLArm);
            bones.setRightUpperArmLength(upRArm);
            bones.setRightDownArmLength(downRArm);
            bones.setLeftUpperLegLength(upLLeg);
            bones.setLeftDownLegLength(downLLeg);
            bones.setRightUpperLegLength(upRLeg);
            bones.setRightDownLegLength(downRLeg);
            bones.setSpineLength(spine);
            bones.setNeck(neck);

            bonesLength.add(bones);
//            System.out.println(bones.toString());

            posePart fakeSkeleton = makeT_Pose(bones, jointRot);
            T_Pose.add(fakeSkeleton);
//            System.out.println(fakeSkeleton.toString());
        }

    }

    private posePart makeT_Pose(Bones bones, posePart keypoints) {
        posePart T_POSE = new posePart();
        double downLLeg = bones.getLeftDownLegLength();
        double downRLeg = bones.getRightDownLegLength();

        double upLLeg = bones.getLeftUpperLegLength();
        double upRLeg = bones.getRightUpperLegLength();

        // The root the pelvis is on the position 0,0
        position rootPos = new position();
        rootPos.setX(keypoints.getROOT_POSITION().getX());
        rootPos.setY(keypoints.getROOT_POSITION().getY());
        rootPos.setAngle(0);
        rootPos.setAllRotationZero();
        T_POSE.setROOT_POSITION(rootPos);

        double differenceLH = Math.sqrt(Math.pow(keypoints.ROOT_POSITION.getX() - keypoints.LEFT_HIP.getX(), 2) + Math.pow(keypoints.ROOT_POSITION.getY() - keypoints.LEFT_HIP.getY(), 2));
        //Put the left and right hip in pose estimation
        position LHip = new position();
        LHip.setX(keypoints.getROOT_POSITION().getX() - differenceLH);
        LHip.setY(keypoints.getROOT_POSITION().getY());
        LHip.setAngle(0);
        LHip.setAllRotationZero();
        T_POSE.setLEFT_HIP(LHip);

        double differenceRH = Math.sqrt(Math.pow(keypoints.ROOT_POSITION.getX() - keypoints.RIGHT_HIP.getX(), 2) + Math.pow(keypoints.ROOT_POSITION.getY() - keypoints.RIGHT_HIP.getY(), 2));

        position RHip = new position();
        RHip.setX(keypoints.getROOT_POSITION().getX() + differenceRH);
        RHip.setY(keypoints.getROOT_POSITION().getY());
        RHip.setAngle(0);
        RHip.setAllRotationZero();
        T_POSE.setRIGHT_HIP(RHip);

        // Find the position of knee left and right
        position kneeLeft = new position();
        kneeLeft.setX(LHip.getX());
        kneeLeft.setY(LHip.getY() + upLLeg);
        kneeLeft.setAngle(0);
        kneeLeft.setAllRotationZero();
        T_POSE.setLEFT_KNEE(kneeLeft);

        position kneeRight = new position();
        kneeRight.setX(RHip.getX());
        kneeRight.setY(RHip.getY() + upRLeg);
        kneeRight.setAngle(0);
        kneeRight.setAllRotationZero();
        T_POSE.setRIGHT_KNEE(kneeRight);

        // Find the position of angle left and right
        position angleLeft = new position();
        angleLeft.setX(LHip.getX());
        angleLeft.setY(kneeLeft.getY() - downLLeg);
        angleLeft.setAngle(0);
        angleLeft.setAllRotationZero();
        T_POSE.setLEFT_ANKLE(angleLeft);

        position angleRight = new position();
        angleRight.setX(RHip.getX());
        angleRight.setY(kneeRight.getY() - downRLeg);
        angleRight.setAngle(0);
        angleRight.setAllRotationZero();
        T_POSE.setRIGHT_ANKLE(angleRight);

        // Find THE Chest position
        position chest = new position();
        chest.setX(rootPos.getX());
        chest.setY(rootPos.getY() - bones.getSpineLength());
        chest.setAngle(0);
        chest.setAllRotationZero();
        T_POSE.setCHEST(chest);

        //Find the left and right Shoulder in T-pose
        position lShoulder = new position();
        lShoulder.setX(LHip.getX());
        lShoulder.setY(chest.getY());
        lShoulder.setAngle(0);
        lShoulder.setAllRotationZero();
        T_POSE.setLEFT_SHOULDER(lShoulder);

        position rShoulder = new position();
        rShoulder.setX(RHip.getX());
        rShoulder.setY(chest.getY());
        rShoulder.setAngle(0);
        rShoulder.setAllRotationZero();
        T_POSE.setRIGHT_SHOULDER(rShoulder);

        // Find the t pose position of the elbow
        position lElbow = new position();
        lElbow.setX(lShoulder.getX() - bones.getLeftUpperArmLength());
        lElbow.setY(chest.getY());
        lElbow.setAngle(0);
        lElbow.setAllRotationZero();
        T_POSE.setLEFT_ELBOW(lElbow);

        position rElbow = new position();
        rElbow.setX(rElbow.getX() + bones.getRightUpperArmLength());
        rElbow.setY(chest.getY());
        rElbow.setAngle(0);
        rElbow.setAllRotationZero();
        T_POSE.setRIGHT_ELBOW(rElbow);

        // Find the wrist Position of t Pose

        position lWrist = new position();
        lWrist.setX(lElbow.getX() - bones.getLeftDownArmLength());
        lWrist.setY(chest.getY());
        lWrist.setAngle(0);
        lWrist.setAllRotationZero();
        T_POSE.setLEFT_WRIST(lElbow);

        position rWrist = new position();
        rWrist.setX(rElbow.getX() + bones.getRightDownArmLength());
        rWrist.setY(chest.getY());
        rWrist.setAngle(0);
        rWrist.setAllRotationZero();
        T_POSE.setRIGHT_WRIST(lElbow);


        position nose = new position();
        nose.setX(chest.getX());
        nose.setY(chest.getY() - bones.getNeck());
        nose.setAngle(0);
        nose.setAllRotationZero();
        T_POSE.setNOSE(nose);

        double lEyeOffsetX = keypoints.NOSE.getX() - keypoints.LEFT_EYE.getX();
        double lEyeOffsetY = keypoints.NOSE.getY() - keypoints.LEFT_EYE.getY();
        position LeftEye = new position();
        LeftEye.setX(nose.getX() - lEyeOffsetX);
        LeftEye.setY(nose.getY() - lEyeOffsetY);
        LeftEye.setAngle(0);
        T_POSE.setLEFT_EYE(LeftEye);

        double rEyeOffsetX = keypoints.NOSE.getX() - keypoints.RIGHT_EYE.getX();
        double rEyeOffsetY = keypoints.NOSE.getY() - keypoints.RIGHT_EYE.getY();
        position RightEye = new position();
        RightEye.setX(nose.getX() - rEyeOffsetX);
        RightEye.setY(nose.getY() - rEyeOffsetY);
        RightEye.setAngle(0);
        T_POSE.setRIGHT_EYE(RightEye);

        double lEarOffsetX = keypoints.NOSE.getX() - keypoints.LEFT_EAR.getX();
        double lEarOffsetY = keypoints.NOSE.getY() - keypoints.LEFT_EAR.getY();
        position LeftEar = new position();
        LeftEar.setX(nose.getX() - lEarOffsetX);
        LeftEar.setY(nose.getY() - lEarOffsetY);
        LeftEar.setAngle(0);
        T_POSE.setLEFT_EAR(LeftEar);

        double rEarOffsetX = keypoints.NOSE.getX() - keypoints.RIGHT_EAR.getX();
        double rEarOffsetY = keypoints.NOSE.getY() - keypoints.RIGHT_EAR.getY();
        position RightEar = new position();
        RightEar.setX(nose.getX() - rEarOffsetX);
        RightEar.setY(nose.getY() - rEarOffsetY);
        RightEar.setAngle(0);
        T_POSE.setRIGHT_EAR(RightEar);


        T_POSE.setLeftFootContactLabel(true);
        T_POSE.setRightFootContactLabel(true);

        return T_POSE;


    }

    public List<posePart> forwardKinematic(List<posePart> poseParts){
        int c =  6;
        for(int i=0; i< poseParts.size(); i+=c){
//            double differenceAngle[] = new double[19];
            posePart pp = poseParts.get(i);
            posePart tPose = this.T_Pose.get(i);
            posePart Frames5[] = new posePart[5];
            posePart Frames5TPose[] = new posePart[5];
           c =  6;
            if(poseParts.size() - i <= 5){
                c = poseParts.size() - i;
            }
            int counter = 0;
            for(int j = 1; j < c; j++){
                counter ++;
                Frames5[j - 1] = poseParts.get(j+i);
                Frames5TPose[j - 1] = T_Pose.get(j+i);
            }
            // Local transforms
            posePart parent = FakecalculateZ(pp, tPose);

            // local transform
            double [][][][]transforms = new double[counter + 1][15][3][3];

            transforms[0] = transformRotation(parent);
            posePart []fakeZ = new posePart[counter];
//            System.out.println(parent.toString());
            for(int j = 0 ;j < counter; j++) {
                fakeZ[j] = FakecalculateZ(Frames5[j], Frames5TPose[j]);

                transforms[j + 1] = transformRotation(fakeZ[j]);
            }
            double [][][][]positions = getPositions(parent, fakeZ);
            double [][][][]localTransforms = concat(transforms, positions, -1);

            double [][][][]zeroOne = makeZeroOneArray(localTransforms.length, 15, 1, 4);
            localTransforms = concat(localTransforms, zeroOne, -2);

            transforms = global(positions, localTransforms);
            poseParts.set(i, putRotation(transforms[0], parent));
            for (int j = 0;j < counter ; j++){
                posePart tmp = putRotation(transforms[j+1], fakeZ[j]);
                if(i != 0 && j == 0){
                    tmp = checkFootLabels(tmp, poseParts.get(i - 1));
                }else if(i != 0 && j != 0){
                    tmp = checkFootLabels(tmp, poseParts.get(i + j - 1));
                }
                poseParts.set(i + j + 1, tmp);
            }
        }
        return poseParts;
    }


    private posePart checkFootLabels(posePart now, posePart before) {
        if(now.isLeftFootContactLabel()){
            if(before.isLeftFootContactLabel()){
                now.LEFT_KNEE.setZ(before.LEFT_KNEE.getZ());
                now.LEFT_ANKLE.setZ(before.LEFT_ANKLE.getZ());
                now.LEFT_HIP.setZ(before.LEFT_HIP.getZ());

                now.ROOT_POSITION.setRotationX(0);
                now.ROOT_POSITION.setRotationY(0);
                now.ROOT_POSITION.setRotationZ(0);

                now.LEFT_KNEE.setRotationX(0);
                now.LEFT_KNEE.setRotationY(0);
                now.LEFT_KNEE.setRotationZ(0);

                now.LEFT_ANKLE.setRotationX(0);
                now.LEFT_ANKLE.setRotationY(0);
                now.LEFT_ANKLE.setRotationZ(0);

                now.LEFT_HIP.setRotationX(0);
                now.LEFT_HIP.setRotationY(0);
                now.LEFT_HIP.setRotationZ(0);

            }
        }

        if(now.isRightFootContactLabel()){
            if(before.isRightFootContactLabel()){
                now.RIGHT_KNEE.setZ(before.RIGHT_KNEE.getZ());
                now.RIGHT_ANKLE.setZ(before.RIGHT_ANKLE.getZ());
                now.RIGHT_HIP.setZ(before.RIGHT_HIP.getZ());

                now.ROOT_POSITION.setRotationX(0);
                now.ROOT_POSITION.setRotationY(0);
                now.ROOT_POSITION.setRotationZ(0);

                now.RIGHT_KNEE.setRotationX(0);
                now.RIGHT_KNEE.setRotationY(0);
                now.RIGHT_KNEE.setRotationZ(0);

                now.RIGHT_ANKLE.setRotationX(0);
                now.RIGHT_ANKLE.setRotationY(0);
                now.RIGHT_ANKLE.setRotationZ(0);

                now.RIGHT_HIP.setRotationX(0);
                now.RIGHT_HIP.setRotationY(0);
                now.RIGHT_HIP.setRotationZ(0);
            }
        }
        return now;
    }

    private posePart putRotation(double[][][] transform, posePart posePart) {
        posePart.ROOT_POSITION.setRotationX(transform[0][0][0]);posePart.ROOT_POSITION.setRotationY(transform[0][0][1]);posePart.ROOT_POSITION.setRotationZ(transform[0][0][2]);
        posePart.LEFT_HIP.setRotationX(transform[1][0][0]);posePart.LEFT_HIP.setRotationY(transform[1][0][1]);posePart.LEFT_HIP.setRotationZ(transform[1][0][2]);
        posePart.RIGHT_HIP.setRotationX(transform[2][0][0]);posePart.RIGHT_HIP.setRotationY(transform[2][0][1]);posePart.RIGHT_HIP.setRotationZ(transform[2][0][2]);
        posePart.LEFT_KNEE.setRotationX(transform[3][0][0]);posePart.LEFT_KNEE.setRotationY(transform[3][0][1]);posePart.LEFT_KNEE.setRotationZ(transform[3][0][2]);
        posePart.RIGHT_KNEE.setRotationX(transform[4][0][0]);posePart.RIGHT_KNEE.setRotationY(transform[4][0][1]);posePart.RIGHT_KNEE.setRotationZ(transform[4][0][2]);
        posePart.LEFT_ANKLE.setRotationX(transform[5][0][0]);posePart.LEFT_ANKLE.setRotationY(transform[5][0][1]);posePart.LEFT_ANKLE.setRotationZ(transform[5][0][2]);
        posePart.RIGHT_ANKLE.setRotationX(transform[6][0][0]);posePart.RIGHT_ANKLE.setRotationY(transform[6][0][1]);posePart.RIGHT_ANKLE.setRotationZ(transform[6][0][2]);
        posePart.CHEST.setRotationX(transform[7][0][0]);posePart.CHEST.setRotationY(transform[7][0][1]);posePart.CHEST.setRotationZ(transform[7][0][2]);
        posePart.NOSE.setRotationX(transform[8][0][0]);posePart.NOSE.setRotationY(transform[8][0][1]);posePart.NOSE.setRotationZ(transform[8][0][2]);
        posePart.LEFT_SHOULDER.setRotationX(transform[9][0][0]);posePart.LEFT_SHOULDER.setRotationY(transform[9][0][1]);posePart.LEFT_SHOULDER.setRotationZ(transform[9][0][2]);
        posePart.RIGHT_SHOULDER.setRotationX(transform[10][0][0]);posePart.RIGHT_SHOULDER.setRotationY(transform[10][0][1]);posePart.RIGHT_SHOULDER.setRotationZ(transform[10][0][2]);
        posePart.LEFT_ELBOW.setRotationX(transform[11][0][0]);posePart.LEFT_ELBOW.setRotationY(transform[11][0][1]);posePart.LEFT_ELBOW.setRotationZ(transform[11][0][2]);
        posePart.RIGHT_ELBOW.setRotationX(transform[12][0][0]);posePart.RIGHT_ELBOW.setRotationY(transform[12][0][1]);posePart.RIGHT_ELBOW.setRotationZ(transform[12][0][2]);
        posePart.LEFT_WRIST.setRotationX(transform[13][0][0]);posePart.LEFT_WRIST.setRotationY(transform[13][0][1]);posePart.LEFT_WRIST.setRotationZ(transform[13][0][2]);
        posePart.RIGHT_WRIST.setRotationX(transform[14][0][0]);posePart.RIGHT_WRIST.setRotationY(transform[14][0][1]);posePart.RIGHT_WRIST.setRotationZ(transform[14][0][2]);

        return posePart;
    }

    private double[][][][] global(double [][][][]positions, double[][][][] locals){
       double[][][][] globals = makeTransformBlank(positions);
       double global[][][][] = concat(locals, globals, 1);
       int parents[] = {0, 0, 0, 1, 2, 3, 4, 0, 7, 7, 7, 9, 10, 11, 12};
       global = multiply(locals, global, parents);
       return global;
    }

    private double[][][][] multiply(double[][][][] locals, double[][][][] global, int[] parents) {
        double [][][][]transformations = new double[global.length][15][1][4];
        for(int i = 0; i < locals.length; i++){
            transformations[i] = transforms_multiply(global[i], locals, i, parents);
        }

        return transformations;
    }

    private double[][][] transforms_multiply(double[][][] global, double[][][][] locals, int index, int[] parents){
            DoubleMatrix dmLocals;
            DoubleMatrix dmGlobal;

            double[][][] transforms = new double[global.length][1][4];
            for(int i = 0; i < locals.length; i++){
               dmLocals = new DoubleMatrix(locals[i][index]);
               for(int j = 0; j < global.length; j++){
                   dmGlobal = new DoubleMatrix(global[j][0]);
                   DoubleMatrix mul = dmLocals.mmul(dmGlobal);
                   int r = mul.rows;
                   int c = mul.columns;
                   for(int row = 0 ; row < r; row++){
                       transforms[j][0][row] = mul.get(row, 0);
                   }

               }
            }
            return transforms;
    }

    private double[][][][] makeTransformBlank(double[][][][] array) {
        double [][][][]ts = new double[array.length][15][4][4];
        //Diagonal tiling
        ts = diagonialTiling(ts);
        return ts;
    }

    public double[][][][] diagonialTiling(double [][][][]ts){
        for (int i = 0; i < ts.length; i++){
            for (int j = 0; j < ts[i].length; j++){
                for (int k = 0; k < ts[i][j].length; k++){
                    for(int l = 0; l < ts[i][j][k].length; l++){
                        if(k == l){
                            ts[i][j][k][l] = 1;
                        }else{
                            ts[i][j][k][l] = 0;
                        }
                    }

                }
            }
        }
        return ts;
    }

    private void print(double[][][][] localTransforms) {
        for (int i = 0; i < localTransforms.length; i++){
            for (int j = 0; j < localTransforms[i].length; j++){
                for (int k = 0; k < localTransforms[i][j].length; k++){
                    for(int l = 0; l < localTransforms[i][j][k].length; l++){
                        System.out.print(localTransforms[i][j][k][l]+" ");
                    }
                    System.out.println();
                }
                System.out.println("---------------");
            }

        }
    }

    private double checkParent(position parent, position child, double offset, int axis){
        if(axis == 1){
            double Offset = Math.abs(parent.getY() - child.getY());
            if(parent.getY() == child.getY() || (offset>=Offset && Offset>=-offset))
                return parent.getAngle();
            else
                return parent.getAngle()/2;
        }else{
            double Offset = Math.abs(parent.getX() - child.getX());
            if(parent.getX() == child.getX() || (offset>=Offset && Offset>=-offset))
                return parent.getAngle();
            else
                return parent.getAngle()/2;
        }

    }

    private posePart FakecalculateZ(posePart pp, posePart tPose) {
        position root = pp.getROOT_POSITION();

        position tPLH = tPose.getLEFT_HIP();
        double diffLH = Math.sqrt(Math.pow(tPLH.getX() - root.getX(), 2) + Math.pow(tPLH.getY() - root.getY(), 2));
        double angle = checkParent(root, pp.getLEFT_HIP(), 1, 1);
        double zLH = Math.sin(angle*Math.PI/180) * diffLH;
        if (angle != root.getAngle()) {
            zLH = zLH * 2;
        }
        pp.getLEFT_HIP().setZ(zLH);

        position tPRH = tPose.getRIGHT_HIP();
        double diffRH = Math.sqrt(Math.pow(tPRH.getX() - root.getX(), 2) + Math.pow(tPRH.getY() - root.getY(), 2));
        angle = checkParent(root, pp.getRIGHT_HIP(), 1, 1);
        double zRH = Math.sin(angle*Math.PI/180) * diffRH;
        if (angle != root.getAngle()) {
            zRH = zRH * 2;
        }
        pp.RIGHT_HIP.setZ(zRH);

        pp.ROOT_POSITION.setZ((zLH+zRH )/ 2);

        // Z knees
        position tPLK = tPose.getLEFT_KNEE();
        position pLH = pp.getLEFT_HIP();

        double diffLK = Math.sqrt(Math.pow(tPLK.getX() - pLH.getX(), 2) + Math.pow(tPLK.getY() - pLH.getY(), 2));
        angle = checkParent(pp.LEFT_HIP, pp.getLEFT_KNEE(), 1, 2);
        double zLK = Math.sin(angle*Math.PI/180) * diffLK;
        if (angle != pp.LEFT_HIP.getAngle()) {
            zLK = zLK * 2;
        }
        pp.LEFT_KNEE.setZ(zLK);

        position tPRK = tPose.getRIGHT_KNEE();
        position pRH = pp.getRIGHT_HIP();
        double diffRK = Math.sqrt(Math.pow(tPRK.getX() - pRH.getX(), 2) + Math.pow(tPRK.getY() - pRH.getY(), 2));
        angle = checkParent(pp.RIGHT_HIP, pp.getRIGHT_KNEE(), 1, 2);
        double zRK = Math.sin(angle*Math.PI/180) * diffRK;
        if (angle != pp.RIGHT_HIP.getAngle()) {
            zRK = zRK * 2;
        }
        pp.RIGHT_KNEE.setZ(zRK);

        // Z ANKLES
        position tPLA = tPose.getLEFT_KNEE();
        position pLK = pp.getLEFT_KNEE();
        double diffLA = Math.sqrt(Math.pow(tPLA.getX() - pLK.getX(), 2) + Math.pow(tPLA.getY() - pLK.getY(), 2));
        angle = checkParent(pp.LEFT_KNEE, pp.LEFT_ANKLE, 1, 2);
        double zLA = Math.sin(angle*Math.PI/180) * diffLA;
        if (angle != pp.LEFT_KNEE.getAngle()) {
            zLA = zLA * 2;
        }
        pp.LEFT_ANKLE.setZ(zLA);

        position tPRA = tPose.getRIGHT_KNEE();
        position pRK = pp.getRIGHT_KNEE();
        double diffRA = Math.sqrt(Math.pow(tPRA.getX() - pRK.getX(), 2) + Math.pow(tPRA.getY() - pRK.getY(), 2));
        angle = checkParent(pp.RIGHT_KNEE, pp.RIGHT_ANKLE, 1, 2);
        double zRA = Math.sin(angle*Math.PI/180) * diffRA;
        if (angle != pp.RIGHT_KNEE.getAngle()) {
            zRA = zRA * 2;
        }
        pp.RIGHT_ANKLE.setZ(zRA);

        // Z CHEST
        position chest = pp.getCHEST();

        position tPC = tPose.getCHEST();
        double diffC = Math.sqrt(Math.pow(root.getX() - tPC.getX(), 2) + Math.pow(root.getY() - tPC.getY(), 2));
        angle = checkParent(pp.getROOT_POSITION(), pp.CHEST, 1, 2);
        double zC = Math.sin(angle*Math.PI/180) * diffC;
        if (angle != pp.getROOT_POSITION().getAngle()) {
            zC = zC * 2;
        }
        pp.CHEST.setZ(zC);

        // Z nose
        position tPN = tPose.getCHEST();
        double diffN = Math.sqrt(Math.pow(chest.getX() - tPN.getX(), 2) + Math.pow(chest.getY() - tPN.getY(), 2));
        angle = checkParent(pp.CHEST, pp.NOSE, 1, 2);
        double zN = Math.sin(angle*Math.PI/180) * diffN;
        if (angle != pp.getCHEST().getAngle()) {
            zN = zN * 2;
        }
        pp.getNOSE().setZ(zN);

        // Z SHOULDER
        position tpLS = tPose.getLEFT_SHOULDER();
        double diffLS = Math.sqrt(Math.pow(tpLS.getX() - chest.getX(), 2) + Math.pow(tpLS.getY() - chest.getY(), 2));
        angle = checkParent(pp.CHEST, pp.LEFT_SHOULDER, 1, 1);
        double zLS = Math.sin(angle*Math.PI/180) * diffLS;
        if (angle != pp.getCHEST().getAngle()) {
            zLS = zLS * 2;
        }
        pp.LEFT_SHOULDER.setZ(zLS);

        position tpLR = tPose.getLEFT_SHOULDER();
        double diffLR = Math.sqrt(Math.pow(tpLR.getX() - chest.getX(), 2) + Math.pow(tpLR.getY() - chest.getY(), 2));
        angle = checkParent(pp.CHEST, pp.RIGHT_SHOULDER, 1, 1);
        double zLR = Math.sin(angle*Math.PI/180) * diffLR;
        if (angle != pp.getCHEST().getAngle()) {
            zLR = zLR * 2;
        }
        pp.RIGHT_SHOULDER.setZ(zLR);

        // Z Elbows
        position tpLE = tPose.getLEFT_ELBOW();
        position LS = pp.getLEFT_ELBOW();
        double diffLE = Math.sqrt(Math.pow(tpLE.getX() - LS.getX(), 2) + Math.pow(tpLE.getY() - LS.getY(), 2));
        angle = checkParent(pp.LEFT_SHOULDER, pp.LEFT_ELBOW, 1, 1);
        double zLE = Math.sin(angle*Math.PI/180) * diffLE;
        if (angle != pp.LEFT_SHOULDER.getAngle()) {
            zLE = zLE * 2;
        }
        pp.LEFT_ELBOW.setZ(zLE);

        position tpRE = tPose.getRIGHT_ELBOW();
        position RS = pp.getRIGHT_SHOULDER();
        double diffRE = Math.sqrt(Math.pow(tpRE.getX() - RS.getX(), 2) + Math.pow(tpRE.getY() - RS.getY(), 2));
        angle = checkParent(pp.RIGHT_SHOULDER, pp.RIGHT_ELBOW, 1, 1);
        double zRE = Math.sin(angle*Math.PI/180) * diffRE;
        if (angle != pp.LEFT_SHOULDER.getAngle()) {
            zRE = zRE * 2;
        }
        pp.RIGHT_ELBOW.setZ(zRE);

        // Z Wrist
        position tpLW = tPose.getLEFT_WRIST();
        position LE = pp.getLEFT_ELBOW();
        double diffLW = Math.sqrt(Math.pow(tpLW.getX() - LE.getX(), 2) + Math.pow(tpLW.getY() - LE.getY(), 2));
        angle = checkParent(pp.LEFT_ELBOW, pp.LEFT_WRIST, 1, 1);
        double zLW = Math.sin(angle*Math.PI/180) * diffLW;
        if (angle != pp.LEFT_ELBOW.getAngle()) {
            zLW = zLW * 2;
        }
        pp.LEFT_WRIST.setZ(zLW);

        position tpRW = tPose.getRIGHT_WRIST();
        position RE = pp.getRIGHT_ELBOW();
        double diffRW = Math.sqrt(Math.pow(tpRW.getX() - RE.getX(), 2) + Math.pow(tpRW.getY() - RE.getY(), 2));
        angle = checkParent(pp.RIGHT_ELBOW, pp.RIGHT_WRIST, 1, 1);
        double zRW = Math.sin(angle*Math.PI/180) * diffRW;
        if (angle != pp.RIGHT_ELBOW.getAngle()) {
            zRW = zRW * 2;
        }
        pp.RIGHT_WRIST.setZ(zRW);

        return pp;
    }

    public double[][][] transformRotation(posePart pose){
        double [][][]rotation = new double[15][3][3];

        rotation[0] = rotate(pose.ROOT_POSITION);
        rotation[1] = rotate(pose.LEFT_HIP);
        rotation[2] = rotate(pose.RIGHT_HIP);
        rotation[3] = rotate(pose.LEFT_KNEE);
        rotation[4] = rotate(pose.RIGHT_KNEE);
        rotation[5] = rotate(pose.LEFT_ANKLE);
        rotation[6] = rotate(pose.RIGHT_ANKLE);
        rotation[7] = rotate(pose.CHEST);
        rotation[8] = rotate(pose.NOSE);
        rotation[9] = rotate(pose.LEFT_SHOULDER);
        rotation[10] = rotate(pose.RIGHT_SHOULDER);
        rotation[11] = rotate(pose.LEFT_ELBOW);
        rotation[12] = rotate(pose.RIGHT_ELBOW);
        rotation[13] = rotate(pose.LEFT_WRIST);
        rotation[14] = rotate(pose.RIGHT_WRIST);

        return rotation;
    }

    public double[][] rotate(position pos){
        //TODO: See if need a cos and sin
        double qLen = Math.sqrt(Math.pow(pos.getAngle(), 2) + Math.pow(pos.getX(), 2) + Math.pow(pos.getY(), 2) + Math.pow(pos.getZ(), 2));
        double qW = pos.getAngle()/qLen;
        double qX = pos.getX()/qLen;
        double qY = pos.getY()/qLen;
        double qZ = pos.getZ()/qLen;

        //Unit quaternion based rotation matrix computation
        double x2 = qX + qX;
        double y2 = qY + qY;
        double z2 = qZ + qZ;
        double xx = qX * x2;
        double yy = qY * y2;
        double wx = qW * x2;
        double xy = qX * y2;
        double yz = qZ * z2;
        double wy = qW * y2;
        double xz = qX * z2;
        double zz = qZ * z2;
        double wz = qW * z2;

        double [][]m = new double[3][3];
        m[0][0] = 1 - (yy + zz); m[0][1] = xy - wz;m[0][2] = xz + wy;
        m[1][0] = xy + wz;m[1][0] = 1 - (xx + zz);m[1][2] = yz - wx;
        m[2][0] = xz - wy;m[2][1] = yz + wx;m[2][2] = 1 - (xx + yy);

        return m;
    }

    public double[][][][] getPositions(posePart pose, posePart[] poses){
        double [][][][]transforms = new double[poses.length + 1][15][3][3];
        transforms[0][0][0][0] = pose.ROOT_POSITION.getX(); transforms[0][0][1][0] = pose.ROOT_POSITION.getY(); transforms[0][0][2][0] = pose.ROOT_POSITION.getZ();
        transforms[0][1][0][0] = pose.LEFT_HIP.getX(); transforms[0][1][1][0] = pose.LEFT_HIP.getY(); transforms[0][1][2][0] = pose.LEFT_HIP.getZ();
        transforms[0][2][0][0] = pose.RIGHT_HIP.getX(); transforms[0][2][1][0] = pose.RIGHT_HIP.getY(); transforms[0][2][2][0] = pose.RIGHT_HIP.getZ();
        transforms[0][3][0][0] = pose.LEFT_KNEE.getX(); transforms[0][3][1][0] = pose.LEFT_KNEE.getY(); transforms[0][3][2][0] = pose.LEFT_KNEE.getZ();
        transforms[0][4][0][0] = pose.RIGHT_KNEE.getX(); transforms[0][4][1][0] = pose.RIGHT_KNEE.getY(); transforms[0][4][2][0] = pose.RIGHT_KNEE.getZ();
        transforms[0][5][0][0] = pose.LEFT_ANKLE.getX(); transforms[0][5][1][0] = pose.LEFT_ANKLE.getY(); transforms[0][5][2][0] = pose.LEFT_ANKLE.getZ();
        transforms[0][6][0][0] = pose.RIGHT_ANKLE.getX(); transforms[0][6][1][0] = pose.RIGHT_ANKLE.getY(); transforms[0][6][2][0] = pose.RIGHT_ANKLE.getZ();
        transforms[0][7][0][0] = pose.CHEST.getX(); transforms[0][7][1][0] = pose.CHEST.getY(); transforms[0][7][2][0] = pose.CHEST.getZ();
        transforms[0][8][0][0] = pose.NOSE.getX(); transforms[0][8][1][0] = pose.NOSE.getY(); transforms[0][8][2][0] = pose.NOSE.getZ();
        transforms[0][9][0][0] = pose.LEFT_SHOULDER.getX(); transforms[0][9][1][0] = pose.LEFT_SHOULDER.getY(); transforms[0][9][2][0] = pose.LEFT_SHOULDER.getZ();
        transforms[0][10][0][0] = pose.RIGHT_SHOULDER.getX(); transforms[0][10][1][0] = pose.RIGHT_SHOULDER.getY(); transforms[0][10][2][0] = pose.RIGHT_SHOULDER.getZ();
        transforms[0][11][0][0] = pose.LEFT_ELBOW.getX(); transforms[0][11][1][0] = pose.LEFT_ELBOW.getY(); transforms[0][11][2][0] = pose.LEFT_ELBOW.getZ();
        transforms[0][12][0][0] = pose.RIGHT_ELBOW.getX(); transforms[0][12][1][0] = pose.RIGHT_ELBOW.getY(); transforms[0][12][2][0] = pose.RIGHT_ELBOW.getZ();
        transforms[0][13][0][0] = pose.LEFT_WRIST.getX(); transforms[0][13][1][0] = pose.LEFT_WRIST.getY(); transforms[0][13][2][0] = pose.LEFT_WRIST.getZ();
        transforms[0][14][0][0] = pose.RIGHT_WRIST.getX(); transforms[0][14][1][0] = pose.RIGHT_WRIST.getY(); transforms[0][14][2][0] = pose.RIGHT_WRIST.getZ();

        for(int i = 0; i < poses.length; i++){
            transforms[i][0][0][0] = pose.ROOT_POSITION.getX(); transforms[i][0][1][0] = pose.ROOT_POSITION.getY(); transforms[i][0][2][0] = pose.ROOT_POSITION.getZ();
            transforms[i][1][0][0] = pose.LEFT_HIP.getX(); transforms[i][1][1][0] = pose.LEFT_HIP.getY(); transforms[i][1][2][0] = pose.LEFT_HIP.getZ();
            transforms[i][2][0][0] = pose.RIGHT_HIP.getX(); transforms[i][2][1][0] = pose.RIGHT_HIP.getY(); transforms[i][2][2][0] = pose.RIGHT_HIP.getZ();
            transforms[i][3][0][0] = pose.LEFT_KNEE.getX(); transforms[i][3][1][0] = pose.LEFT_KNEE.getY(); transforms[i][3][2][0] = pose.LEFT_KNEE.getZ();
            transforms[i][4][0][0] = pose.RIGHT_KNEE.getX(); transforms[i][4][1][0] = pose.RIGHT_KNEE.getY(); transforms[i][4][2][0] = pose.RIGHT_KNEE.getZ();
            transforms[i][5][0][0] = pose.LEFT_ANKLE.getX(); transforms[i][5][1][0] = pose.LEFT_ANKLE.getY(); transforms[i][5][2][0] = pose.LEFT_ANKLE.getZ();
            transforms[i][6][0][0] = pose.RIGHT_ANKLE.getX(); transforms[i][6][1][0] = pose.RIGHT_ANKLE.getY(); transforms[i][6][2][0] = pose.RIGHT_ANKLE.getZ();
            transforms[i][7][0][0] = pose.CHEST.getX(); transforms[i][7][1][0] = pose.CHEST.getY(); transforms[i][7][2][0] = pose.CHEST.getZ();
            transforms[i][8][0][0] = pose.NOSE.getX(); transforms[i][8][1][0] = pose.NOSE.getY(); transforms[i][8][2][0] = pose.NOSE.getZ();
            transforms[i][9][0][0] = pose.LEFT_SHOULDER.getX(); transforms[i][9][1][0] = pose.LEFT_SHOULDER.getY(); transforms[i][9][2][0] = pose.LEFT_SHOULDER.getZ();
            transforms[i][10][0][0] = pose.RIGHT_SHOULDER.getX(); transforms[i][10][1][0] = pose.RIGHT_SHOULDER.getY(); transforms[i][10][2][0] = pose.RIGHT_SHOULDER.getZ();
            transforms[i][11][0][0] = pose.LEFT_ELBOW.getX(); transforms[i][11][1][0] = pose.LEFT_ELBOW.getY(); transforms[i][11][2][0] = pose.LEFT_ELBOW.getZ();
            transforms[i][12][0][0] = pose.RIGHT_ELBOW.getX(); transforms[i][12][1][0] = pose.RIGHT_ELBOW.getY(); transforms[i][12][2][0] = pose.RIGHT_ELBOW.getZ();
            transforms[i][13][0][0] = pose.LEFT_WRIST.getX(); transforms[i][13][1][0] = pose.LEFT_WRIST.getY(); transforms[i][13][2][0] = pose.LEFT_WRIST.getZ();
            transforms[i][14][0][0] = pose.RIGHT_WRIST.getX(); transforms[i][14][1][0] = pose.RIGHT_WRIST.getY(); transforms[i][14][2][0] = pose.RIGHT_WRIST.getZ();

        }
        return transforms;
    }

    public double[][][][] concat(double [][][][]array1, double [][][][]array2, int axis){

        double [][][][]array = null;
        if(axis == 1){
            array = new double[array1.length][15][4][4];
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < 15; j++) {
                    if(j == 0){
                        array[i][j] = array1[i][j];
                    }else{
                        array[i][j] = array2[i][j];
                    }
                }
            }
        } else if(axis == -1){
            array = new double[array1.length][15][3][6];
            for (int i = 0; i < array.length; i++){
                for(int j = 0; j < 15; j++){
                    for (int k = 0; k < 3; k++){
                        array[i][j][k][0] = array1[i][j][k][0];
                        array[i][j][k][1] = array1[i][j][k][1];
                        array[i][j][k][2] = array1[i][j][k][2];
                        //maybe need without norm
                        array[i][j][k][3] = array2[i][j][k][0];
                        array[i][j][k][4] = array2[i][j][k][1];
                        array[i][j][k][5] = array2[i][j][k][2];
                    }
                }
            }
        }else if(axis == -2){
            array = new double[array1.length][15][4][4];
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < 15; j++) {
                    for(int k = 0; k < 3; k++){
                        for(int l = 0; l < 4; l++){
                            array[i][j][k][l] = array1[i][j][k][l];
                        }
                    }
                    for(int k = 0; k < 4; k++){
                        array[i][j][3][k] = array2[i][j][0][k];
                    }
                }
            }
        }
        return  array;
    }

    public double[][][][] makeZeroOneArray(int len, int parts, int row, int col){

        double [][][][]zeroOne = new double[len][parts][row][col];
        for(int i = 0; i < len; i++){
            for(int j = 0; j < parts; j++){
                for(int k = 0; k < row; k++){
                    zeroOne[i][j][k][0] = 0;
                    zeroOne[i][j][k][1] = 0;
                    zeroOne[i][j][k][2] = 0;
                    zeroOne[i][j][k][3] = 1;
                }
            }
        }
        return zeroOne;
    }
}


