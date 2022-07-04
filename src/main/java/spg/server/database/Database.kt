package spg.server.database

import spg.shared.User
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Database {
	companion object {
		lateinit var db: Connection
			private set

		fun initialize() {
			Class.forName("com.mysql.cj.jdbc.Driver")

			Files.readString(
				Path.of("/Users/fabian/Documents/Very Secure Folder/database-connection.txt")
			).split(",").let {
				val host = it[0]
				val port = it[1]
				val db = it[2]
				val user = it[3]
				val pwd = it[4]

				this.db = DriverManager.getConnection(
					"jdbc:mysql://$host:$port/$db?user=$user&password=$pwd"
				)
			}
		}

		/**
		 * Adds a user to the database.
		 * @param uname The username to be registered.
		 * @param password The hashed password of the account.
		 * @param email The email address of the user.
		 */
		fun addEntry(uname: String, email: String, password: String) {
			try {
				db.prepareStatement("INSERT INTO acc (uname, since, email, password) VALUES (?, ?, ?, ?)").apply {
					this.setString(1, uname)
					this.setString(2, LocalDateTime.now().format(
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
					))
					this.setString(3, email)
					this.setString(4, password)
				}.execute().run {

				}
			} catch (_ : SQLException) { }
		}

		/**
		 * Checks if a user exists in the database.
		 * @param email The email to be checked.
		 * @param password The password to be checked.
		 */
		fun hasEntry(email : String, password : String) : Boolean {
			try {
				db.prepareStatement("SELECT * FROM acc WHERE email = ? AND password = ?").apply {
					this.setString(1, email)
					this.setString(2, password)
				}.executeQuery().use {
					return it.next()
				}
			} catch (_ : SQLException) { }
			return false
		}

		/**
		 * Gets a user from the database.
		 * @param id The unique user identifier.
		 */
		fun getEntry(id: Int) : User? {
			try {
				db.prepareStatement("SELECT id, uname, since FROM acc WHERE id = ?").apply {
					this.setInt(1, id)
				}.executeQuery().use {
					it.next()
					return User(
						it.getInt("id"),
						it.getString("uname"),
						LocalDateTime.parse(
							it.getString("since"),
							DateTimeFormatter.ofPattern(
								"yyyy-MM-dd HH:mm:ss"
							)
						),
						null,
						BigInteger.valueOf(123456L)
					)
				}
			} catch (e : SQLException) {
				e.printStackTrace()
				return null
			}
		}
	}
}