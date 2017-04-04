 When converting a byte array containing a hash signature to a human readable string, a conversion mistake can be made if the array is read byte by byte. The following sample illustrates the use of Integer.toHexString() which will trim any leading zeroes from each byte of the computed hash value.
```
MessageDigest md = MessageDigest.getInstance("SHA-256");
byte[] resultBytes = md.digest(password.getBytes("UTF-8"));

StringBuilder stringBuilder = new StringBuilder();
for(byte b :resultBytes) {
    stringBuilder.append( Integer.toHexString( b & 0xFF ) );
}

return stringBuilder.toString();
```

This mistake weakens the hash value computed since it introduces more collisions. For example, the hash values "0x0679" and "0x6709" would both output as "679" for the above function.

In this situation, the use of toHexString() should be replaced with String.format() as follows:

```
stringBuilder.append( String.format( "%02X", b ) );
```
  

**References**  
[CWE-704: Incorrect Type Conversion or Cast](http://cwe.mitre.org/data/definitions/704.html)

