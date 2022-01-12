package com.midhun.technical.network.model.base

class ResponseBase<T> {
    var meta: ResponseMeta? = null
    var data: T? = null
    var errorMessage: String? = null

}

class ResponseMeta {
    var pagination: ResponsePagination? = null
}

class ResponsePagination {
    var total: Int = -1
    var pages: Int = -1
    var page: Int = -1
    var limit: Int = -1

}