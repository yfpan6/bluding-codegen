/*
 * Copyright 2016-2017 the original author or authors.
 */
package ${controllerPkg};

import cn.udesk.insight.core.entity.${modelConfiguration.entityName};
import cn.udesk.insight.core.service.${modelConfiguration.entityName}Service;
import cn.udesk.sdk.common.ParamChecker;
import cn.udesk.sdk.common.bean.Result;
import cn.udesk.sdk.common.consts.CommonEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ${author}
 * @date ${today}
 */
@Controller
@RequestMapping(value="${modelConfiguration.requestMapping}", method={RequestMethod.GET, RequestMethod.POST})
public class ${modelConfiguration.entityName}Controller {

    private static final Logger logger = LoggerFactory.getLogger(${modelConfiguration.entityName}Controller.class);

    @Resource
    private ${modelConfiguration.entityName}Service ${modelConfiguration.camelClassName}Service;

    @RequestMapping(value = "/views/main", method = RequestMethod.GET)
    public String showMainPage(ModelMap modelMap) {
        return "${modelConfiguration.pageLocation}/main";
    }

    @RequestMapping(value = "/views/form", method = RequestMethod.GET)
    public String showFormView(Integer id, ModelMap modelMap) {
        if (id == null || id < 1) {
            modelMap.put("record", new ${modelConfiguration.entityName}());
            return "${modelConfiguration.pageLocation}/formAdd";
        }
        modelMap.put("record", ${modelConfiguration.camelClassName}Service.getById(id));
        return "${modelConfiguration.pageLocation}/formUpdate";
    }

    @RequestMapping(value = "/views/list",method = {RequestMethod.GET, RequestMethod.POST})
    public String showListView(ModelMap modelMap) {
        modelMap.put("recordList", ${modelConfiguration.camelClassName}Service.findAllAsList());
        return "${modelConfiguration.pageLocation}/list";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Result save(${modelConfiguration.entityName} ${modelConfiguration.camelClassName})
            throws Exception {
        ${modelConfiguration.camelClassName}Service.save(${modelConfiguration.camelClassName});
        return Result.success(${modelConfiguration.camelClassName}.get${modelConfiguration.upperCamelKeyColName}());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteById(@PathVariable Integer id) throws Exception {
        ParamChecker.check(id == null || id < 1, CommonEnum.PARAM_INVALID);
        ${modelConfiguration.camelClassName}Service.deleteById(id);
        return Result.success();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateById(@PathVariable Integer id, ${modelConfiguration.entityName} ${modelConfiguration.camelClassName})
            throws Exception {
        ParamChecker.checkNotNull(id == null || id < 1, CommonEnum.PARAM_INVALID);
        ${modelConfiguration.camelClassName}.setId(id);
        ${modelConfiguration.camelClassName}Service.updateById(${modelConfiguration.camelClassName});
        return Result.success();
    }

}