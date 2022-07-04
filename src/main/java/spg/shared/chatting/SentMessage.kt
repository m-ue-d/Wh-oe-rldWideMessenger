package spg.shared.chatting

import java.time.LocalDateTime

data class SentMessage(
	val message: String,
	val timestamp: LocalDateTime,
	val sender: Int, // user id
	val receiver: Int // user id Todo: make this a list of users
)