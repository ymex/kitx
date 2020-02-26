package cn.ymex.kitx.anhttp

import cn.ymex.kitx.anhttp.ssl.SSLParams
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 *
 */

class AnHttpManager private constructor() {

    private lateinit var mRetrofit: Retrofit
    /**
     * 默认参数
     */
    val commonParams = mutableMapOf<String, Any>()
    val commonHeader = mutableMapOf<String, String>()

    companion object {
        val instance: AnHttpManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AnHttpManager()
        }

        fun init(retrofit: Retrofit) {
            instance.setRetrofit(retrofit)
        }

        fun newRetrofitBuilder(
            client: OkHttpClient = newOkHttpClientBuilder().build(),
            baseUrl: String
        ): Retrofit.Builder {
            return Retrofit.Builder().client(client).baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
        }

        /**
         * create OkHttpClient.Builder with default config
         */
        fun newOkHttpClientBuilder(logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY): OkHttpClient.Builder {
            //信任所有证书
            val sslParams = SSLParams.create(null, null)
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
                .retryOnConnectionFailure(true)
                .dispatcher(Dispatcher())
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager)
                .hostnameVerifier(HostnameVerifier { _, _ -> true })
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
        }
    }

    private fun setRetrofit(retrofit: Retrofit) {
        this.mRetrofit = retrofit
    }

    fun getRetrofit(): Retrofit {
        if (!this::mRetrofit.isInitialized) {
            throw IllegalArgumentException("Retrofit is null!")
        }
        return mRetrofit
    }
}