package com.italkutalk.B07170039App08

import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    //回傳權限要求後的結果
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (grantResults.isNotEmpty() && requestCode == 0) {
            for (result in grantResults)
                if (result != PackageManager.PERMISSION_GRANTED)
                    finish() //若拒絕給予定位權限，則關閉應用程式
            loadMap() //因允許定位權限，所以正常執行應用程式
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMap() //取得地圖元件並載入
    }
    override fun onMapReady(map: GoogleMap) {
        //檢查使用者是否已授權定位權限
        if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            //精確定位包含粗略定位，因此只要求精確定位權限
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    0)
        } else {
            //顯示目前位置與目前位置的按鈕
            map.isMyLocationEnabled = true
            //加入標記
            val marker = MarkerOptions()
            marker.position(LatLng(24.864422473836935, 120.99062188205238))
            marker.title("明新科技大學")
            marker.draggable(true)
            map.addMarker(marker)
            marker.position(LatLng(24.869408827141875, 120.99645329739539))
            marker.title("新豐車站")
            marker.draggable(true)
            map.addMarker(marker)
            //繪製線段
            val polylineOpt = PolylineOptions()
            polylineOpt.add(LatLng(24.864422473836935, 120.99062188205238))//明新
            //polylineOpt.add(LatLng(25.032435, 121.534905))//大安
            polylineOpt.add(LatLng(24.869408827141875, 120.99645329739539))//火車站
            polylineOpt.color(Color.BLUE)
            val polyline = map.addPolyline(polylineOpt)
            polyline.width = 10f
            //移動視角
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(24.864422473836935, 120.99062188205238), 15f))
        }
    }
    private fun loadMap() {
        //連接 SupportMapFragment 元件並載入地圖
        val map = supportFragmentManager.findFragmentById(R.id.map)
                as SupportMapFragment
        map.getMapAsync(this)
    }
}