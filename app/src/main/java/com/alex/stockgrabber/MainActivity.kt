package com.alex.stockgrabber

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val request = Request.Builder()
            .url("https://quality.data.gov.tw/dq_download_json.php?nid=11549&md5_url=bb878d47ffbe7b83bfc1b41d0b24946e")
            .build()
        OkHttpClient().newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    println(e?.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response?.body()?.string()
                    println(json)
                    val stockType = object : TypeToken<List<StockNG>>(){}.type
                    val stocks : List<StockNG> = Gson().fromJson(json,stockType)
                    for (stock in stocks){
                        println ("${stock.證券代號} : ${stock.證券名稱}")
                    }
                }
            })
    }
}
