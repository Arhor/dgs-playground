package com.github.mburyshynets.dgs

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

fun <T> Executor.async(action: () -> T): CompletableFuture<T> {
    return CompletableFuture.supplyAsync(action, this)
}
