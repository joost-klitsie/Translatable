package com.klitsie.translatable

data class ExampleViewState(
	val text: Translatable,
	val email: Translatable,
)

fun String.toTranslatable() = Translatable.StringValue(this)
