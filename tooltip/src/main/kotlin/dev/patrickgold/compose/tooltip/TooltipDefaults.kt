/*
 * Copyright (C) 2006,2016 The Android Open Source Project
 * Copyright (C) 2022,2025 Patrick Goldinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.patrickgold.compose.tooltip

import android.view.animation.AccelerateDecelerateInterpolator
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import kotlin.time.Duration.Companion.milliseconds

// Dimension values taken from framework default dimensions:
// https://android.googlesource.com/platform/frameworks/base/+/02772f2e7a6f497a6e209bb8104681468d40d090/core/res/res/values/dimens.xml#724
internal val TooltipYOffsetTouch = 4.dp
internal val TooltipYOffsetNonTouch = 0.dp
internal val TooltipMargin = PaddingValues(all = 8.dp)
internal val TooltipPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
internal val TooltipPreciseAnchorThreshold = 96.dp
internal val TooltipPreciseAnchorExtraThreshold = 8.dp

// Background colors extracted from framework core colors:
// https://android.googlesource.com/platform/frameworks/base/+/02772f2e7a6f497a6e209bb8104681468d40d090/core/res/res/values/colors.xml#220

// Tooltip duration values via ViewConfiguration are only accessible through internal calls, so we copy them:
// https://android.googlesource.com/platform/frameworks/base/+/02772f2e7a6f497a6e209bb8104681468d40d090/core/java/android/view/ViewConfiguration.java#278
internal val TooltipLongPressHideTimeout = 1500.milliseconds
internal val TooltipHoverShowTimeout = 500.milliseconds
internal val TooltipHoverHideTimeout = 15000.milliseconds

// https://android.googlesource.com/platform/frameworks/base/+/02772f2e7a6f497a6e209bb8104681468d40d090/core/res/res/values/config.xml#172
// https://android.googlesource.com/platform/frameworks/base/+/02772f2e7a6f497a6e209bb8104681468d40d090/core/res/res/anim/tooltip_enter.xml#21
// https://android.googlesource.com/platform/frameworks/base/+/02772f2e7a6f497a6e209bb8104681468d40d090/core/res/res/anim/tooltip_exit.xml#21
internal val TooltipAlphaAnimationInterpolator = AccelerateDecelerateInterpolator()
internal val TooltipAlphaAnimationSpec: AnimationSpec<Float> = tween(
    durationMillis = 150,
    easing = { x -> TooltipAlphaAnimationInterpolator.getInterpolation(x) },
)

// Custom properties which match the default tooltip popup as best as possible
internal val TooltipPopupProperties = PopupProperties(
    focusable = false,
    dismissOnBackPress = false,
    dismissOnClickOutside = true,
    securePolicy = SecureFlagPolicy.Inherit,
    excludeFromSystemGesture = true,
    clippingEnabled = true,
)
