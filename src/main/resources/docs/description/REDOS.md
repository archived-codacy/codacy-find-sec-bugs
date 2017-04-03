 Regular expressions (regexs) are frequently subject to Denial of Service (DOS) attacks (called ReDOS). This is due to the fact that regex engines may take a large amount of time when analyzing certain strings, depending on how the regex is defined.

For example, for the regex: **^(a+)+$**, the input "`aaaaaaaaaaaaaaaaX`" will cause the regex engine to analyze 65536 different paths.[1] Example taken from OWASP references

Therefore, it is possible that a single request may cause a large amount of computation on the server side. The problem with this regex, and others like it, is that there are two different ways the same input character can be accepted by the Regex due to the + (or a \*) inside the parenthesis, and the + (or a \*) outside the parenthesis. The way this is written, either + could consume the character 'a'. To fix this, the regex should be rewritten to eliminate the ambiguity. For example, this could simply be rewritten as: **^a+$** , which is presumably what the author meant anyway (any number of a's). Assuming that's what the original regex meant, this new regex can be evaluated quickly, and is not subject to ReDOS.

  

**References**  
[Sebastian Kubeck's Weblog: Detecting and Preventing ReDoS Vulnerabilities](http://www.jroller.com/sebastianKuebeck/entry/detecting_and_preventing_redos_vulnerabilities)  
[1] [OWASP: Regular expression Denial of Service](https://www.owasp.org/index.php/Regular_expression_Denial_of_Service_-_ReDoS)  
[CWE-400: Uncontrolled Resource Consumption ('Resource Exhaustion')](http://cwe.mitre.org/data/definitions/400.html)

