package com.example.camgallery

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.camgallery.databinding.ActivityImageBinding
import java.util.Base64

class Image : AppCompatActivity() {
    private var _binding : ActivityImageBinding? = null
    private val binding get() = _binding!!
    private lateinit var intent: Intent

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = getIntent()
        val imageString = intent.getStringExtra("StringImage")
        val uri: Uri = Uri.parse(imageString)
        binding.imageView.setImageURI(uri)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}