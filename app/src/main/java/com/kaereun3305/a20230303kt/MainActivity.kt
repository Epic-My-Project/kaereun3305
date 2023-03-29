package com.kaereun3305.a20230303kt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.loginbutton)
        val textID: EditText = findViewById(R.id.userid)
        val textPw: EditText = findViewById(R.id.password)
        val regis: TextView = findViewById(R.id.register)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://ce17-117-16-244-19.jp.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var loginService = retrofit.create(LoginService::class.java)

        button.setOnClickListener {
        var textId = textID.text.toString()
        var textPw = textPw.text.toString()

        loginService.requestLogin(textId, textPw).enqueue(object: Callback<Login>{
            override fun onFailure(call: Call<Login>, t: Throwable) {
                //통신 실패후 실행 코드
                var dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("error")
                dialog.setMessage("통신에 실패하였습니다.")
                dialog.show()
            }
            override fun onResponse(call: Call<Login>, response: Response<Login>) {

                if (!response.isSuccessful) {
                    // 로그인 실패 후 실행 코드
                    runOnUiThread {
                        var dialog = AlertDialog.Builder(this@MainActivity)
                        dialog.setTitle("error")
                        dialog.setMessage("회원 정보가 불일치합니다. " +
                                "아이디 혹은 비밀번호를 다시 확인해 주세요.")
                        dialog.show()
                    }
                    return
                }
                Toast.makeText(this@MainActivity, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@MainActivity, MainLobby::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_down_enter, R.anim.slide_up_exit)
                }
            })
        }

        regis.setOnClickListener{
            val intent1 = Intent(this, RegistActivity::class.java)
            startActivity(intent1)
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
        }
    }
}