package com.jenny.domain.executor

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler?
}