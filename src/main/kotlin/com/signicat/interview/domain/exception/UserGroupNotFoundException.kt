package com.signicat.interview.domain.exception

class UserGroupNotFoundException : RuntimeException {
    constructor(msg: String?, t: Throwable?) : super(msg, t) {}
    constructor(msg: String?) : super(msg) {}
}