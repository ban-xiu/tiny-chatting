package com.tinychating.common.user.service.impl;

import com.tinychating.common.common.utils.AssertUtil;
import com.tinychating.common.user.domain.enums.OssSceneEnum;
import com.tinychating.common.user.domain.vo.request.oss.UploadUrlReq;
import com.tinychating.common.user.service.OssService;
import com.tinychating.oss.MinIOTemplate;
import com.tinychating.oss.domain.OssReq;
import com.tinychating.oss.domain.OssResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OssServiceImpl implements OssService {
    @Autowired
    private MinIOTemplate minIOTemplate;

    @Override
    public OssResp getUploadUrl(Long uid, UploadUrlReq req) {
        OssSceneEnum sceneEnum = OssSceneEnum.of(req.getScene());
        AssertUtil.isNotEmpty(sceneEnum, "场景有误");
        OssReq ossReq = OssReq.builder()
                .fileName(req.getFileName())
                .filePath(sceneEnum.getPath())
                .uid(uid)
                .build();
        return minIOTemplate.getPreSignedObjectUrl(ossReq);
    }
}
