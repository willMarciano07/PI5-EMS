package com.example.ems

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomAdapterReceita {

    class CustomAdapter(private val dataSet: List<Receita>, private val apiService: ApiService) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nome: TextView = view.findViewById(R.id.nomeProduto)
            val descricao: TextView = view.findViewById(R.id.descricaoProduto)
            val preco: TextView = view.findViewById(R.id.precoProduto)
            val imagem: ImageView = view.findViewById(R.id.imagemProduto)
            val editarButton: Button = view.findViewById(R.id.editarButton)
            val deletarButton: Button = view.findViewById(R.id.deletarButton)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_produto, viewGroup, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            val produto = dataSet[position]
            viewHolder.nome.text = produto.NOME_RECEITA
            viewHolder.descricao.text = produto.DESCRICAO_RECEITA
            viewHolder.preco.text = "R$ ${produto.VALOR_RECEITA}"
            Picasso.get().load(produto.IMAGEM_RECEITA).into(viewHolder.imagem)

            // Passar os dados do produto para a Activity de ediÃ§Ã£o
            viewHolder.editarButton.setOnClickListener {
                val intent = Intent(it.context, EditarProdutoActivity::class.java)
                intent.putExtra("ID_RECEITA", produto.ID_RECEITA)
                intent.putExtra("NOME_RECEITA", produto.NOME_RECEITA)
                intent.putExtra("DESCRICAO_RECEITA", produto.DESCRICAO_RECEITA)
                intent.putExtra("VALOR_RECEITA", produto.VALOR_RECEITA)
                intent.putExtra("IMAGEM_RECEITA", produto.IMAGEM_RECEITA)
                it.context.startActivity(intent)
            }

            // Deletar produto ao clicar no botÃ£o
            viewHolder.deletarButton.setOnClickListener {
                apiService.deletarProduto(produto.ID_RECEITA).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Toast.makeText(
                            it.context,
                            "Receita deletada com sucesso!",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(it.context, "Erro ao deletar a receita", Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }
        }

        override fun getItemCount() = dataSet.size
    }

}