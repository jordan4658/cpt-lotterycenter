package com.caipiao.core.library.rest.read.circle;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.circle.*;
import com.mapper.domain.CirclePost;
import com.mapper.domain.CirclePostComments;
import com.mapper.domain.CirclePostReport;
import com.mapper.domain.CirclePostReportType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface CirclePostRest {

    @RequestMapping("/circle/pagePost.json")
    PageResult<List<CirclePost>> pagePost(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("account") String account);

    @RequestMapping("/circle/pagePostReport.json")
    PageResult<List<CirclePostReport>> pagePostReport(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("fromAccount") String fromAccount, @RequestParam("typeId") Integer typeId);

    @RequestMapping("/circle/pagePostReportType.json")
    PageResult<List<CirclePostReportType>> pagePostReportType(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping("/circle/calcPost.json")
    Map<String, Integer> calcPost();

    @RequestMapping("/circle/pagePostComments.json")
    PageResult<List<CirclePostComments>> pagePostComments(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam(name = "postId") Integer postId);

    @RequestMapping("/circle/listPostForApp.json")
    ResultInfo<List<ListPostVO>> listPostForApp(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam(name = "type") Integer type, @RequestParam(name = "meId") Integer meId, @RequestParam(name = "otherId") Integer otherId);

    @RequestMapping("/circle/getPostCommentsMore.json")
    ResultInfo<List<PostCommentsVO>> getPostCommentsMore(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam(name = "type") Integer type, @RequestParam(name = "postId") Integer postId, @RequestParam(name = "meId") Integer meId);

    @RequestMapping("/circle/getFansAndFocusNumber.json")
    ResultInfo<FansAndFocusNumberVO> getFansAndFocusNumber(@RequestParam("meId") Integer meId, @RequestParam("otherId") Integer otherId);

    @RequestMapping("/circle/getFansOrFocus.json")
    ResultInfo<List<PostMemberVO>> getFansOrFocus(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("meId") Integer meId, @RequestParam(name = "type") Integer type);

    @RequestMapping("/circle/getPostReportType.json")
    ResultInfo<List<PostReportTypeVO>> getPostReportType();

    @RequestMapping("/circle/toUpdateReportType.json")
    CirclePostReportType findReportTypeById(@RequestParam("groupId") Integer groupId);

    /**
     * 被屏蔽用户列表
     */
    @PostMapping("/circle/getShieldUserList.json")
    ResultInfo<List<ShieldUserListVO>> getShieldUserList(@RequestParam("meId") Integer meId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 圈子个人设置列表
     */
    @PostMapping("/circle/getPostSettingList.json")
    ResultInfo<List<PostSettingDTO>> getPostSettingList(@RequestParam("meId") Integer meId, @RequestParam("classify") String classify);
}
