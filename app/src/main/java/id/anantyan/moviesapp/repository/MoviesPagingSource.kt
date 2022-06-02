package id.anantyan.moviesapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.anantyan.moviesapp.data.api.MoviesApi
import id.anantyan.moviesapp.model.ResultsItem
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(
    private val moviesApi: MoviesApi,
    private val category: String
) : PagingSource<Int, ResultsItem>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val position = params.key ?: 1
        return try {
            val response = when (category) {
                "trending" -> {
                    moviesApi.byTrendingWeek(
                        page = position
                    )
                }
                else -> {
                    moviesApi.byCategory(
                        path = category,
                        page = position
                    )
                }
            }
            val list = response.body()?.results!!
            LoadResult.Page(
                data = list,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (list.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}