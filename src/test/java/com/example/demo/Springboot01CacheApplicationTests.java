package com.example.demo;

import com.example.demo.entity.UserEntity;

import jdk.nashorn.internal.runtime.arrays.IteratorAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class Springboot01CacheApplicationTests {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    RedisTemplate redisTemplate;  //k-v都是对象的

    /**
     * Redis常见的五大数据类型
     * String（字符串）、List（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
     * stringRedisTemplate.opsForValue()[String（字符串）]
     * stringRedisTemplate.opsForList()[List（列表）]
     * stringRedisTemplate.opsForSet()[Set（集合）]
     * stringRedisTemplate.opsForHash()[Hash（散列）]
     * stringRedisTemplate.opsForZSet()[ZSet（有序集合）]
     */
    @Test
    public void contextLoads() {
		/* 存hash
		redisTemplate.opsForHash().put("testHash","wangyueyu",new UserEntity("wangyueyu"));
		redisTemplate.opsForHash().put("testHash","zhoubing",new UserEntity("zhoubing"));
		redisTemplate.opsForHash().put("testHash2","zhoubing",new UserEntity("zhoubing"));
		redisTemplate.opsForHash().put("testHash2","wangyueyu",new UserEntity("wangyueyu"));
		redisTemplate.opsForHash().put("testHash2","hujun",new UserEntity("hujun"));
		redisTemplate.opsForHash().put("testHash1","zengpeng",new UserEntity("zengpeng"));*/
//		System.out.println(redisTemplate.boundHashOps("testHash2").size().intValue());
//		redisTemplate.opsForZSet().add("testZset",100,100);
//		Set zset = redisTemplate.boundZSetOps("testZset").rangeByScore(0, 1000);
//		System.out.println(zset);
//		BoundHashOperations ops = redisTemplate.boundHashOps("hmall:CaChe:{cart}Cart");
//		List<Object> list = new ArrayList<>();
//		list.add("2:key2");
//		System.out.println(ops.multiGet(list));
//		HashMap<String, String> map = new HashMap<>();
//		map.put("wangyueyu","wangyueyu");
//		map.put("zhoubing","wangyueyu");
//
//		map.forEach((key,value)-> System.out.println(key+value));
        Collections.singleton(null);
    }

    @Test
    public void test1() {
//		System.out.println(redisTemplate.boundHashOps("hashtest").values());

//		redisTemplate.opsForHash().put(" hmall:favorite:{rule}Favo","name","wangyueyu");
//		redisTemplate.opsForZSet().
        HashMap<String, UserEntity> map = new HashMap<>();
        map.put("wangyueyu", new UserEntity("wangyueyu"));
        map.put("zhoubing", new UserEntity("zhoubing"));
        System.out.println(redisTemplate.opsForZSet().add("hmall:Cache:{cart}Cart:siteId:300", map, 1000));

    }

    @Test
    public void test2() {
        ArrayList<Object> list = new ArrayList<>();
        list.add("zsetTest");
        list.add("zsetTest2");
        list.add("zsetTest3");

        redisTemplate.opsForZSet().unionAndStore("zsetTest", list, "testUnionAndStore2");
    }

    @Test
    public void test3() {

    }

    @Test
    public void test4() {
        ArrayList<Object> list = new ArrayList<>();
        list.add("hmall:Cache:{cart}Cart:siteId:300");
        list.add("hmall:Cache:{cart}Cart:distribution:3000");
        list.add("hmall:Cache:{cart}Cart:skuCode:10008");
        list.add("hmall:Cache:{cart}Cart:userId:1233");
        redisTemplate.opsForZSet().unionAndStore("hmall:Cache:{cart}Cart:siteId:300", list, "testUnionAndStorez");
    }

    @Test
    public void test5() {
//		System.out.println(redisTemplate.boundHashOps("hmall:CaChe:{cart}Cart:1").get("key1"));
        ArrayList<Object> list = new ArrayList<>();
        list.add("1");
        list.add("key2");
        System.out.println(redisTemplate.opsForHash().multiGet("hmall:CaChe:{cart}Cart:2", list));
//		ArrayList<Object> list = new ArrayList<>();
//		list.add("hmall:CaChe:{cart}Cart:userId:2");
//
//		System.out.println(redisTemplate.opsForZSet().unionAndStore("hmall:CaChe:{cart}Cart:userId:2", list, "wangyueyuTeset"));
    }

    public void test111() {
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        list.add("number1");
        list.add("number2");
        map.put("string", "testString");
        map.put("list", list);
        List list1 = (List) map.get("list");
        System.out.println(list1);

    }

    public String cityGeoKey = "cityGeoKey";

    @Test
    public void testAdd() {
        Long addedNum = redisTemplate.opsForGeo().add(cityGeoKey, new Point(113.558143,34.835406), "bikeId:123");
        System.out.println(addedNum);
    }
    @Test
    public void testGeoGet() {
        List<Point> points = redisTemplate.opsForGeo().position(cityGeoKey, "北京", "上海");
        System.out.println(points);
    }

    @Test
    public void testDist() {
        Distance distance = redisTemplate.opsForGeo().distance(cityGeoKey, "工大门口", "进门70米", RedisGeoCommands.DistanceUnit.METERS);
        System.out.println(distance);
    }

    @Test
    public void testNearByXY() {
        //longitude,latitude
        Circle circle = new Circle(116.405285, 39.904989, Metrics.KILOMETERS.getMultiplier());
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().sortAscending();
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo().radius(cityGeoKey, circle,args);
        results.forEach(System.out::println);
    }

    @Test
    public void testNearByPlace() {
        Distance distance = new Distance(5, Metrics.KILOMETERS);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo().radius(cityGeoKey, "工大门口7", distance, args);
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
            System.out.println(result.getContent());
            System.out.println(result.getDistance());
            Distance distance1 = result.getDistance();
            double value = distance1.getValue();
            System.out.println(value);
        }




    }

    @Test
    public void testGeoHash() {
        List<String> results = redisTemplate.opsForGeo().hash(cityGeoKey, "北京", "上海", "深圳");
        results.forEach(System.out::println);
        for(int i=0;i<100;i++){
            System.out.println(i);
            System.out.println(i);
            System.out.println(i);
            System.out.println(i);
            System.out.println(i);



        }
    }
}
