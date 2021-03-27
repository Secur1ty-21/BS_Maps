package com.example.myapplication

import android.app.Activity
import android.graphics.*
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.core.view.drawToBitmap


class MainActivity : Activity() {
    private var firstfloor: ImageView? = null
    private var way:ImageView? = null
    private var mX : Float = 0.0f
    private var mY : Float = 0.0f
    //UI
    var myBitmap = Bitmap.createBitmap(600, 800, Bitmap.Config.ARGB_8888);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firstfloor = findViewById(R.id.firstfloor)
        way = findViewById(R.id.bitmap)
      
        //var myBitmap = firstfloor?.drawToBitmap(Bitmap.Config.ARGB_8888)

        var canvas = Canvas(myBitmap)
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.setColor(Color.BLACK)
        canvas.drawBitmap(myBitmap, 0f, 0f, null)
        canvas.drawCircle(100f, 100f, 100f, paint)
        firstfloor?.draw(canvas)
        way?.setImageBitmap(myBitmap)




        way?.scaleX = 3.0f
        way?.scaleY = 3.0f
        firstfloor?.scaleX = 3.0f
        firstfloor?.scaleY = 3.0f
        firstfloor?.setOnTouchListener(PrivateOnTouchListener())
    }
//    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
//
//    }
    private inner class PrivateOnTouchListener : View.OnTouchListener {
        private var getLastX = 0.0f
        private var getLastY = 0.0f
        private var upLastX = 0.0f
        private var upLastY = 0.0f
        private var zumm = firstfloor?.scaleX!! * 0.5f
        override fun onTouch(v: View, event: MotionEvent): Boolean {
                var getX = event.x
                var getY = event.y
                when(event.action){
                    MotionEvent.ACTION_DOWN -> {
                        getLastX = event.x
                        getLastY = event.y
//                        runOnUiThread {
//                            Toast.makeText(this@MainActivity, "Down $getLastX", Toast.LENGTH_SHORT).show()
//                        }
                    }
                    MotionEvent.ACTION_MOVE -> {
                        var deltaX = getX - getLastX
                        var deltaY = getY - getLastY
                        if (deltaX + upLastX < 300f * zumm && deltaX + upLastX > -300f * zumm && deltaY + upLastY < 200f * zumm && deltaY + upLastY > -200f * zumm) {
                            runOnUiThread {
                                firstfloor?.scrollX = (deltaX.toInt() + upLastX.toInt()) * -1
                                firstfloor?.scrollY = (deltaY.toInt() + upLastY.toInt()) * -1
                                way?.scrollX = (deltaX.toInt() + upLastX.toInt()) * -1
                                way?.scrollY = (deltaY.toInt() + upLastY.toInt()) * -1

                            }
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        upLastX += (getLastX - event.x) * -1
                        upLastY += (getLastY - event.y) * -1
                        if (upLastX > 300f * zumm || upLastX < -300f * zumm) {
                            if (upLastX > 0)
                                upLastX = 299f * zumm
                            if (upLastX < 0)
                                upLastX = -299f * zumm
                        }
                        if (upLastY > 200f * zumm || upLastY < -200f * zumm) {
                            if (upLastY > 0)
                                upLastY = 199f * zumm
                            if (upLastY < 0)
                                upLastY = -199f * zumm
                        }
//                        runOnUiThread {
//                            Toast.makeText(this@MainActivity, "Up $upLastX", Toast.LENGTH_SHORT).show()
//                        }
                    }
                }
            return true
        }
    }




}