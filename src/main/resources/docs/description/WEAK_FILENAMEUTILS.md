 Some FilenameUtils' methods don't filter NULL bytes (`0x00`).

If a null byte is injected into a filename, if this filename is passed to the underlying OS, the file retrieved will be the name of the file that is specified prior to the NULL byte, since at the OS level, all strings are terminated by a null byte even though Java itself doesn't care about null bytes or treat them special. This OS behavior can be used to bypass filename validation that looks at the end of the filename (e.g., endswith ".log") to make sure its a safe file to access.

To fix this, two things are recommended:

- Upgrade to Java 7 update 40 or later, or Java 8+ since [NULL byte injection in filenames is fixed in those versions](http://bugs.java.com/bugdatabase/view_bug.do?bug_id=8014846).
- Strongly validate any filenames provided by untrusted users to make sure they are valid (i.e., don't contain null, don't include path characters, etc.)

If you know you are using a modern version of Java immune to NULL byte injection, you can probably disable this rule.

  

**References**  
[WASC-28: Null Byte Injection](http://projects.webappsec.org/w/page/13246949/Null%20Byte%20Injection)  
[CWE-158: Improper Neutralization of Null Byte or NUL Character](http://cwe.mitre.org/data/definitions/158.html)

