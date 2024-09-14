package com.klitsie.translatable

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
sealed interface Translatable {

	data class Resource(
		@StringRes val id: Int,
		val params: List<Any> = emptyList(),
	) : Translatable {

		constructor(@StringRes id: Int, vararg params: Any) : this(id, params.toList())

	}

	data class Plural(
		@PluralsRes val id: Int,
		val count: Int,
		val params: List<Any> = emptyList(),
	) : Translatable {

		constructor(@PluralsRes id: Int, count: Int, vararg params: Any) : this(
			id,
			count,
			params.toList()
		)

	}

	data class StringValue(
		val value: String,
	) : Translatable

	@Immutable
	data class Multiple(
		val translations: List<Translatable>,
		val separator: String = "",
	) : Translatable {

		constructor(
			vararg resources: Translatable,
			separator: String = "",
		) : this(resources.toList(), separator)

	}

}
