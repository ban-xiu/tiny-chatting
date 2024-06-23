# 分布式锁工具

基于 redisson + 注解 + aop 封装实现

LockService 实现了基本的分布式锁的功能 
```java
@Service
@Slf4j
public class LockService {

    @Autowired
    private RedissonClient redissonClient;

    public <T> T executeWithLock(String key, int waitTime, TimeUnit unit, SupplierThrow<T> supplier) throws Throwable {
        RLock lock = redissonClient.getLock(key);
        boolean lockSuccess = lock.tryLock(waitTime, unit);
        if (!lockSuccess) {
            throw new BusinessException(CommonErrorEnum.LOCK_LIMIT);
        }
        try {
            return supplier.get();// 执行锁内的代码逻辑
        } finally {
            lock.unlock();
        }
    }
}
```

引入 @RedissonLock 注解配合 aop 作用
```java
/**
 * 分布式锁注解
 */
@Retention(RetentionPolicy.RUNTIME)// 运行时生效
@Target(ElementType.METHOD)// 作用在方法上
public @interface RedissonLock {
    /**
     * key的前缀,默认取方法全限定名，除非我们在不同方法上对同一个资源做分布式锁，就自己指定
     *
     * @return key的前缀
     */
    String prefixKey() default "";

    /**
     * springEl 表达式
     *
     * @return 表达式
     */
    String key();

    /**
     * 等待锁的时间，默认-1，不等待直接失败,redisson默认也是-1
     *
     * @return 单位秒
     */
    int waitTime() default -1;

    /**
     * 等待锁的时间单位，默认毫秒
     *
     * @return 单位
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

}
```

aop 具体实现方法
```java
@Slf4j
@Aspect
@Component
@Order(0)// 确保比事务注解先执行，分布式锁在事务外
public class RedissonLockAspect {
    @Autowired
    private LockService lockService;

    @Around("@annotation(annotation.common.common.com.tinychating.RedissonLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);
        String prefix = StrUtil.isBlank(redissonLock.prefixKey()) ? SpElUtils.getMethodKey(method) : redissonLock.prefixKey();// 默认方法限定名 + 注解排名（可能多个）
        String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockService.executeWithLockThrows(prefix + ":" + key, redissonLock.waitTime(), redissonLock.unit(), joinPoint::proceed);
    }
}
```