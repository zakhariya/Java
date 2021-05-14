package ua.lpr.webhelper.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comparator")
public class ComparatorController {

//    @Autowired
//    private SiteService siteService;
//
////    @Autowired
////    private Result result;
//
//    @PostMapping("/results")
//    public ResponseEntity getCompareResults(){//@RequestBody List<URL> urls
//
//        Set<String> urls = new HashSet<>();
//        List<String> selectors = new ArrayList<>();
//
//        //            urls.add(new URL("https://o7planning.org/ru/10399/jsoup-java-html-parser-tutorial"));
//        urls.add("https://lpr.ua/bejdzhi/bejdzhiki-plastikovye");
//        urls.add("https://api.lpr.ua");
////            urls.add(new URL("https://badges.com.ua"));
////            urls.add(new URL("https://luckyprint.ua"));
//
//        selectors.add("body");
//        selectors.add("body");
//
//        Result result = new Result();
//
//        try {
//            result = siteService.comparePages(urls, selectors);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return new ResponseEntity("Some result: " + (Math.random() * 10.0) + "\n" + result.getPagesWords(), HttpStatus.OK);
//    }
}
