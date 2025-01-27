import java.security.SecureRandom;
import java.util.Base64;

public class CryptoUtil {
	/**
	 * Generate random salt
	 */
	public static byte[] generateSalt() {
		byte[] salt = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		return salt;
	}

	public static String encodeBase64(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	public static byte[] decodeBase64(String data) {
		return Base64.getDecoder().decode(data);
	}

}
