package com.tinychating.common.user.service;

import com.tinychating.common.user.domain.vo.request.oss.UploadUrlReq;
import com.tinychating.oss.domain.OssResp;

/**
 * <p>
 * oss 服务类
 * </p>
 */
public interface OssService {

    /**
     * 获取临时的上传链接
     */
    OssResp getUploadUrl(Long uid, UploadUrlReq req);
}
