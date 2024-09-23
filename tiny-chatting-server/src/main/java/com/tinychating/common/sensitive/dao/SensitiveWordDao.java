package com.tinychating.common.sensitive.dao;

import com.tinychating.common.sensitive.domain.SensitiveWord;
import com.tinychating.common.sensitive.mapper.SensitiveWordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 敏感词DAO
 */
@Service
public class SensitiveWordDao extends ServiceImpl<SensitiveWordMapper, SensitiveWord> {

}
