package com.example.moviemvvm.data.data_source

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED

}

class NetworkState(val status: Status, val msg: String) {

    companion object {

        val ENDOFLIST: NetworkState
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")

            LOADING = NetworkState(Status.RUNNING, "Running")

            ERROR = NetworkState(Status.FAILED, "Something went wrong")
            ENDOFLIST=NetworkState(Status.FAILED, "You have reached to have")
        }
    }
}