 This specific mode of CBC with PKCS5Padding is susceptible to padding oracle attacks. An adversary could potentially decrypt the message if the system exposed the difference between plaintext with invalid padding or valid padding. The distinction between valid and invalid padding is usually revealed through distinct error messages being returned for each condition.

**Code at risk:**

```
Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
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
[Padding Oracles for the masses (by Matias Soler)](http://www.infobytesec.com/down/paddingoracle_openjam.pdf)  
[Wikipedia: Authenticated encryption](http://en.wikipedia.org/wiki/Authenticated_encryption)  
[NIST: Authenticated Encryption Modes](http://csrc.nist.gov/groups/ST/toolkit/BCM/modes_development.html#01)  
[CAPEC: Padding Oracle Crypto Attack](http://capec.mitre.org/data/definitions/463.html)  
[CWE-696: Incorrect Behavior Order](http://cwe.mitre.org/data/definitions/696.html)

