# SPT_store
1. 后端包first-spring中的pom.xml和src\main\resources\application.properties需要根据自己IDEA的配置进行更改
2. first-spring的SDK为Amazon Corretto 1.8，这与大家常用的JDK 1.8不同，所以需要重新下载该SDK，然后项目才能运行
3. 前端命名规约：
  （1）货品资料维护： api/product
  （2）客户资料维护： api/costomer
   (3) 员工资料维护： api/employee
4. 后端返回状态码：
   200：成功
   400：失败
5. 货品资料维护、客户资料维护、员工资料维护三个页面中，实现对每个表项的编辑功能时，前端向后端返回数据时，返回内容为Id和新修改的表项内容
