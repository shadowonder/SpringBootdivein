package com.study.demo.dao;


import com.study.demo.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * SpringData jpa的dao层接口规范
 *      JpaRepository<操作的实体类类型， 实体类中逐渐属性的类型>
 *          * 封装了基本的crud操作
 *      JpaSpecificationExecutor<操作的实体类类型>
 *          * 封装了复杂操作 例如：分页
 */
public interface CustomerDao
        extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {

    /**
     * 基本的增删，save方法是默认的，没必要覆盖
     * @param customer
     * @return
     */
    //保存实体,注意这里的save不仅仅是保存，也是更新。主要看customer有没有id
    Customer save(Customer customer);

    //根据id删除客户信息
    Customer deleteByCustId(Long custId);

    /******************************************************************************************
     * 方法名查询
     * 方法名称约定：
     *      findby： 查询
     *          对象中的属性(首字母大写)：查询条件
     * 在springdataJpa运行阶段spring会对方法名进行解析：
     *      find By from （实体类） where (属性名，属性)
     * 使用：
     *      findBy + 属性名
     *      findBy + 属性名 + 查询方式（Like|Isnull|Contains）
     *      findBy + 属性名 + 查询方式 + 合并语句连接符(and|or) + 另一个查询条件
     * @param custName
     * @return
     */
    //根据客户名称查询客户
    List<Customer> findByCustName(String custName);
    Customer findByCustId(Long custId);

    //特殊条件查询 List<Customer> list = customerDao.findAllByCustAddressContains("test");
    List<Customer> findAllByCustAddressContains(String custAddress);

    //符合查询
    //List<Customer> customer = customerDao.findByCustAddressLikeAndCustIndustryNotNullAndCustNameContains("%test%","test");
    List<Customer> findByCustAddressLikeAndCustIndustryNotNullAndCustNameContains(String custAddress, String custName);


    /******************************************************************************************
     * JPQL语句
     */
    @Query("from Customer where custName = :custName")
    List<Customer> queryCustomerNameFromDatabaseUsingJpql1(String custName);
    //指定参数名并插入进去
    @Query("from Customer where custName = :custName")
    List<Customer> queryCustomerNameFromDatabaseUsingJpql2(@Param("custName") String customer_name);

    //默认情况下会把参数直接赋值给query
    @Query("from Customer where custName = :custName and custLevel = :custLevel")
    List<Customer> queryCustomerUsingNameAndLevel1(String custName, String custLevel);

    @Query("from Customer where custName = :custName and custLevel = :custLevel")
    List<Customer> queryCustomerUsingNameAndLevel2(@Param("custLevel") String custLevel,
                                                   @Param("custName") String custName);

    // 如果参数顺序不正确，也会按照参数的名称进行匹配
    @Query("update Customer set custName = :custName where custId = :custId")
    @Modifying  //表示当前执行的方法是一个更新方法或者是插入方法
    void queryUpdateCustomer(long custId , String custName);


    /***************************************************************************************
     * SQL语句查询
     *      @Query()->
     *          value: jpql语句查询
     *          nativeQuery： false | true   使用sql语句查询
     *
     *     @Test
     *     public void testSQL1(){
     *         List<Object []> customers = customerDao.queryCustomerInfoUsingSQL("test%"); //可以使用通配符
     *         for(Object [] customer : customers){
     *             for(Object o : customer){
     *                 System.out.print(o + " ");
     *             }
     *             System.out.println();
     *         }
     *     }
     */
    // 如果参数顺序不正确，也会按照参数的名称进行匹配
    @Query(value = "select * from cst_customer where cust_source = :custSource" , nativeQuery = true)
    List<Object []> queryCustomerInfoUsingSQL(String custSource);

    @Query(value = "select * from cst_customer where cust_source like :custSource" , nativeQuery = true)
    List<Object []> queryCustomerInfoUsingSQLLike(String custSource);

}
