package com.example.myapplication

import android.app.Activity
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView



class MainActivity : Activity() {
    private var scale = 1f
    //private var touchMatrix: Matrix? = Matrix()
    private var scaleDetector: ScaleGestureDetector? =  null
    //UI
    private var firstFloor: ImageView? = null
    private var way: ImageView? = null
    var myBitmap = Bitmap.createBitmap(600, 800, Bitmap.Config.ARGB_8888);
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MyLog", "this")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstFloor = findViewById(R.id.firstfloor)
        way = findViewById(R.id.bitmap)
        scaleDetector = ScaleGestureDetector(this, ScaleListener())
        var canvas = Canvas(myBitmap)
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        canvas.drawBitmap(myBitmap, 0f, 0f, null)
        canvas.drawCircle(100f, 100f, 100f, paint)
        way?.setImageBitmap(myBitmap)
        way?.scaleX = 3.0f
        way?.scaleY = 3.0f
        firstFloor?.scaleX = 3.0f
        firstFloor?.scaleY = 3.0f
        firstFloor?.setOnTouchListener(PrivateOnTouchListener())
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector : ScaleGestureDetector) : Boolean{
            scale *= detector.scaleFactor
            //Log.d("MyLog", "detector ${detector.scaleFactor}")
            //Log.d("MyLog", "scale1 $scale")
            scale = Math.max(1.0f, Math.min(scale, 3f))
            //Log.d("MyLog", "scale2 $scale")
            runOnUiThread{
                firstFloor?.scaleX = scale
                firstFloor?.scaleY = scale
                way?.scaleX = scale
                way?.scaleY = scale
            }
            return true
        }
    }

     private inner class PrivateOnTouchListener : View.OnTouchListener{

        private var getLastX = 0.0f
        private var getLastY = 0.0f
        private var upLastX = 0.0f
        private var upLastY = 0.0f
        private var zoom = firstFloor?.scaleX!! * 0.5f
        override fun onTouch(v: View, event: MotionEvent): Boolean {
                scaleDetector?.onTouchEvent(event)
                val getX = event.x
                val getY = event.y
                when(event.action){
                    MotionEvent.ACTION_DOWN -> {
                        getLastX = event.x
                        getLastY = event.y
//                        runOnUiThread {
//                            Toast.makeText(this@MainActivity, "Down $getLastX", Toast.LENGTH_SHORT).show()
//                        }
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val deltaX = getX - getLastX
                        val deltaY = getY - getLastY
                        if (deltaX + upLastX < 300f * zoom && deltaX + upLastX > -300f * zoom && deltaY + upLastY < 200f * zoom && deltaY + upLastY > -200f * zoom) {
                            runOnUiThread {
                                firstFloor?.scrollX = (deltaX.toInt() + upLastX.toInt()) * -1
                                firstFloor?.scrollY = (deltaY.toInt() + upLastY.toInt()) * -1
                                way?.scrollX = (deltaX.toInt() + upLastX.toInt()) * -1
                                way?.scrollY = (deltaY.toInt() + upLastY.toInt()) * -1

                            }

                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        upLastX += (getLastX - event.x) * -1
                        upLastY += (getLastY - event.y) * -1
                        if (upLastX > 300f * zoom || upLastX < -300f * zoom) {
                            if (upLastX > 0)
                                upLastX = 299f * zoom
                            if (upLastX < 0)
                                upLastX = -299f * zoom
                        }
                        if (upLastY > 200f * zoom || upLastY < -200f * zoom) {
                            if (upLastY > 0)
                                upLastY = 199f * zoom
                            if (upLastY < 0)
                                upLastY = -199f * zoom
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