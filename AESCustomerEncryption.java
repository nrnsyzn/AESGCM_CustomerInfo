import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import com.google.gson.Gson; // For JSON Serialization and Deserialization

public class AESCustomerEncryption {
	private static final int IV_SIZE = 12; // 12 bytes for GCM
	private static final int TAG_SIZE = 128; // 128 bits for GCM

	public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
		byte[] iv = new byte[IV_SIZE];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(iv);
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_SIZE, iv);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
		byte[] encryptedBytesWithIv = new byte[IV_SIZE + encryptedBytes.length];
		System.arraycopy(iv, 0, encryptedBytesWithIv, 0, IV_SIZE);
		System.arraycopy(encryptedBytes, 0, encryptedBytesWithIv, IV_SIZE, encryptedBytes.length);

		return Base64.getEncoder().encodeToString(encryptedBytesWithIv);
	}

	public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
		// create a cipher object that uses AES transformation
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		byte[] iv = new byte[IV_SIZE];
		byte[] encByteWithIv = Base64.getDecoder().decode(encryptedText);
		System.arraycopy(encByteWithIv, 0, iv, 0, IV_SIZE);
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_SIZE, iv);
		// initialize the cipher with the operation DECRYPTION, and secret key
		cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
		// convert the Base64-encoded string of encrypted data to binary format
		byte[] encryptedBytes = new byte[encByteWithIv.length - IV_SIZE];
		// the binary representation of encrypted data using Cipher object
		// initialized
		System.arraycopy(encByteWithIv, IV_SIZE, encryptedBytes, 0, encryptedBytes.length);
		try {
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
			return new String(decryptedBytes);
		} catch (AEADBadTagException e) {
			throw new SecurityException("Authentication tag verification failed. Data may be tampered with.");
		}
	}

	public static void saveEncryptedCustomer(File file, List<Customer> customer, SecretKey secretKey, byte[] salt)
			throws Exception {
		Gson gson = new Gson();
		// convert the customer information json format to make it more readable
		String json = gson.toJson(customer);
		// encrypt customer's data
		String encryptedData = encrypt(json, secretKey);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			// write the encrypted data into a file
			writer.write(CryptoUtil.encodeBase64(salt) + "\n"); // save the salt
			writer.write(encryptedData);
		}
	}

	public static List<Customer> loadEncryptedCustomer(String encryptedContent, SecretKey secretKey) throws Exception {
		// Split the content into salt and encrypted data
		String[] parts = encryptedContent.split("\n");
		if (parts.length != 2) {
			throw new IllegalArgumentException("Invalid encrypted content format. Expected salt and encrypted data.");
		}

		// Decode the salt (not used in decryption, but included for completeness)
		byte[] salt = CryptoUtil.decodeBase64(parts[0]);

		// Decrypt the data
		String decryptedContent = decrypt(parts[1], secretKey);

		// Deserialize the decrypted content to a list of customers
		Gson gson = new Gson();
	
		return Arrays.asList(gson.fromJson(decryptedContent, Customer[].class));
	}

	public static List<Customer> readCustomersFromCSV(File file) throws Exception {
		String fileName = file.toString();
		if (!fileName.toLowerCase().endsWith("csv")) {
			throw new IllegalArgumentException("Invalid fiel format. Only .csv is accepted.");
		}
		List<Customer> customers = new ArrayList<>();
		try (BufferedReader read = new BufferedReader(new FileReader(file))) {
			String line;
			boolean isHeader = true;
			while ((line = read.readLine()) != null) {
				if (isHeader) {
					isHeader = false;
					continue;
				}
				String[] parts = line.split(",");
				String id = parts.length > 0 && !parts[0].isEmpty() ? parts[0] : "NA";
				String name = parts.length > 1 && !parts[1].isEmpty() ? parts[1] : "NA";
				String phone = parts.length > 2 && !parts[2].isEmpty() ? parts[2] : "NA";
				String email = parts.length > 3 && !parts[3].isEmpty() ? parts[3] : "NA";
				String password = parts.length > 4 && !parts[4].isEmpty() ? parts[4] : "NA";
				String totalMemberPoints = parts.length > 5 && !parts[5].isEmpty() ? parts[5] : "NA";
				customers.add(new Customer(id, name, phone, email, password, totalMemberPoints));
			}

		}
		return customers;
	}

}
