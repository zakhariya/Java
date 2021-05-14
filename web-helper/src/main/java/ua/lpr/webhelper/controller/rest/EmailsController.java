package ua.lpr.webhelper.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
public class EmailsController {

//    @Autowired
//    private SiteService siteService;
//
//
//    @Autowired
//    private HttpServletRequest request;
//
//    @PostMapping("/get-list")
//    public ResponseEntity<Result> getEmails(@RequestBody Set<String> urls) {
//        Result result = null;
//
//        try {
//            result = siteService.getEmailsListFromPages(urls);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return new ResponseEntity(result, HttpStatus.OK);
//    }
}
