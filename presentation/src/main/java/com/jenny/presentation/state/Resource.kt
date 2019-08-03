package com.jenny.presentation.state

class Resource<T>(val status: ResourceState, val data: T?, val error: String?)
