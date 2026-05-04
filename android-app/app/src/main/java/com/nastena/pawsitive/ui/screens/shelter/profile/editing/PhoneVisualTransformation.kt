package com.nastena.pawsitive.ui.screens.shelter.profile.editing

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {
    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.filter { it.isDigit() }.take(maxLength)

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                        .let { if (it == -1) mask.length else it }
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        var nonDigitCount = 0
        var i = 0
        while (i < offset + nonDigitCount && i < mask.length) {
            if (mask[i++] != numberChar) nonDigitCount++
        }
        return offset + nonDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset.coerceAtMost(mask.length)).count { it != numberChar }
}