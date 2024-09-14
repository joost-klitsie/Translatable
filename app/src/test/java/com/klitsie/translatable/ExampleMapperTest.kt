package com.klitsie.translatable

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExampleMapperTest {

	@Test
	fun `Check we get the user and email string`() {
		assertEquals(
			ExampleViewState(
				text = Translatable.Resource(R.string.user, USER_NAME),
				email = EMAIL.toTranslatable(),
			),
			ExampleMapper().map(User(USER_NAME, EMAIL)),
		)
	}

	@Test
	fun `Check we get unknown if the user has no username or email`() {
		assertEquals(
			ExampleViewState(
				text = Translatable.Resource(R.string.unknown_user),
				email = Translatable.Resource(R.string.unknown_email),
			),
			ExampleMapper().map(User(null, null)),
		)
	}

	companion object {

		private const val USER_NAME = "Joost"
		private const val EMAIL = "joost@email.com"

	}

}
