package com.klitsie.translatable

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExampleViewModel(
	mapper: ExampleMapper,
	user: User = User("Joost", "joost@email.com"),
) : ViewModel() {

	private val _viewState = MutableStateFlow(
		mapper.map(user)
	)

	val viewState = _viewState.asStateFlow()

}
