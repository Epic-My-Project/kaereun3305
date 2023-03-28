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

class RegistActivity :AppCompatActivity(){
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registbutton: Button = findViewById(R.id.registbutton)
        val registID: EditText = findViewById(R.id.registid)
        val registPw: EditText = findViewById(R.id.registpw)
        val registPwConfirm: EditText = findViewById(R.id.registpwconfirm)
        val firstname: EditText = findViewById(R.id.registfirstname)
        val lastname: EditText = findViewById(R.id.registlastname)
        val textMail: EditText = findViewById(R.id.registusermail)
        val goback: ImageView = findViewById(R.id.goback)

        //username, password, usermail, firstname, last name


        val retrofit = Retrofit.Builder()
            .baseUrl("https://9d4f-117-16-195-14.jp.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var loginService = retrofit.create(RegisterService::class.java)

        goback.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
        }

        registbutton.setOnClickListener {
            var username = registID.text.toString()
            var password = registPw.text.toString()
            var registPwconfirm = registPwConfirm.text.toString()
            var firstname = firstname.text.toString()
            var lastname = lastname.text.toString()
            var email = textMail.text.toString()



            if(username.equals("")){
                Toast.makeText(this@RegistActivity, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
            if(password.equals("")){
                Toast.makeText(this@RegistActivity, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
            if(registPwconfirm.equals("")){
                Toast.makeText(this@RegistActivity, "확인용 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
            if(firstname.equals("")){
                Toast.makeText(this@RegistActivity, "성을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
            if(lastname.equals("")){
                Toast.makeText(this@RegistActivity, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }

            if(password != registPwconfirm) {
                Toast.makeText(this@RegistActivity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }


            loginService.requestLogin(username, password, firstname, lastname, email).enqueue(object:
                Callback<Register> {
                override fun onFailure(call: Call<Register>, t: Throwable) {
                    //통신 실패후 실행 코드
                    var dialog = AlertDialog.Builder(this@RegistActivity)
                    dialog.setTitle("error")
                    dialog.setMessage("통신에 실패하였습니다")
                    dialog.show()
                }
                override fun onResponse(call: Call<Register>, response: Response<Register>) {
                    // 통신 성공. 응답값 받아옴
                    var login = response.body() //code, msg

                    var dialog = AlertDialog.Builder(this@RegistActivity)
                    dialog.setTitle("error")
                    dialog.setMessage(login?.msg + "(code:" + login?.code + ")")
                    dialog.show()
                }
            })
        }
    }
}