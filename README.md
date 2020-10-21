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

* Goto project directory and run below command which will create ohlc-0.0.1-SNAPSHOT.war  (also it is avaialable in target folder so you can run directly)
	maven clean
	maven install

   This step takes while and generate fat war file for execution under build/libs as ohlc-0.0.1-SNAPSHOT.war
   
* Goto  our project where ohlc-0.0.1-SNAPSHOT.war is available (target folder) , 
 
	java  -Dtrade.file.path=<Absolute path of trade file liek g:/trades.json ]> -jar ohlc-0.0.1-SNAPSHOT.war
	
	 Example 
          Window : java -Dtrade.file.path=G\\trades.json -jar ohlc-0.0.1-SNAPSHOT.war
          Ubuntu : java -Dtrade.file.path=/home/trades.json -jar ohlc-0.0.1-SNAPSHOT.war
	
	Application will start processing trades one by one and keep on generating OHLC for stock symbol.
	
	Now visit http://localhost:8080/upstox/ohlc and subscribe for some symbol ticks.
	
	After subscription is done, you will start getting stock OHLC real-time.
	
	Keep registering for some more stock OHLC and enjoy tick updates.

----------------------------------------------------------------------
### Contact :
**For any queries / issues , Please contact me @ _bipin.anghan@gmail.com_** 
