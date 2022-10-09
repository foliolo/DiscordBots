import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MessageManagerTest {
    private lateinit var SUT: MessageManager

    @BeforeEach
    fun before() {
        SUT = MessageManager.getInstance()
        SUT.contMessages.clear()
    }

    @Test
    fun `messageFromUser add new user to local storage`() {
        // Arrange
        val user = "User1"

        // Act
        SUT.messageFromUser(user)

        // Assert
        assertTrue(SUT.contMessages.containsKey(user))
    }

    @Test
    fun `messageFromUser add two different users`() {
        // Arrange
        val user1 = "User1"
        val user2 = "User2"

        // Act
        SUT.messageFromUser(user1)
        SUT.messageFromUser(user2)

        // Assert
        assertTrue(SUT.contMessages.containsKey(user1))
        assertTrue(SUT.contMessages.containsKey(user2))
    }

    @Test
    fun `messageFromUser increment counter when the user send several messages`() {
        // Arrange
        val user = "User1"

        // Act
        SUT.messageFromUser(user)
        SUT.messageFromUser(user)

        // Assert
        assertTrue(SUT.contMessages.containsKey(user))
        assertEquals(2, SUT.contMessages.getValue(user))
    }

    @Test
    fun `getMessageCounterFromUser returns the correct counter`() {
        // Arrange
        val user = "User1"

        // Act
        SUT.messageFromUser(user)
        SUT.messageFromUser(user)
        val cont = SUT.getMessageCounterFromUser(user)

        // Assert
        assertEquals(2, cont)
    }

    @Test
    fun `getMessageCounterFromUser returns 0 when there is no messages for that user`() {
        // Arrange
        val user1 = "User1"
        val user2 = "User2"

        // Act
        SUT.messageFromUser(user1)
        val cont = SUT.getMessageCounterFromUser(user2)

        // Assert
        assertEquals(0, cont)
    }
}