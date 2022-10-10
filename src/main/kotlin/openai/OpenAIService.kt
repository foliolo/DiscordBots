package openai

import openai.model.CompletionRequest
import openai.model.CompletionResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIService {

    @POST("completions")
    @Headers("Authorization: Bearer ${RetrofitManager.TOKEN} ")
    suspend fun textCompletion(
        @Body completionBody: CompletionRequest
    ): CompletionResponse

}
