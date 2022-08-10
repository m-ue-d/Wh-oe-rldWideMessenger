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

	fun isIpValid(ip: String) : Boolean{
		return ip.matches(Regex(
			"(\\b25[0-5]|\\b2[0-4][0-9]|\\b[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}"
		))
	}

	fun isPortValid(port : Int) : Boolean {
		return port in 1..65535
	}
}