 Initialization vector must be regenerated for each message to be encrypted.

**Vulnerable Code:**

```
private static byte[] IV = new byte[16] {(byte)0,(byte)1,(byte)2,[...]};

public void encrypt(String message) throws Exception {

    IvParameterSpec ivSpec = new IvParameterSpec(IV);
[...]
```

**Solution:**

```
public void encrypt(String message) throws Exception {

    byte[] iv = new byte[16];
    new SecureRandom().nextBytes(iv);

    IvParameterSpec ivSpec = new IvParameterSpec(iv);
[...]
```
  

**References**  
[Wikipedia: Initialization vector](http://en.wikipedia.org/wiki/Initialization_vector)  
[CWE-329: Not Using a Random IV with CBC Mode](http://cwe.mitre.org/data/definitions/329.html)  
[Encryption - CBC Mode IV: Secret or Not?](https://defuse.ca/cbcmodeiv.htm)

 