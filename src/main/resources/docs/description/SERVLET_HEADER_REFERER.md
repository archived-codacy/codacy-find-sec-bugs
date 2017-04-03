 Behavior:
- Any value can be assigned to this header if the request is coming from a malicious user.
- The "Referer" will not be present if the request was initiated from another origin that is secure (https).

Recommendations:

- No access control should be based on the value of this header.
- No CSRF protection should be based only on this value ([because it is optional](http://www.w3.org/Protocols/HTTP/HTRQ_Headers.html#z14)).
  

**Reference**  
[CWE-807: Untrusted Inputs in a Security Decision](http://cwe.mitre.org/data/definitions/807.html)

