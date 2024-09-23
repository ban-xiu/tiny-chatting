package com.tinychating.common.common.algorithm.sensitiveWord;

import java.util.List;

/**
 * 敏感词
 */
public interface IWordFactory {
    /**
     * 返回敏感词数据源
     *
     * @return 结果
     */
    List<String> getWordList();
}
