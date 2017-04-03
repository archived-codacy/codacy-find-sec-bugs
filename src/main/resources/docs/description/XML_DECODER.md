 XMLDecoder should not be used to parse untrusted data. Deserializing user input can lead to arbitrary code execution. This is possible because XMLDecoder supports arbitrary method invocation. This capability is intended to call setter methods, but in practice, any method can be called.

**Malicious XML example:**

```
<?xml version="1.0" encoding="UTF-8" ?>
<java version="1.4.0" class="java.beans.XMLDecoder">
  <object class="java.io.PrintWriter">
    <string>/tmp/Hacked.txt</string>
    <void method="println">
      <string>Hello World!</string>
    </void>
    <void method="close"/>
  </object>
</java>
```

The XML code above will cause the creation of a file with the content "Hello World!".

**Vulnerable Code:**

```
XMLDecoder d = new XMLDecoder(in);
try {
    Object result = d.readObject();
}
[...]
```

**Solution:** The solution is to avoid using XMLDecoder to parse content from an untrusted source.

  

**References**  
[Dinis Cruz Blog: Using XMLDecoder to execute server-side Java Code on an Restlet application](http://blog.diniscruz.com/2013/08/using-xmldecoder-to-execute-server-side.html)  
[RedHat blog : Java deserialization flaws: Part 2, XML deserialization](https://securityblog.redhat.com/2014/01/23/java-deserialization-flaws-part-2-xml-deserialization/)  
[CWE-20: Improper Input Validation](http://cwe.mitre.org/data/definitions/20.html)

 