package com.example.ems

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    // Metodo para obter a lista de produtos
    @GET("listagem/produtos.php")
    fun getProdutos(): Call<List<Receita>>

    // Metodo para incluir um produto
    @FormUrlEncoded
    @POST("incluir/incluir_receita.php")
    fun incluirProduto(
        @Field("NOME_RECEITA") nome: String,
        @Field("DESCRICAO_RECEITA") descricao: String,
        @Field("VALOR_RECEITA") preco: String,
        @Field("IMAGEM_RECEITA") imagem: String
    ): Call<Void>

    // Metodo para editar um produto
    @FormUrlEncoded
    @POST("atualizar/atualizar_receita.php")
    fun editarProduto(
        @Field("ID_RECEITA") id: Int,
        @Field("NOME_RECEITA") nome: String,
        @Field("DESCRICAO_RECEITA") descricao: String,
        @Field("VALOR_RECEITA") preco: String,
        @Field("IMAGEM_RECEITA") imagem: String
    ): Call<Void>

    // Metodo para deletar um produto
    @FormUrlEncoded
    @POST("deletar/deletar_receita.php")
    fun deletarProduto(
        @Field("ID_RECEITA") id: Int
    ): Call<Void>

}