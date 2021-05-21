package com.example.motionet;

public class position {
    private double x;
    private double y;
    private double z;

    private double rotationX;
    private double rotationY;
    private double rotationZ;

    private double angle;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle() {
        // Returns a value based on the quadrant and does some math to deliver the angle
        // of the coordinates
        if (x == 0 && y == 0) {
            this.angle =  (90 - (Math.atan2(y , x) * 180 / Math.PI));
        } else if (x >= 0 && y > 0) {
            this.angle =  (90 - (Math.atan2(y , x) * 180 / Math.PI));
        } else if (x > 0 && y <= 0) {
            this.angle = (90 - (Math.atan2(y , x) * 180 / Math.PI));
        } else if (x <= 0 && y < 0) {
            this.angle = (180 + (Math.atan2(y , x) * 180 / Math.PI));
        } else if (x < 0 && y >= 0) {
            this.angle = (270 - (Math.atan2(y , x) * 180 / Math.PI));
        } else
            this.angle = (90 - (Math.atan2(y , x) * 180 / Math.PI));
    }


    public double getRotationX() {
        return rotationX;
    }

    public void setRotationX(double rotationX) {
        this.rotationX = rotationX * 180 / Math.PI;
    }

    public double getRotationY() {
        return rotationY;
    }

    public void setRotationY(double rotationY) {
        this.rotationY = rotationY * 180 / Math.PI;
    }

    public double getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(double rotationZ) {
        this.rotationZ = rotationZ * 180 / Math.PI ;
    }


    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setAllRotationZero(){
        rotationX = 0;
        rotationY = 0;
        rotationZ = 0;
    }
    public String toString(){
        return "X : "+ getX()+" , Y : "+getY()+" , Z : "+getZ()+", Angle : "+ getAngle();
    }


}
