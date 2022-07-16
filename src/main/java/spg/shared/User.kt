package spg.shared

import java.math.BigInteger
import java.time.LocalDateTime

class User(
	val id : Int,
	val uname : String,
	val email : String? = null,
	val password : String? = null,
	val since : LocalDateTime,
	val key : BigInteger,
) {
	val img : String
		get() = "http://localhost:8000/avatars?id=$id"

	override fun toString() : String {
		return "User: $id, $uname, ${email ?: "-"}, ${password ?: "-"}, $since, $img, $key"
	}
}