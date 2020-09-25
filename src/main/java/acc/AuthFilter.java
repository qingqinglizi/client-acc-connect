package acc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: Lee
 * @Date 2020/6/12 16:47
 * @Description:
 */
@Component    //加上该注解，系统会初始化过滤器两次，先是@Component ，包括filterName、charset值是默认的，后是@WebFilter，值是自定义的
//@WebFilter(urlPatterns = "/*", /*通配符（*）表示对所有的web资源进行拦截*/
//    filterName = "权限过滤器",
//    /*DispatcherType是个枚举类型，有下面几个值:(默认DispatcherType.REQUEST)
//    FORWARD,//转发的
//    INCLUDE,//包含在页面的
//    REQUEST,//请求的
//    ASYNC,//异步的
//    ERROR;//出错的*/
//    dispatcherTypes =  {DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.ERROR},
//    initParams = {
//            @WebInitParam(name = "charset", value = "utf-8")
//    })
public class AuthFilter implements Filter {

    private String charset;

    private String filterName;

    private String accLoginUrl;

    private String accServiceUrl;

    public AuthFilter() {
        charset = "UTF-8";
        filterName = "acc filter";
    }

    public AuthFilter(String accLoginUrl, String accServiceUrl) {
        charset = "UTF-8";
        filterName = "acc filter";
        this.accLoginUrl = accLoginUrl;
        this.accServiceUrl = accServiceUrl;
    }

    public AuthFilter(String filterName, String charset, String accLoginUrl, String accServiceUrl) {
        this.charset = charset;
        this.filterName = filterName;
        this.accLoginUrl = accLoginUrl;
        this.accServiceUrl = accServiceUrl;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        filterName = filterConfig.getFilterName();
//        charset = filterConfig.getInitParameter("charset");
        System.out.println("filterName:" + filterName);
        System.out.println("charset:" + charset);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(filterName + "doFilter()");
        servletRequest.setCharacterEncoding(charset);
        servletResponse.setCharacterEncoding(charset);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (DealAuthTicket.getInstance().checkLoginInfo(accServiceUrl, request, response)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String url = accLoginUrl + "?service=" + request.getRequestURL();
            response.sendRedirect(url);
        }
    }

    @Override
    public void destroy() {
        System.out.println(filterName + "销毁");
    }

}
