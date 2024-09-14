package com.klitsie.translatable

class ExampleMapper {

	fun map(user: User): ExampleViewState {
		val text = user.userName?.let { userName ->
			Translatable.Resource(R.string.user, userName)
		} ?: Translatable.Resource(R.string.unknown_user)

		val email = user.email?.toTranslatable()
			?: Translatable.Resource(R.string.unknown_email)

		return ExampleViewState(text = text, email = email)
	}

}
