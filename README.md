# Project Taba support

## Environment

**1. Apache Tomcate server**   

* Version : higher 8.0.0
* Recommend: 8.5.4
* Download: [https://tomcat.apache.org](https://tomcat.apache.org/download-80.cgi)

**2. IDE**

* jetbrains Intellij IDEA 2016.2
* Download: [https://www.jetbrains.com](https://www.jetbrains.com/products.html?fromMenu)

**3. Java JDK**

* Version: 1.8.x
* Download: [http://www.oracle.com](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html)

## Source checkout:
1.  Checkout source
2.  Pull source về checkout sang develop
3.  Tạo branch mới từ develop
4.  Khi push không push lên develop
5.  Commit branch đã tạo lên sau đó vào tạo merge request Assignee cho DaiVP

## Source Config:

* Copy **WEB-INF/config/config.properties.sample** to **WEB-INF/config/config.properties**
* Edit thông tin DB trong **WEB-INF/config/config.properties**

## Note:

Đọc file **CONTRIBUTING.md** trước khi push code để tránh **conflict** và **tốn tiền cafe**