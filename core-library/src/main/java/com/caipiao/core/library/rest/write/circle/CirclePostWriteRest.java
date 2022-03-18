package com.caipiao.core.library.rest.write.circle;

import java.util.List;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.circle.MsgNumVO;
import com.caipiao.core.library.vo.circle.PostSettingDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface CirclePostWriteRest {
    /**
     * 根据id删除帖子
     * @param id
     * @return
     */
    @PostMapping("/circle/deletePostById.json")
    boolean deletePostById(@RequestParam("id") Integer id);

    /**
     * 根据id删除帖子回复
     * @param id
     * @return
     */
    @PostMapping("/circle/deletePostComments.json")
    boolean deletePostCommentsById(@RequestParam("id") Integer id);

    /**
     *  发帖
     */
    @PostMapping("/circle/createPost.json")
    ResultInfo<Boolean> createPost(@RequestParam("uid") String uid, @RequestParam("content") String content, @RequestParam("image") String image);

    /**
     *  帖子点赞或者删除
     */
    @PostMapping("/circle/praiseOrDelPost.json")
    ResultInfo<Boolean> praiseOrDelPost(@RequestParam("meId") Integer meId, @RequestParam(name = "postId") Integer postId, @RequestParam(name = "type") Integer type);

    /**
     * 帖子评论
     */
    @PostMapping("/circle/commentsPost.json")
    ResultInfo<Boolean> commentsPost(@RequestParam("meId") Integer meId, @RequestParam(name = "postId") Integer postId, @RequestParam(name = "content") String content, @RequestParam(name = "commentsId") Integer commentsId);

    /**
     * 关注/取消关注
     */
    @PostMapping("/circle/focusOrCancle.json")
    ResultInfo<Boolean> focusOrCancle(@RequestParam("meId") Integer meId, @RequestParam(name = "otherId") Integer otherId, @RequestParam(name = "type") Integer type);

    /**
     *  发帖
     */
    @PostMapping("/circle/reportPost.json")
    ResultInfo<Boolean> reportPost(@RequestParam("uid") String uid, @RequestParam("comment") String comment, @RequestParam("proofImage") String proofImage, @RequestParam("typeId") Integer typeId, @RequestParam(name = "otherId") Integer otherId);

    /**
     * 添加或修改帖子举报类型
     */
    @PostMapping("/circle/addOrUpdateReportType.json")
    void addOrUpdateReportType(@RequestParam("id") Integer id, @RequestParam("type") String type, @RequestParam("sort") Integer sort, @RequestParam("admin") String admin);

    /**
     * 删除帖子举报类型
     */
    @PostMapping("/circle/deleteReportTypeById.json")
    void deleteReportTypeById(@RequestParam("id") Integer id);

    /**
     * 屏蔽用户
     */
    @PostMapping("/circle/shieldUser.json")
    ResultInfo<Boolean> shieldUser(@RequestParam("meId") Integer meId, @RequestParam("otherId") Integer otherId);

    /**
     * 被屏蔽用户列表打开和关闭
     */
    @PostMapping("/circle/shieldUserListSetting.json")
    ResultInfo<Boolean> shieldUserListSetting(@RequestParam("meId") Integer meId, @RequestParam("shieldId") Integer shieldId, @RequestParam("onOff") Integer onOff);

    /**
     * 被屏蔽用户列表打开和关闭
     */
    @PostMapping("/circle/postSetting.json")
    ResultInfo postSetting(@RequestParam("meId") Integer meId, @RequestParam("settingId") Integer settingId, @RequestParam("onOff") Integer onOff);

    @RequestMapping("/circle/getFocusMsgNumOrReplyNum.json")
    ResultInfo<MsgNumVO> getFocusMsgNumOrReplyNum(@RequestParam("meId") Integer meId, @RequestParam(name = "type") Integer type);
   
    @PostMapping("/circle/getPostSettingList.json")
	ResultInfo<List<PostSettingDTO>> getPostSettingList(@RequestParam("meId") Integer meId,
                                                        @RequestParam("classify") String classify);
}
