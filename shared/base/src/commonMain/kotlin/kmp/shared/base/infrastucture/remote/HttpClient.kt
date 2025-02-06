package kmp.shared.base.infrastucture.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.authProviders
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kmp.shared.base.infrastucture.provider.AuthProvider
import kmp.shared.base.system.ApiVariant
import kmp.shared.base.system.Config
import kotlin.native.concurrent.ThreadLocal
import co.touchlab.kermit.Logger as KermitLogger
import kotlinx.serialization.json.Json as JsonConfig

internal object HttpClient {
    private val unauthorizedEndpoints = listOf("/api/auth/login", "/api/auth/registration")

    fun init(config: Config, engine: HttpClientEngine, authProvider: AuthProvider) = HttpClient(engine).config {
        expectSuccess = true
        developmentMode = !config.isRelease
        followRedirects = false

        install(ContentNegotiation) {
            json(globalJson)
        }

        if (!config.isRelease) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        KermitLogger.d { message }
                    }
                }
                level = LogLevel.ALL
            }
        }

        install(Auth) {
            // Use if your authentication method is a bearer token (other options are `basic` and `digest`)
            bearer {
                loadTokens {
                    authProvider.token?.let { token ->
                        // Use your access and refresh tokens here (you can use access token for both if you don't use refresh token)
                        BearerTokens(token, token)
                    }
                }

                refreshTokens {
                    authProvider.token?.let { token ->
                        // Use your access and refresh tokens here (you can use access token for both if you don't use refresh token)
                        BearerTokens(token, token)
                    }
                }

                sendWithoutRequest { request ->
                    unauthorizedEndpoints.any(request.url.encodedPath::equals)
                }
            }
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                // Set your host URLs
                host = when (config.apiVariant) {
                    ApiVariant.Alpha -> "jsonplaceholder.typicode.com"
                    ApiVariant.Production -> "jsonplaceholder.typicode.com"
                }
            }
            contentType(ContentType.Application.Json)
        }
    }
}

/**
 * Force the Auth plugin to invoke the `loadTokens` block again on the next client request.
 */
fun HttpClient.clearBearerTokens() {
    authProviders
        .filterIsInstance<BearerAuthProvider>()
        .firstOrNull()
        ?.clearToken()
}

@ThreadLocal
val globalJson = JsonConfig {
    ignoreUnknownKeys = true
    coerceInputValues = true
    useAlternativeNames = false
}
