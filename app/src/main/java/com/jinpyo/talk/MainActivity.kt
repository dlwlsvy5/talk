package com.jinpyo.talk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jinpyo.talk.databinding.ActivityMainBinding

private lateinit var auth: FirebaseAuth
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth= Firebase.auth

        val email=binding.etLoginId
        val password= binding.etLoginPassword
        val btn_loging=binding.profileButton
        btn_loging.setOnClickListener {
            if(email.text.isEmpty() && password.text.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()
                Log.d("Email", "$email, $password")
                email.setText("")
                password.setText("") }
            else{
                signIn(email.text.toString(), password.text.toString())
            }
        }

        //회원가입창 인텐트
        val btn_registration=binding.btnRegistration
        btn_registration.setOnClickListener {
            val intent= Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        val intentMain = Intent(this, HomeActivity::class.java)
        auth.signInWithEmailAndPassword(email, password) .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) { Log.d("로그인", "성공")
                val user = auth.currentUser
                updateUI(user)
                finish()
                startActivity(intentMain)
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(this, "정확한 아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                Log.d("로그인", "실패")
                updateUI(null)
            }
        }
        // [END sign_in_with_email] }
    }

    private fun updateUI(user: FirebaseUser?) {


    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }
    private fun reload() {

    }



}
