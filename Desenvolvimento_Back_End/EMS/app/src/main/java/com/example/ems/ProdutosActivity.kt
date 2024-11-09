package com.example.ems

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

class ProdutosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapterReceita.CustomAdapter
    private lateinit var addProductButton: Button  // BotÃ£o de Adicionar Produto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produtos)

        recyclerView = findViewById(R.id.recyclerViewProdutos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addProductButton = findViewById(R.id.incluirProdutoButton) // Inicializando o botÃ£o

        // Configurando o Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.5/api/")  // Substitua pelo seu ip local
            .addConverterFactory(GsonConverterFactory.create())
            .client(configureOkHttpClient())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Buscar produtos da API
        apiService.getProdutos().enqueue(object : Callback<List<Receita>> {
            override fun onResponse(call: Call<List<Receita>>, response: Response<List<Receita>>) {
                if (response.isSuccessful) {
                    val produtos = response.body() ?: emptyList()
                    adapter = CustomAdapterReceita.CustomAdapter(produtos, apiService)
                    recyclerView.adapter = adapter
                } else {
                    Log.e("API Error", "Falha ao carregar os produtos. CÃ³digo: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Receita>>, t: Throwable) {
                Log.e("API Failure", "Erro ao carregar os produtos", t)
            }
        })

        // Adicionar listener para o botÃ£o de adicionar produto
        addProductButton.setOnClickListener {
            val intent = Intent(this, IncluirProdutoActivity::class.java)
            startActivity(intent)  // Abrir a tela de inclusÃ£o de produto
        }
    }

    // ConfiguraÃ§Ã£o do OkHttpClient com logging
    private fun configureOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor { message -> Log.d("OkHttp", message) }
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

}

