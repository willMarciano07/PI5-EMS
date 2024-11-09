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




    class EditarProdutoActivity : AppCompatActivity() {

        private lateinit var nomeEditText: EditText
        private lateinit var descricaoEditText: EditText
        private lateinit var precoEditText: EditText
        private lateinit var imagemEditText: EditText
        private lateinit var salvarButton: Button

        private var produtoId: Int = 0

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_editar_produto)

            nomeEditText = findViewById(R.id.nomeEditText)
            descricaoEditText = findViewById(R.id.descricaoEditText)
            precoEditText = findViewById(R.id.precoEditText)
            imagemEditText = findViewById(R.id.imagemEditText)
            salvarButton = findViewById(R.id.salvarButton)

            // Resgatar os dados passados pela Intent
            produtoId = intent.getIntExtra("ID_RECEITA", 0)
            nomeEditText.setText(intent.getStringExtra("NOME_RECEITA"))
            descricaoEditText.setText(intent.getStringExtra("DESCRICAO_RECEITA"))
            precoEditText.setText(intent.getStringExtra("VALOR_RECEITA"))
            imagemEditText.setText(intent.getStringExtra("IMAGEM_RECEITA"))

            // ConfiguraÃ§Ã£o do Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.15.5/api/") // Substitua pelo seu endereÃ§o base
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

            salvarButton.setOnClickListener {
                // Atualizar produto via API
                val produtoAtualizado = Receita(
                    produtoId,
                    nomeEditText.text.toString(),
                    descricaoEditText.text.toString(),
                    precoEditText.text.toString(),
                    imagemEditText.text.toString()
                )

                apiService.editarProduto(
                    produtoAtualizado.ID_RECEITA,
                    produtoAtualizado.NOME_RECEITA,
                    produtoAtualizado.DESCRICAO_RECEITA,
                    produtoAtualizado.VALOR_RECEITA,
                    produtoAtualizado.IMAGEM_RECEITA
                ).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@EditarProdutoActivity, "Receita atualizada com sucesso!", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this@EditarProdutoActivity, "Erro na atualizaÃ§Ã£o", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@EditarProdutoActivity, "Erro ao atualizar o produto", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }




