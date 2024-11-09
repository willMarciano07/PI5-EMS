package com.example.ems

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


    class IncluirProdutoActivity : AppCompatActivity() {

        private lateinit var nomeEditText: EditText
        private lateinit var descricaoEditText: EditText
        private lateinit var precoEditText: EditText
        private lateinit var imagemEditText: EditText
        private lateinit var salvarButton: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_incluir_produto)

            nomeEditText = findViewById(R.id.nomeEditText)
            descricaoEditText = findViewById(R.id.descricaoEditText)
            precoEditText = findViewById(R.id.precoEditText)
            imagemEditText = findViewById(R.id.imagemEditText)
            salvarButton = findViewById(R.id.salvarButton)

            // ConfiguraÃ§Ã£o do Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.15.5/api/") // Substitua pelo seu endereÃ§o base
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

            salvarButton.setOnClickListener {
                val novoProduto = Receita(
                    0,  // O ID serÃ¡ gerado automaticamente no banco de dados
                    nomeEditText.text.toString(),
                    descricaoEditText.text.toString(),
                    precoEditText.text.toString(),
                    imagemEditText.text.toString()
                )

                // Fazer a requisiÃ§Ã£o para incluir o produto
                apiService.incluirProduto(
                    nomeEditText.text.toString(),
                    descricaoEditText.text.toString(),
                    precoEditText.text.toString(),
                    imagemEditText.text.toString()
                ).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@IncluirProdutoActivity, "Produto incluÃƒÂ­do com sucesso!", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this@IncluirProdutoActivity, "Erro na inclusÃ£o", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@IncluirProdutoActivity, "Erro ao incluir o produto", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

