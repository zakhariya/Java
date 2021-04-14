package ua.lpr.functions;

import org.springframework.web.servlet.ModelAndView;

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
}
