package com.klitsie.translatable

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

/**
 * Get resources from the current composable.
 * Directly inspired by stringResource(..)
 */
@Composable
@ReadOnlyComposable
internal fun resources(): Resources {
	LocalConfiguration.current
	return LocalContext.current.resources
}

@Composable
@ReadOnlyComposable
fun getTranslatedString(
	translatable: Translatable?,
): String = getTranslatedString(translatable, resources())

@Composable
@ReadOnlyComposable
fun Translatable?.translate() = getTranslatedString(this, resources())

fun getTranslatedString(
	translatable: Translatable?,
	resources: Resources,
): String = when (translatable) {
	null -> ""
	is Translatable.Resource -> resources.getString(
		translatable.id,
		*translatable.params.map {
			if (it is Translatable) {
				getTranslatedString(it, resources)
			} else {
				it
			}
		}.toTypedArray(),
	)

	is Translatable.Plural -> resources.getQuantityString(
		translatable.id,
		translatable.count,
		*translatable.params.toTypedArray(),
	)

	is Translatable.StringValue -> translatable.value
	is Translatable.Multiple -> translatable.translations.joinToString(
		separator = translatable.separator,
	) {
		getTranslatedString(it, resources)
	}
}
