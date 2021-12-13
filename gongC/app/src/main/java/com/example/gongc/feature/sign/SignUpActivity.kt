package com.example.gongc.feature.sign

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.gongc.R
import com.example.gongc.feature.home.HomeActivity
import com.example.gongc.model.dataclass.AuthData
import com.example.gongc.model.network.RetrofitService
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val btnSignUp = findViewById<Button>(R.id.button_signup)
        btnSignUp.setOnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            loadData()
        }
    }


    private fun loadData(){
        /* [REST] 회원가입 버튼 눌렀을 때 POST 요청하기 */
        // TODO 로그인 이미 했을 시 해당 토큰으로 보내기
        val body = HashMap<String, String>()
        body["email"] = findViewById<EditText>(R.id.edit_signup_email).text.toString()
        body["password"] = findViewById<EditText>(R.id.edit_signup_password).text.toString()
//        body["confirmpw"] = findViewById<EditText>(R.id.edit_signup_confirmpw).text.toString()
        body["nickname"] = findViewById<EditText>(R.id.edit_signup_nickname).text.toString()

        val call: Call<AuthData> = RetrofitService.service_ct_tab.postAuthSignup(body)

        call.enqueue(object : Callback<AuthData> {
            override fun onFailure(call: Call<AuthData>, t: Throwable) {
                Log.d("debug11", "from login loaddata, fail $t")
            }

            override fun onResponse(call: Call<AuthData>, response: Response<AuthData>) {
                Log.d("debug11", "from login loaddata, on response")

                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { _ ->
                        val data = response.body()

                        // 토큰이 계속 초기화가 되기때문에 sharedPreferences로 저장하여 초기화 방지
                        val sharedPreferences = getSharedPreferences("sFile1", MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        val token = data?.responseData?.token // 사용자가 입력한 저장할 데이터
                        editor.putString("Token", token) // key, value를 이용하여 저장하는 형태
                        editor.apply()

                        // 홈 화면으로 이동
                        startActivity(intent)
                    }?: showError(response.errorBody())
            }

        })
    }

    private fun showError(error: ResponseBody?){
        /* [REST] 회원가입 이후 데이터 받아 올때 200이 아닐 경우 호출됨 */
        val e = error ?: return
        val ob = JSONObject(e.string())
        val txtErrormsg = findViewById<TextView>(R.id.txt_signup_errormsg)
        txtErrormsg.visibility = View.VISIBLE
        txtErrormsg.text = ob.getString("message")

    }
}