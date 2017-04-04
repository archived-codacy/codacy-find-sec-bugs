 Cryptographic keys should not be kept in the source code. The source code can be widely shared in an enterprise environment, and is certainly shared in open source. To be managed safely, passwords and secret keys should be stored in separate configuration files or keystores. (Hard coded passwords are reported separately by _Hard Coded Password_ pattern)

**Vulnerable Code:**

```
byte[] key = {1, 2, 3, 4, 5, 6, 7, 8};
SecretKeySpec spec = new SecretKeySpec(key, "AES");
Cipher aes = Cipher.getInstance("AES");
aes.init(Cipher.ENCRYPT_MODE, spec);
return aesCipher.doFinal(secretData);
```
  

**References**  
[CWE-321: Use of Hard-coded Cryptographic Key](http://cwe.mitre.org/data/definitions/321.html)

