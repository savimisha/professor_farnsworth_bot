package pro.savichev.bot.impl

import com.google.gson.Gson
import com.pengrad.telegrambot.impl.FileApi
import com.pengrad.telegrambot.request.BaseRequest
import com.pengrad.telegrambot.response.BaseResponse
import io.reactivex.Single
import okhttp3.*
import org.springframework.beans.factory.annotation.Autowired

import java.io.File


class TelegramBot(botToken: String) {

    companion object {
        private const val API_URL = "https://api.telegram.org/bot"
    }

    private var baseUrl: String
    @Autowired
    private lateinit var client: OkHttpClient
    @Autowired
    private lateinit var gson: Gson
    private val fileApi: FileApi

    init {
        baseUrl = "$API_URL$botToken/"
        fileApi = FileApi(botToken)
    }

    fun <T : BaseRequest<*, *>, R : BaseResponse> send(request: BaseRequest<T, R>): Single<R> {
        return Single.create { emitter ->
            val response = client.newCall(createRequest(request)).execute()
            if (response.body() != null) {
                val result = gson.fromJson<R>(response.body()!!.string(), request.responseType)
                emitter.onSuccess(result)
            } else {
                throw Exception("Response body is null.")
            }

        }
    }

    fun getFullFilePath(file: com.pengrad.telegrambot.model.File): String {
        return fileApi.getFullFilePath(file.filePath())
    }

    private fun createRequest(request: BaseRequest<*, *>): Request {
        return Request.Builder()
                .url(baseUrl + request.method)
                .post(createRequestBody(request))
                .build()
    }

    private fun createRequestBody(request: BaseRequest<*, *>): RequestBody {
        if (request.isMultipart) {
            val contentType = MediaType.parse(request.contentType)

            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

            for ((name, value) in request.parameters) {
                when (value) {
                    is ByteArray -> builder.addFormDataPart(name, request.fileName, RequestBody.create(contentType, value))
                    is File -> builder.addFormDataPart(name, request.fileName, RequestBody.create(contentType, value))
                    else -> builder.addFormDataPart(name, value.toString())
                }
            }

            return builder.build()
        } else {
            val builder = FormBody.Builder()
            for ((key, value) in request.parameters) {
                builder.add(key, value.toString())
            }
            return builder.build()
        }
    }
}
