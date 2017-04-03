 An authentication cipher mode which provides better confidentiality of the encrypted data should be used instead of Electronic Codebook (ECB) mode, which does not provide good confidentiality. Specifically, ECB mode produces the same output for the same input each time. So, for example, if a user is sending a password, the encrypted value is the same each time. This allows an attacker to intercept and replay the data.

To fix this, something like Galois/Counter Mode (GCM) should be used instead.

**Code at risk:**

```
Cipher c = Cipher.getInstance("AES/ECB/NoPadding");
c.init(Cipher.ENCRYPT_MODE, k, iv);
byte[] cipherText = c.doFinal(plainText);
```

**Solution:**

```
Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
c.init(Cipher.ENCRYPT_MODE, k, iv);
byte[] cipherText = c.doFinal(plainText);
```
  

**References**  
[Wikipedia: Authenticated encryption](http://en.wikipedia.org/wiki/Authenticated_encryption)  
[NIST: Authenticated Encryption Modes](http://csrc.nist.gov/groups/ST/toolkit/BCM/modes_development.html#01)  
[Wikipedia: Block cipher modes of operation](http://en.wikipedia.org/wiki/Block_cipher_modes_of_operation#Electronic_codebook_.28ECB.29)  
[NIST: Recommendation for Block Cipher Modes of Operation](http://csrc.nist.gov/publications/nistpubs/800-38a/sp800-38a.pdf)

