package com.tinychating.common.user.dao;

import com.tinychating.common.user.domain.entity.Role;
import com.tinychating.common.user.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {

}
