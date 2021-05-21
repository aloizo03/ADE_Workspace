package com.example.motionet;

public class posePart {
    public position NOSE;
    public position LEFT_EYE;
    public position RIGHT_EYE;
    public position LEFT_EAR;
    public position RIGHT_EAR;
    public position CHEST;
    public position LEFT_SHOULDER;
    public position RIGHT_SHOULDER;
    public position LEFT_ELBOW;
    public position RIGHT_ELBOW;
    public position LEFT_WRIST;
    public position RIGHT_WRIST;
    public position ROOT_POSITION;//Pelvis
    public position LEFT_HIP;
    public position RIGHT_HIP;
    public position LEFT_KNEE;
    public position RIGHT_KNEE;
    public position LEFT_ANKLE;
    public position RIGHT_ANKLE;
    public boolean LeftFootContactLabel;
    public boolean RightFootContactLabel;

    public boolean isLeftFootContactLabel() {
        return LeftFootContactLabel;
    }

    public void setLeftFootContactLabel(boolean leftFootContactLabel) {
        LeftFootContactLabel = leftFootContactLabel;
    }

    public boolean isRightFootContactLabel() {
        return RightFootContactLabel;
    }

    public void setRightFootContactLabel(boolean rightFootContactLabel) {
        RightFootContactLabel = rightFootContactLabel;
    }

    public position getNOSE() {
        return NOSE;
    }

    public void setNOSE(position nose) {
        NOSE = nose;
    }

    public position getLEFT_EYE() {
        return LEFT_EYE;
    }

    public void setLEFT_EYE(position LEFT_EYE) {
        this.LEFT_EYE = LEFT_EYE;
    }

    public position getRIGHT_EYE() {
        return RIGHT_EYE;
    }

    public void setRIGHT_EYE(position RIGHT_EYE) {
        this.RIGHT_EYE = RIGHT_EYE;
    }

    public position getLEFT_EAR() {
        return LEFT_EAR;
    }

    public void setLEFT_EAR(position LEFT_EAR) {
        this.LEFT_EAR = LEFT_EAR;
    }

    public position getRIGHT_EAR() {
        return RIGHT_EAR;
    }

    public void setRIGHT_EAR(position RIGHT_EAR) {
        this.RIGHT_EAR = RIGHT_EAR;
    }

    public position getLEFT_SHOULDER() {
        return LEFT_SHOULDER;
    }

    public void setLEFT_SHOULDER(position LEFT_SHOULDER) {
        this.LEFT_SHOULDER = LEFT_SHOULDER;
    }

    public position getRIGHT_SHOULDER() {
        return RIGHT_SHOULDER;
    }

    public void setRIGHT_SHOULDER(position RIGHT_SHOULDER) {
        this.RIGHT_SHOULDER = RIGHT_SHOULDER;
    }

    public position getLEFT_ELBOW() {
        return LEFT_ELBOW;
    }

    public void setLEFT_ELBOW(position LEFT_ELBOW) {
        this.LEFT_ELBOW = LEFT_ELBOW;
    }

    public position getRIGHT_ELBOW() {
        return RIGHT_ELBOW;
    }

    public void setRIGHT_ELBOW(position RIGHT_ELBOW) {
        this.RIGHT_ELBOW = RIGHT_ELBOW;
    }

    public position getLEFT_WRIST() {
        return LEFT_WRIST;
    }

    public void setLEFT_WRIST(position LEFT_WRIST) {
        this.LEFT_WRIST = LEFT_WRIST;
    }

    public position getRIGHT_WRIST() {
        return RIGHT_WRIST;
    }

    public void setRIGHT_WRIST(position RIGHT_WRIST) {
        this.RIGHT_WRIST = RIGHT_WRIST;
    }

    public position getROOT_POSITION() {
        return ROOT_POSITION;
    }

    public void setROOT_POSITION(position leftHip, position rightHip) {
        double x = (leftHip.getX() + rightHip.getX())/2;
        double y = (leftHip.getY() + rightHip.getY())/2;
        position pos = new position();
        pos.setY(y);
        pos.setX(x);
        ROOT_POSITION = pos;

    }

    public position getCHEST() {
        return CHEST;
    }

    public void setCHEST(position leftShoulder, position rightShoulder) {
        double x = (leftShoulder.getX() + rightShoulder.getX())/2;
        double y = (leftShoulder.getY() + rightShoulder.getY())/2;
        position pos = new position();
        pos.setY(y);
        pos.setX(x);
        this.CHEST = pos;
    }

    public void setCHEST(position chest) {
        this.CHEST = chest;
    }

    public void setROOT_POSITION(position ROOT_POSITION) {
        this.ROOT_POSITION = ROOT_POSITION;
    }

    public position getLEFT_HIP() {
        return LEFT_HIP;
    }

    public void setLEFT_HIP(position LEFT_HIP) {
        this.LEFT_HIP = LEFT_HIP;
    }

    public position getRIGHT_HIP() {
        return RIGHT_HIP;
    }

    public void setRIGHT_HIP(position RIGHT_HIP) {
        this.RIGHT_HIP = RIGHT_HIP;
    }

    public position getLEFT_KNEE() {
        return LEFT_KNEE;
    }

    public void setLEFT_KNEE(position LEFT_KNEE) {
        this.LEFT_KNEE = LEFT_KNEE;
    }

    public position getRIGHT_KNEE() {
        return RIGHT_KNEE;
    }

    public void setRIGHT_KNEE(position RIGHT_KNEE) {
        this.RIGHT_KNEE = RIGHT_KNEE;
    }

    public position getLEFT_ANKLE() {
        return LEFT_ANKLE;
    }

    public void setLEFT_ANKLE(position LEFT_ANKLE) {
        this.LEFT_ANKLE = LEFT_ANKLE;
    }

    public position getRIGHT_ANKLE() {
        return RIGHT_ANKLE;
    }

    public void setRIGHT_ANKLE(position RIGHT_ANKLE) {
        this.RIGHT_ANKLE = RIGHT_ANKLE;
    }

    public String toString(){
        String str = "Nose : "+ this.NOSE.toString()+" \n"
                    +"LEFT_EYE : "+ this.LEFT_EYE.toString()+ " \n"
                    +"RIGHT_EYE : "+ this.RIGHT_EYE.toString()+ " \n"
                    +"LEFT_EAR : "+ this.LEFT_EAR.toString()+ " \n"
                    +"RIGHT_EAR : "+ this.RIGHT_EAR.toString()+ " \n"
                    +"CHEST : "+ this.CHEST.toString()+ " \n"
                    +"LEFT_SHOULDER : "+ this.LEFT_SHOULDER.toString()+ " \n"
                    +"RIGHT_SHOULDER : "+ this.RIGHT_SHOULDER.toString()+ " \n"
                    +"LEFT_ELBOW : "+ this.LEFT_ELBOW.toString()+ " \n"
                    +"RIGHT_ELBOW : "+ this.RIGHT_ELBOW.toString()+ " \n"
                    +"LEFT_WRIST : "+ this.LEFT_WRIST.toString()+ " \n"
                    +"RIGHT_WRIST : "+ this.RIGHT_WRIST.toString()+ " \n"
                    +"LEFT_HIP : "+ this.LEFT_HIP.toString()+ " \n"
                    +"Root position : " + this.getROOT_POSITION().toString()+" \n"
                    +"RIGHT_HIP : "+ this.RIGHT_HIP.toString()+ " \n"
                    +"LEFT_KNEE : "+ this.LEFT_KNEE.toString()+ " \n"
                    +"RIGHT_KNEE : "+ this.RIGHT_KNEE.toString()+ " \n"
                    +"LEFT_ANKLE : "+ this.LEFT_ANKLE.toString()+ " \n"
                    +"RIGHT_ANKLE : "+ this.RIGHT_ANKLE.toString()+ " \n"
                    +"Left Contact : "+ this.LeftFootContactLabel + "\n"
                    +"Right Contact : "+ this.RightFootContactLabel + "\n";

        return str;
    }


}
