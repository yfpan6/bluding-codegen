/*
 * Copyright 2016-2017 the original author or authors.
 */
package ${controllerPkg};

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longooc.sdk.orm.PagingQueryParam;
import com.longooc.sdk.common.bean.Paging;
import com.longooc.sdk.common.bean.Result;

/**
 *
 * @author ${author}
 * @date ${today}
 */
@Controller
@RequestMapping(value="/${modelConfiguration.requestMapping}", method={RequestMethod.GET, RequestMethod.POST})
public class BrandController {

    @Resource
    private ${modelConfiguration.entityName}Service ${modelConfiguration.camelClassName}Service;

    @RequestMapping
    public String index() throws Exception {
        return "${modelConfiguration.pageLocation}";
    }

    @RequestMapping("/query")
    @ResponseBody
    public String query(@RequestParam Map<String, Object> paramMap) throws Exception {
        PagingQueryParam paging = PagingQueryParam.create(1,20).setCondition(paramMap);
        Paging<${modelConfiguration.entityName}> paging = ${modelConfiguration.camelClassName}Service.findAsPaging(paging);
        return Result.success(paging);
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(${modelConfiguration.entityName} ${modelConfiguration.camelClassName})
            throws Exception {
        ${modelConfiguration.camelClassName}Service.save(${modelConfiguration.camelClassName});
        return Result.success(${modelConfiguration.camelClassName}.get${modelConfiguration.upperCamelKeyColName}());
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(Long id) throws Exception {
        if (id == null) {
            return Result.fail("id is null");
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("${modelConfiguration.camelKeyColName}", id);
        ${modelConfiguration.camelClassName}Service.delete(param);
        return Result.success();
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(${modelConfiguration.entityName} ${modelConfiguration.camelClassName})
            throws Exception {
        ${modelConfiguration.camelClassName}Service.update(${modelConfiguration.camelClassName});
        return Result.success();
    }

}