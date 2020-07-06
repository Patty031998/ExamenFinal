package com.example.examenfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.item_post_list.*
import kotlinx.android.synthetic.main.segment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FeedActivity : AppCompatActivity(), PostAdapter.PostHolder.OnAdapterListener{

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PostAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        btn_feed_item_comment.setOnClickListener{view ->
            val intent = Intent(this, ComentariosActivity::class.java)
            startActivity(intent)
        }

        adapter = PostAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        postRecyclerView.layoutManager= linearLayoutManager
        postRecyclerView.adapter = adapter

        callService()


    }

    private fun callService() {
        val service = Repository.RetrofitRepository.getService()

        GlobalScope.launch(Dispatchers.IO) {
            val response =  service.getPosts()

            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {
                        val posts : List<PostReponse>?  = response.body()
                        if( posts != null) updateInfo(posts)
                    }else{
                        Toast.makeText(this@FeedActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@FeedActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(list: List<PostReponse>) {
        adapter.updateList(list)
    }


    //GSON
    override fun onItemClickListener(item: PostReponse) {


        val postString : String = Gson().toJson(item, PostReponse::class.java)
        Log.d("GSON Class to String", postString )



        val post : PostReponse = Gson().fromJson(postString, PostReponse::class.java)
        Log.d("GSON string to class", post.username )
    }


}