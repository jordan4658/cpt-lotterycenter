package com.caipiao.core.library.rest.write.lotterymanage;

import com.caipiao.core.library.dto.lotterymanage.LhcPhotoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface LhcPhotoWriteRest {

    @PostMapping("/lhcPhoto/addOrUpdatePhoto.json")
    boolean addOrUpdatePhoto(@RequestBody LhcPhotoDTO lhcPhotoDTO);

    @PostMapping("/lhcPhoto/deletePhotoById.json")
    boolean deletePhotoById(@RequestParam("photoId") Integer photoId);

    @PostMapping("/lhcPhoto/deletePhotoCommentById.json")
    boolean deletePhotoCommentById(@RequestParam("photoId") Integer photoId);

    @PostMapping("/lhcPhoto/deletePhotoCategoryById.json")
    boolean deletePhotoCategoryById(@RequestParam("categoryId") Integer categoryId);

    @PostMapping("/lhcPhoto/addOrUpdatePhotoCategory.json")
    boolean addOrUpdatePhotoCategory(@RequestParam("id") Integer id, @RequestParam("name") String name, @RequestParam("parentId") Integer parentId, @RequestParam("sort") Integer sort);

    @PostMapping("/lhcPhoto/deletePhotoByIssue.json")
	boolean deletePhotoByIssue(@RequestParam("issue") String issue);
    
    
    @PostMapping("/lhcPhoto/addphptoCommentsByAPP.json")
   	boolean addphptoCommentsByAPP(@RequestParam("content") String content, @RequestParam("id") Integer id, @RequestParam("uid") String uid);
}
