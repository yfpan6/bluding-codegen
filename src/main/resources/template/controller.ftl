/*
 * Copyright 2016-2017 the original author or authors.
 */
package ${controllerPkg};

import cn.udesk.insight.core.entity.${modelConfiguration.entityName};
import cn.udesk.insight.core.service.${modelConfiguration.entityName}Service;
import cn.udesk.sdk.common.ParamChecker;
import cn.udesk.sdk.common.bean.Paging;
import cn.udesk.sdk.common.bean.Result;
import cn.udesk.sdk.common.consts.CommonEnum;
import cn.udesk.sdk.orm.QueryByPagingParam;
import cn.udesk.sdk.util.bean.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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

    @RequestMapping
    public String index() throws Exception {
        return "${modelConfiguration.pageLocation}";
    }

    @RequestMapping("/query")
    @ResponseBody
    public Result query(@RequestParam Map<String, Object> paramMap) throws Exception {
        PagingQueryParam paging = PagingQueryParam.create(1,20).setCondition(paramMap);
        Paging<${modelConfiguration.entityName}> paging = ${modelConfiguration.camelClassName}Service.findAsPaging(paging);
        return Result.success(paging);
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result save(${modelConfiguration.entityName} ${modelConfiguration.camelClassName})
            throws Exception {
        ${modelConfiguration.camelClassName}Service.save(${modelConfiguration.camelClassName});
        return Result.success(${modelConfiguration.camelClassName}.get${modelConfiguration.upperCamelKeyColName}());
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Long id) throws Exception {
        ParamChecker.checkNotNull(id, CommonEnum.PARAM_IS_NULL);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("${modelConfiguration.camelKeyColName}", id);
        ${modelConfiguration.camelClassName}Service.delete(param);
        return Result.success();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(${modelConfiguration.entityName} ${modelConfiguration.camelClassName})
            throws Exception {
        ${modelConfiguration.camelClassName}Service.update(${modelConfiguration.camelClassName});
        return Result.success();
    }

}