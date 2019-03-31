# Blockchain Web Services
The primary purpose of this project was to develop a simple blockchain (capable of holding string transactions) from scratch, and then deploy the blockchain as a web service using various architectural styles.

The core blockchain code can be found in the "Blockchain" folder. Core features of the blockchain were then implemented using the following web service archiectures:
1. Standard SOAP - request parameters are passed as their native data types
2. Single message SOAP - request parameters are sent as a single comma-delimited string, then parsed by the server
3. REST - request parameters are passed via HTTP requests

All development was done using NetBeans IDE and GlassFish server. For each web service, separate Client/Server applications have been developed for demonstration purposes. Main code files can be found within each folder's "src" sub-directory. To run one of the services, simply import the Client/Server pair into Netbeans and run the applications.
