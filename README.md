# AESGCM Customer Encryption Project
This is a project of basic implementation of Advanced Encryption Standard - Galois/ Counter Mode in Java. This project uses the Cipher library provided by Java,and Google Java Library for serialization and deserialization of customer datas. This project takes customer data from a file, encrypt, and save it into the file. It can also be decrypted again and saved into the file. The idea is to ensure that companies can protect their data against attackers and secure their assets despite the general AES algorithm is public. It is known that modern cryptography makes the algorithm public and makes the secret.

In this project, I use Google Gson reference Library, and BouncyCastle for the process of Serialization and Key Derivation.
Google Gson is used for Serialization and Deserialization of Customer information so that the customer information can be in a more readable format. 
This project utilizes Argon2 to derive cryptographic keys from passwords. It is designed to be memory-hard and computationally intensive to make it hard to brute force. This project uses the Argon2id variant which is a hybrid between resistance against side-channel vulnerabilities and GPU-based attacks. 

# AES-GCM Definition
AES is a secure encryption algorithm and it is a symmetric block cipher algorithm that has been adopted by National Institute of Science and Technology (NIST) in 2001. AES-GCM is a short form for Advanced Encryption Standard- Galois/Counter Mode.
One of the benefit of using GCM mode is GCM provides encryption and authentication. In this project, we will use unique Initialization Vector for every encryption to ensure security. We will also can verify the integrity and authenticity of the data using GCM.

# Counter Mode definition
A block cipher mode of operation that turns a block cipher into stream cipher. It encrypts a counter value to produce a keystream, which is then XORed with plaintext to produce ciphertext.

# Characteristics of AES-GCM
1) Provides autheticated encryption. GCM gives authentication through cryptographic hash function (GHASH), which generates Nessage Authentication Code (MAC) that helps with maintaining data integrity and authenticity. 
2) Does not require padding, which can prevent the potential vulnerability with padding that can lead to Padding Oracle Attack
3) AES-GCM requires unique nonce for every encryption operation to ensure security. In this project we are using SecureRandom to generate a new unique nonce
4) GCM is efficient making it suitable for high-speed network and systems.
5) The encryption and authentication can be paralellized, which means it can improve performance on hardware. Parallelizable means operations in the process can be broken down into smaller tasks that can be executed simultaneously on modern hardware.

# Steps in AES-GCM Algorithm
1) Initialize the counter block and GHASH key. If the nonce is 96 bits, then we can directly use the initial counter block. Else, it will be hashed using GHASH to create initial counter block. To initialize the GHASH key, we encrypt a block of zeros using AES with the provided key, and this produces GHASH which wil nbe used in authentication process
2) Encryption in CTR Mode. Plaintext is divided into blocks of 128-bits. A counter is initialized with the initial counter block and incremented for each block. Each counter value then will be encrypted using AES to produce keystream block, which is then XORed with keystream block to produce ciphertext block. 
3) Authentication using GHASH. This process ensures the integrity of ciphertext. It uses polynomial multiplication in Galois field with GHASH key, which will produce 128-bit authentication tag. In this project, teh authentication tag is automtically handled by Cipher class.
4) The authentication tag is encrypted using AES in CTR mode with the initial counter block incremented by 1. Final output consist of authentication tag and ciphertext.

# Key Derivation Process
This project relies on external library like BouncyCastle. Using BC, we are able to use Argon2 Utility, which is a password hashing algorithm. Argon2 will turn user's password into a hashed format. 
