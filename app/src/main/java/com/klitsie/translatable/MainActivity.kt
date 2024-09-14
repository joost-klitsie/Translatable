package com.klitsie.translatable

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.klitsie.translatable.ui.theme.TranslatableTheme

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			TranslatableTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					ExampleComposable(
						modifier = Modifier.padding(innerPadding)
					)
				}
			}
		}
	}
}

@Composable
fun ExampleComposable(modifier: Modifier = Modifier) {
	val context = LocalContext.current
	val localeOptions = remember {
		mapOf(
			"en" to R.string.language_en,
			"nl" to R.string.language_nl,
		)
	}
	val viewModel = viewModel<ExampleViewModel> {
		ExampleViewModel(ExampleMapper())
	}
	val viewState by viewModel.viewState.collectAsState()
	val currentLanguageTag by rememberUpdatedState(
		context.resources.configuration.locales[0].toLanguageTag()
	)
	val newLanguageTag = remember(currentLanguageTag, localeOptions) {
		localeOptions.keys.let { countryCodes ->
			val currentIndex = countryCodes.indexOfFirst { countryCode ->
				countryCode.equals(currentLanguageTag, ignoreCase = true)
			}
			countryCodes.toList()[currentIndex.plus(1) % countryCodes.size]
		}
	}
	Column(
		modifier = modifier
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = spacedBy(16.dp, CenterVertically)
	) {
		Text(viewState.text.translate())
		Text(viewState.email.translate())
		TextButton(
			text = Translatable.Resource(
				R.string.choose_next_language,
				stringResource(localeOptions.getValue(newLanguageTag)),
			),
			onClick = {
				AppCompatDelegate.setApplicationLocales(
					LocaleListCompat.forLanguageTags(newLanguageTag)
				)
			}
		)
	}
}

@Composable
fun TextButton(
	text: Translatable, // Translatable is @Immutable
	onClick: () -> Unit,
) {
	Button(
		onClick = onClick,
	) {
		Text(text.translate())
	}
}
