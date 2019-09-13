package com.example.app.data

import io.objectbox.query.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
fun <T> Query<T>.toFlow(): Flow<List<T>> = callbackFlow<List<T>> {
	val subscription = subscribe()
			.observer { data -> offer(data) }
	awaitClose {
		subscription.cancel()
	}
}.flowOn(Dispatchers.IO)
