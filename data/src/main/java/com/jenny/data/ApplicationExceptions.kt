package com.jenny.data

class ApplicationExceptions private constructor() {

    init {
        throw UnsupportedOperationException(CANNOT_INSTANTIATE_STATIC)
    }

    companion object {
        const val CANNOT_INSTANTIATE_STATIC = "Cannot instantiate static holder."
        const val NO_CACHE_SUPPORT = "This operation is not supported on cache."
        const val NO_REMOTE_SUPPORT = "This operation is not supported on remote."
    }
}