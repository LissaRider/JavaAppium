e:
cd E:\QA_DEV\JavaAppium\
set PLATFORM=mobile_web
mvn -s C:\Users\lisan\.m2\default-settings.xml -Dtest=TestSuite test
allure serve target/allure-results --host localhost --port 9999