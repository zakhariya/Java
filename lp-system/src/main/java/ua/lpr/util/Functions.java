package ua.lpr.util;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class Functions {

    /**
     * retuns url (string)
     *
     * @param url
     * @param deep
     * @return
     */
    public static String getUrlByDeep(String url, int deep){

        if(url == null | (url != null && url.length() < 1))
            return "/";

        char[] c = url.toCharArray();
        int j = 0;
        for(int i=0; i<c.length; i++) {
            if(c[i] == '/')
                j++;
            if(j > deep + 1) {
                url = url.substring(0, i);
                break;
            }
        }

        return url;
    }

    public static ArrayList<String> fromStringToArray(String string, String separator){
        ArrayList<String> arr = new ArrayList<>();

        while(string.length() > 0) {
            int endIdx = string.indexOf(separator);

            if(endIdx < 0)
                endIdx = string.length();

            String n = string.substring(0, endIdx);

            if(n.length() > 0) {
                arr.add(n);
            } else {
                arr.add(string);

                break;
            }

            string = string.replaceFirst(n, "");
            string = string.replaceFirst(separator, "");

            n = "";
        }

        return arr;
    }

    public static ModelAndView errorPage(String message, String url){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("title", "Ошибка");
        modelAndView.addObject("message", message);
        modelAndView.addObject("url", url);

        modelAndView.setViewName("error-page");

        return modelAndView;
    }

    // Handling Proxies or Load Balancers
    public static String getClientIp(HttpServletRequest request) {
        //		String requestInfo = "Request host: " + request.getHeader("host")
//				+ "\nLocal name: " + request.getLocalName()
//				+ "\nLocal address: " + request.getLocalAddr()
//				+ "\nRemote user: " + request.getRemoteUser()
//				+ "\nRemote host: " + request.getRemoteHost()
//				+ "\nRemote address: " + request.getRemoteAddr()
//				+ "\nRemote port: " + request.getRemotePort();
//
//		System.out.println(requestInfo);


//        String xfHeader = request.getHeader("X-Forwarded-For");
//        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
//            return request.getRemoteAddr();
//        }
//        return xfHeader.split(",")[0];

        String remoteAddr = request.getHeader("X-Forwarded-For");

        // If header is empty, use the direct remote address
        if (remoteAddr == null || remoteAddr.isEmpty()) {
            remoteAddr = request.getRemoteAddr();
        } else {
            // X-Forwarded-For can be a comma-separated list; the first one is the client
            remoteAddr = remoteAddr.split(",")[0].trim();
        }

        return remoteAddr;
    }
/*
    //Spring WebFlux (Reactive)
    public Mono<String> getIp(ServerHttpRequest request) {
        String ip = request.getRemoteAddress().getAddress().getHostAddress();
        return Mono.just(ip);
    }
*/
}
