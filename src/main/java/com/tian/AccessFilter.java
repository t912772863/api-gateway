package com.tian;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 通过继承ZuulFilter, 实现一个网关则的过滤器, 它通常可以用来实现权限过滤等功能, 防止不合法请求路由到具体的服务上
 *
 *
 *
 * Created by Administrator on 2018/8/1 0001.
 */
public class AccessFilter extends ZuulFilter {
    private static final Logger log = LoggerFactory.getLogger(AccessFilter.class);

    /**
     * 过滤器的类型, 它决定了过滤器在哪个生命周期被执行,
     * 这里的"pre"代表会在请求路由前执行.
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序, 当请求在一个阶段存在多个过滤器时, 需要根据该方法返回的值来依次执行.
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断该过滤器是否要执行. 这里我们直接返回一个true, 表示所有请求都经过该过滤器.
     * 实际应用中, 可以根据函数来指定过滤器的有效范围.
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑.
     * 这里通过ctx.setSendZuulResponse(false), 令zuul过滤该请求, 不进行路由.
     * 然后通过ctx.setResponseStatusCode(401) 设置了返回的错误码.也可以返回其它内容,
     * 比如，通过ctx.setResponseBody(body)对返回body内容进行编辑等。
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

        String token = request.getHeader("token");
        if(token == null){
            log.warn("access token is empty.");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }

        log.info("access token ok.");
        return null;
    }
}
