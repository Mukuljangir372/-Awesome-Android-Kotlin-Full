package com.mu.jan.problems.architecture

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.MockView
import androidx.lifecycle.LiveData
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mu.jan.problems.R

/**
 * Paging 3 library is a part of android jetpack.
 * It used for loading few items of list on screen at a time.
 *
 * To Implement Paging 3 library in your project. You need,
 * 1. PagingSource or DataSource class
 *    - class having two params, PagingSource<Key,Value>
 *    - It means key = page number and Value = data class
 *
 * 2. Repo
 * 3. PagingDataAdapter
 *
 */

class PagingActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //..
        /**
         * val myAdapter = MyAdapter()
         *
         * recyclerView.apply {
         *     layoutManger = ...
         *     setHasFixedSize(true)
         *     adapter = myAdapter
         *  }
         *
         *  lifeCycleScope.launch{
         *      viewModel.movieList.observe{ it ->
         *           myAdapter.submitData(it)
         *      }
         *  }
         *
         */
    }
}
class Movie{
    val id: String? = null
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}
interface MovieApiService{
    fun getList(): List<Movie>
}
//dataSource OR pagingSource
class MyDataSource(
    private val apiService: MovieApiService
) : PagingSource<Int,Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        //copied code from google
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPageNumber = params.key ?: 1
        val response = apiService.getList()
        return LoadResult.Page(
            data = response,
            prevKey =  if(nextPageNumber==1) null else nextPageNumber - 1,
            nextKey = nextPageNumber
        )
    }
}
//Repo
object MyRepo{
    fun getList(apiService: MovieApiService): LiveData<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MyDataSource(apiService) }
        ).liveData
    }
}
//adapter
class MyAdapter : PagingDataAdapter<Movie,MyAdapter.MyViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class MyViewHolder(itemView: View
    ): RecyclerView.ViewHolder(itemView){
        fun onBind(item: Movie){

        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
































