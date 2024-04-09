package com.lk77.server.controller;

import com.github.pagehelper.PageInfo;
import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import com.lk77.server.domain.entity.Expert;
import com.lk77.server.domain.entity.Question;
import com.lk77.server.service.ExpertService;
import com.lk77.server.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "问题模块接口")
@RestController
@RequestMapping("/question")
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;
    private final ExpertService expertService;

    public QuestionController(QuestionService questionService, ExpertService expertService) {
        this.questionService = questionService;
        this.expertService = expertService;
    }

    @ApiOperation("根据ID查询询问情况")
    @GetMapping("/selectId/{id}")
    public HttpResult selectById(@PathVariable("id") Integer id) {
        Question question = questionService.selectById(id);
        return new HttpResult(true, Constant.OK, "查询成功", question);
    }

    @ApiOperation("添加询问情报")
    @PostMapping("/add")
    public HttpResult add(@RequestBody Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuffer = new StringBuilder();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            return new HttpResult<String>(false, Constant.ERROR, "添加失败", s);
        }
        questionService.insert(question);
        return new HttpResult(true, Constant.OK, "添加成功");
    }

    @ApiOperation("根据id修改询问情报")
    @PutMapping("/update")
    public HttpResult update(@RequestBody Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuffer = new StringBuilder();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            return new HttpResult<String>(false, Constant.ERROR, "修改失败", s);
        }
        questionService.updateById(question);
        return new HttpResult(true, Constant.OK, "修改成功");
    }

    @ApiOperation("根据id删除询问情报")
    @DeleteMapping("/delete/{id}")
    public HttpResult delete(@PathVariable("id") Integer id) {
        questionService.delete(id);
        return new HttpResult(true, Constant.OK, "删除成功");
    }

    @ApiOperation("根据用户查询询问情况")
    @GetMapping("/selectByKind/{kind}")
    public HttpResult selectByKind(@PathVariable("kind") String kind) {
        List<Question> questions = questionService.selectByQuestion(kind);
        return new HttpResult(true, Constant.OK, "查询成功", questions);
    }

    @ApiOperation("分页条件查询所有问答情况")
    @GetMapping("/findPageQues/{keys}/{pageNum}")
    public HttpResult<PageInfo<Question>> findPageQues(@PathVariable("keys") String keys, @PathVariable Integer pageNum) {
        PageInfo<Question> questionPageInfo = questionService.selectByKeys(keys, pageNum);
        return new HttpResult(true, Constant.OK, "查询成功", questionPageInfo);
    }

    @ApiOperation("分页查询所有问答情况")
    @GetMapping("/findAllQues/{pageNum}")
    public HttpResult<PageInfo<Question>> findAllQues(@PathVariable Integer pageNum) {
        PageInfo<Question> questionPageInfo = questionService.selectByKeys(null, pageNum);
        return new HttpResult(true, Constant.OK, "查询成功", questionPageInfo);
    }

    @ApiOperation("分页查询所有专家")
    @GetMapping("/findExpert/{pageNum}")
    public HttpResult<PageInfo<Expert>> findPage(@PathVariable Integer pageNum) {
        PageInfo<Expert> expertPageInfo = expertService.findPage(pageNum);
        return new HttpResult<PageInfo<Expert>>(true, Constant.OK, "查询成功", expertPageInfo);
    }

    @ApiOperation("分页条件查询专家")
    @GetMapping("/findExpertByKeys/{keys}/{pageNum}")
    public HttpResult<PageInfo<Expert>> findExpertByKeys(@PathVariable("keys") String keys, @PathVariable Integer pageNum) {
        PageInfo<Expert> expertPageInfo = expertService.findPageByKeys(keys, pageNum);
        return new HttpResult<PageInfo<Expert>>(true, Constant.OK, "查询成功", expertPageInfo);
    }
}
