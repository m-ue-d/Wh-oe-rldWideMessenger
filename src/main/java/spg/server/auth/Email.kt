package spg.server.auth

import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.function.Supplier
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.io.path.toPath

object Email {
	private val code = Code()

	lateinit var session: Session
		private set

	private lateinit var from: String

	fun initialize() {
		println("Initializing email server...")
		Files.readString(
			Path.of("C:\\Users\\Admin\\Documents\\Very Secure Folder\\email-connection.txt")
		).split(",").let {
			from = it[0]
			val pwd = it[1]
			val host = it[2]

			val properties = System.getProperties().apply {
				this["mail.smtp.host"] = host
				this["mail.smtp.port"] = "465"
				this["mail.smtp.ssl.enable"] = "true"
				this["mail.smtp.auth"] = "true"
			}

			session = Session.getInstance(properties, object : Authenticator() {
				override fun getPasswordAuthentication() : PasswordAuthentication {
					return PasswordAuthentication(from, pwd)
				}
			})
		}
	}

	fun genCode() : String {
		return code.get()
	}

	fun sendMail(recipient: String, code: String) {
		try {
			MimeMessage(session).apply {
				this.setFrom(InternetAddress(Email.from))
				this.addRecipient(
					Message.RecipientType.TO, InternetAddress(recipient)
				)
				this.subject = "Your Verification Code!"
				this.setText(
					Files.readString(
						this::class.java.getResource("/spg/server/email/email minified.html")!!.toURI().toPath()
					)
						.replace("{{ code }}", code)
						.replace("{{ date }}", LocalDate.now().format(
							DateTimeFormatter.ofPattern(
								"dd.MM.yyyy"
							))), "UTF-8", "html"
				)
			}.let {
				println("Sending verification code to $recipient...")
				Transport.send(it)
			}
		} catch (e: MessagingException) {
			println("Error sending verification code: " + e.message)
		}
	}

	class Code : Supplier<String> {
		private val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		private val random = Random()
		override fun get() : String {
			return StringBuilder().apply {
				for (i in 0 until 6) {
					this.append(
						chars[random.nextInt(chars.length)]
					)
				}
			}.toString()
		}
	}
}