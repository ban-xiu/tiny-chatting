# 黑名单

有撤回消息和拉黑两种体现

主要有四张表：黑名单表和用户表 -> 用户角色表 -> 角色权限表，一个用户可以对应多个角色，撤回消息和拉黑都需要对应的权限

实现细节：
1. 在用户认证的时候，需要给前端返回用户所属的角色
2. 调用拉黑接口，首先需要校验是否拥有权限
```java
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private UserCache userCache;

    @Override
    public boolean hasPower(Long uid, RoleEnum roleEnum) {
        // 查寻本地缓存
        Set<Long> roleSet = userCache.getRoleSet(uid);
        return isAdmin(roleSet) || roleSet.contains(roleEnum.getId());
    }

    private boolean isAdmin(Set<Long> roleSet) {
        return Objects.requireNonNull(roleSet).contains(RoleEnum.ADMIN.getId());
    }
}
```
3. 将用户拉黑，即将信息写入黑名单库
4. 发送拉黑事件，关心该事件的消费者做了三件事 
- 清除相关的缓存，让拉黑立马生效
- 对该用户所有的消息都进行删除，以后不会出现在消息列表
- 给所有在线用户推送用户拉黑事件
5. 前端接收到拉黑通知，会把该 uid 的所有消息从前端缓存中删除
6. 通过拦截器，对每一个接口的请求都进行黑名单的校验，被拉黑用户以后不能访问

