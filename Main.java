import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Security;
import java.util.List;

import javax.crypto.SecretKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Main {
	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());

		try {
			// generate a salt
			byte[] salt = CryptoUtil.generateSalt();
			// make up a password. Hardcoded for this project.
			String password = "rthjk4567899876xv";
			SecretKey secretKey = Argon2.deriveKeyFromPassword(password, salt);
			// read customer data from csv
			File csvFile = new File("src/customers.csv");
			List<Customer> customers = AESCustomerEncryption.readCustomersFromCSV(csvFile);

			// save encrypted customer data into a new file
			File encryptedFile = new File("encryptedCustomer.txt");
			AESCustomerEncryption.saveEncryptedCustomer(encryptedFile, customers, secretKey, salt);
			String encryptedContent = new String(Files.readAllBytes(Paths.get(encryptedFile.getPath()))).trim();
			
			// load and decrypt the customer data
			List<Customer> decryptedCustomers = AESCustomerEncryption.loadEncryptedCustomer(encryptedContent, secretKey);

			// print the decrypted customer data
			for (Customer customer : decryptedCustomers) {
				System.out.println(customer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
