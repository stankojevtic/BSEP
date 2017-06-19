package dbconn;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.DatatypeConverter;

import bcrypt.BCrypt;

public class DbConn {
	private static Connection con;
	private static int workload = 12;

	public static void OpenConnection() {
		String url = "jdbc:sqlserver://localhost:1433;databaseName=bezbednost;integratedSecurity=true";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(url);
			System.out.println("connected");
			/*
			 * Statement statement = con.createStatement(); String queryString =
			 * "select * from Persons"; ResultSet rs =
			 * statement.executeQuery(queryString); while (rs.next()) {
			 * System.out.println(rs.getString(2)); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("cannot connect");
		}
	}

	public void adminApprove(String alias) {

		PreparedStatement stmt = null;

		try {

			stmt = con
					.prepareStatement("UPDATE Certificate SET Approved=? WHERE Alias=?");

			stmt.setString(1, "1");
			stmt.setString(2, alias);
			int rs = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void adminDecline(String alias) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;

		try {

			stmt = con
					.prepareStatement("UPDATE Certificate SET Valid=? WHERE Alias=?");

			stmt.setString(1, "0");
			stmt.setString(2, alias);
			int rs = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public boolean removeCertBySerialNumber(String serial) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;

		try {

			stmt = con
					.prepareStatement("UPDATE Certificate SET Valid=? WHERE SerialNumber=?");

			stmt.setString(1, "0");
			stmt.setString(2, serial);
			int rs = stmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int login(String username, String pass) {


		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		String adminPass = "";
		String userPass = "";

		// int role = 0;
		try {

			stmt = con
					.prepareStatement("SELECT Password FROM Admins Where Username=?");

			stmt1 = con
					.prepareStatement("SELECT Password FROM Users Where Username=?");

			stmt.setString(1, username);
			// stmt.setString(2, hashPassword(pass));
			ResultSet rs = stmt.executeQuery();

			stmt1.setString(1, username);
			// stmt1.setString(2, pass);
			ResultSet rs1 = stmt1.executeQuery();

			if (rs.next()) {
				adminPass = rs.getString("Password");
			} else if (rs1.next()) {
				userPass = rs1.getString("Password");
			} else {
				return 0;
			}

			if (!adminPass.equals("")) {
				if (checkPassword(pass, adminPass)) {
					return 1;
				}
			} else if (!userPass.equals("")) {
				if (checkPassword(pass, userPass)) {
					return 3;
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("greska");
			return 0;
		}
		return 0;

	}

	public static String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);

		return (hashed_password);
	}

	public static boolean checkPassword(String password_plaintext,
			String stored_hash) {
		boolean password_verified = false;

		if (null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException(
					"Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return (password_verified);
	}

	public void insert(String serialNumber, String alias, int approved) {

		PreparedStatement stmt = null;
		/*
		 * try { conn = setupTheDatabaseConnectionSomehow(); stmt =
		 * conn.prepareStatement
		 * ("INSERT INTO person (name, email) values (?, ?)"); stmt.setString(1,
		 * name); stmt.setString(2, email);
		 * 
		 * PreparedStatement stmt = null;
		 */
		// Statement statement;
		try {

			stmt = con
					.prepareStatement("INSERT INTO Certificate (SerialNumber, Alias, Valid, Approved) values (?, ?, ?, ?)");

			stmt.setString(1, serialNumber);
			stmt.setString(2, alias);
			stmt.setString(3, "1");
			stmt.setString(4, String.valueOf(approved));
			stmt.executeUpdate();

			/*
			 * statement = con.createStatement(); String sql =
			 * "INSERT INTO Certificate values ('" + alias + "'," + "'" +
			 * serialNumber + "'," + valid + "," + ca + ")";
			 */
			// insert into Persons values (4,'sasd','dsds','sds','dsds')
			// ResultSet rs = statement.executeQuery(sql);
			// statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getAliasBySerialNumber(String serialNumber) {

		PreparedStatement stmt = null;
		String alias = "";
		try {
			stmt = con
					.prepareStatement("SELECT Alias FROM Certificate Where SerialNumber=?");
			stmt.setString(1, serialNumber);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				alias = rs.getString("Alias");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("siso vesla");
		}
		return alias;
	}

	public int isApproved(String alias) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		int approved = 0;
		try {
			stmt = con
					.prepareStatement("SELECT Approved FROM Certificate Where Alias=?");
			stmt.setString(1, alias);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				approved = rs.getInt("Approved");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("siso vesla");
		}
		return approved;
	}
	public boolean isValidBySerial(String serial) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		int i = -1;
		boolean valid = false;
		try {
			stmt = con
					.prepareStatement("SELECT Valid FROM Certificate Where SerialNumber=?");
			stmt.setString(1, serial);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				i = rs.getInt("Valid");
			}
			if (i == 1)
				valid = true;
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("isValid database greska");
		}
		System.out.println("vratio " + valid);
		return valid;
	}
	public boolean isValid(String alias) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		int i = -1;
		boolean valid = false;
		try {
			stmt = con
					.prepareStatement("SELECT Valid FROM Certificate Where Alias=?");
			stmt.setString(1, alias);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				i = rs.getInt("Valid");
			}
			if (i == 1)
				valid = true;
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("isValid database greska");
		}
		System.out.println("vratio " + valid);
		return valid;
	}

	/*
	 * public int getCaNumber(String ca) { // TODO Auto-generated method stub
	 * Statement statement; int returnValue = -1; try { statement =
	 * con.createStatement(); // select CA from Certificate where Alias='a'
	 * String sql = "Select CA from Certificate where Alias='" + ca + "'"; //
	 * insert into Persons values (4,'sasd','dsds','sds','dsds') ResultSet rs =
	 * statement.executeQuery(sql); while (rs.next()) { returnValue =
	 * rs.getInt(1); } return returnValue;
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return returnValue; }
	 */

	/*
	 * public int getValidity(String ca) { // TODO Auto-generated method stub
	 * Statement statement; int returnValue = -1; try { statement =
	 * con.createStatement(); // select CA from Certificate where Alias='a'
	 * String sql = "Select valid from Certificate where Alias='" + ca + "'"; //
	 * insert into Persons values (4,'sasd','dsds','sds','dsds') ResultSet rs =
	 * statement.executeQuery(sql); while (rs.next()) { returnValue =
	 * rs.getInt(1); } return returnValue;
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return returnValue; }
	 */

	/*
	 * public void removeCertBySerialNumber(String sn) { Statement statement;
	 * 
	 * try { statement = con.createStatement(); String sql =
	 * "UPDATE Certificate SET valid = '0' WHERE SerialNumber = '" + sn + "';";
	 * 
	 * statement.executeUpdate(sql);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */
	public void save(X509Certificate cert, String filePath) {
		StringWriter sw = new StringWriter();
		try {
			sw.write(DatatypeConverter.printBase64Binary(cert.getEncoded())
					.replaceAll("(.{64})", "$1\n"));
		} catch (CertificateEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FileWriter fw;
		try {
			// fw = new FileWriter("C:\\Users\\Jevtic\\Desktop\\xx11.cer");
			fw = new FileWriter(filePath + ".cer");
			fw.write(sw.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
