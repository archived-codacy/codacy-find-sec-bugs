 A file is opened to write to its contents. The filename comes from an **input** parameter. If an unfiltered parameter is passed to this file API, files at an arbitrary filesystem location could be modified.

This rule identifies **potential** path traversal vulnerabilities. In many cases, the constructed file path cannot be controlled by the user. If that is the case, the reported instance is a false positive.

  

**References**  
[WASC-33: Path Traversal](http://projects.webappsec.org/w/page/13246952/Path%20Traversal)  
[OWASP: Path Traversal](https://www.owasp.org/index.php/Path_Traversal)  
[CAPEC-126: Path Traversal](http://capec.mitre.org/data/definitions/126.html)  
[CWE-22: Improper Limitation of a Pathname to a Restricted Directory ('Path Traversal')](http://cwe.mitre.org/data/definitions/22.html)

