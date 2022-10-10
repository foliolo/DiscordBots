package openai.model


import com.google.gson.annotations.SerializedName

data class CompletionRequest(
    @SerializedName("model") val model: String = "text-davinci-002",
    @SerializedName("prompt") val prompt: String = "",
    @SerializedName("max_tokens") val maxTokens: Int = 30,
    @SerializedName("temperature") val temperature: Int = 0,
    @SerializedName("top_p") val topP: Int = 1,
    @SerializedName("n") val n: Int = 1,
    @SerializedName("stream") val stream: Boolean = false,
    @SerializedName("logprobs") val logprobs: String? = null,
    @SerializedName("stop") val stop: String = "/n"
)