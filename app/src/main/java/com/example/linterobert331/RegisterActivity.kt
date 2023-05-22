package com.example.linterobert331

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class RegisterActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_GALLERY = 132
    private val REQUEST_IMAGE_CAMERA = 142
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firebaseAuth = FirebaseAuth.getInstance()

        storageReference = FirebaseStorage.getInstance().getReference("uploads")
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads")

        var registerButton = findViewById<Button>(R.id.button4)
        registerButton.setOnClickListener{
            val email = findViewById<TextInputEditText>(R.id.emailInput).text.toString()
            val password = findViewById<TextInputEditText>(R.id.passwordInput).text.toString()
            val repass = findViewById<TextInputEditText>(R.id.repasswordInput).text.toString()
            if(email.isNotEmpty() && password.isNotEmpty() && repass.isNotEmpty()){
                if(password == repass){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if(it.isSuccessful){
                            uploadProfilePic()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Emty Fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }

        val registerTextView = findViewById<Button>(R.id.button3)
        registerTextView.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Select image")
            builder.setMessage("Choose your option:")
            builder.setPositiveButton("Gallery"){ dialog, which ->
                dialog.dismiss()

                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/"
                startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
            }
            builder.setNegativeButton("Camera"){ dialog, which ->
                dialog.dismiss()
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA);
                }
            }
            val dialog :AlertDialog = builder.create()
            dialog.show()
        }

        val emailInput = findViewById<TextInputLayout>(R.id.editTextTextEmailAddress2)
        emailInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Aici verificați valoarea din input și decideți dacă trebuie să setați sau să eliminați eroarea
                if (isValidEmailAddress(s.toString())) {
                    emailInput.error = null // elimină eroarea dacă adresa de email este validă
                } else {
                    emailInput.error = "Email invalid" // setează eroarea dacă adresa de email nu este validă
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Implementați acțiuni pentru a fi efectuate înainte de schimbarea textului din input
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Implementați acțiuni pentru a fi efectuate în timpul schimbării textului din input
            }
        })

        var passwordInput = findViewById<TextInputLayout>(R.id.editTextTextPassword3)
        passwordInput.editText?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isValidPassword(s.toString())) {
                    passwordInput.error = null // elimină eroarea dacă adresa de email este validă
                } else {
                    passwordInput.error = "Must contain A1$" // setează eroarea dacă adresa de email nu este validă
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        var rePasswordInput = findViewById<TextInputLayout>(R.id.editTextTextPassword4)
        rePasswordInput.editText?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isValidPassword(s.toString())) {
                    rePasswordInput.error = null // elimină eroarea dacă adresa de email este validă
                } else {
                    rePasswordInput.error = "Must contain A1$" // setează eroarea dacă adresa de email nu este validă
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        val loginTextView = findViewById<TextView>(R.id.textView4)
        loginTextView.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null){
            val image = findViewById<ImageView>(R.id.imageView)
            imageUri = data.data!!
            image.setImageURI(data.data)
        }
        else if(requestCode == REQUEST_IMAGE_CAMERA && resultCode == Activity.RESULT_OK && data != null){
            val image = findViewById<ImageView>(R.id.imageView)
            image.setImageBitmap(data.extras?.get("data") as Bitmap)
        }
        else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    fun isValidEmailAddress(email: String): Boolean {
        val pattern = "^([\\w.-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$"
        val regex = Regex(pattern)
        return regex.matches(email)
    }
    fun isValidPassword(password: String): Boolean {
        val pattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}"
        val regex = Regex(pattern)
        return regex.matches(password)
    }

    fun getFileExtension(uri: Uri): String?{
        var cR: ContentResolver = contentResolver
        var mime: MimeTypeMap = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    fun uploadProfilePic(){
        if(imageUri != null){
            val email = findViewById<TextInputEditText>(R.id.emailInput).text.toString()
            val fileExtension = getFileExtension(imageUri)
            val fileReference: StorageReference = storageReference.child("$email.$fileExtension")
            fileReference.putFile(imageUri).addOnCompleteListener {
                if(it.isSuccessful){
                    println("DA")
                }else{
                    Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}