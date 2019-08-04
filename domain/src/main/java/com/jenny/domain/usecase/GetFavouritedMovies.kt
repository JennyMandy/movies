package com.jenny.domain.usecase

import com.jenny.domain.executor.PostExecutionThread
import com.jenny.domain.model.Movie
import com.jenny.domain.repository.MoviesRepository
import com.jenny.domain.usecase.common.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetFavouritedMovies @Inject
constructor(
    private val movieRepository: MoviesRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<MutableList<Movie>, Void>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Void?): Single<MutableList<Movie>> = movieRepository.getFavouritedMovies()

}