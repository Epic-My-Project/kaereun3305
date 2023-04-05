package com.kaereun3305.a20230303kt

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetflixInfo :AppCompatActivity(){
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_netflixinfo)

        val infobutton: Button = findViewById(R.id.netflixinfo)
        val netID: EditText = findViewById(R.id.netflixid)
        val netPw: EditText = findViewById(R.id.netflixpw)
        val goback: ImageView = findViewById(R.id.goback)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://a4aa-117-16-195-25.jp.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var NetinfoService = retrofit.create(NetinfoService::class.java)

        goback.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
        }

        infobutton.setOnClickListener {
            var netid = netID.text.toString()
            var netpw = netPw.text.toString()

            if(netid.equals("")){
                Toast.makeText(this@NetflixInfo, "넷플릭스 아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
            if(netpw.equals("")){
                Toast.makeText(this@NetflixInfo, "넷플릭스 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }

            NetinfoService.requestNetInfo(netid, netpw).enqueue(object:
                Callback<netinfo> {
                override fun onResponse(call: Call<netinfo>, response: Response<netinfo>) {

                    var fromserver = response.body() //code, msg

                    var dialog = AlertDialog.Builder(this@NetflixInfo)
                    dialog.setTitle("error")
                    dialog.setMessage(fromserver?.msg + "(code:" + fromserver?.code + ")")
                    dialog.show()
                }

                override fun onFailure(call: Call<netinfo>, t: Throwable) {
                    //통신 실패후 실행 코드
                    var dialog = AlertDialog.Builder(this@NetflixInfo)
                    dialog.setTitle("error")
                    dialog.setMessage("통신에 실패하였습니다")
                    dialog.show()
                }


            })

        }
    }
}