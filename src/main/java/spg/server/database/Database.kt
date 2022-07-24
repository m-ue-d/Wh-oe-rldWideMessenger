package spg.server.database

import spg.shared.User
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Database {
	lateinit var db: Connection
		private set

	fun initialize() {
		println("Initializing database...")
		Class.forName("com.mysql.cj.jdbc.Driver")

		Files.readString(
			Path.of("C:\\Users\\Admin\\Documents\\Very Secure Folder\\database-connection.txt")
		).split(",").let {
			val host = it[0]
			val port = it[1]
			val db = it[2]
			val user = it[3]
			val pwd = it[4]

			try {
				this.db = DriverManager.getConnection(
					"jdbc:mysql://$host:$port/$db?user=$user&password=$pwd"
				)
			} catch (e: SQLException) {
				System.err.println("Failed to connect to database: ${e.message}")
			}
		}
	}

	/**
	 * Adds a user to the database.
	 * @param name The username to be registered.
	 * @param email The email address of the user.
	 * @param password The hashed password of the account.
	 */
	fun addEntry(name: String, email: String, password: String) : Boolean {
		return try {
			db.prepareStatement("INSERT INTO accounts (name, since, email, password) VALUES (?, ?, ?, ?)").apply {
				this.setString(1, name)
				this.setString(2, LocalDateTime.now().format(
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
				))
				this.setString(3, email)
				this.setString(4, password)
			}.execute()
			true
		} catch (e : SQLException) {
			System.err.println("Failed to add entry: ${e.message}")
			false
		}
	}

	/**
	 * Checks if a user exists in the database.
	 * @param email The email to be checked.
	 * @param password The password to be checked.
	 */
	fun hasEntry(email : String, password : String) : Boolean {
		return try {
			db.prepareStatement("SELECT * FROM accounts WHERE email = ? AND password = ?").apply {
				this.setString(1, email)
				this.setString(2, password)
			}.executeQuery().use {
				it.next()
			}
		} catch (e : SQLException) {
			System.err.println("No entry found: ${e.message}")
			false
		}
	}

	/**
	 * Checks if a user exists in the database.
	 * @param email The email to be checked.
	 */
	fun hasEntry(email : String) : Boolean {
		return try {
			db.prepareStatement("SELECT * FROM accounts WHERE email = ?").apply {
				this.setString(1, email)
			}.executeQuery().use {
				it.next()
			}
		} catch (e : SQLException) {
			System.err.println("No entry found: ${e.message}")
			false
		}
	}

	/**
	 * Gets a user from the database.
	 * @param id The unique user identifier.
	 */
	fun getEntry(id: Int) : User? {
		return try {
			parseResultSet(
				db.prepareStatement("SELECT id, name, since, email, password FROM accounts WHERE id = ?").apply {
					this.setString(1, id.toString())
				}.executeQuery()
			)
		} catch (e : SQLException) {
			System.err.println("No User registered with id $id: ${e.message}")
			null
		}
	}

	/**
	 * Gets a user from the database.
	 * @param email The unique user identifier.
	 */
	fun getEntry(email : String) : User? {
		return try {
			parseResultSet(
				db.prepareStatement("SELECT id, name, since, email, password FROM accounts WHERE email = ?").apply {
					this.setString(1, email)
				}.executeQuery()
			)
		} catch (e : SQLException) {
			System.err.println("No User registered with email $email: ${e.message}")
			null
		}
	}

	/**
	 * Updates the password for a user.
	 * @param email The email address of the user.
	 * @param password The new password.
	 */
	fun updatePassword(email: String, password: String): Boolean {
		return try {
			db.prepareStatement("UPDATE accounts SET password = ? WHERE email = ?").apply {
				this.setString(1, password)
				this.setString(2, email)
			}.execute()
			true
		} catch (e : SQLException) {
			System.err.println("Failed to update password: ${e.message}")
			false
		}
	}

	private fun parseResultSet(rs: ResultSet) : User? {
		return if (rs.next()) {
			User(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getString("email"),
				rs.getString("password"),
				LocalDateTime.parse(
					rs.getString("since"),
					DateTimeFormatter.ofPattern(
						"yyyy-MM-dd HH:mm:ss"
					)
				),
				BigInteger.valueOf(
					// rs.getLong("publicKey") TODO: Implement public key
					10 // arbitrary number
				)
			)
		} else {
			null
		}
	}
}