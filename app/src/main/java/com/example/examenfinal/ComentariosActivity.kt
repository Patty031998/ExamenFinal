package com.example.examenfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_comentarios_list.*
import kotlinx.android.synthetic.main.segment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ComentariosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comentarios)

        callService()
    }


    private fun callService(){
        val service = RepositoryComment.RetrofitRepository.getService()

        GlobalScope.launch(Dispatchers.IO) {
            val response =  service.getPosts()

            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {

                        val comentario : List<PostReponse>? = response.body()

                        Toast.makeText(this@ComentariosActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@ComentariosActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(comentario: Comentario) {
        if(comentario.user_image.isNotEmpty()){
            Picasso.get().load(comentario.user_image).into(iv_feed_foto_comment)
        }


        tv_feed_item_body.text = comentario.coment

    }
}