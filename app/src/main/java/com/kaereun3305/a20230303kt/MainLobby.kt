package com.kaereun3305.a20230303kt

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.squareup.picasso.Callback as PicassoCallback

class MainLobby : AppCompatActivity() {
    private lateinit var movieList: RecyclerView
    private var data: MutableList<movies> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieList = findViewById(R.id.movieList)

        class ItemSpacingDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.right = space
                outRect.left = space
                outRect.bottom = space
                outRect.top = space
            }
        }
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        val itemSpacingDecoration = ItemSpacingDecoration(spacingInPixels)
        movieList.addItemDecoration(itemSpacingDecoration)
        movieList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://ce17-117-16-244-19.jp.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val movieService = retrofit.create(MovieService::class.java)

        val movieAdapter = MovieAdapter(data)
        movieList.adapter = movieAdapter


        movieService.getMovies().enqueue(object : Callback<ServerResponse> {
            override fun onResponse(
                call: Call<ServerResponse>,
                response: Response<ServerResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.data
                    if (responseBody != null) {
                        data.clear()
                        data.addAll(responseBody)
                        val movieAdapter = MovieAdapter(data)
                        movieList.adapter = movieAdapter
                    } else {
                        Log.e("MainActivity", "Response body is null")
                    }
                } else {
                    Log.e("MainActivity", "onResponse failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Toast.makeText(this@MainLobby, "네트워크 연결 실패", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "onFailure", t)
            }
        })
    }

    private class MovieAdapter(private val movies: List<movies>) :
        RecyclerView.Adapter<MovieViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
            return MovieViewHolder(view)
        }

        override fun getItemCount() = movies.size

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val movie = movies[position]
            holder.title.text = movie.title
            Picasso.get().load(movie.imglink).into(holder.imglink, object : PicassoCallback {
                override fun onSuccess() {
                    Log.d("MainActivity", "Picasso onSuccess")
                }

                override fun onError(e: Exception?) {
                    Log.e("MainActivity", "Picasso onError", e)
                }
            })
        }
    }

    private class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val imglink: ImageView = view.findViewById(R.id.image)
    }
}