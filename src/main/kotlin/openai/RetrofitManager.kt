package openai

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import openai.model.CompletionRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    private val service: OpenAIService = retrofit.create(OpenAIService::class.java)

    init {

        runBlocking {
            launch {

                CompletionRequest(
                    prompt = "I'm a fucking ",
                ).let { request ->
                    val response = service.textCompletion(request)

                    println(response.choices?.first()?.text)
                }
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://api.openai.com/v1/"
        const val TOKEN = ""
    }
}
