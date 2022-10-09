import discord4j.core.`object`.entity.Message

class MessageManager {

    internal val contMessages = hashMapOf<String, Int>()

    /**
     * Increment the message counter for the user who send the message
     */
    fun messageFromUser(user: String) {
        if (contMessages.containsKey(user)) {
            contMessages[user] = contMessages.getValue(user) + 1
        } else {
            contMessages[user] = 1
        }
    }

    fun getMessageCounterFromUser(user: String): Int =
        if (!contMessages.containsKey(user)) {
            0
        } else {
            contMessages.getValue(user)
        }

    fun toString(message: Message): String {
        return """
                User: ${message.userData.username()}
                Message: ${message.content}
                Cont: ${contMessages.getValue(message.userData.username())}
            """.trimIndent()
    }

    companion object {
        @Volatile
        private lateinit var instance: MessageManager

        fun getInstance(): MessageManager {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = MessageManager()
                }
                return instance
            }
        }
    }
}