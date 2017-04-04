 In Struts 2, the endpoints are Plain Old Java Objects (POJOs) which means no Interface/Class needs to be implemented/extended.

When a request is routed to its controller (like the selected class), the supplied HTTP parameters are automatically mapped to setters for the class. Therefore, all setters of this class should be considered as untrusted input even if the form doesn't include those values. An attacker can simply provide additional values in the request, and they will be set in the object anyway, as long as that object has such a setter. The use of these parameters should be reviewed to make sure they are used safely.

