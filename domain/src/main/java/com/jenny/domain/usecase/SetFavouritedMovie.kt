package com.jenny.domain.usecase

import com.jenny.common.ApplicationExceptions
import com.jenny.domain.executor.PostExecutionThread
import com.jenny.domain.model.Movie
import com.jenny.domain.repository.MoviesRepository
import com.jenny.domain.usecase.common.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class SetFavouritedMovie @Inject
constructor(
    private val movieRepository: MoviesRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<SetFavouritedMovie.Params>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Completable {
        params?.let {
            return movieRepository.setFavouriteMovie(params.movie)
        }
        throw IllegalArgumentException(ApplicationExceptions.NO_NULL_PARAMS)
    }


    data class Params constructor(val movie: Movie) {
        companion object {
            fun getParams(movie: Movie) = Params(movie)
        }
    }

}