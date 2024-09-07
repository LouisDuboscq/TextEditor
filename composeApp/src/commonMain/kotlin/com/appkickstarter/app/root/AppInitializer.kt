package com.lduboscq.appkickstarter.template.root

import com.appkickstarter.shared.BuildKonfig
import com.lduboscq.appkickstarter.template.di.templateModules
import com.lduboscq.appkickstarter.template.firebase.initFirebase
import com.lduboscq.appkickstarter.template.posthog.PostHogHelper
import com.lduboscq.appkickstarter.template.utils.AppPlatform
import com.lduboscq.appkickstarter.template.utils.ImageHelper
import com.lduboscq.appkickstarter.template.utils.isDebug
import com.lduboscq.appkickstarter.template.utils.logger.AppLogger
import com.lduboscq.appkickstarter.template.utils.platform
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import com.mmk.kmprevenuecat.purchases.LogLevel
import com.mmk.kmprevenuecat.purchases.Purchases
import io.sentry.kotlin.multiplatform.Sentry
import io.sentry.kotlin.multiplatform.SentryOptions
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

object AppInitializer {

    fun initialize(
        versionName: String,
        versionCode: String,
        postHogHelper: PostHogHelper,
        imageHelper: ImageHelper,
        appDeclaration: KoinApplication.() -> Unit
    ) {

        initializeSentry()
        AppLogger.initialize()

        startKoin {
            appDeclaration()
            modules(
                module {
                    single<PostHogHelper> { postHogHelper }
                    single<ImageHelper> { imageHelper }
                } + templateModules(versionName, versionCode)
            )
        }

        postHogHelper.init(
            host = BuildKonfig.POSTHOG_HOST,
            key = BuildKonfig.POSTHOG_API_KEY
        )

        initAdMob()
        initFirebase()
        initRevenueCat()
        initGoogleAuth()
        initFCM()
    }
}

private fun initFCM() {
    NotifierManager.addListener(object : NotifierManager.Listener {
        override fun onNewToken(token: String) {
            AppLogger.d("FirebaseOnNewToken: $token") // Update user token in the server if needed
        }

        override fun onPushNotification(title: String?, body: String?) {
            AppLogger.d("Push Notification notification title: $title")
        }

        override fun onPayloadData(data: PayloadData) {
            AppLogger.d("Push Notification payloadData: $data") //PayloadData is just typeAlias for Map<String,*>.
        }

        override fun onNotificationClicked(data: PayloadData) {
            super.onNotificationClicked(data)
            AppLogger.d("Notification clicked, Notification payloadData: $data")
        }
    })
}

private fun initRevenueCat() {
    val apiKey =
        if (platform() == AppPlatform.ANDROID) {
            BuildKonfig.REVENUECAT_API_KEY_ANDROID
        } else {
            BuildKonfig.REVENUECAT_API_KEY_IOS
        }
    if (apiKey.isNotEmpty()) {
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(apiKey)
        initASATracking()
        Purchases.collectDeviceIdentifiers()
    }
}

private fun initGoogleAuth() {
    GoogleAuthProvider.create(GoogleAuthCredentials(serverId = BuildKonfig.GOOGLE_SIGN_IN_WEB_CLIENT_ID))
}

expect fun initASATracking()
expect fun initAdMob()

private fun initializeSentry() {
    if (isDebug) {
        val configuration: (SentryOptions) -> Unit = {
            it.dsn = BuildKonfig.SENTRY_DSN
        }
        Sentry.init(configuration)
    }
}
