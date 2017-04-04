 Deserialization gadget are class that could be use by an attacker to take advantage of a remote API using Native Serialization. This class is either adding custom behavior to deserialization with the readObject method (Serializable) or can be called from a serialized object (InvocationHandler).

This detector is intended to be use mostly by researcher. The real issue is using deserialization for remote operation. Removing gadget is an hardening practice to reduce the risk of being exploited.

**References**  
[CWE-502: Deserialization of Untrusted Data](https://cwe.mitre.org/data/definitions/502.html)  
[Deserialization of untrusted data](https://www.owasp.org/index.php/Deserialization_of_untrusted_data)  
[Serialization and Deserialization](http://www.oracle.com/technetwork/java/seccodeguide-139067.html#8)  
[A tool for generating payloads that exploit unsafe Java object deserialization](https://github.com/frohoff/ysoserial)  
[1] [Example of Denial of Service using the class java.util.HashSet](https://gist.github.com/coekie/a27cc406fc9f3dc7a70d)  
[2] [OpenJDK: Deserialization issue in ObjectInputStream.readSerialData() (CVE-2015-2590)](https://bugzilla.redhat.com/show_bug.cgi?id=CVE-2015-2590)  
[3] [Rapid7: Sun Java Calendar Deserialization Privilege Escalation (CVE-2008-5353)](https://www.rapid7.com/db/modules/exploit/multi/browser/java_calendar_deserialize)

