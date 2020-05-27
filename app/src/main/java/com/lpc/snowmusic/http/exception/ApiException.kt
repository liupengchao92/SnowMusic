package com.lpc.snowmusic.http.exception

/**
 * Author: liupengchao
 * Date: 2020/5/27
 * ClassName :ApiException
 * Desc:
 */
class ApiException : RuntimeException {

    private var code: Int? = 0

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}