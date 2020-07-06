package com.example.examenfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_amigos.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AmigosActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AmigosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amigos)


        adapter = AmigosAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        amigosRecyclerView.layoutManager= linearLayoutManager
        amigosRecyclerView.adapter = adapter

        callService()

    }



    private fun callService() {
        val service = RepositoryAmigos.RetrofitRepository.getService()

        GlobalScope.launch(Dispatchers.IO) {
            val response =  service.getUsers()

            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {
                        val users : List<AmigosReponse>?  = response.body()
                        if( users != null) updateInfo(users)
                    }else{
                        Toast.makeText(this@AmigosActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@AmigosActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(list: List<AmigosReponse>) {
        adapter.updateList(list)
    }


    //GSON
    fun onItemClickListener(item: AmigosReponse) {
        Toast.makeText(this, "Para comunicarte con  ${item.name} puedes comunicarte al ${item.phone}", Toast.LENGTH_LONG).show()

        val amigoString : String = Gson().toJson(item, AmigosReponse::class.java)
        Log.d("GSON Class to String", amigoString )


        val user : AmigosReponse = Gson().fromJson(amigoString, AmigosReponse::class.java)
        Log.d("GSON string to class", user.name )
    }



}