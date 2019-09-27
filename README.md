# DubboTestingFeasible

写了一个dubbo接口，不知道咋测试？对于新手来说，肯定是搞一个controller，然后写一个http接口取调用。
但是这样做有什么缺点呢？ 假设工程不支持Spring MVC，那些controller测试肯定行不通。另外就是会引入一些
无效代码，如果上线前忘记删除，就是一堆垃圾代码。 