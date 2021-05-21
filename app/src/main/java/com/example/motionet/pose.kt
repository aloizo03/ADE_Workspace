package com.example.motionet

import android.content.Context
import android.graphics.*
import androidx.fragment.app.Fragment
import org.tensorflow.lite.examples.posenet.lib.BodyPart
import org.tensorflow.lite.examples.posenet.lib.Person
import org.tensorflow.lite.examples.posenet.lib.Posenet
import java.lang.Math.abs

class pose {
    private lateinit var posenet: Posenet
    private val MODEL_WIDTH = 257
    private val MODEL_HEIGHT = 257

    private var paint = Paint()
    private val circleRadius = 8.0f
    private val bodyJoints = listOf(
            Pair(BodyPart.LEFT_WRIST, BodyPart.LEFT_ELBOW),
            Pair(BodyPart.LEFT_ELBOW, BodyPart.LEFT_SHOULDER),
            Pair(BodyPart.LEFT_SHOULDER, BodyPart.RIGHT_SHOULDER),
            Pair(BodyPart.RIGHT_SHOULDER, BodyPart.RIGHT_ELBOW),
            Pair(BodyPart.RIGHT_ELBOW, BodyPart.RIGHT_WRIST),
            Pair(BodyPart.LEFT_SHOULDER, BodyPart.LEFT_HIP),
            Pair(BodyPart.LEFT_HIP, BodyPart.RIGHT_HIP),
            Pair(BodyPart.RIGHT_HIP, BodyPart.RIGHT_SHOULDER),
            Pair(BodyPart.LEFT_HIP, BodyPart.LEFT_KNEE),
            Pair(BodyPart.LEFT_KNEE, BodyPart.LEFT_ANKLE),
            Pair(BodyPart.RIGHT_HIP, BodyPart.RIGHT_KNEE),
            Pair(BodyPart.RIGHT_KNEE, BodyPart.RIGHT_ANKLE)
    )

    fun calculateKeyPoint(bitmap: Bitmap, context: Context): Person {

        posenet = Posenet(context!!)
        val person = posenet.estimateSinglePose(bitmap)


        return person;

    }

    public fun cropBitmap(bitmap: Bitmap): Bitmap {
        val bitmapRatio = bitmap.height.toFloat() / bitmap.width
        val modelInputRatio = MODEL_HEIGHT.toFloat() / MODEL_WIDTH
        var croppedBitmap = bitmap

        // Acceptable difference between the modelInputRatio and bitmapRatio to skip cropping.
        val maxDifference = 1e-5

        // Checks if the bitmap has similar aspect ratio as the required model input.
        when {
            abs(modelInputRatio - bitmapRatio) < maxDifference -> return croppedBitmap
            modelInputRatio < bitmapRatio -> {
                // New image is taller so we are height constrained.
                val cropHeight = bitmap.height - (bitmap.width.toFloat() / modelInputRatio)
                croppedBitmap = Bitmap.createBitmap(
                        bitmap,
                        0,
                        (cropHeight / 2).toInt(),
                        bitmap.width,
                        (bitmap.height - cropHeight).toInt()
                )
            }
            else -> {
                val cropWidth = bitmap.width - (bitmap.height.toFloat() * modelInputRatio)
                croppedBitmap = Bitmap.createBitmap(
                        bitmap,
                        (cropWidth / 2).toInt(),
                        0,
                        (bitmap.width - cropWidth).toInt(),
                        bitmap.height
                )
            }
        }
        return croppedBitmap
    }


    fun draw(canvas: Canvas, person: Person, bitmap: Bitmap): Canvas {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        val screenWidth: Int
        val screenHeight: Int
        val left: Int
        val right: Int
        val top: Int
        val bottom: Int

        if (canvas.height > canvas.width) {
            screenWidth = canvas.width
            screenHeight = canvas.width
            left = 0
            top = (canvas.height - canvas.width) / 2
        } else {
            screenWidth = canvas.height
            screenHeight = canvas.height
            left = (canvas.width - canvas.height) / 2
            top = 0
        }
        right = left + screenWidth
        bottom = top + screenHeight
        setPaint()
        canvas.drawBitmap(
                bitmap,
                Rect(0, 0, bitmap.width, bitmap.height),
                Rect(left, top, right, bottom),
                paint
        )

        val widthRatio = screenWidth.toFloat() / MODEL_WIDTH
        val heightRatio = screenHeight.toFloat() / MODEL_HEIGHT

        for (keyPoint in person.keyPoints) {
            if (keyPoint.score > 0.6) {
                val position = keyPoint.position
                val adjustedX: Float = position.x.toFloat() * widthRatio + left
                val adjustedY: Float = position.y.toFloat() * heightRatio + top
                canvas.drawCircle(adjustedX, adjustedY, circleRadius, paint)
            }
        }

        val LeftHip = person.keyPoints[11]
        val RightHip = person.keyPoints[12]
        if(LeftHip.score > 0.6 && RightHip.score > 0.6){
            val RootX = (LeftHip.position.x + RightHip.position.x)/2
            val RootY = (LeftHip.position.y + RightHip.position.y)/2

            val adjustedX: Float = RootX.toFloat() * widthRatio + left
            val adjustedY: Float = RootY.toFloat() * heightRatio + top
            canvas.drawCircle(adjustedX, adjustedY, circleRadius, paint)
        }

        val LeftShoulder = person.keyPoints[5]
        val RightShoulder = person.keyPoints[6]
        if(LeftShoulder.score > 0.6 && RightShoulder.score > 0.6){
            val ChestX = (LeftShoulder.position.x + RightShoulder.position.x)/2
            val ChestY = (LeftShoulder.position.y + RightShoulder.position.y)/2

            val adjustedX: Float = ChestX.toFloat() * widthRatio + left
            val adjustedY: Float = ChestY.toFloat() * heightRatio + top
            canvas.drawCircle(adjustedX, adjustedY, circleRadius, paint)
        }

        for (line in bodyJoints) {
            if (
                    (person.keyPoints[line.first.ordinal].score > 0.6) and
                    (person.keyPoints[line.second.ordinal].score > 0.6)
            ) {
                canvas.drawLine(
                        person.keyPoints[line.first.ordinal].position.x.toFloat() * widthRatio + left,
                        person.keyPoints[line.first.ordinal].position.y.toFloat() * heightRatio + top,
                        person.keyPoints[line.second.ordinal].position.x.toFloat() * widthRatio + left,
                        person.keyPoints[line.second.ordinal].position.y.toFloat() * heightRatio + top,
                        paint
                )
            }
        }

        return canvas;
    }

    private fun setPaint() {
        paint.color = Color.RED
        paint.textSize = 80.0f
        paint.strokeWidth = 8.0f
    }

}