package com.appkickstarter.app.designsystem

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.mohamedrejeb.richeditor.model.RichTextState
import kotlinx.coroutines.CoroutineScope


@Composable
public fun BasicRichTextEditor(
    state: RichTextState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    maxLength: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() }
) {
    BasicRichTextEditor(
        state = state,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        maxLength = maxLength,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = decorationBox,
        contentPadding = PaddingValues()
    )
}

@Composable
public fun BasicRichTextEditor(
    state: RichTextState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    singleParagraph: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    maxLength: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() },
    contentPadding: PaddingValues
) {
    val density = LocalDensity.current
    val localTextStyle = LocalTextStyle.current
    val layoutDirection = LocalLayoutDirection.current
    val clipboardManager = LocalClipboardManager.current
    val richClipboardManager = remember(state) {
        RichTextClipboardManager(
            richTextState = state,
            clipboardManager = clipboardManager
        )
    }

    LaunchedEffect(singleParagraph) {
        state.singleParagraphMode = singleParagraph
    }

    if (!singleParagraph) {
        // Workaround for Android to fix a bug in BasicTextField where it doesn't select the correct text
        // when the text contains multiple paragraphs.
        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                if (interaction is PressInteraction.Press) {
                    val pressPosition = interaction.pressPosition
                    val topPadding = with(density) { contentPadding.calculateTopPadding().toPx() }
                    val startPadding = with(density) {
                        contentPadding.calculateStartPadding(layoutDirection).toPx()
                    }

                    adjustTextIndicatorOffset(
                        pressPosition = pressPosition,
                        state = state,
                        topPadding = topPadding,
                        startPadding = startPadding,
                    )
                }
            }
        }
    }

    CompositionLocalProvider(LocalClipboardManager provides richClipboardManager) {
        BasicTextField(
            value = state.textFieldValue,
            onValueChange = {
                if (readOnly) return@BasicTextField
                if (it.text.length > maxLength) return@BasicTextField

                state.onTextFieldValueChange(it)
            },
            modifier = modifier
                .drawRichSpanStyle(
                    richTextState = state,
                    topPadding = with(density) { contentPadding.calculateTopPadding().toPx() },
                    startPadding = with(density) {
                        contentPadding.calculateStartPadding(
                            layoutDirection
                        ).toPx()
                    },
                )
                .then(
                    if (!readOnly) Modifier
                    else Modifier.focusProperties { canFocus = false }
                )
                .then(
                    if (singleParagraph) {
                        Modifier
                    } else {
                        Modifier
                            // Workaround for Desktop to fix a bug in BasicTextField where it doesn't select the correct text
                            // when the text contains multiple paragraphs.
                            .adjustTextIndicatorOffset(
                                state = state,
                                contentPadding = contentPadding,
                                textStyle = localTextStyle,
                                density = density,
                                layoutDirection = layoutDirection,
                                scope = rememberCoroutineScope()
                            )
                    }
                ),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = state.visualTransformation,
            onTextLayout = {
                state.onTextLayout(
                    textLayoutResult = it,
                    density = density,
                )
                onTextLayout(it)
            },
            interactionSource = interactionSource,
            cursorBrush = cursorBrush,
            decorationBox = decorationBox,
        )
    }
}

internal expect fun Modifier.adjustTextIndicatorOffset(
    state: RichTextState,
    contentPadding: PaddingValues,
    textStyle: TextStyle,
    density: Density,
    layoutDirection: LayoutDirection,
    scope: CoroutineScope,
): Modifier

internal suspend fun adjustTextIndicatorOffset(
    pressPosition: Offset,
    state: RichTextState,
    topPadding: Float,
    startPadding: Float,
) {
    state.adjustSelectionAndRegisterPressPosition(
        pressPosition = Offset(
            x = pressPosition.x - startPadding,
            y = pressPosition.y - topPadding
        ),
    )
}