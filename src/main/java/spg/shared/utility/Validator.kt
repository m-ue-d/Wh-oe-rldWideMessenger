package spg.shared.utility

object Validator {
	fun isUnameValid(uname: String) : Boolean {
		return uname.matches(Regex(
			"[a-zA-Z\\d]{3,16}"
		))
	}

	fun isEmailValid(email: String) : Boolean {
		return email.matches(Regex(
			"^[a-zA-Z\\d._-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,4}$"
		))
	}

	fun isPasswordValid(password: String) : Boolean {
		return password.matches(Regex(
			"[a-zA-Z\\d]{8,16}"
		))
	}

	fun isVerificationCodeValid(verificationCode: String) : Boolean {
		return verificationCode.matches(Regex(
			"[A-Z\\d]{6}"
		))
	}
}