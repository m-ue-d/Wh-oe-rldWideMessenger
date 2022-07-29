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
			"^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"
		))
	}

	fun isVerificationCodeValid(verificationCode: String) : Boolean {
		return verificationCode.matches(Regex(
			"[A-Z\\d]{6}"
		))
	}
}