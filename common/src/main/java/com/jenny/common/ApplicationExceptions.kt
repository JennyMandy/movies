package com.jenny.common

class ApplicationExceptions private constructor() {

    init {
        throw UnsupportedOperationException(CANNOT_INSTANTIATE_STATIC)
    }

    companion object {
        const val CANNOT_INSTANTIATE_STATIC = "Cannot instantiate static holder."

        const val NO_NULL_PARAMS = "Params cannot be null or empty."
        const val NO_ZERO_PAGE = "Page number cannot be 0."
        const val NO_NULL_REPO = "Repository cannot be null."
        const val NO_NULL_OBSERVER = "Observer cannot be null."
        const val NO_NULL_EXECUTION_THREAD = "ExecutionThread cannot be null."
        const val NO_NULL_SCHEDULER = "Scheduler cannot be null."
    }
}
