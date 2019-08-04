package com.jenny.domain.usecase

import com.jenny.common.ApplicationExceptions
import com.jenny.domain.executor.PostExecutionThread
import com.jenny.domain.repository.MoviesRepository
import com.jenny.domain.response.TopRatedMovieResponse
import com.jenny.domain.usecase.common.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetTopRatedMovies @Inject
constructor(
    private val movieRepository: MoviesRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<TopRatedMovieResponse, GetTopRatedMovies.Params>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Single<TopRatedMovieResponse> {
        params?.let {
            if (it.pageNo == 0) {
                throw IllegalArgumentException(ApplicationExceptions.NO_ZERO_PAGE)
            }
            return movieRepository.getTopRatedMovies(it.pageNo)
        }
        throw IllegalArgumentException()
    }

    data class Params constructor(val pageNo: Int) {
        companion object {
            fun getParams(pageNo: Int) = Params(pageNo)
        }
    }

}