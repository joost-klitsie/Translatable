package com.klitsie.translatable

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

fun Translatable?.translate(
	resources: Resources,
): String = when (this) {
	null -> ""
	is Translatable.Resource -> resources.getString(
		id,
		*params.map {
			if (it is Translatable) {
				it.translate(resources)
			} else {
				it
			}
		}.toTypedArray(),
	)

	is Translatable.Plural -> resources.getQuantityString(id, count, *params.toTypedArray())

	is Translatable.StringValue -> value
	is Translatable.Multiple -> translations.joinToString(
		separator = separator,
	) {
		it.translate(resources)
	}
}

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
fun Translatable?.translate() = translate(resources())
