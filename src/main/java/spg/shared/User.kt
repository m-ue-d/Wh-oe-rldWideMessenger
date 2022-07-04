package spg.shared

import java.math.BigInteger
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class User(
	val id : Int,
	val uname : String,
	val since : LocalDateTime,
	val email : String? = null,
	val key : BigInteger,
) {
	val img : String
		get() = "http://localhost:8000/avatars?id=$id"

	override fun toString() : String {
		return "User: $id, $uname, $since, $img, ${email ?: "-"}"
	}
}