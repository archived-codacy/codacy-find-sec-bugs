 The NullCipher is rarely used intentionally in production applications. It implements the Cipher interface by returning ciphertext identical to the supplied plaintext. In a few contexts, such as testing, a NullCipher may be appropriate.

**Vulnerable Code:**

```
Cipher doNothingCihper = new NullCipher();
[...]
//The ciphertext produced will be identical to the plaintext.
byte[] cipherText = c.doFinal(plainText);
```

**Solution:**  
 Avoid using the NullCipher. Its accidental use can introduce a significant confidentiality risk.

  

**Reference**  
[CWE-327: Use of a Broken or Risky Cryptographic Algorithm](http://cwe.mitre.org/data/definitions/327.html)

