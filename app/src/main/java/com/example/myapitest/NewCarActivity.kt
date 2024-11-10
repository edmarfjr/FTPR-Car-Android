package com.example.myapitest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapitest.databinding.ActivityLoginBinding
import com.example.myapitest.databinding.ActivityNewCarBinding
import com.example.myapitest.model.CarValue
import com.example.myapitest.service.Result
import com.example.myapitest.service.retrofitClient
import com.example.myapitest.service.safeApiCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.checkerframework.checker.units.qual.C
import java.security.SecureRandom

class NewCarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewCarBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_car)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupView()
    }

    private fun setupView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener{
            finish()
        }
        binding.saveCta.setOnClickListener {
            save()
        }
    }

    private fun save(){
        if(!validateForm()) return
        CoroutineScope(Dispatchers.IO).launch {
            val carValue = CarValue(
                binding.imageUrl.text.toString(),
                binding.name.text.toString(),
                binding.ano.text.toString(),
                binding.licensa.text.toString(),
                binding.name.text.toString()
            )

            var result = safeApiCall { retrofitClient.apiService.addItens(carValue)}
            withContext(Dispatchers.Main){
                when (result){
                    is Result.Error -> {
                        Toast.makeText(
                            this@NewCarActivity
                            ,R.string.error_create,
                            Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        Toast.makeText(
                            this@NewCarActivity
                            ,getString(R.string.success_create,result.data.id),
                            Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun validateForm(): Boolean {
        if (binding.name.text.toString().isBlank()) {
            Toast.makeText(this, getString(R.string.error_validate_form, "Modelo"), Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.licensa.text.toString().isBlank()) {
            Toast.makeText(this, getString(R.string.error_validate_form, "Licensa"), Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.ano.text.toString().isBlank()) {
            Toast.makeText(this, getString(R.string.error_validate_form, "Ano"), Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.imageUrl.text.toString().isBlank()) {
            Toast.makeText(this, getString(R.string.error_validate_form, "Image Url"), Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.loc.text.toString().isBlank()) {
            Toast.makeText(this, getString(R.string.error_validate_form, "Localização"), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    companion object{
        fun newIntent(context: Context)= Intent(context, MainActivity::class.java)
    }

}