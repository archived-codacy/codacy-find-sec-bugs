 The ciphertext produced is susceptible to alteration by an adversary. This mean that the cipher provides no way to detect that the data has been tampered with. If the ciphertext can be controlled by an attacker, it could be altered without detection.

The solution is to used a cipher that includes a Hash based Message Authentication Code (HMAC) to sign the data. Combining a HMAC function to the existing cipher is prone to error [[1]](http://www.thoughtcrime.org/blog/the-cryptographic-doom-principle/). Specifically, it is always recommended that you be able to verify the HMAC first, and only if the data is unmodified, do you then perform any cryptographic functions on the data.

The following modes are vulnerable because they don't provide a HMAC:  
 - CBC  
 - OFB  
 - CTR  
 - ECB  
  
 The following snippets code are some examples of vulnerable code.  
  
**Code at risk:**  
_AES in CBC mode_

```
Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
c.init(Cipher.ENCRYPT_MODE, k, iv);
byte[] cipherText = c.doFinal(plainText);
```
  
_Triple DES with ECB mode_  

```
Cipher c = Cipher.getInstance("DESede/ECB/PKCS5Padding");
c.init(Cipher.ENCRYPT_MODE, k, iv);
byte[] cipherText = c.doFinal(plainText);
```

**Solution:**

```
Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
c.init(Cipher.ENCRYPT_MODE, k, iv);
byte[] cipherText = c.doFinal(plainText);
```

In the example solution above, the GCM mode introduces an HMAC into the resulting encrypted data, providing integrity of the result.

  

**References**  
[Wikipedia: Authenticated encryption](http://en.wikipedia.org/wiki/Authenticated_encryption)  
[NIST: Authenticated Encryption Modes](http://csrc.nist.gov/groups/ST/toolkit/BCM/modes_development.html#01)  
[Moxie Marlinspike's blog: The Cryptographic Doom Principle](http://www.thoughtcrime.org/blog/the-cryptographic-doom-principle/)  
[CWE-353: Missing Support for Integrity Check](http://cwe.mitre.org/data/definitions/353.html)

