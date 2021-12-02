# SPT_store

## 环境

-   后端包first-spring中的pom.xml和src\main\resources\application.properties需要根据自己IDEA的配置进行更改

-   first-spring的SDK为Amazon Corretto 1.8，这与大家常用的JDK 1.8不同，所以需要重新下载该SDK，然后项目才能运行

## 后端接口所在url：

前端可以通过**"http://后端ip:8443/api"**加上相应的部分以访问以下后端函数

### 类别一

以下三个为简单的增删改查。

-   货品资料维护： /product

-   客户资料维护： /customer

-   员工资料维护： /staff

其后应加上以下url的来实现增删改查。他们的输入输出可以观看后端代码，输入是结构体需要传入JSON。

-   增加

    -   url：/add，举例：在product中新增，用api/product/add/来实现

-   删除

    -   url：/delete

-   修改

    -   url：/update

-   查询

    -   /search：查询0个，1个或多个，返回一个列表

    -   /get：查询1个，返回结构体，其他个数会报错

-   得到所有数据，返回list：/all

### 类别二：零售

零售部分函数所在接口均在/api/retail下，举例："http://后端ip:8443/api/retail/init"就可以调用零售订单初始化函数

-   初始化订单：
    -   url：/init
    -   输入：String staffId，int repositoryId，String customerId
    -   输出：Orders
-   增加一条订单明细
    -   url：/add
    -   输入：int product_id，int product_quantity，Orders order
    -   输出：JSON类型
        -   "orderitemList"：订单明细列表
        -   "totalPriceList"：各项明细总价列表
        -   "ordersTotalPrice"：订单总价
-   删除一条订单明细
    -   url：/delete
    -   输入：int product_id，int product_quantity，Orders order
    -   输出：JSON类型
        -   "orderitemList"：订单明细列表
        -   "totalPriceList"：各项明细总价列表
        -   "ordersTotalPrice"：订单总价
        -   "isDelete"：删除是否成功
    -   注意：如果订单里面只有4只笔，但是删除6只笔，就看做仅删除4只笔。
-   返回所有零售订单
    -   url：/all
    -   输入：无
    -   输出：订单列表
-   取消订单
    -   url：/cancel
    -   输入：Orders
    -   返回状态码
-   付款
    -   url：/pay
    -   输入：Orders
    -   输出：状态码

### 类别三：批发

批发部分函数所在接口均在/api/wholesale下，举例："http://后端ip:8443 /api/retail /init"就可以调用批发订单初始化函数。

-   初始化订单
    -   url：/init
    -   输入：String staffId，int repositoryId，String customerId
    -   输出：Orders
-   添加订单明细
    -   url：/add
    -   输入：int productId，int productQuantity，Decimal productPrice, Orders orders
    -   输出
        -   "orderitemList"：订单明细列表
        -   "totalPriceList"：各项明细总价列表
        -   "ordersTotalPrice"：订单总价
        -   "isDelete"：删除是否成功
-   所有批发订单
    -   url：/all
    -   输入：无
    -   输出：订单列表
-   审核
    -   url：check
    -   输入：Orders
    -   返回：状态码，200表示通过，400表示不通过
-   支付
    -   url：/pay
    -   输入：Orders orders，Decimal paidMoney
    -   输出：
        -   "ordersTotalPrice"：订单总价
        -   "ordersPaidAmount"：已付金额
        -   "ordersUnpaidAmount"：未付金额
-   获取下拉框内容
    -   url：/options
    -   输入：无
    -   输出：
        -   "ordersStatusList"：订单状态列表
        -   "repositoryNameList"：仓库名列表
        -   "staffNameList"：员工名列表
-   筛选
    -   url：/filter
    -   输入：(required = false) int ordersId, (required = false) String customerId, (required = false) int ordersState,(required = false) String repositoryName, (required = false) String staffName
    -   输出：订单列表

## 部分规约

### 状态码

-   200：表示成功
-   250：表示有重复项（实际不允许有重复）
-   400：表示失败
-   登录界面：200——老板， 201——库存管理员， 202——售货员

1. 货品资料维护、客户资料维护、员工资料维护三个页面中，实现对每个表项的编辑功能时，前端向后端返回数据时，返回内容为原来表项的Id和新修改的表项内容
    例：原来为 ID：1， name：刀， 价格：199 修改为 name：笔， 价格200；发送{1, 笔， 200}

### 订单状态
0：未审核   1：审核未通过   2：待付款   3：已付款  4:退货

### 支出表

|           | amount>0 | amount<0 |
| --------- | -------- | -------- |
| tag=true  | 批发     | 进货     |
| tag=false | 零售     | 退货     |

### 利润计算

只有收到的钱才算利润，如果有钱没收到，就不算本月的。