package com.example.motionet;

public class Bones {
    //Arm bones length
    private double leftUpperArmLength;
    private double rightUpperArmLength;
    private double leftDownArmLength;
    private double rightDownArmLength;

    //Leg bones length
    private double leftUpperLegLength;
    private double rightUpperLegLength;
    private double leftDownLegLength;
    private double rightDownLegLength;

    //Spine length
    private double spineLength;

    //neck length
    private double neck;

    public double getLeftUpperArmLength() {
        return leftUpperArmLength;
    }

    public void setLeftUpperArmLength(double leftUpperArmLength) {
        this.leftUpperArmLength = leftUpperArmLength;
    }

    public double getRightUpperArmLength() {
        return rightUpperArmLength;
    }

    public void setRightUpperArmLength(double rightUpperArmLength) {
        this.rightUpperArmLength = rightUpperArmLength;
    }

    public double getLeftDownArmLength() {
        return leftDownArmLength;
    }

    public void setLeftDownArmLength(double leftDownArmLength) {
        this.leftDownArmLength = leftDownArmLength;
    }

    public double getRightDownArmLength() {
        return rightDownArmLength;
    }

    public void setRightDownArmLength(double rightDownArmLength) {
        this.rightDownArmLength = rightDownArmLength;
    }

    public double getLeftUpperLegLength() {
        return leftUpperLegLength;
    }

    public void setLeftUpperLegLength(double leftUpperLegLength) {
        this.leftUpperLegLength = leftUpperLegLength;
    }

    public double getRightUpperLegLength() {
        return rightUpperLegLength;
    }

    public void setRightUpperLegLength(double rightUpperLegLength) {
        this.rightUpperLegLength = rightUpperLegLength;
    }

    public double getLeftDownLegLength() {
        return leftDownLegLength;
    }

    public void setLeftDownLegLength(double leftDownLegLength) {
        this.leftDownLegLength = leftDownLegLength;
    }

    public double getRightDownLegLength() {
        return rightDownLegLength;
    }

    public void setRightDownLegLength(double rightDownLegLength) {
        this.rightDownLegLength = rightDownLegLength;
    }

    public double getSpineLength() {
        return spineLength;
    }

    public void setSpineLength(double spineLength) {
        this.spineLength = spineLength;
    }

    public double getNeck() {
        return neck;
    }

    public void setNeck(double neck) {
        this.neck = neck;
    }

    public String toString(){
        return "leftDownLegLength : " + getLeftDownLegLength() + "\n"+
                "RIGHTDownLegLength : " + getRightDownLegLength() + "\n"+
                "leftUpLegLength : " + getLeftUpperLegLength() + "\n"+
                "rightUpLegLength : " + getRightUpperLegLength() + "\n"+
                "leftDownArmLength : " + getLeftDownArmLength() + "\n"+
                "rightDownArmLength : " + getRightDownArmLength() + "\n"+
                "leftUpArmLength : " + getLeftUpperArmLength() + "\n"+
                "rightUpArmLength : " + getRightUpperArmLength() + "\n"+
                "Spine : " + getSpineLength() + "\n"+
                "Neck : " + getNeck() + "\n";
    }
}
