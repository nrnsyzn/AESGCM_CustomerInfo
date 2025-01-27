import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Argon2 {
	/**
	 * In this code, the deriveKeyFromPassword method takes a password and a salt as
	 * inputs, configures Argon2 parameters (such as iterations, memory usage, and
	 * parallelism), and generates a 256-bit (32-byte) key suitable for use with AES
	 * encryption. The salt ensures that even identical passwords produce unique
	 * keys, enhancing security. This approach ensures that sensitive data, such as
	 * passwords, is securely hashed and stored.
	 * 
	 */
	public static SecretKey deriveKeyFromPassword(String password, byte[] salt) throws Exception {
		Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id).withSalt(salt)
				.withIterations(3).withMemoryAsKB(65536).withParallelism(2);
		Argon2Parameters params = builder.build();
		// initialize the generator. Takes the configured Argon2Parameters
		Argon2BytesGenerator generator = new Argon2BytesGenerator();
		generator.init(params);
		// convert string to byte
		byte[] passwordBytes = password.getBytes();

		byte[] derivedKey = new byte[32];
		generator.generateBytes(passwordBytes, derivedKey);
		return new SecretKeySpec(derivedKey, "AES");
	}

}
