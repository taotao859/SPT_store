# SPT_store
1. 后端包first-spring中的pom.xml和src\main\resources\application.properties需要根据自己IDEA的配置进行更改
2. first-spring的SDK为Amazon Corretto 1.8，这与大家常用的JDK 1.8不同，所以需要重新下载该SDK，然后项目才能运行
3. 前端命名规约：
  （1）货品资料维护： api/product
  （2）客户资料维护： api/costomer
   (3) 员工资料维护： api/employee
   
   补充：增加：/add     举例：在product中新增，用api/product/add
         删除：/delete
         修改：/update
         查询0个，1个或多个，返回列表：/search
         查询1个，其他个数会报错，返回结构体:/get
         得到所有数据，返回list：/all
4. 后端返回状态码：
   200：成功
   400：失败
   在登录界面：200——老板， 201——库存管理员， 202——售货员
5. 货品资料维护、客户资料维护、员工资料维护三个页面中，实现对每个表项的编辑功能时，前端向后端返回数据时，返回内容为原来表项的Id和新修改的表项内容
    例：原来为 ID：1， name：刀， 价格：199 修改为 name：笔， 价格200；发送{1, 笔， 200}
