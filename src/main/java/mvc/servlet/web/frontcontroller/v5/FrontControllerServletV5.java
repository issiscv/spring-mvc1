package mvc.servlet.web.frontcontroller.v5;

import mvc.servlet.web.frontcontroller.ModelView;
import mvc.servlet.web.frontcontroller.MyView;
import mvc.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import mvc.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import mvc.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import mvc.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import mvc.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import mvc.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import mvc.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import mvc.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    
    //핸들러 목록
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    //핸들러 어댑터 목록
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //핸들러 조회
       Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        MyHandlerAdapter adapter = getHandlerAdapter(handler);//어댑터 목록을 조회하며 처리가능한 어댑터 찾기
        ModelView mv = adapter.handle(request, response, handler);//어댑터가 핸들러 호출

        String viewName = mv.getViewName();
        MyView myView = viewResolver(viewName);
        
        myView.render(mv.getModel(), request,response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다." + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }
}
