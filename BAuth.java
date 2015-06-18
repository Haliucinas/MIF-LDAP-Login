/**
 *  @author Mantas Jonytis
 *  @student Informatika, 2 kursas, 1 grupė
 *  @class HTTPClient class
 * 	@version 0.1
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *	This class connects to mif LDAP database
 *	and gathers information (name, surname, etc.)
 */
public class BAuth {

	// database url
	final String webPage = "https://k007.mif.vu.lt/ldap/user.php";
	// encoded username+passwd
	String authStringEnc;
	// loaded html document
	Document doc;

	/**
	 *	Constructor encodes username and password and prints it in stdout
	 */
	public BAuth (String name, char[] password) {
		authStringEnc = new String(Base64.encodeBase64((name + ":" + new String(password)).getBytes()));
	}

	/**
	 *	Connects to LDAP, saves ouputed HTML
	 *  @return true on success
	 *	@return false if unable to connect
	 */
	public boolean connect () {
		try {
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			doc = Jsoup.parse(sb.toString());
			System.out.println("##DEBUG: Connected!");
			return true;
		} catch (IOException e) {
			System.out.println("##DEBUG: Failed to connect!");
			return false;
		}
	}

	/**
	 * Get element text with given string
	 * @param string to search
	 * @return element text value
	 */
	private String getTDText (String str) {
		return doc.select("TD:containsOwn(" + str + ":)").first().nextElementSibling().text();
	}

	public String getName () {
		return getTDText("givenName");
	}

	public String getSurname () {
		return getTDText("sn");
	}

	public String getNr () {
		return getTDText("employeeNumber");
	}

	public String getHomedir () {
		return getTDText("homeDirectory");
	}

	public String getMail () {
		return getTDText("mail");
	}
}