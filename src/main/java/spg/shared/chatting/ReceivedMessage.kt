package spg.shared.chatting

import java.time.LocalDateTime

data class ReceivedMessage(
	val message: String,
	val timestamp: LocalDateTime,
	val sender: Int // user id
)