package com.jenny.domain.usecase

import com.jenny.common.ApplicationExceptions
import com.jenny.domain.executor.PostExecutionThread
import com.jenny.domain.model.Movie
import com.jenny.domain.repository.MoviesRepository
import com.jenny.domain.usecase.common.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetSelectedMovies @Inject
constructor(
    private val movieRepository: MoviesRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Movie, GetSelectedMovies.Params>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Single<Movie> {
        params?.let {
            if (it.movieId == 0) {
                throw IllegalArgumentException(ApplicationExceptions.NO_ZERO_ID)
            }
            return movieRepository.getSelectedMovie(it.movieId)
        }
        throw IllegalArgumentException()
    }

    data class Params constructor(val movieId: Int) {
        companion object {
            fun getParams(movieId: Int) = Params(movieId)
        }
    }

}