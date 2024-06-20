package com.tinychating.common.user.controller;

import com.tinychating.common.common.domain.vo.request.IdReqVO;
import com.tinychating.common.common.domain.vo.response.ApiResult;
import com.tinychating.common.common.domain.vo.response.IdRespVO;
import com.tinychating.common.common.utils.RequestHolder;
import com.tinychating.common.user.domain.vo.request.user.UserEmojiReq;
import com.tinychating.common.user.domain.vo.response.user.UserEmojiResp;
import com.tinychating.common.user.service.UserEmojiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户表情包
 */
@RestController
@RequestMapping("/capi/user/emoji")
@Api(tags = "用户表情包管理相关接口")
public class UserEmojiController {

    /**
     * 用户表情包 Service
     */
    @Resource
    private UserEmojiService emojiService;


    /**
     * 表情包列表
     *
     * @return 表情包列表
     **/
    @GetMapping("/list")
    @ApiOperation("表情包列表")
    public ApiResult<List<UserEmojiResp>> getEmojisPage() {
        return ApiResult.success(emojiService.list(RequestHolder.get().getUid()));
    }


    /**
     * 新增表情包
     *
     * @param req 用户表情包
     * @return 表情包
     **/
    @PostMapping()
    @ApiOperation("新增表情包")
    public ApiResult<IdRespVO> insertEmojis(@Valid @RequestBody UserEmojiReq req) {
        return emojiService.insert(req, RequestHolder.get().getUid());
    }

    /**
     * 删除表情包
     *
     * @return 删除结果
     **/
    @DeleteMapping()
    @ApiOperation("删除表情包")
    public ApiResult<Void> deleteEmojis(@Valid @RequestBody IdReqVO reqVO) {
        emojiService.remove(reqVO.getId(), RequestHolder.get().getUid());
        return ApiResult.success();
    }
}
