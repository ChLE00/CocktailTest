package com.test.app.cocktail.network

class Resource<T>(
    var status: Status,
    var data: T,
    var message: String
)