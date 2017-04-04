 DES and DESede (3DES) are not considered strong ciphers for modern applications. Currently, NIST recommends the usage of AES block ciphers instead of DES/3DES.

**Example weak code:**

```
Cipher c = Cipher.getInstance("DESede/ECB/PKCS5Padding");
c.init(Cipher.ENCRYPT_MODE, k, iv);
byte[] cipherText = c.doFinal(plainText);
```

**Example solution:**

```
Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
c.init(Cipher.ENCRYPT_MODE, k, iv);
byte[] cipherText = c.doFinal(plainText);
```
  

**References**  
[NIST Withdraws Outdated Data Encryption Standard](http://www.nist.gov/itl/fips/060205_des.cfm)  
[CWE-326: Inadequate Encryption Strength](http://cwe.mitre.org/data/definitions/326.html)

