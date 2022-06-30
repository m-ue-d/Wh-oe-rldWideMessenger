package spg.server.auth

import java.nio.file.Files
import java.nio.file.Path
import java.security.SecureRandom
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/**
 * Password-Based Key Derivation Function 2 (PBKDF2) with HMAC-SHA1.
 * [Link to wiki](https://en.wikipedia.org/wiki/PBKDF2)
 */
class PBKDF2 {
	companion object {
		private const val ID = "$31$"
		private val ALGORITHM = Files.readString(
			Path.of("/Users/fabian/Documents/Very Secure Folder/auth-algorithm.txt"), Charsets.UTF_8
		)
		private const val SIZE = 128
		private val layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})")

		private val random = SecureRandom()
		private const val cost = 16

		private fun iterations(cost : Int) : Int {
			return 1 shl cost
		}

		/**
		 * Hash a password for storage. Equivalent to php's password_hash.
		 * @return a secure authentication token to be stored for later authentication
		 */
		fun hash(password : CharArray) : String {
			val salt = ByteArray(SIZE / 8)
			random.nextBytes(salt)
			val dk : ByteArray = pbkdf2(password, salt, 1 shl cost)
			val hash = ByteArray(salt.size + dk.size)
			System.arraycopy(salt, 0, hash, 0, salt.size)
			System.arraycopy(dk, 0, hash, salt.size, dk.size)
			val enc = Base64.getUrlEncoder().withoutPadding()
			return "$ID$cost\$${enc.encodeToString(hash)}"
		}

		/**
		 * Authenticate with a password and a stored password token. Equivalent to php's password_verify.
		 * @return true if the password and token match
		 */
		fun verify(password: CharArray, token: String) : Boolean {
			val m : Matcher = layout.matcher(token)
			require(m.matches()) {
				"Invalid token format"
			}
			val iterations : Int = iterations(m.group(1).toInt())
			val hash : ByteArray = Base64.getUrlDecoder().decode(m.group(2))
			val salt = hash.copyOfRange(0, SIZE / 8)
			val check : ByteArray = pbkdf2(password, salt, iterations)
			var zero = 0
			for (idx in check.indices)
				zero = zero or (
					hash[salt.size + idx].toInt() xor check[idx].toInt()
				)
			return zero == 0
		}

		private fun pbkdf2(password : CharArray, salt : ByteArray, iterations : Int) : ByteArray {
			return SecretKeyFactory
				.getInstance(ALGORITHM)
				.generateSecret(
					PBEKeySpec(password, salt, iterations, SIZE)
				).encoded
		}
	}
}