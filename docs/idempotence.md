# 物品发放幂等性

解决了物品超发和错发

主要流程：
1. 利用 redis 加分布式锁
2. 根据幂等键去查库，并做一些检查
3. 若不存在则发放物品，即将信息写入物品库
4. 若存在则返回结果

```java
@Service
public class UserBackpackServiceImpl implements IUserBackpackService {

    // 本地缓存
    @Autowired
    private ItemCache itemCache;
    
    @Override
    public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        // 组装幂等号：物品 id + 物品类型 + 业务 id
        String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
        userBackpackService.doAcquireItem(uid, itemId, idempotent);
    }

    @RedissonLock(key = "#idempotent", waitTime = 5000)// 分布式锁：相同幂等如果同时发放物品，需要排队等上一个执行完，取出之前数据返回
    public void doAcquireItem(Long uid, Long itemId, String idempotent) {
        UserBackpack userBackpack = userBackpackDao.getByIdp(idempotent);
        // 幂等检查
        if (Objects.nonNull(userBackpack)) {
            return;
        }
        // 业务检查
        ItemConfig itemConfig = itemCache.getById(itemId);
        if (ItemTypeEnum.BADGE.getType().equals(itemConfig.getType())) {// 类型做唯一性检查
            Integer countByValidItemId = userBackpackDao.getCountByValidItemId(uid, itemId);
            if (countByValidItemId > 0) {// 已经有了不发
                return;
            }
        }
        // 发物品
        UserBackpack insert = UserBackpack.builder()
                .uid(uid)
                .itemId(itemId)
                .status(YesOrNoEnum.NO.getStatus())
                .idempotent(idempotent)
                .build();
        userBackpackDao.save(insert);
        // 用户收到物品的事件
        applicationEventPublisher.publishEvent(new ItemReceiveEvent(this, insert));
    }
    
}
```