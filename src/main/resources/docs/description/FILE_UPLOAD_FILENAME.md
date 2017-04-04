 The filename provided by the FileUpload API can be tampered with by the client to reference unauthorized files.

For example:

- `"../../../config/overide_file"`
- `"shell.jsp\u0000expected.gif"`

Therefore, such values should not be passed directly to the filesystem API. If acceptable, the application should generate its own file names and use those. Otherwise, the provided filename should be properly validated to ensure it's properly structured, contains no unauthorized path characters (e.g., / \), and refers to an authorized file.

  

**References**  
[Securiteam: File upload security recommendations](http://blogs.securiteam.com/index.php/archives/1268)  
[CWE-22: Improper Limitation of a Pathname to a Restricted Directory ('Path Traversal')](http://cwe.mitre.org/data/definitions/22.html)  
[WASC-33: Path Traversal](http://projects.webappsec.org/w/page/13246952/Path%20Traversal)  
[OWASP: Path Traversal](https://www.owasp.org/index.php/Path_Traversal)  
[CAPEC-126: Path Traversal](http://capec.mitre.org/data/definitions/126.html)  
[CWE-22: Improper Limitation of a Pathname to a Restricted Directory ('Path Traversal')](http://cwe.mitre.org/data/definitions/22.html)

