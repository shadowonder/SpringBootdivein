package com.study.demo;

import com.study.demo.dao.CustomerDao;
import com.study.demo.dao.CustomerSpecDao;
import com.study.demo.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/**
 * 动态查询
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootSpecTest {

    @Autowired
    private CustomerSpecDao customerSpecDao;

    /**
     * 查询单个对象
     *      1. 实现接口，lambda表达式
     *      2. 借助参数：
     *          root： 获取需要查询的对象属性
     *          criteriabuilder： 构造查询条件，内部不封装了很多查询条件 模糊/精准匹配等
     * 查询条件
     *      1. 查询方式
     *          cb对象
     *      2. 比较属性
     *          root对象
     */
    @Test
    public void specTest() {
        Customer customer = customerSpecDao.findOne((root,criteriaQuery,criteriaBuilder) -> {
            //获取比较属性
            Path<String> custName = root.get("custName");//这里是属性名，而不是数据库的字段名称

            //构造条件查询,equal是精准匹配（属性名，属性的取值）
            Predicate predicate = criteriaBuilder.equal(custName,"test");

            return predicate;
        }).get();
        System.out.println(customer);
    }

    /**
     * 多条件查询
     * root 获取属性
     * cb 构造查询
     */
    @Test
    public void specMultiQueryTest() {
        List<Customer> customers = customerSpecDao.findAll((root, criteriaQuery, criteriaBuilder) -> {
            //获取比较属性
            Path<String> custName = root.get("custName");//这里是属性名，而不是数据库的字段名称
            Path<String> custIndustry = root.get("custIndustry");

            //构造条件查询,equal是精准匹配（属性名，属性的取值）
            Predicate predicateName = criteriaBuilder.like(custName,"%test%");
            Predicate predicateIndustry = criteriaBuilder.like(custIndustry,"%test%");

            //组合查询
            Predicate returnPred = criteriaBuilder.and(predicateName,predicateIndustry);

            return returnPred;
        });
        System.out.println(customers);
    }

    /**
     * 排序/分页, 排序lambda bug 了
     */
    @Test
    public void specTestOrder() {
//        List<Customer> customer = customerSpecDao.findAll((root,criteriaQuery,criteriaBuilder) -> {
//            Path<String> custName = root.get("custName");
//            Predicate predicate = criteriaBuilder.like(custName,"%test%");
//            return predicate;
//        } , Sort.Direction.DESC);
//        System.out.println(customer);

        //分页
        Pageable pageable = PageRequest.of(0,2);
        Page<Customer> customers = customerSpecDao.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Path<String> custName = root.get("custName");//这里是属性名，而不是数据库的字段名称
            Predicate predicateName = criteriaBuilder.like(custName,"%test%");
            return predicateName;
        }, pageable);
        System.out.println(customers.getContent());
    }

    @Test
    public void specTestWithOldFaction() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        Optional<Customer> customer = customerSpecDao.findOne(spec);
        System.out.println(customer.get());
    }
}
