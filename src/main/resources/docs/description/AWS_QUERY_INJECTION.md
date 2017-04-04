 Constructing SimpleDB queries containing user input can allow an attacker to view unauthorized records.  
The following example dynamically constructs and executes a SimpleDB select() query allowing the user to specify the productCategory. The attacker can modify the query, bypass the required authentication for customerID and view records matching any customer.

**Vulnerable Code:**

```
...
String customerID = getAuthenticatedCustomerID(customerName, customerCredentials);
String productCategory = request.getParameter("productCategory");
...
AmazonSimpleDBClient sdbc = new AmazonSimpleDBClient(appAWSCredentials);
String query = "select * from invoices where productCategory = '"
            + productCategory + "' and customerID = '"
            + customerID + "' order by '"
            + sortColumn + "' asc";
SelectResult sdbResult = sdbc.select(new SelectRequest(query));
```

**Solution:**  
This issue is analogical to SQL Injection. Sanitize user input before using it in a SimpleDB query.

  

**References**  
[CWE-943: Improper Neutralization of Special Elements in Data Query Logic](https://cwe.mitre.org/data/definitions/943.html)

 