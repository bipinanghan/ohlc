# Upstox OHLC Solution

## PROBLEM : 

**There are some trades in trades.json file and we need to read that data and send to client who is connected throgh tocket base in 15 min interval if stock data is like that way.**


----------------------------------------------------------------------
## Prerequisites : 

**We required below software in your local machine.**

   Mave-> Build tool
   JRE + JDK 1.8 or higher -> For project compilation.

----------------------------------------------------------------------
## How to Run Project :

**Application is build using Spring boot + Embedded Jetty container + Jetty WebSocket API.**

* Goto project directory and run below command which will create upstoxohlc-0.0.1-SNAPSHOT.war
	maven install

   This step takes while and generate fat war file for execution under build/libs as upstoxohlc-0.0.1-SNAPSHOT.war
   
* Goto build/libs folder and type following command on terminal / cmd, 
 
	java  -Dtrade.file.path=<Absolute path of trade file liek g:/trades.json ]> -jar upstoxohlc-0.0.1-SNAPSHOT.war
	
	 Example 
          Window : java -Dtrade.file.path=G\\trades.json -jar upstoxohlc-0.0.1-SNAPSHOT.war
          Ubuntu : java -Dtrade.file.path=/home/trades.json -jar upstoxupstoxohlc-0.0.1-SNAPSHOT.war
	
	Application will start processing trades one by one and keep on generating OHLC for stock symbol.
	
	Now visit http://localhost:8080/upstox/ohlc and subscribe for some symbol ticks.
	
	After subscription is done, you will start getting stock OHLC real-time.
	
	Keep registering for some more stock OHLC and enjoy tick updates.

----------------------------------------------------------------------

### Assumption :

> FileReaderStockTickProvider keeps reading symbol tick in an interval of 100ms

> Complete system is based on in-memory data structures like HashMap, & HashSet and doesn't store any data in DBMS.

----------------------------------------------------------------------
### Contact :
**For any queries / issues , Please contact me @ _bipin.anghan@gmail.com_** 
