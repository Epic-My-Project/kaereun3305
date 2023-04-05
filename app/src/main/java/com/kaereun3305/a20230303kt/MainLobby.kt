package com.kaereun3305.a20230303kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.kaereun3305.a20230303kt.ChatMessage
import com.kaereun3305.a20230303kt.ChatService

class MainLobby : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val button: Button = findViewById(R.id.sendbutton)
        val textID: EditText = findViewById(R.id.text)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://a4aa-117-16-195-25.jp.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var ChatService = retrofit.create(ChatService::class.java)

        button.setOnClickListener {
            var connectmsg = textID.text.toString()

            ChatService.sendChat(connectmsg).enqueue(object: Callback<ChatMessage>{
                override fun onFailure(call: Call<ChatMessage>, t: Throwable) {
                    //통신 실패후 실행 코드
                    var dialog = AlertDialog.Builder(this@MainLobby)
                    dialog.setTitle("error")
                    dialog.setMessage("통신에 실패하였습니다")
                    dialog.show()
                }
                override fun onResponse(call: Call<ChatMessage>, response: Response<ChatMessage>) {
                    // 통신 성공. 응답값 받아옴
                    var receive = response.body() //code, msg

                    Toast.makeText(getApplicationContext(), receive?.msg, Toast.LENGTH_LONG).show();
                    textID.setText("") // EditText 비우기
                }
            })
        }
    }
}