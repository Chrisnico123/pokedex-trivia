package com.example.pokedex

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokedex.model.DataItem
import com.example.pokedex.model.Pokemon
import com.example.pokedex.config.ApiService
import retrofit2.HttpException
import java.io.IOException

class PokemonPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiService.getListPokemon(nextPageNumber).execute()

            if (response.isSuccessful) {
                val responseData = response.body()
                val pokemonList = mutableListOf<Pokemon>()

                responseData?.results?.forEach { dataItem ->
                    val pokemonResponse = apiService.getPokemon(getIdFromUrl(dataItem.url ?: ""))
                    val pokemon = pokemonResponse.execute().body()
                    pokemon?.let { pokemonList.add(it) }
                }

                val prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1
                val nextKey = responseData?.next?.let { nextPageNumber + 1 }

                LoadResult.Page(
                    data = pokemonList,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception("Failed to fetch data"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun getIdFromUrl(url: String): String {
        val segments = url.split("/")
        return segments[segments.size - 2]
    }
}



