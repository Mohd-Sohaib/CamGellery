package com.example.camgallery


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.camgallery.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.util.Base64


class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var intent: Intent


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            imageCapture.launch(intent)
        }

        binding.btnGallery.setOnClickListener {
            intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            imagePicker.launch(intent)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private var imagePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK && result.data != null){
            val uriImage : Uri? = result.data?.data

            intent = Intent(this, Image::class.java)
            intent.putExtra("StringImage", uriImage.toString())
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private var imageCapture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
        if(result.resultCode == RESULT_OK && result.data != null){
            val imageBitmap = result.data?.extras?.get("data") as Bitmap

            //Encode bitmap to uri
            val baos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
            val path : String = MediaStore.Images.Media.insertImage(this.contentResolver,imageBitmap,"photo",null)
            val uri : Uri = Uri.parse(path)

            intent = Intent(this, Image::class.java)
            intent.putExtra("StringImage", uri.toString() )
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

