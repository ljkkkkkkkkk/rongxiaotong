package com.lk77.server.controller;

import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import com.lk77.server.domain.entity.Discuss;
import com.lk77.server.domain.entity.Knowledge;
import com.lk77.server.service.DiscussService;
import com.lk77.server.service.KnowledgeService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "知识模块接口")
@RestController
@RequestMapping("/knowledge")
@CrossOrigin
public class KnowledgeController {
    private final KnowledgeService knowledgeService;
    private final DiscussService discussService;

    public KnowledgeController(KnowledgeService knowledgeService, DiscussService discussService) {
        this.knowledgeService = knowledgeService;
        this.discussService = discussService;
    }

    @ApiOperation("分页查询所有知识")
    @GetMapping("/{pageNum}")
    public HttpResult<PageInfo<Knowledge>> findPage(@PathVariable Integer pageNum) {
        PageInfo<Knowledge> knowledgePageInfo = knowledgeService.findPage(pageNum);
        return new HttpResult<>(true, Constant.OK, "查询成功", knowledgePageInfo);
    }

    @ApiOperation("添加知识")
    @PostMapping()
    @PreAuthorize("hasAuthority('expert')")
    public HttpResult add(@RequestBody Knowledge knowledge) {
        knowledgeService.add(knowledge);
        return new HttpResult(true, Constant.OK, "添加成功");
    }

    @ApiOperation("根据id修改知识")
    @PutMapping("/{id}")
    public HttpResult update(@RequestBody Knowledge knowledge,
                             @PathVariable("id") Integer id) {
        knowledgeService.update(knowledge, id);
        return new HttpResult(true, Constant.OK, "修改成功");
    }

    @ApiOperation("根据id删除知识")
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") Integer id) {
        knowledgeService.delete(id);
        return new HttpResult(true, Constant.OK, "删除成功");
    }

    @ApiOperation("根据登录用户查询知识")
    @GetMapping("/selectByUsername")
    public HttpResult selectByUsername() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        List<Knowledge> knowledges = knowledgeService.selectByUsername(name);
        return new HttpResult<>(true, Constant.OK, "查询成功", knowledges);
    }

    @ApiOperation("根据id查询知识")
    @GetMapping("/selectById/{id}")
    public HttpResult selectById(@PathVariable("id") Integer id) {
        Knowledge knowledge = knowledgeService.selectById(id);
        return new HttpResult<>(true, Constant.OK, "查询成功", knowledge);
    }

    @ApiOperation("查询讨论消息")
    @GetMapping("/selectByKnowledge/{id}")
    public HttpResult selectByKnowledge(@PathVariable("id") Integer id) {
        List<Discuss> discuss = discussService.selectByKnowledgeId(id);
        return new HttpResult<>(true, Constant.OK, "查询成功", discuss);
    }

    @ApiOperation("添加讨论消息")
    @PostMapping("/addByKnowledge/{id}/{content}")
    public HttpResult addByKnowledge(@PathVariable("id") Integer id, @PathVariable("content") String content) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();

        Discuss discuss = new Discuss();
        discuss.setKnowledgeId(id);
        discuss.setOwnName(name);
        discuss.setCreateTime(new Date());
        discuss.setContent(content);

        discussService.add(discuss);
        return new HttpResult<>(true, Constant.OK, "添加成功", discuss);
    }

    @ApiOperation("分页条件查询所有知识")
    @GetMapping("/{keys}/{pageNum}")
    public HttpResult<PageInfo<Knowledge>> findPageByKeys(@PathVariable String keys, @PathVariable Integer pageNum) {
        PageInfo<Knowledge> knowledgePageInfo = knowledgeService.findPageByKeys(keys, pageNum);
        return new HttpResult<>(true, Constant.OK, "查询成功", knowledgePageInfo);
    }
}
